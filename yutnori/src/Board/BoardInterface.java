package Board;

import java.util.Map;

public interface BoardInterface {

    Node getStartPoint();

    void setStartPoint(Node startPoint);

    Node getEndPoint();

    void setEndPoint(Node endPoint);

    Map<Integer, Node> getNodes();

    void setNodes(Map<Integer, Node> nodes);

    void createNode(int key, int x_pos, int y_pos, boolean b, boolean b1, boolean b2, boolean b3);

    void createConnection(int from_key, int to_key );

}
