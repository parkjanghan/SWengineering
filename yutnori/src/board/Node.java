package board;

import play.Mal;
import play.YutResult;

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
    private Map<YutResult, ArrayList<Integer>> next_nodes; //<윷 결과, 이동 가능 노드>

    private boolean occupied;
    private ArrayList<Mal> occupyingPieces; //<사용자 id, Mal Id>
    private int numOfOccupyingPieces;

    public Node(int key, int x_pos, int y_pos, boolean isStartPoint, boolean isEndPoint, boolean isCenter, boolean isCorner) {
        this.key = key;
        this.x_pos = x_pos;
        this.y_pos = y_pos;

        this.isStartPoint = isStartPoint;
        this.isEndPoint = isEndPoint;
        this.isCenter = isCenter;
        this.isCorner = isCorner;
        this.occupied = false;
        this.occupyingPieces = new ArrayList<>();
        this.numOfOccupyingPieces = 0;

        next_nodes = new HashMap<YutResult, ArrayList<Integer>>();
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
    public ArrayList<Integer> getNext_nodes(YutResult yut_result) {
        return  next_nodes.get(yut_result);
    }
    public void setNext_nodes(Map<YutResult, ArrayList<Integer>> next_nodes) {
        this.next_nodes = next_nodes;
    }
    public void addNext_nodes(YutResult yut_result, ArrayList<Integer> next_node) {
        this.next_nodes.put(yut_result, next_node);
    }

    public boolean isOccupied() {
        return occupied;
    }
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    public void resetOccupied() {
        this.occupied = false;
        this.occupyingPieces.clear();
        this.numOfOccupyingPieces = 0;
    }

    public ArrayList<Mal> getOccupyingPieces() {
        return occupyingPieces;
    }

    public void addOccupyingPiece(int playerId, Mal mal) {

        occupyingPieces.add(mal);

        this.occupied = true;
        this.numOfOccupyingPieces++;
    }

    public void clearOccupyingPieces() {
        this.occupyingPieces.clear();
        this.numOfOccupyingPieces = 0;
    }

    public int getNumOfOccupyingPieces()
    {
        return this.occupyingPieces.size();
    }
    public void resetNumOfOccupyingPieces() {
        this.numOfOccupyingPieces = 0;
    }
    public void resetNode()
    {
        this.occupied = false;
        this.occupyingPieces.clear();
        this.numOfOccupyingPieces = 0;
    }

    public boolean isEndPoint() {
        return isEndPoint;
    }
}
