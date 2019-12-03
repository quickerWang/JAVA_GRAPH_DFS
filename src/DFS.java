import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DFS {
    private int time ;
    public void DFS(Graph graph){
        Vertex[] V = graph.getVertex();
        for(Vertex u:V){
            u.setC("white");
            u.setP(null);
        }
        time=0;
        for(Vertex u:V){
            if(u.getC().equals("white")){
                DFS_visit(graph,u);
            }
        }
    }
    private void DFS_visit(Graph G,Vertex u){
        System.out.print(u.getId()+" ");
        u.setDt(++time);
        u.setC("gray");
        List<Edge> edges = G.getEdges(u.getId());
        for(Edge edge:edges){
            Vertex v = G.getVertex(edge.getEnd());
            if(v.getC().equals("white")) {
                v.setP(u);
                DFS_visit(G, v);
            }
        }
        u.setC("black");
        u.setFt(++time);
    }
}

//图边
class Edge{
    private int start;
    private int end;
    private int value;
    Edge nextEdge;

    public Edge(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Edge() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Edge getNextEdge() {
        return nextEdge;
    }

    public void setNextEdge(Edge nextEdge) {
        this.nextEdge = nextEdge;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}

//图结点
class Vertex{
    private int id; //编号
    private String c; //write,gray,black
    private int d;  //距离
    private Vertex p;
    private int dt; //时间戳
    private int ft; //时间戳
    Edge first = new Edge();



    public Edge getFirst() {
        return first;
    }

    public void setFirst(Edge first) {
        this.first = first;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getFt() {
        return ft;
    }

    public void setFt(int ft) {
        this.ft = ft;
    }
    public Vertex(String c, int d, Vertex p) {
        this.c = c;
        this.d = d;
        this.p = p;
    }

    public Vertex(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public Vertex getP() {
        return p;
    }

    public void setP(Vertex p) {
        this.p = p;
    }
}

//定义图结构
class Graph {
    //定义一下的属性变量
    Vertex[] u;//定义顶点数组
    int e; //边数
    int v; //顶点数

    //图类的构造函数
    public Graph(int v,int e) {
        this.v = v;
        this.e =e;
        u = new Vertex[v];
        for(int i=0;i<v;i++){
            u[i] =new Vertex(i);
        }
    }


    //返回边数组
    public List<Edge> getEdges(int u){
        List<Edge> edges = new LinkedList<>();
        Vertex vertex = this.u[u];
        Edge edge = vertex.getFirst();
        edges.add(vertex.getFirst());
        while(edge.getNextEdge()!=null) {
            edge=edge.getNextEdge();
            edges.add(edge);
        }
        return edges;
    }

    //创建vertex的set get方法
    public Vertex[] getVertex() {
        return u;
    }

    public Vertex getVertex(int id){
        return u[id];
    }




    //---主函数部分
    public static void main(String[] args) throws FileNotFoundException {
        CreateGraph.createGraph();
        Graph graph = CreateGraph.getGraph();
        CreateGraph.outputGraph();
        System.out.println("DFS遍历结果：");
        new DFS().DFS(graph);
    }
}

class CreateGraph {
    private static Graph G;
    public static Graph getGraph(){
        return G;
    }
    public static void createGraph() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入顶点数v和边数e:");
        int v = sc.nextInt();
        int e = sc.nextInt();
        G = new Graph(v, e);
        for (int i = 0; i < G.v; i++) {
            G.u[i].first = null; // 不可或缺
        }
        System.out.println("请输入各边信息(用空格隔开):");
        for (int i = 0; i < G.e; i++) {
            Edge en1 = new Edge();
            // 保证e1,e2都是合法输入
            int e1 = sc.nextInt();
            int e2 = sc.nextInt();
            en1.setStart(e1);
            en1.setEnd(e2);
            en1.setValue(1);
            if(G.u[e1].first==null){
                G.u[e1].first = en1;
            }else{
                Edge edge = G.u[e1].first;
                while(edge.nextEdge!=null)
                    edge =edge.nextEdge;
                edge.nextEdge =en1;
            }

            Edge en2 = new Edge();
            en2.setStart(e2);
            en2.setEnd(e1);
            en2.setValue(1);
            if(G.u[e2].first==null){
                G.u[e2].first = en2;
            }else{
                Edge edge = G.u[e2].first;
                while(edge.nextEdge!=null)
                    edge =edge.nextEdge;
                edge.nextEdge =en2;
            }
        }
    }
    public static void outputGraph() {
            System.out.println("输出邻接表存储情况：");
            for (int i = 0; i < G.v; i++) {
                System.out.print(i);
                Edge en = G.u[i].first;
                while (en != null) {
                    //System.out.println("Edge:"+en.getStart()+" "+en.getEnd());
                    System.out.print("->" + en.getEnd());
                    en = en.nextEdge;
                }
                System.out.println();
            }
    }
}

