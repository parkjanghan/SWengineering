package GameController;

import board.board.Board4;
import board.board.Board5;
import board.board.Board6;
import board.board.BoardAbstract;
import play.Mal;
import play.Player;
import play.YutResult;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Map;

public class YutnoriSet {

    public BoardAbstract board;
    private int boardType;
    public YutTotal yutTotal;
    public ArrayList<Player> players = new ArrayList<>();
    private int playerTurn; //어느 사용자의 턴인지?
    private int inGameFlag;
    private ArrayList<YutResult> playerResults;



    private int chosenDestNodeId ;

    public static final int NEED_TO_ROLL = 0;
    public static final int NEED_TO_SELECT = 1;
    public static final int NEED_TO_MOVE = 2;



    private PropertyChangeSupport observable; //GUI 갱신을 위한 옵저버 패턴



    public YutnoriSet( int boardType)
    {
        if(boardType == 4) {
            this.board = new Board4();
        }
        else if(boardType == 5)
        {
            this.board = new Board5();
        }
        else if(boardType ==6)
        {
            this.board = new Board6();
        }
        else
        {
            throw new IllegalArgumentException("Invalid board type");
        }

        this.playerTurn = 0;   // 0번 사용자(첫 사용자)의 턴으로
        this.inGameFlag = NEED_TO_ROLL;  //윷 던지기 저 상태
        this.playerResults = new ArrayList<>();
        this.yutTotal = new YutTotal();
    }

    public void setPlayer(int numberOfPlayers, int numberOfPieces)
    {

        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player(i));
            for (int j = 0; j < numberOfPieces; j++) {
                players.get(i).addMal(new Mal(i, j, players.get(i)));
            }
        }
        notifyGameStateChange("사용자 생성됨", players);
    }


    public void rollYut()
    {

        YutResult result = yutTotal.rollYut();
        addPlayerResult(result);

       // 다시 한 번 던질 수 있나 확인->  그러면 한 번 더 저장
        boolean isExtraTurnAllowed;
        isExtraTurnAllowed = (result==YutResult.YUT || result == YutResult.MO);


        if (!isExtraTurnAllowed) //inGameFlag에 따라서 rollYut을 한 번 더 수행해야함
        {
            inGameFlag = NEED_TO_SELECT;
        }
        else // isExtraTurnAllowed == true
        {
            inGameFlag = NEED_TO_ROLL;
        }

        notifyGameStateChange("윷 던지기 결과", result);

    }

    public void rollYutforTest(YutResult input) //테스트 용으로 윷을 지정할 수 있음
    {
        addPlayerResult(input);
        boolean isExtraTurnAllowed;
        isExtraTurnAllowed = (input==YutResult.YUT || input == YutResult.MO);


        if (!isExtraTurnAllowed) //inGameFlag에 따라서 rollYut을 한 번 더 수행해야함
        {
            inGameFlag = NEED_TO_SELECT;
        }
        else // isExtraTurnAllowed == true
        {
            inGameFlag = NEED_TO_ROLL;
        }
        notifyGameStateChange("윷 던지기 결과", input);

    }

    public void addPlayerResult(YutResult yutResult)
    {

        playerResults.add(yutResult);
        notifyGameStateChange("사용자의 결과 추가됨", yutResult);
    }

    public void deletePlayerResult(YutResult result) {
        for (int i = 0; i < playerResults.size(); i++)
        {
            if (playerResults.get(i) == result)
            {
                playerResults.remove(i);
                notifyGameStateChange("사용자의 결과 삭제됨", result);
                break;
            }
        }
    } //rollYut test 구현해야 됨
/// ////////////말 던지기
    public int selectOutOfBoardPiece(int playerTurn)
    {
        //어느 player의 턴인지
        Player player = players.get(playerTurn);
        //player의 말들 리스트
        ArrayList<Mal> malList = player.getMalList();
        int selectedMalNumber = -1;

        //보드 밖에 있는 말들을 불러옴
        ArrayList<Mal> OutOfBoardMal = new ArrayList<>();

        //아직 시작 안한 말들을 보여 주기
        OutOfBoardMal = showMoveableMalOutOfBoard(malList);
        if (OutOfBoardMal.isEmpty())
        {
            return selectedMalNumber; //선택할 수 있는 외부의 말이 없음
            //selectedMalNumber가 -1인 상태여야됨
        }//플레이어가 어떤 말을 선택했는지를 어떻게 알려주는지? 0428

        //어떻게 남이 아씨는 말을 고를 것인 지에 대해서 수정이 필요함
      setInGameFlag(NEED_TO_MOVE);
      notifyGameStateChange("말 선택됨", new int[]{playerTurn, selectedMalNumber});
      return OutOfBoardMal.getFirst().getMalNumber();
        //-1 이면 선택 할 수 있는 말이 없음
    }

    public ArrayList<Mal> showMoveableMalOutOfBoard(ArrayList<Mal> malList)
    {
        ArrayList<Mal> moveableOutoOfBoardMal = new ArrayList<>();
        for (Mal mal : malList) {
            if (mal.getPosition() == 0) {
                moveableOutoOfBoardMal.add(mal);
            }
        }
        return moveableOutoOfBoardMal;
    }


    public int selectInBoardPiece(int playerTurn, int selectedMalNumber)
    {
        Player player = players.get(playerTurn);
        ArrayList<Mal> malList = player.getMalList();
        Mal selectedMal = malList.get(selectedMalNumber);
        int selectedMalPosition = selectedMal.getPosition();

        if (selectedMalPosition == 0)
        {
            return -1; //선택할 수 있는 말이 없음
        }

        setInGameFlag(NEED_TO_MOVE);
        notifyGameStateChange("말 선택됨", new int[]{playerTurn, selectedMalNumber});
        return selectedMalNumber;
    }

    public ArrayList<Integer> showMoveableNodeId(int position, YutResult yutResult)
    {
        return board.getNext_nodes_board(position, yutResult);
    } //아마 수정이 조금 필요 할 수 도 있음 일단 다음 가능 위치가 어디 인지만 작성함0427
/// ////////말 어디로 이동 해야 할 지 정하기
///
///

    public void setChosenDestNodeId(int chosenDestNodeId) {
        this.chosenDestNodeId = chosenDestNodeId;
        notifyGameStateChange("목적지 노드 선택됨", chosenDestNodeId);
    }


    // Catch
    public boolean tryCatchMal(int playerTurn, int destNodeId)
    {
        Player currentPlayer = players.get(playerTurn); //현재 플레이어

        ArrayList<Mal> occupyingMal = board.boardShape.get(destNodeId).getOccupyingPieces();
        if(occupyingMal.isEmpty())
        {
            return false;
        }

        for(Mal mal : occupyingMal)
        {
            if (mal.getTeam() != playerTurn)
            {
                mal.setPosition(0);//판 밖으로 이동

            }
        }
        board.boardShape.get(destNodeId).clearOccupyingPieces(); //아무 말도 없는 상태로 바꿈
        setInGameFlag(NEED_TO_ROLL);

        notifyGameStateChange("말 잡힘", new int[]{playerTurn, destNodeId});
        return true;
    }

    //tryStackMal
//     public boolean tryStackMal(int playerTurn, int destNodeId) {
//            Player currentPlayer = players.get(playerTurn); // 현재 플레이어
//            Mal selectedMal = null;
//            ArrayList<Mal> occupyingMal = board.boardShape.get(destNodeId).getOccupyingPieces();
//        if (occupyingMal.isEmpty())
//        {
//            return false; // 선택할 수 있는 말이 없음
//        }
//        for (Mal mal : occupyingMal) {
//            if (mal.getTeam() == playerTurn) {
//                selectedMal = mal;
//                break;
//            }
//        }
//     }
    //미완성인 함수이나 필요 없을 듯합니다
    // 하나의 노드에는 결국 한 사람의 말만 존재 할 수 있으므로 말들을 스택하는게 아니라
    //순회하면서 존재하는 모든 말들을 모두 같은 위치로 이동시키거나(탈락)시키는 방법으로
    //관리하는 것이 더 쉽고 관리가 편할 듯 합니다.


    public void moveMal(int playerTurn, int selectedMalNumber, int destNodeId, YutResult yutResult)
    {
        Player currentPlayer = players.get(playerTurn);

        //trycatchMal 과 tryStackMal이 선행 되어야함
        Mal selectedMal = currentPlayer.getMalList().get(selectedMalNumber);
        int currentNode = selectedMal.getPosition();
        playerResults.remove(yutResult);
        // 선택된 말의 위치 업데이트 + 같은 위치에 스택 되어 있는 말들도 동시에 움직임
        if(currentNode ==0)
        {
            selectedMal.setPosition(destNodeId);
            board.boardShape.get(destNodeId).addOccupyingPiece(playerTurn, selectedMal);
        }
        else
        {

            for(Mal mal : board.boardShape.get(currentNode).getOccupyingPieces())
            {
                //끝 지점 넘어가면 점수 올라가는 로직도 추가해 줘야함
                //예를 들어 board4에서 30번 넘어가면 자동으로 30으로 이동하고,
                //말의 position을 30으로 하고, score 올릴 수 있도록 해야함
                if(mal.getTeam()==playerTurn) {
                    mal.setPosition(destNodeId);

                    if (board.boardShape.get(destNodeId).isEndPoint()) {
                        players.get(playerTurn).addScore(1);
                        //사용자의 말이 끝에 도달했다면 점수를 올립니다.
                        //전체 process 로직에서 이를 어떻게 관리 할 지는 gui 연결하며
                        //해결하는 것이 좋아 보입니다.
                        mal.setFinished(true);
                        mal.setPosition(destNodeId);

                        board.boardShape.get(destNodeId).addOccupyingPiece(playerTurn, selectedMal);
                    } else {

                        board.boardShape.get(destNodeId).addOccupyingPiece(playerTurn, mal);
                    }
                }

            }
            board.boardShape.get(currentNode).clearOccupyingPieces();
        }

        //게임 플래그를 다양하게 경우 나눠서
        if(playerResults.isEmpty()) {
            setInGameFlag(NEED_TO_ROLL);
        }
        else
        {
            setInGameFlag(NEED_TO_SELECT);
        }

        notifyGameStateChange("말 이동됨", new int[]{playerTurn, selectedMalNumber, destNodeId});
    }

    // decisionMaking은 process 보고 결정을 해야 할 듯 합니다
    public void decisionMaking()
    {
        if (inGameFlag == NEED_TO_ROLL)
        {
            rollYut();
        }
        else if (inGameFlag == NEED_TO_SELECT)
        {
            //selectOutOfBoardPiece(playerTurn);
            //+
        }
        else if (inGameFlag == NEED_TO_MOVE)
        {
            //selectInBoardPiece(playerTurn, chosenDestNodeId);
        }
    }


    public void setPlayerResults(YutResult yutResult) {
        this.playerResults.add(yutResult);
    }

    public ArrayList<YutResult> getPlayerResults() {
        return playerResults;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }


    public int getInGameFlag() {
        return inGameFlag;
    }

    public void setInGameFlag(int inGameFlag) {
        this.inGameFlag = inGameFlag;
    }

    public BoardAbstract getBoard() {
        return board;
    }

    public void setBoard(BoardAbstract board) {
        this.board = board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    private void notifyGameStateChange(String property, Object to)
    {
        if (observable != null) {
            observable.firePropertyChange(property, null, to);
        }
    }
    //add observer properties
    public void addObserver(PropertyChangeSupport observable) {
        this.observable = observable;
    }
    public int getChosenDestNodeId() {
        return chosenDestNodeId;
    }

}
