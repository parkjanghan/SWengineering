package logic;

import java.util.ArrayList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class YutnoriSet {
  //윷놀이 전체 게임의 핵심 로직을 제어하는 컨트롤러
  //플레이어, 보드판, 윷 던지기, 이동 경로(Rule) 등을 모두 포함해서
  //게임 상태를 저장하고 제어하는 중앙 허브 역할

  private Player player; //모든 플레이어와 그들의 말 관리
  private Board board; //말판 구조 및 상태 관리
  private YutSet yutSet; //윷 4개 던지기 및 결과 계산
  private Rule ruleTable; //위치별 이동 규칙 저장

  int numOfPlayer; //플레이어 수
  int numOfPiece; //말 개수
  ArrayList<Integer> movable; //이동 가능한 위치 ID 리스트

  private int playerTurn; //현재 턴인 플레이어
  private int inGameFlag; //게임 상태 (0: 윷 던지기, 1: 말 선택, 2: 말 이동 등)
  private PropertyChangeSupport observable; //GUI 갱신을 위한 옵저버 패턴

  public YutnoriSet(){
    board = new Board();
    yutSet = new YutSet();
    ruleTable = new Rule();
    observable = new PropertyChangeSupport(this);
    movable = new ArrayList<Integer>();
  }

  //플레이어 세팅
  public void setPlayer(int numberOfPlayer, int numberOfPiece){
    //플레이어 수와 말 개수를 받아 Player 객체 생성
    player = new Player(numberOfPlayer, numberOfPiece);
    this.numOfPlayer = numberOfPlayer;
    this.numOfPiece = numberOfPiece;
  }

  public void addObserver(PropertyChangeListener observer){
    this.observable.addPropertyChangeListener(observer);
  }

  public int getNumOfPlayer(){
    return numOfPlayer;
  }
  public int getNumOfPiece (){
    return numOfPiece;
  }

  public Player getPlayer(){
    return player;
  }

  public Board getBoard(){
    return board;
  }

  public YutSet getYutSet(){
    return yutSet;
  }

  public int getPlayerTurn(){
    return playerTurn;
  }

  public void setPlayerTurn(int turn){
    this.playerTurn = turn;
    observable.firePropertyChange("player change", false, true);
  }

  //잡기 처리
  //다음 위치에 상대방 말이 있다면: 잡고
  //resetPieceToOrigin()으로 보드 밖으로 보냄
  //resetCircle()로 말판 초기화
  //잡았다면 true 반환
  public boolean tryCatch(int pieceId, int row, int column){
    Mal targetPiece = player.getPieceByPieceId(pieceId);
    boolean result = false;
    Circle nextCircle = board.getCircleByLocation(row,column);
    Mal occupyingPiece = null;
    if(nextCircle.isOccupied()){
      for(int i : nextCircle.getOccupyingPieces()) {
        occupyingPiece = player.getPieceByPieceId(i);
        if (occupyingPiece.getOwnerId() != targetPiece.getOwnerId()) {
          occupyingPiece.resetPieceToOrigin();
          result = true;
        }
      }
    }

    if(result){
      nextCircle.resetCircle();
    }
    return result;
  }

  //어떤 윷 결과로 이 위치를 선택했는지 확인
  //클릭한 위치가 어떤 윷 결과(i=0~5)로 이동 가능한지를 역으로 계산
  //Rule 테이블을 탐색해서 일치하는 결과 반환
  public int getClickedResult(int pieceId, int row, int column){
    Mal targetPiece = player.getPieceByPieceId(pieceId);
    Circle currentCircle;
    if(player.getPieceByPieceId(pieceId).isOutOfBoard()){
      currentCircle = board.getCircleByLocation(7, 7);
    }else {
      currentCircle = board.getCircleByLocation(targetPiece.getRow(), targetPiece.getColumn());
    }
    int nextCicleId = board.getCircleByLocation(row, column).getId();
    for(int i = 0; i < 6; i++){
      int[] getResult = ruleTable.nextMoveTable[currentCircle.getId()][i];
      if(getResult[0] == nextCicleId  || getResult[1]  == nextCicleId) {
        return i;
      }
    }
    return -1;
  }

  //이동 가능한 위치 표시
  public void showMovable(int pieceId){
    Mal targetPiece = player.getPieceByPieceId(pieceId);
    Circle currentCircle;
    if(targetPiece.isOutOfBoard()) {
      currentCircle = board.getCircleByLocation(7, 7);
    } else {
      currentCircle = board.getCircleByLocation(targetPiece.getRow(), targetPiece.getColumn());
    }

    for(int i : player.getPlayerResult(targetPiece.getOwnerId())){
      int[] nextMovableCircleIds = ruleTable.getNextMoveCircleIds(currentCircle.getId(), i);

      board.getCircleByCircleId(nextMovableCircleIds[0]).setChangeable();
      board.getCircleByCircleId(nextMovableCircleIds[0]).getId();
      movable.add(nextMovableCircleIds[0]);
      if(nextMovableCircleIds[1] != -1){
        board.getCircleByCircleId(nextMovableCircleIds[1]).setChangeable();
        movable.add(nextMovableCircleIds[1]);
      }
    }

    observable.firePropertyChange("movable",false, true);
  }

  public ArrayList<Integer> getMovable() { return movable; }


  //이동 처리
  //말의 현재 위치와 목적지를 가져와 이동 수행
  //같은 위치에 있는 말들을 함께 이동 (쌓인 경우)
  //위치 정보 업데이트
  //원래 위치는 초기화 (resetCircle())
  public void move(int pieceId, int row, int column){
    Mal targetPiece = player.getPieceByPieceId(pieceId);
    Circle lastCircle;
    if(targetPiece.isOutOfBoard()) {
      lastCircle = board.getCircleByLocation(7, 7);
      lastCircle.addOccupyingPieces(targetPiece.getId());
    } else {
      lastCircle = board.getCircleByLocation(targetPiece.getRow(), targetPiece.getColumn());
    }
    Circle nextCircle = board.getCircleByLocation(row,column);

    for(int i : lastCircle.getOccupyingPieces()){
      Mal movePiece = player.getPieceByPieceId(i);
      movePiece.setLocation(nextCircle.getRow(), nextCircle.getColumn());
      nextCircle.addOccupyingPieces(i);
    }
    targetPiece.setOutOfBoard(false);
    nextCircle.setOccupied();
    lastCircle.resetCircle();
  }

  public int getInGameFlag(){
    return inGameFlag;
  }

  public void setInGameFlag(int flag){
    inGameFlag = flag;
    observable.firePropertyChange("turn change", false, true);
  }

  public void setBoardUnchangeable(){
    for(int i = 0; i < 29; i++){
      board.getCircleByCircleId(i).resetChangeable();
    }
    observable.firePropertyChange("unchange", true, false);
  }

  public void resetBoard(){
    for(int i = 0; i < 29; i++){
      board.getCircleByCircleId(i).resetCircle();
    }
  }

  public boolean addPlayerResult(int playerId, int result){
    if(player.getPlayerResult(playerId).add((Integer)result)){
      observable.firePropertyChange("add result", true, false);
      return true;
    }
    return false;
  }

  public boolean deletePlayerResult(int playerId, int target){
    if(player.getPlayerResult(playerId).remove((Integer)target)){
      observable.firePropertyChange("delete result", true, false);
      return true;
    }
    return false;
  }
}