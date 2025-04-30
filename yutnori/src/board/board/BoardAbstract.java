package board.board;

import play.YutResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BoardAbstract implements BoardInterface {
    public Map<Integer, Node> boardShape;

    public BoardAbstract()
    {
        this.boardShape = new HashMap<>();

    }




    @Override
    public int getKey(int key) {
        return boardShape.get(key).getKey();
    }

    @Override
    public ArrayList<Integer> getNext_nodes_board(int key, YutResult yut_result) {
        Node node = boardShape.get(key);
        if (node == null) return new ArrayList<>(); // 노드가 존재하지 않을 경우 예외 방지
        return node.getNext_nodes(yut_result);
    }
}