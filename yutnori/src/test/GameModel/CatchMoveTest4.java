package GameModel;


import GameModel.YutnoriSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import play.YutResult;

import static org.junit.jupiter.api.Assertions.*;

class CatchMoveTest4
{


    YutnoriSet yutnoriSet;
    int numberOfPlayers;
    int numberOfPieces;



    @BeforeEach
    void setUp() {

        yutnoriSet = new YutnoriSet(4);

        numberOfPlayers = 2;
        numberOfPieces = 4;
        //yutnoriSet.setPlayerTurn(0);
        yutnoriSet.setPlayer(2, 4);
        yutnoriSet.addPlayerResult(YutResult.GAE);

        yutnoriSet.moveMal(0, 0, 2, YutResult.GAE);
        yutnoriSet.addPlayerResult(YutResult.GAE);
        yutnoriSet.moveMal(0, 1, 2, YutResult.GAE);
        //0번 사용자의 0번과 1번 말이 순차적으로 2번 노드로 이동(개의 결과)
        // 그 후 올바르게 이동 확인
        // 잡았을 경우 2번 노드에 아무 것도 없는지 확인
    }

    @Test
    void tryCatchMaltest() {
        yutnoriSet.addPlayerResult(YutResult.GAE);

        boolean isMalCaught = yutnoriSet.tryCatchMal(1,  2);
        yutnoriSet.moveMal(1,0,2,YutResult.GAE);
        assertTrue(isMalCaught);
        assertEquals(1, yutnoriSet.board.boardShape.get(2).getNumOfOccupyingPieces());
        assertEquals(0, yutnoriSet.board.boardShape.get(2).getOccupyingPieces().getFirst()
                .getMalNumber());

        //1번이 0번의 말들을 잘 잡는지 체크

    }


    @Test
    void selectBoardPieceTest()
    {
        yutnoriSet.addPlayerResult(YutResult.GAE);

        int unstartedMalID= yutnoriSet.selectOutOfBoardPiece(0);
        assertEquals(2, unstartedMalID);
        yutnoriSet.addPlayerResult(YutResult.GEOL);
        yutnoriSet.moveMal(0,unstartedMalID,3,YutResult.GEOL);
        assertEquals(3, yutnoriSet.players.getFirst().
                getMalList().get(2).getPosition());


        yutnoriSet.addPlayerResult(YutResult.GEOL);
        int unstartedMalID2= yutnoriSet.selectOutOfBoardPiece(0);
        yutnoriSet.moveMal(0,unstartedMalID2,3,YutResult.GEOL);
        assertEquals(3, yutnoriSet.players.getFirst().
                getMalList().get(2).getPosition());

        assertEquals(2, yutnoriSet.board.boardShape.get(3).
                getNumOfOccupyingPieces());

        //System.out.println("Mals on Node 3: " + yutnoriSet.board.boardShape.get(3).
        //      getOccupyingPieces());
        //여기까지해서 외부에서 선택 된 0번 플레이어의 아직 시작 안한 3번과 4번 말들이 3번 노드에
        //잘 선택 되어서 올바르게 위치했음을 알 수 있음

        //이 후는 1번 플레이어의 말(1번 노드) 3번 노드로 움직여서 스택 되어 있는 말들을
        //잡을 수 있도록함
        yutnoriSet.addPlayerResult(YutResult.DO);
        int unstartedMalID3= yutnoriSet.selectOutOfBoardPiece(1);
        yutnoriSet.moveMal(1,unstartedMalID3,1,YutResult.DO);
        assertEquals(1, yutnoriSet.players.get(1).
                getMalList().getFirst().getPosition()); //1번 플레이어는 아무런 말도 아직 안
        //움직인 상태이다!
        int startedMalID1 = yutnoriSet.selectInBoardPiece(1,
                yutnoriSet.board.boardShape.get(1).getOccupyingPieces().
                        getFirst().getMalNumber());

        yutnoriSet.moveMal(1,startedMalID1,3,YutResult.DO);
        assertEquals(1, yutnoriSet.board.boardShape.get(3).
                getNumOfOccupyingPieces());
        System.out.println("Player 1 Mal Position: " + yutnoriSet.players.get(1).getMalList().
                getFirst().getPosition());
        System.out.println("whos mal is on 3: " + yutnoriSet.board.boardShape.get(3).
                getOccupyingPieces().getFirst().getOwner().getTeam());

    }
    @Test
    void roll_choose_move()
    {
        yutnoriSet.setInGameFlag(YutnoriSet.NEED_TO_ROLL); //굴릴 수 있는 상태로
        yutnoriSet.setPlayerTurn(1);//1번 플레이어의 턴으로 바꿈
        yutnoriSet.rollYut();
        while(yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_ROLL) {
            yutnoriSet.rollYut();
        }
        System.out.println("Roll Result: " + yutnoriSet.getPlayerResults());
        //말을 선택할 수 있는 경우에만 진입 할 수 있음
        while(yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_SELECT)
        {
            //사용자가 어떤 움직임을 취할 지 입력을 받지는 않은 상태->자동으로 순서대로 수행
            //그리고 일단 외부의 말들만 선택한다고 가정

            int chosenMalId = yutnoriSet.selectOutOfBoardPiece(1);
            //순차적으로 0번 말부터 선택이 되어야 함
            if(chosenMalId ==-1)
            {
                System.out.println("chosenMalId: " + chosenMalId + " is not valid");
                break;
            }
            if(yutnoriSet.getInGameFlag()== YutnoriSet.NEED_TO_ROLL)
            {
                break;
            }
            int destNodeId = yutnoriSet.getPlayerResults().getFirst().getMoveCount();
            boolean isCaught = yutnoriSet.tryCatchMal(1, destNodeId);

            if(isCaught)
            {
                System.out.println("Player 0's Mal is caught");
            }
            else
            {
                System.out.println("Player 1's Mal is not caught");
            }
            yutnoriSet.moveMal(1,chosenMalId,yutnoriSet.getPlayerResults().
                    getFirst().getMoveCount(),yutnoriSet.getPlayerResults().getFirst());
            System.out.println("Player 1's Mal Position: " + yutnoriSet.players.get(1).getMalList().
                    get(chosenMalId).getPosition());

        }
        //2번 노드에 '개'가 나와서 도착을 한다면 0번 플레이어의 말을 잡고
        //그게 아니라면 외부의 말들만 선택한 것므로 위치에 도착함
    }
}
