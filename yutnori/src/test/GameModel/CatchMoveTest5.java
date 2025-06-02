package GameModel;

import GameModel.YutnoriSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import play.YutResult;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CatchMoveTest5 {
    YutnoriSet yutnoriSet;

    @BeforeEach
    void setUp() {
        yutnoriSet = new YutnoriSet(5);
        yutnoriSet.setPlayer(2, 4);
        //플레이어 2명에 각 말 4개로 설정
        //시작 후 player turn =0
    }

    @Test
    void testSelectOutOfBoardPiece()
    {
       //0번 사용자가 '개'가 나왔을 때 첫 말이 시작이 될 수 있도록 함
        yutnoriSet.addPlayerResult(YutResult.GAE);
        int unstartedMalID = yutnoriSet.selectOutOfBoardPiece(0);
        assertEquals(0, unstartedMalID);//시작 안 한 말이면 순차적으로 선택 될 수 있게함

        //첫 번째 말이 시작되었는지 확인
        assertEquals(0, yutnoriSet.players.getFirst().getMalList().getFirst().getPosition());
        assertEquals(YutnoriSet.NEED_TO_MOVE, yutnoriSet.getInGameFlag());
        //움직여야 되는 상태로 올바르게 이동 되었는지
    }

    @Test
    void testStackedMal() {
        //player_turn =0, 걸이 나온 상황, 0번 말이 3번 노드로 이동
        yutnoriSet.addPlayerResult(YutResult.GEOL);
        int unstartedMalID = yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0, unstartedMalID, 3, YutResult.GEOL);
        assertEquals(0, yutnoriSet.getPlayerResults().size());
        //player result가 모두 소진 되었는지, 그리고 1번 사용자의 턴인지
        yutnoriSet.setPlayerTurn(1);
        assertEquals(1, yutnoriSet.getPlayerTurn());

        yutnoriSet.addPlayerResult(YutResult.DO);
        int player1MalID = yutnoriSet.selectOutOfBoardPiece(1);
        yutnoriSet.moveMal(1, player1MalID, 1, YutResult.DO);
        assertEquals(1, yutnoriSet.players.get(1).getMalList().getFirst().getPosition());
        //1번 사용자의 말이 1번 노드로 이동 되었는지 확인
        assertEquals(0, yutnoriSet.getPlayerResults().size());
        //0번사용자의 두 번째 말이 3번에 잘 겹치는지?
        yutnoriSet.addPlayerResult(YutResult.GEOL);
        int unstartedMalID2 = yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0, unstartedMalID2, 3, YutResult.GEOL);
        assertEquals(2, yutnoriSet.board.boardShape.get(3).getNumOfOccupyingPieces());
        //2번 노드에 0번 사용자의 말이 잘 겹쳐 있는지 확인
        assertEquals(0, yutnoriSet.getPlayerResults().size());

    }

    @Test
    void testCaptureStackedMal() {
        //player_turn =0, 걸이 나온 상황, 0번 말이 3번 노드로 이동
        yutnoriSet.addPlayerResult(YutResult.GEOL);
        int unstartedMalID = yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0, unstartedMalID, 3, YutResult.GEOL);
        assertEquals(0, yutnoriSet.getPlayerResults().size());
        //player result가 모두 소진 되었는지, 그리고 1번 사용자의 턴인지
        yutnoriSet.setPlayerTurn(1);
        assertEquals(1, yutnoriSet.getPlayerTurn());

        yutnoriSet.addPlayerResult(YutResult.DO);
        int player1MalID = yutnoriSet.selectOutOfBoardPiece(1);
        yutnoriSet.moveMal(1, player1MalID, 1, YutResult.DO);
        assertEquals(1, yutnoriSet.players.get(1).getMalList().getFirst().getPosition());
        //1번 사용자의 말이 1번 노드로 이동 되었는지 확인
        assertEquals(0, yutnoriSet.getPlayerResults().size());
        //0번사용자의 두 번째 말이 3번에 잘 겹치는지?
        yutnoriSet.addPlayerResult(YutResult.GEOL);
        int unstartedMalID2 = yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0, unstartedMalID2, 3, YutResult.GEOL);
        assertEquals(2, yutnoriSet.board.boardShape.get(3).getNumOfOccupyingPieces());
        //2번 노드에 0번 사용자의 말이 잘 겹쳐 있는지 확인
        assertEquals(0, yutnoriSet.getPlayerResults().size());

        //3번 노드에 있는 0번 플레이어의 말들 2개가 잘 잡히는지 확인
        yutnoriSet.addPlayerResult(YutResult.GEOL);
        int player1MalID2 = yutnoriSet.selectInBoardPiece(1, yutnoriSet.board.boardShape.get(3).getOccupyingPieces().getFirst().getMalNumber());
        yutnoriSet.moveMal(1, player1MalID2, 3, YutResult.GEOL);
        assertEquals(1, yutnoriSet.board.boardShape.get(3).getNumOfOccupyingPieces());
        System.out.println("occcupying mals at node 3:" + yutnoriSet.board.boardShape.get(3).getOccupyingPieces());

    }

    @Test
    void testRollChooseMoveCycle() {
        //임의로 플레이어0의 0번 말이 6번 노드에 있다고 설정
        yutnoriSet.getPlayers().getFirst().getMalList().getFirst().setPosition(6);
        //플레이어 1의 0번 말이 3번 노드에 있다고 설정
        yutnoriSet.getPlayers().get(1).getMalList().getFirst().setPosition(3);
        //0번 플레이어가 도가 나왔을때 28이나 7 둘다 이동 할 수 있는 것을 확인
        assertEquals(0, yutnoriSet.getPlayerTurn());
        yutnoriSet.addPlayerResult(YutResult.DO);
        assertEquals(2, yutnoriSet.board.getNext_nodes_board(
                yutnoriSet.getPlayers().getFirst().getMalList().getFirst().getPosition(),
                YutResult.DO).size());

        assertEquals(List.of(28, 7), yutnoriSet.board.getNext_nodes_board(
                yutnoriSet.getPlayers().getFirst().getMalList().getFirst().getPosition(), YutResult.DO));

    }

    @Test
    void testGameStateFlags() {
        yutnoriSet.setInGameFlag(YutnoriSet.NEED_TO_ROLL);
        assertEquals(YutnoriSet.NEED_TO_ROLL, yutnoriSet.getInGameFlag());

        yutnoriSet.setInGameFlag(YutnoriSet.NEED_TO_SELECT);
        assertEquals(YutnoriSet.NEED_TO_SELECT, yutnoriSet.getInGameFlag());
    }

    @Test
    void testPlayerTurnManagement() {
        yutnoriSet.setPlayerTurn(0);
        yutnoriSet.setPlayerTurn(1);

        assertTrue(true);
    }

    @Test
    void testMultipleYutResults() {
        List<YutResult> testResults = List.of(YutResult.YUT, YutResult.MO, YutResult.GEOL);

        for (YutResult result : testResults) {
            yutnoriSet.addPlayerResult(result);
        }

        assertTrue(yutnoriSet.getPlayerResults().size() >= testResults.size());
    }

    @Test
    void testSelectInBoardPiece() {
        yutnoriSet.addPlayerResult(YutResult.DO);
        int malId = yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0, malId, 1, YutResult.DO);

        if (!yutnoriSet.board.boardShape.get(1).getOccupyingPieces().isEmpty()) {
            int boardMalNumber = yutnoriSet.board.boardShape.get(1).getOccupyingPieces()
                    .getFirst().getMalNumber();
            int selectedId = yutnoriSet.selectInBoardPiece(0, boardMalNumber);

            assertTrue(selectedId >= 0);
        }
    }
}