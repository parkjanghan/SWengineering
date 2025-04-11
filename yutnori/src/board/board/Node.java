package board.board;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

public class Node {

    public int key;
    int x_pos;
    int y_pos;
    private final boolean isStartPoint;
    private final boolean isEndPoint;

    private final boolean isCenter; //가운데 있는 점인지?
    private final boolean isCorner; //방향이 갈리는 점인지?
    private Map<Integer, ArrayList<Integer>> next_nodes; //<윷 결과, 이동 가능 노드>

    public Node(int key, int x_pos, int y_pos, boolean isStartPoint, boolean isEndPoint, boolean isCenter, boolean isCorner) {
        this.key = key;
        this.x_pos = x_pos;
        this.y_pos = y_pos;

        this.isStartPoint = isStartPoint;
        this.isEndPoint = isEndPoint;
        this.isCenter = isCenter;
        this.isCorner = isCorner;
        next_nodes = new HashMap<>();
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
    public ArrayList<Integer> getNext_nodes(int yut_result) {
        return  next_nodes.get(yut_result);
    }
    public void setNext_nodes(Map<Integer, ArrayList<Integer>> next_nodes) {
        this.next_nodes = next_nodes;
    }
    public void addNext_nodes(int yut_result, ArrayList<Integer> next_node) {
        this.next_nodes.put(yut_result, next_node);
    }
}
