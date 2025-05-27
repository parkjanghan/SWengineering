package GameModel;

import board.Board4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import play.YutResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Board4Test {

    private Board4 board;

    @BeforeEach
    void setUp() {
        board = new Board4();
    }

    @Test
    void testStartNode() {
        int[] startNodes = {0, -1, -2, -3};
        for (int startNode : startNodes) {
            assertEquals(List.of(20), board.getNext_nodes_board(startNode, YutResult.BACK_DO));
            assertEquals(List.of(2), board.getNext_nodes_board(startNode, YutResult.DO));
            assertEquals(List.of(3), board.getNext_nodes_board(startNode, YutResult.GAE));
            assertEquals(List.of(4), board.getNext_nodes_board(startNode, YutResult.GEOL));
            assertEquals(List.of(5), board.getNext_nodes_board(startNode, YutResult.YUT));
            assertEquals(List.of(6), board.getNext_nodes_board(startNode, YutResult.MO));
        }
    }

    @Test
    void testNode1EndNode() {
        assertEquals(List.of(20), board.getNext_nodes_board(1, YutResult.BACK_DO));
        assertEquals(List.of(30), board.getNext_nodes_board(1, YutResult.DO));
        assertEquals(List.of(30), board.getNext_nodes_board(1, YutResult.GAE));
        assertEquals(List.of(30), board.getNext_nodes_board(1, YutResult.GEOL));
        assertEquals(List.of(30), board.getNext_nodes_board(1, YutResult.YUT));
        assertEquals(List.of(30), board.getNext_nodes_board(1, YutResult.MO));
    }

    @Test
    void testSideNodes() {
        for (int i = 2; i <= 5; i++) {
            assertEquals(List.of(i - 1), board.getNext_nodes_board(i, YutResult.BACK_DO));
            assertEquals(List.of(i + 1), board.getNext_nodes_board(i, YutResult.DO));
            assertEquals(List.of(i + 2), board.getNext_nodes_board(i, YutResult.GAE));
            assertEquals(List.of(i + 3), board.getNext_nodes_board(i, YutResult.GEOL));
            assertEquals(List.of(i + 4), board.getNext_nodes_board(i, YutResult.YUT));
            assertEquals(List.of(i + 5), board.getNext_nodes_board(i, YutResult.MO));
        }
    }

    @Test
    void testNode6() {
        assertEquals(List.of(5), board.getNext_nodes_board(6, YutResult.BACK_DO));
        assertEquals(List.of(21, 7), board.getNext_nodes_board(6, YutResult.DO));
        assertEquals(List.of(22, 8), board.getNext_nodes_board(6, YutResult.GAE));
        assertEquals(List.of(29, 9), board.getNext_nodes_board(6, YutResult.GEOL));
        assertEquals(List.of(23, 10), board.getNext_nodes_board(6, YutResult.YUT));
        assertEquals(List.of(24, 11), board.getNext_nodes_board(6, YutResult.MO));
    }

    @Test
    void testNode11() {
        assertEquals(List.of(10), board.getNext_nodes_board(11, YutResult.BACK_DO));
        assertEquals(List.of(25, 12), board.getNext_nodes_board(11, YutResult.DO));
        assertEquals(List.of(26, 13), board.getNext_nodes_board(11, YutResult.GAE));
        assertEquals(List.of(29, 14), board.getNext_nodes_board(11, YutResult.GEOL));
        assertEquals(List.of(27, 15), board.getNext_nodes_board(11, YutResult.YUT));
        assertEquals(List.of(28, 16), board.getNext_nodes_board(11, YutResult.MO));
    }

    @Test
    void testNode78910() {
        for (int i = 7; i <= 10; i++) {
            assertEquals(List.of(i - 1), board.getNext_nodes_board(i, YutResult.BACK_DO));
            assertEquals(List.of(i + 1), board.getNext_nodes_board(i, YutResult.DO));
            assertEquals(List.of(i + 2), board.getNext_nodes_board(i, YutResult.GAE));
            assertEquals(List.of(i + 3), board.getNext_nodes_board(i, YutResult.GEOL));
            assertEquals(List.of(i + 4), board.getNext_nodes_board(i, YutResult.YUT));
            assertEquals(List.of(i + 5), board.getNext_nodes_board(i, YutResult.MO));
        }
    }

    @Test
    void testNode12to15() {
        for (int i = 12; i <= 15; i++) {
            assertEquals(List.of(i - 1), board.getNext_nodes_board(i, YutResult.BACK_DO));
            assertEquals(List.of(i + 1), board.getNext_nodes_board(i, YutResult.DO));
            assertEquals(List.of(i + 2), board.getNext_nodes_board(i, YutResult.GAE));
            assertEquals(List.of(i + 3), board.getNext_nodes_board(i, YutResult.GEOL));
            assertEquals(List.of(i + 4), board.getNext_nodes_board(i, YutResult.YUT));
            assertEquals(List.of(i + 5), board.getNext_nodes_board(i, YutResult.MO));
        }
    }

    @Test
    void testEnd16to20() {
        for (int i = 16; i <= 20; i++) {
            assertEquals(List.of(i - 1), board.getNext_nodes_board(i, YutResult.BACK_DO));
            if (i == 20) {
                assertEquals(List.of(1), board.getNext_nodes_board(i, YutResult.DO));
                assertEquals(List.of(30), board.getNext_nodes_board(i, YutResult.GAE));
                assertEquals(List.of(30), board.getNext_nodes_board(i, YutResult.GEOL));
                assertEquals(List.of(30), board.getNext_nodes_board(i, YutResult.YUT));
                assertEquals(List.of(30), board.getNext_nodes_board(i, YutResult.MO));
            } else if (i == 19) {
                assertEquals(List.of(20), board.getNext_nodes_board(i, YutResult.DO));
                assertEquals(List.of(1), board.getNext_nodes_board(i, YutResult.GAE));
                assertEquals(List.of(30), board.getNext_nodes_board(i, YutResult.GEOL));
                assertEquals(List.of(30), board.getNext_nodes_board(i, YutResult.YUT));
                assertEquals(List.of(30), board.getNext_nodes_board(i, YutResult.MO));
            } else {
                assertEquals(List.of(i + 1), board.getNext_nodes_board(i, YutResult.DO));
                if (i + 2 <= 20) {
                    assertEquals(List.of(i + 2), board.getNext_nodes_board(i, YutResult.GAE));
                } else if (i + 2 == 21) {
                    assertEquals(List.of(1), board.getNext_nodes_board(i, YutResult.GAE));
                } else {
                    assertEquals(List.of(30), board.getNext_nodes_board(i, YutResult.GAE));
                }
            }
        }
    }

    @Test
    void testNode21to22()
        {
        assertEquals(List.of(20), board.getNext_nodes_board(21, YutResult.BACK_DO));
        assertEquals(List.of(22), board.getNext_nodes_board(21, YutResult.DO));
        assertEquals(List.of(29), board.getNext_nodes_board(21, YutResult.GAE));
        assertEquals(List.of(23), board.getNext_nodes_board(21, YutResult.GEOL));
        assertEquals(List.of(24), board.getNext_nodes_board(21, YutResult.YUT));
        assertEquals(List.of(16), board.getNext_nodes_board(21, YutResult.MO));

        assertEquals(List.of(21), board.getNext_nodes_board(22, YutResult.BACK_DO));
        assertEquals(List.of(29), board.getNext_nodes_board(22, YutResult.DO));
        assertEquals(List.of(23), board.getNext_nodes_board(22, YutResult.GAE));
        assertEquals(List.of(24), board.getNext_nodes_board(22, YutResult.GEOL));
        assertEquals(List.of(16), board.getNext_nodes_board(22, YutResult.YUT));
        assertEquals(List.of(17), board.getNext_nodes_board(22, YutResult.MO));
    }

    @Test
    void testCenterNode29() {
        assertTrue(board.getNext_nodes_board(29, YutResult.BACK_DO).isEmpty());
        assertEquals(List.of(23, 27), board.getNext_nodes_board(29, YutResult.DO));
        assertEquals(List.of(28, 24), board.getNext_nodes_board(29, YutResult.GAE));
        assertEquals(List.of(16, 1), board.getNext_nodes_board(29, YutResult.GEOL));
        assertEquals(List.of(30), board.getNext_nodes_board(29, YutResult.YUT));
        assertEquals(List.of(30), board.getNext_nodes_board(29, YutResult.MO));
    }

    @Test
    void testDiagonal25to28() {
        assertEquals(List.of(11), board.getNext_nodes_board(25, YutResult.BACK_DO));
        assertEquals(List.of(26), board.getNext_nodes_board(25, YutResult.DO));
        assertEquals(List.of(29), board.getNext_nodes_board(25, YutResult.GAE));
        assertEquals(List.of(27), board.getNext_nodes_board(25, YutResult.GEOL));
        assertEquals(List.of(28), board.getNext_nodes_board(25, YutResult.YUT));
        assertEquals(List.of(1), board.getNext_nodes_board(25, YutResult.MO));

        assertEquals(List.of(25), board.getNext_nodes_board(26, YutResult.BACK_DO));
        assertEquals(List.of(29), board.getNext_nodes_board(26, YutResult.DO));
        assertEquals(List.of(27), board.getNext_nodes_board(26, YutResult.GAE));
        assertEquals(List.of(28), board.getNext_nodes_board(26, YutResult.GEOL));
        assertEquals(List.of(1), board.getNext_nodes_board(26, YutResult.YUT));
        assertEquals(List.of(30), board.getNext_nodes_board(26, YutResult.MO));
    }

    @Test
    void test27to28() {
        assertEquals(List.of(29), board.getNext_nodes_board(27, YutResult.BACK_DO));
        assertEquals(List.of(28), board.getNext_nodes_board(27, YutResult.DO));
        assertEquals(List.of(1), board.getNext_nodes_board(27, YutResult.GAE));
        assertEquals(List.of(30), board.getNext_nodes_board(27, YutResult.GEOL));
        assertEquals(List.of(30), board.getNext_nodes_board(27, YutResult.YUT));
        assertEquals(List.of(30), board.getNext_nodes_board(27, YutResult.MO));

        assertEquals(List.of(27), board.getNext_nodes_board(28, YutResult.BACK_DO));
        assertEquals(List.of(1), board.getNext_nodes_board(28, YutResult.DO));
        assertEquals(List.of(30), board.getNext_nodes_board(28, YutResult.GAE));
        assertEquals(List.of(30), board.getNext_nodes_board(28, YutResult.GEOL));
        assertEquals(List.of(30), board.getNext_nodes_board(28, YutResult.YUT));
        assertEquals(List.of(30), board.getNext_nodes_board(28, YutResult.MO));
    }
}