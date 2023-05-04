public class SocialGraph implements IGraph{
    @Override
    public void init() {

    }

    @Override
    public int nodeNum() {
        return 0;
    }

    @Override
    public int edgeNum() {
        return 0;
    }

    @Override
    public Object getVertexInfo(int v) {
        return null;
    }

    @Override
    public void setVertexInfo(int v, Object info) {

    }

    @Override
    public void addEdge(int v, int u, double w) {

    }

    @Override
    public double getWeight(int v, int u) {
        return 0;
    }

    @Override
    public void removeEdge(int v, int u) {

    }

    @Override
    public boolean hasEdge(int v, int u) {
        return false;
    }

    @Override
    public int[] getAdj(int v) {
        return new int[0];
    }
}
