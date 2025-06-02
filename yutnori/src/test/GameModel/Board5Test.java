package test.GameModel;

import board.Board5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import play.YutResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Board5Test {
    private Board5 board;

    @BeforeEach
    void setUp() {
        board = new Board5();
    }

    @Test

    void testStartingNodes() {
        // Test all starting nodes have the same connections
        int[] startingNodes = {0, -1, -2, -3};

        for (int startNode : startingNodes) {
            assertEquals(List.of(25, 27), board.getNext_nodes_board(startNode, YutResult.BACK_DO));
            assertEquals(List.of(2), board.getNext_nodes_board(startNode, YutResult.DO));
            assertEquals(List.of(3), board.getNext_nodes_board(startNode, YutResult.GAE));
            assertEquals(List.of(4), board.getNext_nodes_board(startNode, YutResult.GEOL));
            assertEquals(List.of(5), board.getNext_nodes_board(startNode, YutResult.YUT));
            assertEquals(List.of(6), board.getNext_nodes_board(startNode, YutResult.MO));
        }
    }

    @Test

    void testNode1() {
        assertEquals(List.of(25, 27), board.getNext_nodes_board(1, YutResult.BACK_DO));
        assertEquals(List.of(37), board.getNext_nodes_board(1, YutResult.DO));
        assertEquals(List.of(37), board.getNext_nodes_board(1, YutResult.GAE));
        assertEquals(List.of(37), board.getNext_nodes_board(1, YutResult.GEOL));
        assertEquals(List.of(37), board.getNext_nodes_board(1, YutResult.YUT));
        assertEquals(List.of(37), board.getNext_nodes_board(1, YutResult.MO));
    }

    @Test

    void testgeneralNode() {
        // Test nodes 2-5
        loopTestforNodes(2, 5);
        // Test nodes 7-10
        loopTestforNodes(7, 10);
        // Test nodes 12-15
        loopTestforNodes(12, 15);
        // Test nodes 17-20
        loopTestforNodes(17, 20);
    }

    private void loopTestforNodes(int start, int end) {
        for (int i = start; i <= end; i++) {
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
        assertEquals(List.of(28, 7), board.getNext_nodes_board(6, YutResult.DO));
        assertEquals(List.of(29, 8), board.getNext_nodes_board(6, YutResult.GAE));
        assertEquals(List.of(36, 9), board.getNext_nodes_board(6, YutResult.GEOL));
        assertEquals(List.of(34, 10), board.getNext_nodes_board(6, YutResult.YUT));
        assertEquals(List.of(35, 11), board.getNext_nodes_board(6, YutResult.MO));
    }

    @Test

    void testNode11() {
        assertEquals(List.of(10), board.getNext_nodes_board(11, YutResult.BACK_DO));
        assertEquals(List.of(30, 12), board.getNext_nodes_board(11, YutResult.DO));
        assertEquals(List.of(31, 13), board.getNext_nodes_board(11, YutResult.GAE));
        assertEquals(List.of(36, 14), board.getNext_nodes_board(11, YutResult.GEOL));
        assertEquals(List.of(34, 15), board.getNext_nodes_board(11, YutResult.YUT));
        assertEquals(List.of(35, 16), board.getNext_nodes_board(11, YutResult.MO));
    }

    @Test
    void testNode16() {
        assertEquals(List.of(15), board.getNext_nodes_board(16, YutResult.BACK_DO));
        assertEquals(List.of(32, 17), board.getNext_nodes_board(16, YutResult.DO));
        assertEquals(List.of(33, 18), board.getNext_nodes_board(16, YutResult.GAE));
        assertEquals(List.of(36, 19), board.getNext_nodes_board(16, YutResult.GEOL));
        assertEquals(List.of(34, 20), board.getNext_nodes_board(16, YutResult.YUT));
        assertEquals(List.of(35, 21), board.getNext_nodes_board(16, YutResult.MO));
    }

    @Test
    void testNode21() {
        assertEquals(List.of(20, 35), board.getNext_nodes_board(21, YutResult.BACK_DO));
        assertEquals(List.of(22), board.getNext_nodes_board(21, YutResult.DO));
        assertEquals(List.of(23), board.getNext_nodes_board(21, YutResult.GAE));
        assertEquals(List.of(24), board.getNext_nodes_board(21, YutResult.GEOL));
        assertEquals(List.of(25), board.getNext_nodes_board(21, YutResult.YUT));
        assertEquals(List.of(1), board.getNext_nodes_board(21, YutResult.MO));
    }

    @Test
    void testEnding() {
        // Node 22
        assertEquals(List.of(21), board.getNext_nodes_board(22, YutResult.BACK_DO));
        assertEquals(List.of(23), board.getNext_nodes_board(22, YutResult.DO));
        assertEquals(List.of(24), board.getNext_nodes_board(22, YutResult.GAE));
        assertEquals(List.of(25), board.getNext_nodes_board(22, YutResult.GEOL));
        assertEquals(List.of(1), board.getNext_nodes_board(22, YutResult.YUT));
        assertEquals(List.of(37), board.getNext_nodes_board(22, YutResult.MO));

        // Node 23
        assertEquals(List.of(22), board.getNext_nodes_board(23, YutResult.BACK_DO));
        assertEquals(List.of(24), board.getNext_nodes_board(23, YutResult.DO));
        assertEquals(List.of(25), board.getNext_nodes_board(23, YutResult.GAE));
        assertEquals(List.of(1), board.getNext_nodes_board(23, YutResult.GEOL));
        assertEquals(List.of(37), board.getNext_nodes_board(23, YutResult.YUT));
        assertEquals(List.of(37), board.getNext_nodes_board(23, YutResult.MO));

        // Node 24
        assertEquals(List.of(23), board.getNext_nodes_board(24, YutResult.BACK_DO));
        assertEquals(List.of(25), board.getNext_nodes_board(24, YutResult.DO));
        assertEquals(List.of(1), board.getNext_nodes_board(24, YutResult.GAE));
        assertEquals(List.of(37), board.getNext_nodes_board(24, YutResult.GEOL));
        assertEquals(List.of(37), board.getNext_nodes_board(24, YutResult.YUT));
        assertEquals(List.of(37), board.getNext_nodes_board(24, YutResult.MO));

        // Node 25
        assertEquals(List.of(24), board.getNext_nodes_board(25, YutResult.BACK_DO));
        assertEquals(List.of(1), board.getNext_nodes_board(25, YutResult.DO));
        assertEquals(List.of(37), board.getNext_nodes_board(25, YutResult.GAE));
        assertEquals(List.of(37), board.getNext_nodes_board(25, YutResult.GEOL));
        assertEquals(List.of(37), board.getNext_nodes_board(25, YutResult.YUT));
        assertEquals(List.of(37), board.getNext_nodes_board(25, YutResult.MO));
    }

    @Test

    void testDiagonal() {
        // Node 28
        assertEquals(List.of(6), board.getNext_nodes_board(28, YutResult.BACK_DO));
        assertEquals(List.of(29), board.getNext_nodes_board(28, YutResult.DO));
        assertEquals(List.of(36), board.getNext_nodes_board(28, YutResult.GAE));
        assertEquals(List.of(34), board.getNext_nodes_board(28, YutResult.GEOL));
        assertEquals(List.of(35), board.getNext_nodes_board(28, YutResult.YUT));
        assertEquals(List.of(21), board.getNext_nodes_board(28, YutResult.MO));

        // Node 29
        assertEquals(List.of(28), board.getNext_nodes_board(29, YutResult.BACK_DO));
        assertEquals(List.of(36), board.getNext_nodes_board(29, YutResult.DO));
        assertEquals(List.of(34), board.getNext_nodes_board(29, YutResult.GAE));
        assertEquals(List.of(35), board.getNext_nodes_board(29, YutResult.GEOL));
        assertEquals(List.of(21), board.getNext_nodes_board(29, YutResult.YUT));
        assertEquals(List.of(22), board.getNext_nodes_board(29, YutResult.MO));

        // Test other diagonal nodes (30-36)
        testDiagonalNode(30, 11, 31, 36, 34, 35, 21);
        testDiagonalNode(31, 30, 36, 34, 35, 21, 22);
        testDiagonalNode(32, 16, 33, 36, 34, 35, 21);
        testDiagonalNode(33, 32, 36, 34, 35, 21, 22);

        // Node 34
        assertEquals(List.of(36), board.getNext_nodes_board(34, YutResult.BACK_DO));
        assertEquals(List.of(35), board.getNext_nodes_board(34, YutResult.DO));
        assertEquals(List.of(21), board.getNext_nodes_board(34, YutResult.GAE));
        assertEquals(List.of(22), board.getNext_nodes_board(34, YutResult.GEOL));
        assertEquals(List.of(23), board.getNext_nodes_board(34, YutResult.YUT));
        assertEquals(List.of(24), board.getNext_nodes_board(34, YutResult.MO));

        // Node 35
        assertEquals(List.of(34), board.getNext_nodes_board(35, YutResult.BACK_DO));
        assertEquals(List.of(21), board.getNext_nodes_board(35, YutResult.DO));
        assertEquals(List.of(22), board.getNext_nodes_board(35, YutResult.GAE));
        assertEquals(List.of(23), board.getNext_nodes_board(35, YutResult.GEOL));
        assertEquals(List.of(24), board.getNext_nodes_board(35, YutResult.YUT));
        assertEquals(List.of(25), board.getNext_nodes_board(35, YutResult.MO));

        // Node 36 (center)
        assertEquals(List.of(31), board.getNext_nodes_board(36, YutResult.BACK_DO));
        assertEquals(List.of(26), board.getNext_nodes_board(36, YutResult.DO));
        assertEquals(List.of(27), board.getNext_nodes_board(36, YutResult.GAE));
        assertEquals(List.of(1), board.getNext_nodes_board(36, YutResult.GEOL));
        assertEquals(List.of(37), board.getNext_nodes_board(36, YutResult.YUT));
        assertEquals(List.of(37), board.getNext_nodes_board(36, YutResult.MO));
    }

    private void testDiagonalNode(int nodeId, int backDo, int doNext, int gae, int geol, int yut, int mo) {
        assertEquals(List.of(backDo), board.getNext_nodes_board(nodeId, YutResult.BACK_DO));
        assertEquals(List.of(doNext), board.getNext_nodes_board(nodeId, YutResult.DO));
        assertEquals(List.of(gae), board.getNext_nodes_board(nodeId, YutResult.GAE));
        assertEquals(List.of(geol), board.getNext_nodes_board(nodeId, YutResult.GEOL));
        assertEquals(List.of(yut), board.getNext_nodes_board(nodeId, YutResult.YUT));
        assertEquals(List.of(mo), board.getNext_nodes_board(nodeId, YutResult.MO));
    }

    @Test

    void testFinalNodes() {
        // Node 26
        assertEquals(List.of(36), board.getNext_nodes_board(26, YutResult.BACK_DO));
        assertEquals(List.of(27), board.getNext_nodes_board(26, YutResult.DO));
        assertEquals(List.of(1), board.getNext_nodes_board(26, YutResult.GAE));
        assertEquals(List.of(37), board.getNext_nodes_board(26, YutResult.GEOL));
        assertEquals(List.of(37), board.getNext_nodes_board(26, YutResult.YUT));
        assertEquals(List.of(37), board.getNext_nodes_board(26, YutResult.MO));

        // Node 27
        assertEquals(List.of(26), board.getNext_nodes_board(27, YutResult.BACK_DO));
        assertEquals(List.of(1), board.getNext_nodes_board(27, YutResult.DO));
        assertEquals(List.of(37), board.getNext_nodes_board(27, YutResult.GAE));
        assertEquals(List.of(37), board.getNext_nodes_board(27, YutResult.GEOL));
        assertEquals(List.of(37), board.getNext_nodes_board(27, YutResult.YUT));
        assertEquals(List.of(37), board.getNext_nodes_board(27, YutResult.MO));
    }


}
