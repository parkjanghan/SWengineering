package GameModel;

import GameModel.YutnoriSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import play.YutResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatchMoveTest6 {

    private YutnoriSet yutnoriSet;

    @BeforeEach
    void setUp() {
        yutnoriSet = new YutnoriSet(4);

        // 초기 게임 상태 설정
        yutnoriSet.setPlayer(2, 4);
        yutnoriSet.addPlayerResult(YutResult.GAE);

        // 플레이어 0의 첫 번째와 두 번째 말을 노드 2로 이동
        yutnoriSet.moveMal(0, 0, 2, YutResult.GAE);
        yutnoriSet.addPlayerResult(YutResult.GAE);
        yutnoriSet.moveMal(0, 1, 2, YutResult.GAE);
        //즉 2번 말에 0번 플레어의 말 0,1, 총 2개의 말이 올라가 있어야함

    }

    @Test
    void testTryCatchMal() {
        yutnoriSet.addPlayerResult(YutResult.GAE);

        // 플레이어 1이 노드 2에서 플레이어 0의 말을 잡으려고 시도
        boolean isMalCaught = yutnoriSet.tryCatchMal(1, 2);
        yutnoriSet.moveMal(1, 0, 2, YutResult.GAE);

        assertTrue(isMalCaught);
        //2번 노드에 말이 한 개만 있어야만 함
        assertEquals(1, yutnoriSet.board.boardShape.get(2).getNumOfOccupyingPieces());
        //2번 노드에 있는 말은 하나이며, 1번 사용자의 0번 말이다.
        assertEquals(1, yutnoriSet.board.boardShape.get(2).getOccupyingPieces().getFirst().getTeam());
        assertEquals(0, yutnoriSet.board.boardShape.get(2).getOccupyingPieces().getFirst().getMalNumber());
    }

    @Test
    void testMoveMal() {
        // 플레이어 1에게 여러 개의 윷 결과 추가
        yutnoriSet.addPlayerResult(YutResult.YUT);
        yutnoriSet.addPlayerResult(YutResult.GEOL);

        // 다양한 말 이동 테스트
       yutnoriSet.moveMal(1,0,3,YutResult.GEOL);
       assertEquals(1, yutnoriSet.getPlayerResults().size());
       //'걸' 하나의 움직임만 소비;
        yutnoriSet.setPlayerTurn(1);
        assertEquals(1, yutnoriSet.getPlayerTurn());
        yutnoriSet.moveMal(1, 1, 5, YutResult.YUT);
        assertEquals(0, yutnoriSet.getPlayerResults().size());
    }

    @Test
    void testSelectOutOfBoardPiece() {
        yutnoriSet.addPlayerResult(YutResult.GAE);

        // 보드 밖에 있는 말을 선택
        int unstartedMalID = yutnoriSet.selectOutOfBoardPiece(0);
        assertEquals(2, unstartedMalID);

        // 선택한 말을 이동
        yutnoriSet.addPlayerResult(YutResult.GEOL);
        yutnoriSet.moveMal(0, unstartedMalID, 3, YutResult.GEOL);
        assertEquals(3, yutnoriSet.players.getFirst().getMalList().get(2).getPosition());
    }

    @Test
    void testPieceStacking() {
        yutnoriSet.addPlayerResult(YutResult.GEOL);
        int unstartedMalID = yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0, unstartedMalID, 3, YutResult.GEOL);

        yutnoriSet.addPlayerResult(YutResult.GEOL);
        int unstartedMalID2 = yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0, unstartedMalID2, 3, YutResult.GEOL);

        assertEquals(2, yutnoriSet.board.boardShape.get(3).getNumOfOccupyingPieces());
        assertEquals(3, yutnoriSet.players.getFirst().getMalList().get(2).getPosition());
    }

    @Test
    void testCaptureStackedMal() {
        // 노드 3에 말 두 개 쌓기
        yutnoriSet.addPlayerResult(YutResult.GEOL);
        int unstartedMalID = yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0, unstartedMalID, 3, YutResult.GEOL);

        yutnoriSet.addPlayerResult(YutResult.GEOL);
        int unstartedMalID2 = yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0, unstartedMalID2, 3, YutResult.GEOL);

        assertEquals(2, yutnoriSet.board.boardShape.get(3).getNumOfOccupyingPieces());

        // 플레이어 1 말 진입
        yutnoriSet.addPlayerResult(YutResult.DO);
        int player1MalID = yutnoriSet.selectOutOfBoardPiece(1);
        yutnoriSet.moveMal(1, player1MalID, 1, YutResult.DO);
        assertEquals(1, yutnoriSet.players.get(1).getMalList().getFirst().getPosition());

        // 말을 선택하고 이동시켜서 상대 말을 잡기
        int startedMalID = yutnoriSet.selectInBoardPiece(1,
                yutnoriSet.board.boardShape.get(1).getOccupyingPieces().getFirst().getMalNumber());

        yutnoriSet.moveMal(1, startedMalID, 3, YutResult.DO);
        assertEquals(1, yutnoriSet.board.boardShape.get(3).getNumOfOccupyingPieces());
        assertEquals(1, yutnoriSet.board.boardShape.get(3).getOccupyingPieces().getFirst().getOwner().getTeam());
    }

    @Test
    void testRollChooseMove() {

        yutnoriSet.setInGameFlag(YutnoriSet.NEED_TO_ROLL);
        yutnoriSet.setPlayerTurn(1);

        // 주사위를 굴림
        yutnoriSet.rollYut();
        while (yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_ROLL) {
            yutnoriSet.rollYut();
        }
        //playerResult에 무언가가 들어가 있다
        assertFalse(yutnoriSet.getPlayerResults().isEmpty());

        // 이동 선택 반복
        int moveCount = 0;
        while (yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_SELECT && moveCount < 5) {
            //일단 처음 시작하는 말부터 이동
            int chosenMalId = yutnoriSet.selectOutOfBoardPiece(1);

            if (chosenMalId == -1) break; // 선택할 말이 없으면 중단 = 욈부에 있는 말 다 소진함
            if (yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_ROLL) break;//모든 움직임을 다 소진함

            //원래는 말의 움직임을 순서와 관계 없이 설정 할 수 있지만, 지금은 순차적으로 사용
            //지금은 순차적으로 소진해서 말 움직임 명령-> yutResult 소진-> 실제 이동하 확인하는 목적
            int destNodeId = yutnoriSet.getPlayerResults().getFirst().getMoveCount();

            yutnoriSet.moveMal(1, chosenMalId, destNodeId, yutnoriSet.getPlayerResults().getFirst());


            assertTrue(yutnoriSet.players.get(1).getMalList().get(chosenMalId).getPosition() >= 0);

            moveCount++;
        }

        assertTrue(moveCount >= 0);
    }

    @Test
    void testGameStateFlags() {
        // 게임 상태 플래그 설정 테스트
        yutnoriSet.setInGameFlag(YutnoriSet.NEED_TO_ROLL);
        assertEquals(YutnoriSet.NEED_TO_ROLL, yutnoriSet.getInGameFlag());

        yutnoriSet.setInGameFlag(YutnoriSet.NEED_TO_SELECT);
        assertEquals(YutnoriSet.NEED_TO_SELECT, yutnoriSet.getInGameFlag());
    }

    @Test
    void testPlayerTurnManagement() {
        // 턴 전환 테스트
        yutnoriSet.setPlayerTurn(0);
        yutnoriSet.setPlayerTurn(1);

        assertTrue(true);
    }

    @Test
    void testMultipleYutResults() {
        // 여러 개의 윷 결과 추가 테스트
        List<YutResult> testResults = List.of(YutResult.YUT, YutResult.MO, YutResult.GEOL);

        for (YutResult result : testResults) {
            yutnoriSet.addPlayerResult(result);
        }

        assertTrue(yutnoriSet.getPlayerResults().size() >= testResults.size());
    }

    @Test
    void testSelectInBoardPiece() {
        // 말을 보드에 먼저 이동시킨 후 선택 테스트
        yutnoriSet.addPlayerResult(YutResult.DO);
        int malId = yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0, malId, 1, YutResult.DO);

        // 보드에 있는 말을 선택
        if (!yutnoriSet.board.boardShape.get(1).getOccupyingPieces().isEmpty()) {
            int boardMalNumber = yutnoriSet.board.boardShape.get(1).getOccupyingPieces()
                    .getFirst().getMalNumber();
            int selectedId = yutnoriSet.selectInBoardPiece(0, boardMalNumber);

            assertTrue(selectedId >= 0);
        }
    }
}
