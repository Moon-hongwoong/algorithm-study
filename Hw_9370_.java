import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Hw_9370_ {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());
        for(int i = 0; i < T; i++){
            solution(br, bw);
            bw.write("\n");
        }
        bw.flush();
    }

    public static class Edge{
        int to;
        int weight;

        public Edge(int to, int weight){
            this.to = to;
            this.weight = weight;
        }
    }

    public static void solution(BufferedReader br, BufferedWriter bw) throws Exception{
        StringTokenizer st = null;
        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()); // ��� ����
        int m = Integer.parseInt(st.nextToken()); // ���� ����
        int t = Integer.parseInt(st.nextToken()); // ������ ��� ����

        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken()); // ���� ���
        int g = Integer.parseInt(st.nextToken()); // ������ ���
        int h = Integer.parseInt(st.nextToken()); // ������ ���

        ArrayList<Edge>[] adjs = new ArrayList[n+1]; // ���� ��� ����
        for(int i = 0; i <= n; i++){
            adjs[i] = new ArrayList<Edge>();
        }

        int GH = 0;
        // ���� ��� ���� ����
        for(int i = 0; i < m; i++){
           st = new StringTokenizer(br.readLine());
           int a = Integer.parseInt(st.nextToken());
           int b = Integer.parseInt(st.nextToken());
           int w = Integer.parseInt(st.nextToken());
           adjs[a].add(new Edge(b, w));
           adjs[b].add(new Edge(a, w));
           if((a == g && b == h)||(a == h && b == g)){
               GH = w;
           }
        }

        // ������ ��� ����
        int[] dest = new int[t];
        for(int i = 0; i < t; i++){
            dest[i] = Integer.parseInt(br.readLine());
        }

        // start���� ���������� ���� �׳� �ִܰŸ���, G-H ���ļ� ���� �ִܰŸ� ���ؼ� ��
        int[] distS = dijkstra(s, n, adjs);
        int[] distG = dijkstra(g, n, adjs);
        int[] distH = dijkstra(h, n, adjs);
        // start->g ����ġ + g->h ������ ����ġ + h->������ ����ġ == start -> ������ ����ġ
        // start->h ����ġ + h->g ������ ����ġ + g->������ ����ġ == start -> ������ ����ġ

        ArrayList<Integer> answer = new ArrayList<Integer>();
        for(int d : dest){
            int SD = distS[d];
            int SG = distS[g];
            int SH = distS[h];
            int HD = distH[d];
            int GD = distG[d];

            if((SD == SG + GH + HD) || (SD == SH + GH + GD)){
                answer.add(d);
            }
        }
        Collections.sort(answer);

        for(int ans : answer){
            bw.write(ans + " ");
        }

    }

    public static class Info {
        int node;
        int weight; // node ���� ���µ� �ʿ��ߴ� weight
        public Info(int node, int weight){
            this.node = node;
            this.weight = weight;
        }
    }

    public static int[] dijkstra(int start, int n, ArrayList<Edge>[] adjs){

        // �ִ� �Ÿ� ������ �迭 ����
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<Info> pq = new PriorityQueue<Info>(new Comparator<Info>(){
           @Override
           public int compare(Info i1, Info i2){
               return i1.weight - i2.weight;
           }
        });

        // Start ���� �ֱ�
        dist[start] = 0;
        pq.offer(new Info(start, 0));

        // ���ͽ�Ʈ�� �˰��� ����
        while(!pq.isEmpty()){
            Info info = pq.poll();
            if(info.weight > dist[info.node]) continue;
            // ���� ����� ������� ������ ��ȸ�Ѵ�.
            for(Edge adj : adjs[info.node]){
                // �Ϲ� �ִ� �Ÿ� ����
                if(dist[adj.to] > adj.weight + info.weight){
                    dist[adj.to] = info.weight + adj.weight;
                    pq.offer(new Info(adj.to, dist[adj.to]));
                }

            }
        }
        return dist;
    }
}