package display;

import logic.YutnoriSet;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class UIclick implements MouseListener{
  //마우스 클릭 이벤트를 받아서 MouseClick class의 메서드를 호출하는 class
  MouseClick mouseClick; //실제 마우스 클릭을 처리할 핵심 클래스인 MouseClick 객체를 참조

  public UIclick(final YutnoriSet yutSet) {
    mouseClick = new MouseClick(yutSet);
  }
  @Override
  public void mouseClicked(MouseEvent e) {
    mouseClick.mouseInput(e);
  }
  //마우스를 클릭하면, mouseClick.mouseInput(e)를 호출하여 처리
  //어떤 버튼을 클릭했는지 분석하고 그에 맞는 동작을 수행
  @Override
  public void mousePressed(MouseEvent e) { //사용안함
  }
  @Override
  public void mouseReleased(MouseEvent e) { //사용안함
  }
  @Override
  public void mouseEntered(MouseEvent e) { //사용안함
  }
  @Override
  public void mouseExited(MouseEvent e) { //사용안함
  }
}
