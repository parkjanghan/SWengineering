package logic;

import java.util.ArrayList;

public class Player {
  //윷놀이 게임에서 각 플레이어가 소유한 말들과 윷 결과 정보를 관리하는 클래스
  //한마디로 말하면, 각 플레이어의 말 상태, 윷 결과, 승리 여부, 말의 위치 등을 추적
  private ArrayList<ArrayList<Mal>> players;
  private int pieceNumber;
  private int playerNumber;

  private ArrayList<ArrayList<Integer>> playerResult;

  Player(int playerNumber, int pieceNumber){
    this.playerNumber = playerNumber;
    this.pieceNumber = pieceNumber;
    players = new ArrayList<ArrayList<Mal>>();
    playerResult = new ArrayList<ArrayList<Integer>>();

    for(int i = 0; i < playerNumber; i++){
      ArrayList<Mal> pieces = new ArrayList<Mal>();
      for(int j = 0; j < pieceNumber; j++){
        pieces.add(new Mal(0,0,i,i*10+j)); //ID부여 방식
      }
      players.add(pieces);

      ArrayList<Integer> playerR = new ArrayList<Integer>();
      playerResult.add(playerR);
    }
  }

  public Mal getPieceByPieceId(int pieceId){
    if(pieceId/10 >= playerNumber && pieceId%10 >= pieceNumber){
      return null;
    }

    return players.get(pieceId/10).get(pieceId%10); //말 객체 조회
    //pieceId를 통해 말 객체를 찾음
    //10으로 나눈 몫 = 플레이어 번호
    //나머지 = 말 번호
  }
  public Mal getPieceByLocation(int row, int column){
    for(int i = 0; i < playerNumber; i++){
      for( Mal j : players.get(i)){
        if(j.getRow() == row && j.getColumn() == column){
          return j;
        }
      }
    }
    return null;
  } //보드의 위치로 말 객체를 찾음 -> 각 말의 row, column을 일일이 비교

  public ArrayList<Mal> getPlayerPieces(int playerId){
    return players.get(playerId);
  }
  //특정 플레이어가 가지고 있는 말 리스트를 반환


  //남은 말 개수 체크
  public int getLeftNumOfPieceOfPlayer(int playerId){
    try{
      int numOfLeftPieces = 0;
      for(Mal i : getPlayerPieces(playerId)){
        if(!i.isGone() && i.isOutOfBoard()){
          numOfLeftPieces++; //보드 밖에 있고, 잡히지 않은 말의 개수를 카운트
        }
      }
      return numOfLeftPieces;
    } catch (NullPointerException e){
    }
    return -1;
  }

  //승자 판별
  public int getWinnerPlayerId(){
    for(int i = 0; i < playerNumber; i++) {
      int numOfGonePiece = 0;
      for (Mal j : players.get(i)) {
        if (!j.isGone()) {
          numOfGonePiece++;
        }
      }
      System.out.println(numOfGonePiece);
      if(numOfGonePiece == 0){
        return i;
      }
    }
    return -1;
  }
  //말이 모두 보드에 없고, setGone() 상태면 → 해당 플레이어가 승자
  //즉, 모든 말을 골인시킨 상태 = isGone() == true

  public int getPieceIdFromOutOfBoard(int playerId){
    for(Mal i : getPlayerPieces(playerId)){
      if(i.isOutOfBoard()){
        if(!i.isGone())
          return i.getId();
      } //아직 잡히지 않았고, 보드 밖에 있는 말 중 하나를 찾아서 반환 -> 출전시킬 말 결정할 때 사용됨
    }
    return -1;
  }

  public ArrayList<Integer> getPlayerResult(int playerId) {
    return playerResult.get(playerId);
  }
  //특정 플레이어의 윷 결과 리스트를 반환
}
