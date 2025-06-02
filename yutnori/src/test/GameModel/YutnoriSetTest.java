package test.GameModel;

import GameModel.YutnoriSet;
import org.junit.jupiter.api.Test;
import play.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
class YutnoriSetTest {

    @Test
    void testYutnoriSetCreate() {
        //보드 생성시 초기 값들이 잘 설정되는 지 체크
        YutnoriSet yutnoriSet = new YutnoriSet(4);
        assertNotNull(yutnoriSet);

        assertEquals(0, yutnoriSet.getPlayerTurn());
        assertEquals(yutnoriSet.getInGameFlag(), yutnoriSet.NEED_TO_ROLL);
    }

    @Test
    void testYutnoriSetCreate2() {
        //보드 생성시 초기 값들이 잘 설정되는 지 체크
        YutnoriSet yutnoriSet = new YutnoriSet(5);
        assertNotNull(yutnoriSet);

        assertEquals(0, yutnoriSet.getPlayerTurn());
        assertEquals(yutnoriSet.getInGameFlag(), yutnoriSet.NEED_TO_ROLL);
    }

    @Test
    void testYutnoriSetCreate3() {
        //보드 생성시 초기 값들이 잘 설정되는 지 체크
        YutnoriSet yutnoriSet = new YutnoriSet(6);
        assertNotNull(yutnoriSet);

        assertEquals(0, yutnoriSet.getPlayerTurn());
        assertEquals(yutnoriSet.getInGameFlag(), yutnoriSet.NEED_TO_ROLL);
    }
    @Test
    void yutnoriSetGeneration()
    {
        //육각형 보드에 4명의 플레이어, 각 5개의 말이 지정하면 잘 생성 되는가?
        YutnoriSet yutnoriSet = new YutnoriSet(6);
        yutnoriSet.setPlayer(4,5);

        assertEquals(0, yutnoriSet.getPlayerTurn());
        assertEquals(4, yutnoriSet.getPlayers().size());
        for(Player player : yutnoriSet.getPlayers())
        {
            assertEquals(5, player.getMalList().size());
        }


    }


}