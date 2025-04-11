package board.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;



class Board4Test {

    @Test
    void getNext_nodes() {
        Board4 board4 = new Board4();
        ArrayList<Integer> test_array1 = new ArrayList<>();
        test_array1.add(21);
        test_array1.add(7);
        Assertions.assertEquals(test_array1, board4.getNext_nodes_board(6, 1));


        ArrayList<Integer> test_array2 = new ArrayList<>();
        test_array2.add(28);
        test_array2.add(24);
        Assertions.assertEquals(test_array2, board4.getNext_nodes_board(29, 2));

        ArrayList<Integer> test_array3 = new ArrayList<>();
        test_array3.add(28);
        test_array3.add(16);
        Assertions.assertEquals(test_array3, board4.getNext_nodes_board(11, 5));

        ArrayList<Integer> test_array4 = new ArrayList<>();
        test_array4.add(3);

        Assertions.assertEquals(test_array4, board4.getNext_nodes_board(4, 0));
    }
}