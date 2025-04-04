package Board;

import java.util.Map;

public class Board4 implements BoardInterface{
    private Node startPoint; //시작점
    private Node endPoint;   // 끝 점
    private Map<Integer, Node > nodes;  // Map<key,Node>
    // 노드들을 HashMap으로 저장/ 상속 후 nodes = new HashMap<>();














    @Override
    public Node getStartPoint() {
        return startPoint;
    }

    @Override
    public void setStartPoint(Board.Node startPoint) {
        this.startPoint = startPoint;
    }

    @Override
    public Node getEndPoint() {
        return endPoint;
    }

    @Override
    public void setEndPoint(Board.Node endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public Map<Integer, Board.Node> getNodes() {
        return nodes;
    }

    @Override
    public void setNodes(Map<Integer, Board.Node> nodes) {
        this.nodes = nodes;
    }



}
