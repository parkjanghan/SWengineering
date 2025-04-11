package display;

import logic.Process;
import logic.YutnoriSet;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseClick{
  private Color clickedColor = new Color(0x41bdb5);
  //클릭된 말의 배경 색(시각적 강조를 위해 넣음/사용자 편의)
  private Color backgroundColor;
  //이전 배경색을 저장해서 클릭 해제 시 원래 색으로 복원하기 위해 사용
  public ImagePanel firstClk, secondClk;
  //클릭된 말(버튼)을 저장하는 변수
  boolean isClicked;
  //클릭 상태를 추적하는 플래그
  //말이 하나 선택된 상태인지 여부를 나타냄
  private Process pc;
  private YutnoriSet yutset;
  //게임 전체의 흐름 제어 객체(pc)와 현재 게임 상태 객체(yutset)

  public MouseClick(final YutnoriSet yutSet) {
    firstClk = null;
    secondClk = null;
    isClicked = false;
    yutset = yutSet;
  }
  //YutnoriSet 객체를 받아 저장
  //처음에는 아무 말도 클릭되지 않은 상태로 초기화

  public void initVars() {
    firstClk = null;
    secondClk = null;
  }
  //클릭 상태 초기화

  public void getProcessController(Process pc) {  this.pc = pc; }
  //외부에서 ProcessController를 주입하기 위한 메서드

  //**말 선택 처리 - 첫번째 클릭
  public void firstClickSetup(int row, int column) {
    initVars();
    //첫번째 클릭이 발생하면 클릭 상태 초기화부터 시작
    if(column == 0) { //colum == 0이면 아직 bord에 나가지 않은 말(시작지점) 선택
      pc.selectOutOfBoardPieceProcess(row);
      firstClk = TotalManage.beginPiece[row];
      isClicked = true;
      return;
      //ProcessController를 통해 시작 위치 말 선택 로직 수행
      //해당 말을 firstClk에 저장하고 클릭 상태 플래그를 설정
    } else {
      pc.selectInTheBoardPieceProcess(row, column);
      firstClk = TotalManage.btn[row][column];
      firstClk.setBackground(clickedColor);
      firstClk.repaint();
      isClicked = true;
      return;
      //그 외의 경우엔 이미 판 위에 올라간 말이므로 일반 위치 말 처리
      //해당 말의 배경색을 클릭 색으로 바꾸고 repaint.
    }
  }

  //**말 선택 처리 - 두번째 클릭
  public void secondClickSetup(int row, int column) {
    firstClk.setBackground(backgroundColor);
    firstClk.repaint();
    //첫 번째 클릭된 말의 배경색 복원.
    if (!yutset.getMovable().contains(yutset.getBoard().getCircleByLocation(row, column).getId())) {
      initVars();
      //선택된 위치가 이동 가능한 위치가 아니면 초기화
    }
    if (pc.yutnoriSet.getInGameFlag() == 0) {
      initVars();
    } else {
      pc.movePieceProcess(row, column);
    }

    isClicked = false;
    //게임이 아직 시작되지 않았으면 아무 동작도 하지 않음
    //이동 가능하면 movePieceProcess()로 이동 처리 후 클릭 상태 초기화
  }

  public void mouseInput(MouseEvent e) { //마우스 클릭 이벤트 발생 시 실행됨
    //시작 위치의 말 클릭 처리
    for(int i = 0; i< TotalManage.beginPiece.length; i++) {
      if (e.getSource().equals(TotalManage.beginPiece[i])) {
        firstClickSetup(i, 0);
      }
      // 시작 위치에 있는 말을 클릭한 경우 -> firstClickSetup()호출
    }

    //윷 던지기 버튼 클릭 처리
    if (e.getSource().equals(TotalManage.yutBtn)) {
      pc.rollYutProcess();
    }
    //윷 던지기 버튼 클릭 시 윷을 던지는 처리 실행

    //윷 결과 버튼 처리
    for (int i = 0; i < TotalManage.resButtonLength; i++) {
      String result = TotalManage.resButton[i].getText();
      int removeNum = -1;
      if(e.getSource().equals(TotalManage.resButton[i])) {
        //결과 선택 버튼 중 클릭된 것을 탐색
        switch (result) {
          case "빽도": removeNum = 0;break;
          case "도": removeNum = 1;break;
          case "개": removeNum = 2;break;
          case "걸": removeNum = 3;break;
          case "윷": removeNum = 4;break;
          case "모": removeNum = 5;break;
          //각 윺 결과 텍스트를 숫자로 변환
        }

        System.out.println("this is sensation " + removeNum);
        pc.multiPossibleEnd(removeNum);
        TotalManage.d.setVisible(false);
        TotalManage.d.removeAll();
        TotalManage.resButtonLength = 0;
        //결과에 따라 가능한 이동 처리 후, 결과 버튼 관련 컴포넌트를 제거 및 초기화
      }
    }

    //테스트용 윷 버튼 처리
    for (int i = 0; i< TotalManage.testYutBtn.length; i++) {
      if (e.getSource().equals(TotalManage.testYutBtn[i])) {
        pc.rollYutTest(i);
      }
    }
    //테스트 모드에서 특정 윷 결과를 강제로 실행시킴

    for(int i=1; i<8; i++) {
      for(int j=1; j<8; j++) {
        if(e.getSource().equals(TotalManage.btn[i][j])) {
          if (!isClicked) {
            firstClickSetup(i, j);
          } else {
            secondClickSetup(i, j);
          }
        }
      }
    }
    //보드판의 각 위치에서 클릭 여부 판단 후
    //-> 클릭된 적이 없으면 첫번째 클릭 처리
    //-> 이미 클릭되어 있으면 두번째 클릭 처리
  }
}
