package test.GameModel;

import GameModel.YutnoriSet;
import org.junit.jupiter.api.Test;
import play.YutResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RollTest {

    @Test
    void rollYut() {
        //2번 굴리면 윷놀이의 결과 2개가 저장이 잘 되도록 저장
        YutnoriSet yutnoriSet = new YutnoriSet(4);
        yutnoriSet.rollYut();
        yutnoriSet.rollYut();
        ArrayList<YutResult> playerResult = yutnoriSet.getPlayerResults();
        System.out.println("playerResult = " + playerResult);
        assertEquals(2, playerResult.size());
        assertNotNull(playerResult);

    }

    @Test
    void rollYutforTest() {
        YutnoriSet yutnoriSet = new YutnoriSet(5);
        //윷 모 도 순으로 여러번 굴렸을 때도 올바르게 지정하는 결과가 잘 저장 되는지
        yutnoriSet.rollYutforTest(YutResult.YUT);
        yutnoriSet.rollYutforTest(YutResult.MO);
        yutnoriSet.rollYutforTest(YutResult.DO);

        ArrayList<YutResult> playerResult = yutnoriSet.getPlayerResults();
        System.out.println("playerResult = " + playerResult);
        assertEquals(3, playerResult.size());

    }
}