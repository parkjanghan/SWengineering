package display;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;


public class BackGround extends JPanel{
  //게임 시작 화면의 배경을 표시하고, setting하는 class
  private BufferedImage img; //배경에 사용할 이미지 데이터를 저장하는 변수
  private int playerNumber = 2;
  private int pieceNumber = 2;
  //기본 설정값으로 player 수와 piece 수를 2로 지정

  public JButton enter = new JButton(); //게임을 시작할 때 눌러야 할 버튼 생성

  public JComboBox playerNumberInput;
  public JComboBox pieceNumberInput;
  //player 수와 piece 수를 선택할 수 있는 드롭다운 메뉴를 위한 컴포넌트 선언

  public void setGUI() { //UI 구성 요소들을 초기화하고 패널에 배치하는 메서드
    JLabel initialString = new JLabel("Start");

    initialString.setFont(new Font("Consolas", Font.BOLD, 40));
    initialString.setForeground(new Color(0x99191c));
    initialString.setVerticalAlignment(SwingConstants.CENTER);
    initialString.setHorizontalAlignment(SwingConstants.CENTER);
    //Start라는 라벨 생성 및 스타일 설정(폰트, 색상, 정렬)

    setLayout(new BorderLayout(10, 10));
    setBorder(new EmptyBorder(100 , 100, 100, 100));
    //전체 패널 레이아웃을 BorderLayout으로 설정하고 여백(100) 설정

    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false);
    buttonPanel.setLayout(new GridLayout(3, 1));
    buttonPanel.add(initialString);
    //버튼들과 텍스트를 담을 패널 생성
    //setOpaque(false)는 배경을 투명하게
    //GridLayout(3, 1)은 세로로 3개 칸을 나눠 UI 구성.
    //initialString을 첫 번째 칸에 추가.

    JPanel enterBtnPanel = new JPanel();
    enterBtnPanel.setOpaque(false);
    enterBtnPanel.setLayout(new BorderLayout());
    enterBtnPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    //버튼 전용 패널 설정. 여백 지정 및 투명 배경 처리.

    enter.setText("클릭!");
    //버튼 텍스트 설정

    Color pink = new Color(255,160,154);
    Color red = new Color(165, 8, 33);
    enter.setBackground(pink);
    enter.setForeground(Color.WHITE);
    enter.setBorder(new LineBorder(red));
    enter.setOpaque(true);
    //버튼 색상 설정: 배경은 분홍색, 글씨는 흰색, 테두리는 붉은색

    enterBtnPanel.add(enter);
    //버튼을 버튼 전용 패널에 추가

    JLabel playerInput = new JLabel("player 수");
    JLabel pieceInput = new JLabel("말의 갯수");
    buttonPanel.add(playerInput);
    buttonPanel.add(pieceInput);
    //드롭다운 메뉴 위에 설명용 라벨 추가

    String s1[] = { "2", "3", "4" };
    String s2[] = { "2", "3", "4", "5" };
    //콤보박스에 들어갈 선택지 배열 s1: 플레이어 수, s2: 말의 수

    playerNumberInput = new JComboBox(s1);
    pieceNumberInput = new JComboBox(s2);
    //콤보박스 생성 및 위 배열을 값으로 설정

    playerNumberInput.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == playerNumberInput) {
          playerNumber = Integer.parseInt((playerNumberInput.getSelectedItem()).toString());
          System.out.println((playerNumberInput.getSelectedItem()).toString());
        }
      }
    });
    //플레이어 콤보박스에 이벤트 리스너 추가.
    //선택 항목이 바뀌면 해당 숫자를 정수로 바꿔 playerNumber에 저장하고 출력

    pieceNumberInput.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == pieceNumberInput) {
          pieceNumber = Integer.parseInt((pieceNumberInput.getSelectedItem()).toString());
          System.out.println((pieceNumberInput.getSelectedItem()).toString());
        }
      }
    });
    //말 콤보박스에도 같은 방식으로 리스너 추가.



    buttonPanel.add(playerNumberInput, BorderLayout.SOUTH);
    buttonPanel.add(pieceNumberInput, BorderLayout.SOUTH);
    //콤보박스를 버튼 패널에 추가

    buttonPanel.add(initialString);
    buttonPanel.add(enterBtnPanel, BorderLayout.NORTH);
    //버튼 패널에 enter버튼 패널도 추가.

    add(buttonPanel);
    //전체 패널에 buttonPanel 추가.
  }

  //getter&setter
  public int getPlayerNumber(){
    return this.playerNumber;
  }
  public void setPlayerNumber(int playerNum){
    this.playerNumber=playerNum;
  }
  public int getPieceNumber(){
    return this.pieceNumber;
  }
  public void setPieceNumber(int pieceNum){
    this.pieceNumber=pieceNum;
  }
}
