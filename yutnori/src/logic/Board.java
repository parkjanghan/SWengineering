package logic;

import java.util.ArrayList;
public class Board {
  //윷놀이 보드판의 논리 구조를 담당하는 클래스
  //게임판에 존재하는 말의 칸들을 Circle 객체들로 저장
  //row, column 좌표 또는 id로 해당 칸을 검색할 수 있게 해 주는 구조

  public final int BOARDSIZE = 29; //전체 보드의 칸 수
  private ArrayList<Circle> boards;
  //Circle 객체를 저장하는 리스트, 말판의 논리적 위치(row, column)와 ID를 과리

  Board(){
    int id = 0;
    boards = new ArrayList<Circle>(0);

    //각 Circle은 id, row, column을 가진다.
    //예를 들어, new Circle(0, 7, 7) 은 (7, 7) 위치에 ID 0인 칸을 만든다는 의미.
    //배치 순서: 오른쪽 아래 -> 위 -> 왼쪽 -> 아래 -> 오른쪽
    boards.add(new Circle(id++, 7, 7));
    boards.add(new Circle(id++, 6, 7));
    boards.add(new Circle(id++, 5, 7));
    boards.add(new Circle(id++, 3, 7));
    boards.add(new Circle(id++, 2, 7));
    boards.add(new Circle(id++, 1, 7));

    boards.add(new Circle(id++, 1, 6));
    boards.add(new Circle(id++, 1, 5));
    boards.add(new Circle(id++, 1, 3));
    boards.add(new Circle(id++, 1, 2));
    boards.add(new Circle(id++, 1, 1));

    boards.add(new Circle(id++, 2, 1));
    boards.add(new Circle(id++, 3, 1));
    boards.add(new Circle(id++, 5, 1));
    boards.add(new Circle(id++, 6, 1));
    boards.add(new Circle(id++, 7, 1));

    boards.add(new Circle(id++, 7, 2));
    boards.add(new Circle(id++, 7, 3));
    boards.add(new Circle(id++, 7, 5));
    boards.add(new Circle(id++, 7, 6));

    boards.add(new Circle(id++, 6, 6));
    boards.add(new Circle(id++, 5, 5));
    boards.add(new Circle(id++, 4, 4));
    boards.add(new Circle(id++, 3, 3));
    boards.add(new Circle(id++, 2, 2));

    boards.add(new Circle(id++, 2, 6));
    boards.add(new Circle(id++, 3, 5));
    boards.add(new Circle(id++, 5, 3));
    boards.add(new Circle(id++, 6, 2));
  }

  public Circle getCircleByLocation(int row, int column){
    for(Circle i : boards){
      if(i.getRow() == row && i.getColumn() == column)
        return i;
    }
    return null;
  }
  //(row, column) 좌표를 기반으로 해당 위치에 있는 칸(Circle)을 찾아서 반환
  //없으면 null을 리턴함 → TotalManage 클래스에서 이걸로 유효한 칸인지 판단

  public Circle getCircleByCircleId(int circleId){
    return boards.get(circleId);
  }
  //Circle의 ID를 기준으로 바로 가져옴.
  //(boards 리스트는 ID 순서대로 들어갔기 때문에 인덱스로 바로 접근 가능)


}
