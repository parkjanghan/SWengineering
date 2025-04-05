package Board;

import java.util.List;
import java.util.Map;

public abstract class BoardAbstract
{
    private Node startPoint; //시작점
    private Node endPoint;   // 끝 점
    private Map<Integer, Node > nodes;  // Map<key,Node>
    // 노드들을 HashMap으로 저장/ 상속 후 nodes = new HashMap<>();


    public Node getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Board.Node startPoint) {
        this.startPoint = startPoint;
    }

    public Node getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Board.Node endPoint) {
        this.endPoint = endPoint;
    }

    public Map<Integer, Board.Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Integer, Board.Node> nodes) {
        this.nodes = nodes;
    }


}
