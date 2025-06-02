package GameModel;

import GameModel.YutnoriSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import play.Mal;
import play.YutResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MalMovementTest {

    YutnoriSet yutnoriSet;

    @BeforeEach
    void setUp() {
        yutnoriSet = new YutnoriSet(6);
        yutnoriSet.setPlayer(2, 4);
    }

    @Test
    void moveMalTest() {
        int playerId = 0;
        int malId = 0;
        int startNodeId = 0;  // 말은 아직 시작하지 않은 상태
        int destNodeId = 2;    // 일반적인 첫 이동 목적지

        // 1. 이동 전 상태 점검
        Mal mal = yutnoriSet.players.get(playerId).getMalList().get(malId);
        assertEquals(startNodeId, mal.getPosition(), "초기 위치는 시작 전이어야 합니다.");
        assertEquals(0, yutnoriSet.board.boardShape.get(destNodeId).getNumOfOccupyingPieces(), "도착 노드는 비어 있어야 합니다.");

        // 2. 이동 실행
        yutnoriSet.addPlayerResult(YutResult.DO);
        yutnoriSet.moveMal(playerId, malId, destNodeId, YutResult.DO);

        // 3. 말 위치 상태 점검
        assertEquals(destNodeId, mal.getPosition(), "말이 올바른 위치로 이동해야 합니다.");

        // 4. 도착 노드에 말이 추가되었는지 확인
        var destNode = yutnoriSet.board.boardShape.get(destNodeId);
        assertEquals(1, destNode.getNumOfOccupyingPieces(), "도착 노드에 정확히 하나의 말이 있어야 합니다.");

        // 5. 해당 말이 정확한 플레이어/말 번호인지 확인
        Mal movedMal = destNode.getOccupyingPieces().getFirst();
        assertEquals(playerId, movedMal.getOwner().getTeam(), "말의 주인이 일치해야 합니다.");
        assertEquals(malId, movedMal.getMalNumber(), "말 번호가 일치해야 합니다.");
    }

    // 업힌 말이 함께 이동하는지 확인
    @Test
    void testStackedMalMoveTogether() {
        yutnoriSet.addPlayerResult(YutResult.DO);
        yutnoriSet.moveMal(0, 0, 5, YutResult.DO);

        yutnoriSet.addPlayerResult(YutResult.DO);
        yutnoriSet.moveMal(0, 1, 5, YutResult.DO);

        assertEquals(2, yutnoriSet.board.boardShape.get(5).getNumOfOccupyingPieces());

        yutnoriSet.addPlayerResult(YutResult.GAE);
        yutnoriSet.moveMal(0, 0, 6, YutResult.GAE);

        assertEquals(2, yutnoriSet.board.boardShape.get(6).getNumOfOccupyingPieces());
        assertEquals(6, yutnoriSet.players.get(0).getMalList().get(0).getPosition());
        assertEquals(6, yutnoriSet.players.get(0).getMalList().get(1).getPosition());
    }
}

