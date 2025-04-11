package logic;

public class Clickable {
  private int id; //각 칸의 고유 식별자
  private int row;
  private int column;
  //2D 보드 좌표(화면상 위치와도 연동)
  private boolean clickable; //클릭 가능한 칸인지 여부
  private boolean changeable; //현재 말이 이동 가능한 칸인지 여부
  public int getId(){
    return id;
  }
  public void setId(int id){
    this.id = id;
  }

  public int getRow(){
    return row;
  }
  public int getColumn() {
    return column;
  }
  public void setLocation(int row, int column){
    this.row = row;
    this.column = column;
  }

  public void setClickable(){
    clickable = true;
  }
  public void resetClickable(){
    clickable =false;
  }
  public boolean isClickable(){
    return clickable;
  }

  public boolean isChangeable(){
    return changeable;
  }
  public void setChangeable(){
    changeable = true;
  }
  public void resetChangeable(){
    changeable = false;
  }

}
