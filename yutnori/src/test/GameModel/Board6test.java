package GameModel;

import board.Board6;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import play.YutResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Board6test {
    private Board6 board;

    @BeforeEach
    void setUp() {
        board = new Board6();
    }

    @Test
    void testBoardInitialization() {
        // Test that key nodes exist
        assertEquals(-1, board.getKey(-1));
        assertEquals(-2, board.getKey(-2));
        assertEquals(-3, board.getKey(-3));
        assertEquals(0, board.getKey(0));
        assertEquals(44, board.getKey(44));
    }

    @Test
    void testStart() {
        // Test BACK_DO (빽도) from starting node
        ArrayList<Integer> backDoResult = board.getNext_nodes_board(-1, YutResult.BACK_DO);
        assertEquals(List.of(30, 32), backDoResult);

        // Test DO (도) from starting node
        ArrayList<Integer> doResult = board.getNext_nodes_board(-1, YutResult.DO);
        assertEquals(List.of(2), doResult);

        // Test GAE (개) from starting node
        ArrayList<Integer> gaeResult = board.getNext_nodes_board(-1, YutResult.GAE);
        assertEquals(List.of(3), gaeResult);

        // Test GEOL (걸) from starting node
        ArrayList<Integer> geolResult = board.getNext_nodes_board(-1, YutResult.GEOL);
        assertEquals(List.of(4), geolResult);

        // Test YUT (윷) from starting node
        ArrayList<Integer> yutResult = board.getNext_nodes_board(-1, YutResult.YUT);
        assertEquals(List.of(5), yutResult);

        // Test MO (모) from starting node
        ArrayList<Integer> moResult = board.getNext_nodes_board(-1, YutResult.MO);
        assertEquals(List.of(6), moResult);
    }

    @Test
    void testStartingNode() {
        YutResult[] allResults = {YutResult.BACK_DO, YutResult.DO, YutResult.GAE,
                YutResult.GEOL, YutResult.YUT, YutResult.MO};

        for (YutResult result : allResults) {
            ArrayList<Integer> node1 = board.getNext_nodes_board(-1, result);
            ArrayList<Integer> node2 = board.getNext_nodes_board(-2, result);
            ArrayList<Integer> node3 = board.getNext_nodes_board(-3, result);
            ArrayList<Integer> node0 = board.getNext_nodes_board(0, result);

            assertEquals(node1, node2);
            assertEquals(node2, node3);
            assertEquals(node3, node0);
        }
    }

    @Test
    void testNode1() {
        // Node 1 should lead to end point (44) for all moves except BACK_DO
        ArrayList<Integer> backDoResult = board.getNext_nodes_board(1, YutResult.BACK_DO);
        assertEquals(List.of(30, 32), backDoResult);

        ArrayList<Integer> doResult = board.getNext_nodes_board(1, YutResult.DO);
        assertEquals(List.of(44), doResult);

        ArrayList<Integer> gaeResult = board.getNext_nodes_board(1, YutResult.GAE);
        assertEquals(List.of(44), gaeResult);

        ArrayList<Integer> geolResult = board.getNext_nodes_board(1, YutResult.GEOL);
        assertEquals(List.of(44), geolResult);

        ArrayList<Integer> yutResult = board.getNext_nodes_board(1, YutResult.YUT);
        assertEquals(List.of(44), yutResult);

        ArrayList<Integer> moResult = board.getNext_nodes_board(1, YutResult.MO);
        assertEquals(List.of(44), moResult);
    }

    @Test
    void testGeneralNodes() {
        // Test node 2 (should be sequential)
        ArrayList<Integer> backDoResult = board.getNext_nodes_board(2, YutResult.BACK_DO);
        assertEquals(List.of(1), backDoResult);

        ArrayList<Integer> doResult = board.getNext_nodes_board(2, YutResult.DO);
        assertEquals(List.of(3), doResult);

        ArrayList<Integer> gaeResult = board.getNext_nodes_board(2, YutResult.GAE);
        assertEquals(List.of(4), gaeResult);

        ArrayList<Integer> geolResult = board.getNext_nodes_board(2, YutResult.GEOL);
        assertEquals(List.of(5), geolResult);

        ArrayList<Integer> yutResult = board.getNext_nodes_board(2, YutResult.YUT);
        assertEquals(List.of(6), yutResult);

        ArrayList<Integer> moResult = board.getNext_nodes_board(2, YutResult.MO);
        assertEquals(List.of(7), moResult);
    }

    @Test
    void testNode6B() {
        ArrayList<Integer> backDoResult = board.getNext_nodes_board(6, YutResult.BACK_DO);
        assertEquals(List.of(5), backDoResult);

        ArrayList<Integer> doResult = board.getNext_nodes_board(6, YutResult.DO);
        assertEquals(List.of(33, 7), doResult);

        ArrayList<Integer> gaeResult = board.getNext_nodes_board(6, YutResult.GAE);
        assertEquals(List.of(34, 8), gaeResult);

        ArrayList<Integer> geolResult = board.getNext_nodes_board(6, YutResult.GEOL);
        assertEquals(List.of(43, 9), geolResult);

        ArrayList<Integer> yutResult = board.getNext_nodes_board(6, YutResult.YUT);
        assertEquals(List.of(41, 10), yutResult);

        ArrayList<Integer> moResult = board.getNext_nodes_board(6, YutResult.MO);
        assertEquals(List.of(42, 11), moResult);
    }

    @Test
    void testNode11() {
        ArrayList<Integer> backDoResult = board.getNext_nodes_board(11, YutResult.BACK_DO);
        assertEquals(List.of(10), backDoResult);

        ArrayList<Integer> doResult = board.getNext_nodes_board(11, YutResult.DO);
        assertEquals(List.of(35, 12), doResult);

        ArrayList<Integer> gaeResult = board.getNext_nodes_board(11, YutResult.GAE);
        assertEquals(List.of(36, 13), gaeResult);

        ArrayList<Integer> geolResult = board.getNext_nodes_board(11, YutResult.GEOL);
        assertEquals(List.of(43, 14), geolResult);

        ArrayList<Integer> yutResult = board.getNext_nodes_board(11, YutResult.YUT);
        assertEquals(List.of(41, 15), yutResult);

        ArrayList<Integer> moResult = board.getNext_nodes_board(11, YutResult.MO);
        assertEquals(List.of(42, 16), moResult);
    }

    @Test
    void testCorner() {
        // Test node 33 (corner shortcut)
        ArrayList<Integer> node33Do = board.getNext_nodes_board(33, YutResult.DO);
        assertEquals(List.of(34), node33Do);

        ArrayList<Integer> node33Gae = board.getNext_nodes_board(33, YutResult.GAE);
        assertEquals(List.of(43), node33Gae);

        // Test node 35 (corner shortcut)
        ArrayList<Integer> node35Do = board.getNext_nodes_board(35, YutResult.DO);
        assertEquals(List.of(36), node35Do);

        ArrayList<Integer> node35Gae = board.getNext_nodes_board(35, YutResult.GAE);
        assertEquals(List.of(43), node35Gae);
    }

    @Test
    void testEndGameNodes() {
        // Test node 31 (near end)
        ArrayList<Integer> node31Geol = board.getNext_nodes_board(31, YutResult.GEOL);
        assertEquals(List.of(44), node31Geol);

        ArrayList<Integer> node31Yut = board.getNext_nodes_board(31, YutResult.YUT);
        assertEquals(List.of(44), node31Yut);

        ArrayList<Integer> node31Mo = board.getNext_nodes_board(31, YutResult.MO);
        assertEquals(List.of(44), node31Mo);

        // Test node 32 (final node before end)
        ArrayList<Integer> node32Gae = board.getNext_nodes_board(32, YutResult.GAE);
        assertEquals(List.of(44), node32Gae);

        ArrayList<Integer> node32Geol = board.getNext_nodes_board(32, YutResult.GEOL);
        assertEquals(List.of(44), node32Geol);
    }

    @Test
    void testCenterNodes() {
        // Test node 41 (center area)
        ArrayList<Integer> node41Do = board.getNext_nodes_board(41, YutResult.DO);
        assertEquals(List.of(42), node41Do);

        ArrayList<Integer> node41Gae = board.getNext_nodes_board(41, YutResult.GAE);
        assertEquals(List.of(26), node41Gae);

        // Test node 42 (center area)
        ArrayList<Integer> node42Do = board.getNext_nodes_board(42, YutResult.DO);
        assertEquals(List.of(26), node42Do);

        ArrayList<Integer> node42Mo = board.getNext_nodes_board(42, YutResult.MO);
        assertEquals(List.of(30), node42Mo);

        // Test node 43 (center area)
        ArrayList<Integer> node43Do = board.getNext_nodes_board(43, YutResult.DO);
        assertEquals(List.of(31), node43Do);

        ArrayList<Integer> node43Geol = board.getNext_nodes_board(43, YutResult.GEOL);
        assertEquals(List.of(1), node43Geol);
    }

    @Test
    void testNonExistentNode() {
        ArrayList<Integer> result = board.getNext_nodes_board(999, YutResult.DO);
        assertTrue(result.isEmpty());
    }

    @Test
    void testEndGame() {
        // Test node 30 with different moves
        ArrayList<Integer> node30BackDo = board.getNext_nodes_board(30, YutResult.BACK_DO);
        assertEquals(List.of(29), node30BackDo);

        ArrayList<Integer> node30Do = board.getNext_nodes_board(30, YutResult.DO);
        assertEquals(List.of(1), node30Do); // Special case: goes to node 1

        ArrayList<Integer> node30Gae = board.getNext_nodes_board(30, YutResult.GAE);
        assertEquals(List.of(32), node30Gae);

        ArrayList<Integer> node30Geol = board.getNext_nodes_board(30, YutResult.GEOL);
        assertEquals(List.of(44), node30Geol); // Goes to end
    }

    @Test
    void testBackDo() {
        // Test various back connections
        ArrayList<Integer> node26BackDo = board.getNext_nodes_board(26, YutResult.BACK_DO);
        assertEquals(List.of(25, 42), node26BackDo);

        ArrayList<Integer> node43BackDo = board.getNext_nodes_board(43, YutResult.BACK_DO);
        assertEquals(List.of(36), node43BackDo);

        ArrayList<Integer> node41BackDo = board.getNext_nodes_board(41, YutResult.BACK_DO);
        assertEquals(List.of(43), node41BackDo);
    }
}
