package logic;

import java.util.Random;

public class Yut {
  // 윷놀이에서 사용하는 윷 하나를 나타내는 클래스
  //윷 하나의 상태 (앞/뒤) 를 나타냄

  private boolean status; //윷이 앞면인지(false) 뒷면인지(true)
  Random generate = new Random();

  Yut(){
    status = true;
  } //윷 하나를 만들 때 기본 상태는 true (뒷면)로 초기화됨

  //랜덤하게 앞 또는 뒤를 결정하는 기능을 제공
  public boolean throwYut(){
    status = generate.nextBoolean();
    return status;
  }
}
