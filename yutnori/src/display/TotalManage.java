package display;


import logic.Process;
import logic.YutnoriSet;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class TotalManage {
  //게임 전체의 GUI를 설정 및 관리하는 class
  final int FRAME_WIDTH = 1500, FRAME_HEIGHT = 1000; //프레임 기본 크기 설정

  public BackGround midPanel;
  public JFrame mainFrame, initFrame, exitFrame;
  //중앙 시작 화면, 메인 게임 화면, 게임 종료 화면 용 프레임.

  public static ImagePanel[][] btn;
  static JButton[] testYutBtn;
  public JPanel yutBoard;
  //게임 보드 위 말 위치들을 담을 패널 배열 btn, 테스트 버튼, 보드 영역 yutBoard

  YutnoriSet yutnoriset;
  MalManage pieceSprite;
  //게임 로직 상태와 말 스프라이트를 다루는 클래스

  JLabel player[];
  static ImagePanel[] beginPiece;
  static JButton yutBtn;
  //플레이어 이름, 말 시작 위치 이미지, 윷 던지기 버튼

  private UIclick clickAction;
  //마우스 클릭 이벤트 처리기

  private JLabel [] numberOutOfPiece;
  //각 플레이어가 남은 말 수를 나타내는 라벨 배열

  private JLabel playerStatus;
  private JLabel turnStatus;
  private JLabel playerMovePrompt;
  private JLabel playerMovable;
  //게임 상태 정보를 타나내는 다양한 라벨들

  private JLabel[][] groupingNum;
  //보드의 각 위치에 놓인 말의 개수를 표시할 라벨들

  public JPanel dialogPanel; // 윷결과 선택 팝업에 사용될 패널

  static public JButton [] resButton;
  static int resButtonLength;
  static public JDialog d;
  //팝업에 표시되는 윷 결과 버튼들, 그 개수, 팝업 다이얼로그 자체

  JPanel statusPanels;
  JPanel yutButtonPanels;
  //게임 상태 영역과 윷 버튼을 표시하는 영역을 위한 패널들

  //생성자
  public TotalManage(final YutnoriSet yutSet) {
    midPanel = new BackGround();
    yutnoriset = yutSet;
    //시작 화면용 배경 패널 생성, 게임 상태 저장

    mainFrame = new JFrame("Mode Selection");
    initFrame = new JFrame("Game View");
    exitFrame = new JFrame("Exit View");
    // 세가지 메인 창 생성(게임, 시작, 종료)

    yutBoard= new JPanel();
    btn = new ImagePanel[8][8];
    //보드 패널과 보드 위 버튼들(7X7)을 위한 배열 생성

    clickAction = new UIclick(yutSet);
    //마우스 클릭 이벤트 처리기 생성

    yutBtn = new JButton("윷 던지기");
    yutBtn.addMouseListener(clickAction);
    //윷 던지기 버튼 생성하고 클릭 리스너 연결

    pieceSprite = new MalManage(); //말 이미지 불러오는 클래스
    testYutBtn = makeTestYutBtn(); //테스트용 윷 버튼들 생성

    playerStatus = new JLabel();
    turnStatus = new JLabel();
    playerMovePrompt = new JLabel();
    playerMovable = new JLabel();
    //상태 출력용 라벨

    groupingNum = new JLabel[8][8]; //각 보드 칸에 표시할 말 개수용 라벨 배열

    dialogPanel = new JPanel();
    resButtonLength = 0;
    statusPanels = new JPanel();
    yutButtonPanels = new JPanel();
  }

  public void pcBridge(Process pc) { clickAction.mouseClick.getProcessController(pc); }
  //ProcessController 객체를 클릭 이벤트 처리기와 연결시킴


  //게임 종료시 나오는 프레임 설정
  //승자 출력 + 다시시작 / 종료 버튼
  public void setupExitGUI() {
    exitFrame.setSize(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
    exitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    exitFrame.setLayout(new BorderLayout(10, 10));
    exitFrame.setLocationRelativeTo(null);

    JPanel exitP = new JPanel();
    exitP.setLayout(new GridLayout(2, 0));
    JPanel selection = new JPanel();
    selection.setLayout(new GridLayout(0,2));

    JLabel winner = new JLabel();
    String winnerText = "승리자는 Player" + (yutnoriset.getPlayer().getWinnerPlayerId() + 1);
    winner.setText(winnerText);
    winner.setFont(new Font("돋움",Font.PLAIN, 30));
    winner.setHorizontalAlignment(JLabel.CENTER);
    exitP.add(winner);

    JButton restart = new JButton();
    restart.setText("다시하기");
    JButton exit = new JButton();
    exit.setText("게임종료");
    restart.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        exitFrame.dispose();
        setupStartUI();
      }
    });

    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });


    selection.add(restart);
    selection.add(exit);
    exitP.add(selection);
    exitFrame.add(exitP, BorderLayout.CENTER);

    mainFrame.setVisible(false);
    exitFrame.setVisible(true);
  }

  //숫자를 도, 개 등의 문자열로 바꿈
  private String getYutType(int yut) {
    String res = "";
    switch(yut) {
      case 0: res = "빽도";break;
      case 1: res = "도";break;
      case 2 : res = "개";break;
      case 3: res = "걸";break;
      case 4: res = "윷";break;
      case 5: res = "모";break;
    }
    return res;
  }

  //플레이어의 윷 결과 중 유효한 것들만 버튼으로 만들어 팝업 창에 보임
  public void popUp(int curPlayerID, int lowerBound) {
    ArrayList<Integer> res = new ArrayList<>(); //조건을 만족하는 윷 결과만 저장할 리스트 생성
    for(int i : yutnoriset.getPlayer().getPlayerResult(curPlayerID)){
      //해당 플레이어가 현재 가지고 있는 윷 결과들을 하나씩 순회
      if(lowerBound <= i){
        res.add(i);
      }
      //조건(lowerBound 이상)을 만족하는 결과만 res에 추가
    }
    dialogPanel.removeAll(); //이전에 만들어진 버튼이나 내용 제거해서 새로 만들 준비
    d = new JDialog(mainFrame, "Select yut res");//팝업 창 생성, 부모 창은 mainFrame
    JLabel l = new JLabel("Select yut res");//팝업 상단에 표시할 안내 텍스트 라벨 생성


    resButton = new JButton[res.size()]; //선택 가능한 결과 수만큼 버튼 배열 생성
    for(int i = 0; i<res.size(); i++) { //각각의 결과에 대해 버튼을 생성하는 반복문
      resButton[i] = new JButton(Integer.toString(res.get(i)));
      //기본적으로 숫자를 문자열로 변환해서 표시
      resButton[i].setText(getYutType(res.get(i)));
      //숫자를 윷타입으로 바꿔 버튼 텍스트 지정
      resButton[i].addMouseListener(clickAction);
      //각 버튼에 마우스 클릭 리스너 연결 -> 클릭하면 이벤트 발생
      dialogPanel.add(resButton[i]); //버튼을 팝업 패널에 추가
    }
    d.add(l); //안내 라벨을 다이얼로그에 추가
    d.add(dialogPanel); //결과 선택버튼들이 들어있는 패널도 다이얼로그에 추가
    d.setSize(200, 200); //다이얼로그 크기 설정
    d.setLocation(200, 200); //화면상 위치 설정
    d.setVisible(true); //다이얼로그를 실제로 보이게
    d.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    //사용자가 창을 닫아도 아무 일도 일어나지 않도록 설정

    d.repaint();
    mainFrame.repaint();
    //강제로 UI 다시 그림

    resButtonLength = res.size();
    //생성된 버튼 수를 전역 변수에 저장
    //다른 클래스에서 버튼 처리할 때 몇 개 있는지 알 수 있게
  }

  //말의 위치와 상태에 따라 보드를 다시 그림
  //남은 말의 수와 플레이어 상태 등을 업데이트
  public void boardRepaint() {
    for(int i = 1; i < 8; i++) {
      for (int j = 1; j < 8; j++) {

        if(yutnoriset.getBoard().getCircleByLocation(i,j) == null){
          continue;
        } //말 판에 존재하지 않는 위치일 경우(빈 칸)은 건너뜀

        if(yutnoriset.getBoard().getCircleByLocation(i,j).isOccupied()) { //말이 하나 이상이면,
          int numPiece = yutnoriset.getBoard().getCircleByLocation(i,j).getNumOfoccupyingPieces();
          //몇 개의 말이 있는지 개수 저장
          int pieceID = yutnoriset.getBoard().getCircleByLocation(i,j).getOccupyingPieces().get(0);
          //첫번째 말의 ID를 가져옴
          int playerID = pieceID / 10;
          //플레이어 ID 추출: 10으로 나눈 몫이 플레이어 번호
          BufferedImage[] pieceList = pieceSprite.pieceList;
          btn[i][j].setImage(pieceList[playerID]);
          //해당 플레이어의 말 이미지로 버튼 이미지 설정
          groupingNum[i][j].setText(Integer.toString(numPiece));
          //몇 개의 말이 있는지 라벨에 숫자로 표시
          groupingNum[i][j].setForeground(Color.BLACK);
          groupingNum[i][j].setHorizontalTextPosition(JLabel.CENTER);
          groupingNum[i][j].setVerticalTextPosition(JLabel.CENTER);
          //말 개수 텍스트의 스타일 설정(중앙 정렬, 검은색 글씨)
        } else {
          groupingNum[i][j].setText("");
          btn[i][j].setImage(null);
          //말이 없는 경우 초기화
        }

        if (yutnoriset.getBoard().getCircleByLocation(i, j).isChangeable()) {
          btn[i][j].setBackground(Color.YELLOW); //말 이동이 가능한 칸 -> 노란색
        } else {
          btn[i][j].setBackground(Color.decode("#8f784b")); //아니면 -> 기본색(갈색)
        }
        groupingNum[i][j].repaint();
        btn[i][j].repaint();
        //말 개수 라벨과 이미지 패널을 다시 그림
      }
    }
    for(int i = 0;i <  numberOutOfPiece.length; i++) {
      numberOutOfPiece[i].setText(Integer.toString(yutnoriset.getPlayer().getLeftNumOfPieceOfPlayer(i)));
      numberOutOfPiece[i].repaint();
    } //각 플레이어가 보드 밖에 가지고 있는 말의 개수를 다시 계산해서 표시

    if (yutnoriset.getInGameFlag() == 0) {
      playerStatus.setText("Player 상태 : 윷을 던지세요.");
    } else if (yutnoriset.getInGameFlag() == 1) {
      playerStatus.setText("Player 상태 : 말을 선택하세요.");
    } else if (yutnoriset.getInGameFlag() == 2) {
      playerStatus.setText("Player 상태 : 말을 움직이세요.");
    } //게임 상태 플래그 값에 따라 플레이어 상태 텍스트 갱신.

    turnStatus.setText("turn : Player " + (yutnoriset.getPlayerTurn() +1 ) + "  차례입니다.");
    //현재 턴인 플레이어 번호 출력
    playerMovePrompt.setText("Player " + (yutnoriset.getPlayerTurn() +1 ) + "의 결과 : ");
    //현재 플레이어의 윷 결과 설명 시작 텍스트 출력
    playerMovable.setText("");
    for(int i : yutnoriset.getPlayer().getPlayerResult(yutnoriset.getPlayerTurn())){
      playerMovable.setText(playerMovable.getText() + getYutType(i) + " ");
    } //현재 플레이어가 가진 윷 결과들을 모두 나열해서 출력

    playerStatus.repaint();
    turnStatus.repaint();
    playerMovePrompt.repaint();
    playerMovable.repaint();
    //텍스트 라벨들을 다시 그림(화면에 최신 내용이 반영될 수 있도록)

  }

  //게임 모델에 변화가 생겼을 때 boardRepaint()호출
  public class ModelChangeListener implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      boardRepaint();
    }
  }

  public ModelChangeListener modelListner  = new ModelChangeListener();

  //테스트 버튼을 생성하고 리스너 연결
  private JButton[] makeTestYutBtn() {
    JButton [] test = new JButton[6];
    test[0] = new JButton("Test [백도]");
    test[1] = new JButton("Test [도]");
    test[2] = new JButton("Test [개]");
    test[3] = new JButton("Test [걸]");
    test[4] = new JButton("Test [윷]");
    test[5] = new JButton("Test [모]");

    for (int i =0; i<6; i++) {
      test[i].setPreferredSize(new Dimension(100, 20));
      test[i].addMouseListener(clickAction);
    }
    return test;
  }

  //시작 화면 구성 및 보이게 하기
  public void setupStartUI(){
    initFrame.setSize(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
    initFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    initFrame.setLayout(new BorderLayout(10, 10));
    initFrame.setLocationRelativeTo(null);

    midPanel.setGUI();

    initFrame.add(midPanel);
    initFrame.setVisible(true);
  }

  public void setupYutGUI(int playerNumber, int pieceNumber) {
    mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT); //크기 설정
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창을 닫으면 프로그램 종료
    mainFrame.setLocationRelativeTo(null); //창을 화면 중앙에 배치

    Container contentPane = mainFrame.getContentPane();
    contentPane.setLayout(new BorderLayout(10, 10));
    //전체 레이아웃을 BoardLayout으로 설정

    yutBoard.setLayout(new GridLayout(7, 7)); //7X7 보드판
    yutBoard.setBackground(Color.WHITE); //흰색배경
    yutBoard.setBorder(new EmptyBorder(30, 30, 30, 30)); //여백
    yutBoard.removeAll();
    yutBoard.repaint();

    for(int i = 1; i < 8; i++) {
      for(int j = 1; j < 8; j++) {
        btn[i][j] = new ImagePanel();
        btn[i][j].setOpaque(true);
        //ImagePanel을 생성해서 각 위치에 배치, 배경 투명 아님.
        if (yutnoriset.getBoard().getCircleByLocation(i, j) != null) {
          if ((yutnoriset.getBoard().getCircleByLocation(i, j).isClickable())) {
            //이동 가능하다면,
            btn[i][j].setBackground(Color.decode("#FFFFFF")); //배경을 흰색으로 설정
            btn[i][j].setBorder(new CompoundBorder(
                    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK),
                    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK)
                    //테두리 설정(검정+검정)
            ));
            groupingNum[i][j] = new JLabel();
            btn[i][j].add(groupingNum[i][j]);
            //해당 위치에 있는 말 개수 표시할 라벨 추가

            btn[i][j].addMouseListener(clickAction);
            //마우스 클릭 리스너 추가(말 선택/이동용)
          }
          else {
            btn[i][j].setBackground(Color.WHITE);
          } //클릭 불가능한 위치는 단순히 흰색 배경만 지정
        }
        yutBoard.add(btn[i][j]);
        btn[i][j].repaint();
        //보드판에 버튼 추가하고 강제로 다시 그림
      }
    }

    statusPanels.removeAll();
    statusPanels.setLayout(new GridLayout(2, 0));
    statusPanels.setBorder(new EmptyBorder(0, 30, 0, 30));
    //오른쪽 상태 표시 패널 초기화 및 레이아웃 설정

    yutButtonPanels.removeAll();
    yutButtonPanels.setLayout(new GridLayout(0, 1));
    yutButtonPanels.setBorder(new EmptyBorder(0, 30, 0, 30));
    //왼쪽 버튼 영역 초기화 및 설정(윷 버튼, 결과, 테스트 버튼 등등)

    yutButtonPanels.add(yutBtn); //윷 던지기 버튼 추가

    BufferedImage[] pieceList = pieceSprite.pieceList;
    setPiecePanel(pieceList, clickAction);
    //말 이미지 배열을 가져와서 각 플레이어의 시작 말 패널 설정

    setPlayerLabel(); //player1 등의 라벨을 생성해서 player[] 배열에 넣음

    for(int i=0; i<6; i++) {
      yutButtonPanels.add(testYutBtn[i]);
    } //윷 테스트 버튼 6개 모두 왼쪽에 추가

    numberOutOfPiece = new JLabel[playerNumber];
    //플레이어 수에 막제 남은 말 수 표시용 라벨 배열 생성

    JPanel selectP = new JPanel();
    selectP.setLayout(new GridLayout(playerNumber, 3));
    //오른쪽 하단: 각 플레이어의 라벨, 말 아이콘, 남은 말 수 표시할 그리드 패널 생성

    JPanel stateP = new JPanel();
    stateP.setLayout(new GridLayout(4 , 0));
    stateP.add(playerStatus);
    stateP.add(turnStatus);
    stateP.add(playerMovePrompt);
    stateP.add(playerMovable);
    statusPanels.add(stateP);
    //오른쪽 상단: 게임 상태 정보를 출력하는 라벨 배치

    for (int i=0; i< playerNumber; i++) {
      selectP.add(player[i]);
      selectP.add(beginPiece[i]);
      numberOutOfPiece[i] = new JLabel(Integer.toString(yutnoriset.getPlayer().getLeftNumOfPieceOfPlayer(i)));
      selectP.add(numberOutOfPiece[i]);
    } //플레이어 수만큼 반복해서 각 UI 요소 배치

    statusPanels.add(selectP); //오른쪽 하단 상태 영역에 selectP 추가


    contentPane.add(yutButtonPanels,  BorderLayout.LINE_START); //왼쪽
    contentPane.add(statusPanels, BorderLayout.LINE_END); //오른쪽
    contentPane.add(yutBoard, BorderLayout.CENTER); //중앙
    mainFrame.setVisible(true); //창을 화면에 표시
  }

  //플레이어 라벨을 생성
  private void setPlayerLabel() {
    JLabel player1 = new JLabel("Player 1");
    JLabel player2 = new JLabel("Player 2");
    JLabel player3 = new JLabel("Player 3");
    JLabel player4 = new JLabel("Player 4");
    player = new JLabel[] { player1, player2, player3, player4};
  }

  //각 플레이어의 시작 위치 말 이미지 세팅 및 리스너 연결
  private void setPiecePanel(BufferedImage[] pieceList, UIclick clickAction) {
    ImagePanel piece1 = new ImagePanel();
    ImagePanel piece2 = new ImagePanel();
    ImagePanel piece3 = new ImagePanel();
    ImagePanel piece4 = new ImagePanel();

    piece1.setImage(pieceList[0]);
    piece2.setImage(pieceList[1]);
    piece3.setImage(pieceList[2]);
    piece4.setImage(pieceList[3]);

    piece1.addMouseListener(clickAction);
    piece2.addMouseListener(clickAction);
    piece3.addMouseListener(clickAction);
    piece4.addMouseListener(clickAction);

    beginPiece = new ImagePanel[] { piece1, piece2, piece3, piece4};
  }
}