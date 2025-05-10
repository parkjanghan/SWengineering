package GameModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import play.Mal;
import play.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

class setboardchoicetest {
    YutnoriSet yutnoriSet;

    @BeforeEach
    void setUp() {
        // 기본 바탕 yutnoriSet 설정
        yutnoriSet = new YutnoriSet(5);
        yutnoriSet.setPlayer(4,4);
        //4명의 사용자, 각 4개의 말
    }

    @Test
    void initBoardTest() {
        for(Player player : yutnoriSet.getPlayers()) {
            for(Mal mal : player.getMalList()) {
                //System.out.println("mal.getPosition() = " + mal.getPosition());
                assertEquals(mal.getPosition(), player.getTeam() * (-1));
            }
        }

    }

}