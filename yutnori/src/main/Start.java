package main;

import logic.Process;
import logic.YutnoriSet;
import display.TotalManage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import display.BackGround;

public class Start {
    public static Process processController; //게임 진행 로직을 담당할 컨트롤러

    public static void main(String[] args) throws IOException {
        final YutnoriSet yutSet = new YutnoriSet(); //게임 전체 상태를 담는 객체 생성
        TotalManage yutgui = new TotalManage(yutSet); //GUI 전체를 관리할 객체 생성
        yutSet.addObserver(yutgui.modelListner);
        //게임 전체 상태가 변경될 때마다 GUI가 자동으로 갱신되도록 리스너 등록

        yutgui.setupStartUI(); //시작 UI 설정 및 보여줌

        BackGround midP =yutgui.midPanel;
        //TotalManage 내부의 BackGround 객체를 로컬 변수로 꺼내서 쓰기 편하게 저장

        yutgui.midPanel.enter.addActionListener(new ActionListener() { //enter버튼을 클릭시 실행
            @Override
            public void actionPerformed(ActionEvent e) {
                yutgui.initFrame.setVisible(false); //시작 창을 숨김
                if (e.getSource().equals(midP.enter)) { //재차 확인
                    yutSet.setPlayer(midP.getPlayerNumber(), midP.getPieceNumber());
                    //플레이어 수 및 말의 수를 YutnoriSet에 설정(게임 상태 초기화)
                    yutgui.setupYutGUI(midP.getPlayerNumber(), midP.getPieceNumber());
                    //본 게임 GUI 설정 시작
                    yutSet.setPlayerTurn(0);
                    //첫번째 플레이어의 턴으로 설정
                    processController = new Process(yutSet, yutgui);
                    //게임 로직 처리 객체 생성
                    yutgui.pcBridge(processController);
                    //클릭리스너에 processController를 연결해서, 클릭 이벤트가 게임 로직으로 전달되도록 함
              }
            }
        });
        return;
    }
}

