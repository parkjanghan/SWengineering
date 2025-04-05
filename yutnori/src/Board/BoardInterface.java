package Board;

import java.util.Map;

public interface BoardInterface {

    Node getStartPoint();

    void setStartPoint(Node startPoint);

    Node getEndPoint();

    void setEndPoint(Node endPoint);

    Map<Integer, Node> getNodes();

    void setNodes(Map<Integer, Node> nodes);
}
