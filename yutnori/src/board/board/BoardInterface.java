package board.board;

import java.util.ArrayList;

public interface BoardInterface {


    public int getKey();
    public void setKey(int key);
    public void setNext_nodes(int key, int next_node);



    ArrayList<Integer> getNext_nodes_board(int key, int yut_result);

    public void addNext_nodes(int key, int next_node);
    public void removeNext_nodes(int key, int next_node);
    public boolean isEmpty();
    public boolean isFull();
}
