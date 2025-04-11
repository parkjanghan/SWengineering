package logic;

public class YutSet {
  //윷놀이에서 사용하는 윷 4개 전체를 관리하고, 윷을 던져서 결과를 계산해주는 클래스
  Yut[] yutSet; //윷 4개를 저장하는 배열
  final int YUTSETSIZE = 4; //윷 개수(4개 고정)

  public YutSet(){
    yutSet = new Yut[4];
    for(int i = 0; i < YUTSETSIZE; i++){
      yutSet[i] = new Yut();
    }
  }
  //윷 4개를 새로 만들어 배열에 저장해줌
  //각 Yut 객체는 독립적으로 랜덤한 앞/뒤 상태를 가짐

  //윷을 4개 던지고, 그 결과를 바탕으로
  //도(1), 개(2), 걸(3), 윷(4), 모(5), 빽도(0) 중 하나를 숫자로 반환하는 메서드
  public int rollYut(){
    int cal = 0;
    for(int i = 0; i < YUTSETSIZE-1; i++){
      if(!yutSet[i].throwYut()){
        cal++;
      }
    }

    if(!yutSet[YUTSETSIZE-1].throwYut()){
      if(cal == 0){
        return 0;
      }
      cal++;
    }

    return cal+1;
  }
}
