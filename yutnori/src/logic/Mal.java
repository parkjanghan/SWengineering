package logic;


public class Mal extends Clickable {
  //윷놀이에서 하나의 말(말 조각) 을 표현하는 클래스
  //말 하나하나가 이 클래스로 생성돼서, 누가 소유한 말인지, 보드 안에 있는지, 현재 위치가 어딘지를 관리
  //Clickable을 상속해서 말도 id, row, column, clickable 상태를 가짐
  private int ownerId; //이 말이 어떤 플레이어의 말인지
  private boolean outOfBoard; //말이 현재 보드 밖에 있는지 여부(시작 전/잡힌 상태)

  final int defaultRow = 0;
  final int defaultColumn = 0;
  //말의 초기 위치 (보드판 밖)로 리셋할 때 사용

  Mal(int row, int column, int ownerId, int pieceId) {
    setId(pieceId); //클릭 가능 객체 ID 설정
    this.ownerId = ownerId;
    setLocation(row, column);
    this.setClickable(); //기본적으로 클릭 가능 상태로 시작
    outOfBoard = true; //처음에는 판 밖에 있음
  }

  public int getOwnerId(){
    return ownerId;
  } //소유자 ID 관련/ 이 말의 주인이 누구인지 반환

  public void setGone(){
    resetClickable();
  } //말을 비활성화 상태로 만듦

  public boolean isGone(){
    return !isClickable();
  } //말이 사라졌는지 확인

  //말 초기화 관련
  public void resetPieceToOrigin(){
    this.setLocation(defaultRow, defaultColumn);
    outOfBoard = true;
  }
  //말의 위치를 (0,0)으로 되돌리고, 다시 보드 밖 상태로 설정
  //게임 리셋할 때 또는 잡혔을 때 사용

  public boolean isOutOfBoard(){
    return outOfBoard;
  }
  public void setOutOfBoard(boolean value){
    outOfBoard = value;
  }
  //말이 현재 보드 안에 있는지, 대기 중인지(=보드 밖) 확인/설정

}
