package GameController;

import board.Board4;
import board.Board5;
import board.Board6;
import board.BoardAbstract;
import play.Mal;
import play.Player;
import play.YutResult;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

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



    public YutnoriSet(int boardType)
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
        System.out.println("[YutnoriSet] 🎯 결과: " + result.getName());
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
        System.out.println("[YutnoriSet] 🎯 테스트 결과 지정: " + input.getName());
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
    } 

    public int selectOutOfBoardPiece(int playerTurn)
    {
        //말의 위치가 0번 노드에 있어야함
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
        ArrayList<Mal> moveableOutOfBoardMal = new ArrayList<>();
        for (Mal mal : malList) {
            if (mal.getPosition() == 0) {
                moveableOutOfBoardMal.add(mal);
            }
        }
        return moveableOutOfBoardMal;
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

    public void moveMal(int playerTurn, int selectedMalNumber, int destNodeId, YutResult yutResult) {
        Player currentPlayer = players.get(playerTurn);
        Mal selectedMal = currentPlayer.getMalList().get(selectedMalNumber);
        int currentNode = selectedMal.getPosition();

        playerResults.remove(yutResult);

        if (currentNode == 0) {
            selectedMal.setPosition(destNodeId);
            board.boardShape.get(destNodeId).addOccupyingPiece(playerTurn, selectedMal);
        } else {
            ArrayList<Mal> currentMals = board.boardShape.get(currentNode).getOccupyingPieces();

            for (Mal mal : currentMals) {
                if (mal.getTeam() == playerTurn) {
                    mal.setPosition(destNodeId);
                    if (!mal.equals(selectedMal)) {
                        selectedMal.stackMal(mal); // ✅ 그룹화
                    }
                }
            }

            // clear old node
            board.boardShape.get(currentNode).clearOccupyingPieces();

            // 도착지 등록
            board.boardShape.get(destNodeId).addOccupyingPiece(playerTurn, selectedMal);

            // 점수 체크
            if (board.boardShape.get(destNodeId).isEndPoint()) {
                selectedMal.setFinished(true);
                currentPlayer.addScore(1);
            }
        }

        inGameFlag = playerResults.isEmpty() ? NEED_TO_ROLL : NEED_TO_SELECT;

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

    //add observer properties

    private void notifyGameStateChange(String property, Object newValue) {
        if (observable != null) {
            observable.firePropertyChange(property, null, newValue);
        }
    }

    public void addObserver(PropertyChangeListener listener) {
    if (observable == null) {
        observable = new PropertyChangeSupport(this);
    }
    observable.addPropertyChangeListener(listener);
}
    public int getChosenDestNodeId() {
        return chosenDestNodeId;
    }

    public void startGame(int numberOfPlayers, int numberOfPieces) {
        setPlayer(numberOfPlayers, numberOfPieces);
        this.playerTurn = 0;
        this.inGameFlag = NEED_TO_ROLL;
        notifyGameStateChange("게임 시작됨", null);
    }

    public void nextTurn() {
        this.playerTurn = (this.playerTurn + 1) % players.size();
        notifyGameStateChange("턴 변경됨", playerTurn);
    }


    public void addPlayer() {
        Player newPlayer = new Player(players.size()); // 새로운 플레이어 생성 (ID는 현재 플레이어 수)
        players.add(newPlayer); // 플레이어 리스트에 추가
        notifyGameStateChange("새로운 플레이어 추가됨", newPlayer); // 상태 변경 알림
    }

    public Object getPlayer(int playerId) {
        for (Player player : players) {
            if (player.getTeam() == playerId) {
                return player;
            }
        }
        return null; // 플레이어를 찾지 못한 경우
    }
}
