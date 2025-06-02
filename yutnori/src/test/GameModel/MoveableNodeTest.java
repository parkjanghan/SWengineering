package GameModel;

import GameModel.YutnoriSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import play.Mal;
import play.Player;
import play.YutResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveableNodeTest {
    YutnoriSet yutnoriSet;

    @BeforeEach
    void setUp() {
        yutnoriSet = new YutnoriSet(6);
        yutnoriSet.setPlayer(4,4);
    }
    @Test
    void showMoveableMalOutOfBoard() {
        ArrayList<Mal> outofBoardMal = new ArrayList<>();

        for(Player player : yutnoriSet.getPlayers()) {
            for(Mal mal : player.getMalList()) {
                if(mal.getPosition() <= 0) {
                    outofBoardMal = yutnoriSet.showMoveableMalOutOfBoard(player.getMalList());
                    assertEquals(4, outofBoardMal.size());
                }
            }
        }

    }

    @Test
    void showMoveableNodeId()
    {
        ArrayList<Integer> moveableNodeId = new ArrayList<>();

        moveableNodeId = yutnoriSet.showMoveableNodeId(2, YutResult.GAE);
        ArrayList<Integer> expectedMoveableNodeId = new ArrayList<>();
        expectedMoveableNodeId.add(4);
        assertEquals(expectedMoveableNodeId, moveableNodeId);
        expectedMoveableNodeId.clear();

        ArrayList<Integer> moveableNodeId2 = new ArrayList<>();
        moveableNodeId2 = yutnoriSet.showMoveableNodeId(43, YutResult.GEOL);

        expectedMoveableNodeId.add(1);
        assertEquals(expectedMoveableNodeId, moveableNodeId2);
        expectedMoveableNodeId.clear();

        ArrayList<Integer> moveableNodeId3 = new ArrayList<>();
        moveableNodeId3 = yutnoriSet.showMoveableNodeId(16, YutResult.GEOL);

        expectedMoveableNodeId.add(43);
        expectedMoveableNodeId.add(19);
        assertEquals(expectedMoveableNodeId, moveableNodeId3);
        expectedMoveableNodeId.clear();
    }


}