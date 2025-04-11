package logic;

import java.util.ArrayList;

public class Circle extends Clickable { //클릭이 가능한 보드 칸
  //이 클래스는 윷놀이 보드의 한 칸(말판)을 나타내는 논리 단위
  //게임에서 말이 실제로 올라가 있는지를 판단하고,
  //몇 개의 말이 있는지, 누가 올라가 있는지,
  //말을 추가하거나 제거하는 작업 등을 이 클래스에서 다루고 있음

  private boolean occupied; //현재 이 칸에 말이 하나라도 있는지 여부
  private ArrayList<Integer> occupyingPieces; //이 칸에 올라가 있는 말들의 ID 목록
  private int numOfoccupyingPieces; //말의 개수


  Circle(int circleId, int row, int column){ //이 칸의 ID와 위치 정보를 받아옴
    setId(circleId); //Clickable 상속
    setLocation(row,column); //위치 설정
    this.occupyingPieces = new ArrayList<Integer>(); //이 칸에 있는 말 ID들을 저장
    this.setClickable(); //이 칸은 클릭 가능
    occupied = false;
    numOfoccupyingPieces = 0;
  }

  public boolean isOccupied(){
    return occupied;
  } //현재 말이 하나라도 있는지
  public void setOccupied(){
    occupied = true;
  } //말이 올라왔을 때 호출(한 마리라도 올라가면 호출)
  public void resetOccupied(){
    occupied = false;
  } //말이 없어졌을 때 호출(말이 다 빠졌을 때)
  public ArrayList<Integer> getOccupyingPieces(){
    return occupyingPieces;
  }
  public void addOccupyingPieces(int pieceId){ //말 하나가 이 칸에 도착했을 때 호출
    occupyingPieces.add(pieceId);
    numOfoccupyingPieces++;
  }
  public void clearOccupyingPieces(){
    occupyingPieces.clear();
  } //모든 말을 이 칸에서 제거(잡았을 때나 이동했을 때)
  public int getNumOfoccupyingPieces(){
    return numOfoccupyingPieces;
  }
  public void resetNumOfoccupyingPieces(){
    numOfoccupyingPieces = 0;
  }
  public void resetCircle(){ //말이 전부 이동하거나 잡혔을 때 사용하는 함수
    resetOccupied();
    resetNumOfoccupyingPieces();
    clearOccupyingPieces();
  }

}
