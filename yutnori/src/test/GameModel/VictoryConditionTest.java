package GameModel;

import org.junit.jupiter.api.Test;
import play.Mal;
import play.Player;
import play.YutResult;

import static org.junit.jupiter.api.Assertions.*;

public class VictoryConditionTest {


    @Test
    void testVictoryOnBoard4() {
        // 4각형 보드, 플레이어 2명, 말 2개
        YutnoriSet yutnoriSet = new YutnoriSet(4);
        yutnoriSet.setPlayer(2, 2);

        Player player0 = yutnoriSet.getPlayers().get(0);

        // 첫 번째 말: 도착 직전 20번 → 30번으로 이동
        Mal mal0 = player0.getMalList().get(0);
        mal0.setPosition(20); // 도착 직전
        yutnoriSet.getBoard().boardShape.get(20).addOccupyingPiece(0, mal0);

        yutnoriSet.addPlayerResult(YutResult.DO);
        yutnoriSet.moveMal(0, 0, 30, YutResult.DO);

        // 두 번째 말: 도착 직전 20번 → 30번으로 이동
        Mal mal1 = player0.getMalList().get(1);
        mal1.setPosition(20);
        yutnoriSet.getBoard().boardShape.get(20).addOccupyingPiece(0, mal1);

        yutnoriSet.addPlayerResult(YutResult.DO);
        boolean result = yutnoriSet.moveMal(0, 1, 30, YutResult.DO);

        //검증
        assertEquals(YutnoriSet.GameFlag.WAITING, yutnoriSet.getInGameFlag()); // 게임 종료
        assertFalse(result); // 더 움직일 말 없음
        assertTrue(player0.getMalList().get(0).getFinished());
        assertTrue(player0.getMalList().get(1).getFinished());
        assertEquals(2, player0.getScore());
    }

    @Test
    void testVictoryOnBoard5() {
        // 5각형 보드, 플레이어 2명, 말 2개
        YutnoriSet yutnoriSet = new YutnoriSet(5);
        yutnoriSet.setPlayer(2, 2);

        Player player0 = yutnoriSet.getPlayers().get(0);

        // 첫 번째 말 도착 처리
        Mal mal0 = player0.getMalList().get(0);
        mal0.setPosition(25);
        yutnoriSet.getBoard().boardShape.get(25).addOccupyingPiece(0, mal0);

        yutnoriSet.addPlayerResult(YutResult.DO); // DO는 1칸 이동
        yutnoriSet.moveMal(0, 0, 37, YutResult.DO);

        // 두 번째 말 도착 처리
        Mal mal1 = player0.getMalList().get(1);
        mal1.setPosition(25);
        yutnoriSet.getBoard().boardShape.get(25).addOccupyingPiece(0, mal1);

        yutnoriSet.addPlayerResult(YutResult.DO);
        boolean result = yutnoriSet.moveMal(0, 1, 37, YutResult.DO);

        //검증
        assertEquals(YutnoriSet.GameFlag.WAITING, yutnoriSet.getInGameFlag());
        assertFalse(result);
        assertTrue(player0.getMalList().get(0).getFinished());
        assertTrue(player0.getMalList().get(1).getFinished());
        assertEquals(2, player0.getScore());
    }

    @Test
    void testVictoryOnBoard6() {
        // 6각형 보드, 플레이어 2명, 말 2개
        YutnoriSet yutnoriSet = new YutnoriSet(6);
        yutnoriSet.setPlayer(2, 2);

        Player player0 = yutnoriSet.getPlayers().get(0);

        // 첫 번째 말 도착 처리
        Mal mal0 = player0.getMalList().get(0);
        mal0.setPosition(30);
        yutnoriSet.getBoard().boardShape.get(30).addOccupyingPiece(0, mal0);

        yutnoriSet.addPlayerResult(YutResult.DO);
        yutnoriSet.moveMal(0, 0, 44, YutResult.DO);

        // 두 번째 말 도착 처리
        Mal mal1 = player0.getMalList().get(1);
        mal1.setPosition(30);
        yutnoriSet.getBoard().boardShape.get(30).addOccupyingPiece(0, mal1);

        yutnoriSet.addPlayerResult(YutResult.DO);
        boolean result = yutnoriSet.moveMal(0, 1, 44, YutResult.DO);

        //검증
        assertEquals(YutnoriSet.GameFlag.WAITING, yutnoriSet.getInGameFlag());
        assertFalse(result);
        assertTrue(player0.getMalList().get(0).getFinished());
        assertTrue(player0.getMalList().get(1).getFinished());
        assertEquals(2, player0.getScore());
    }



}


