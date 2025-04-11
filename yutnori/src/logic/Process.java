package logic;

import display.TotalManage;

public class Process {
  //게임 진행 로직을 제어하는 메인 컨트롤러 클래스 선언
  public TotalManage yutGui;
  public YutnoriSet yutnoriSet;
  public int currentTurn = 0; //현재 턴을 진행 중인 플레이어의 ID
  public int numCanMove = 0; //이동 가능한 횟수
  public int catchPoint = 0; //말을 잡았을 때 증가하는 포인트, 잡으면 윷을 한번 더 던질 수 있음.
  public int chosenPiece = -1; //현재 선택된 말의 ID, 말이 선택되지 않았을 경우 -1

  public Process(YutnoriSet set, TotalManage gui) {
    yutnoriSet = set;
    yutnoriSet.setPlayerTurn(0);
    yutnoriSet.setInGameFlag(0);
    //첫번째 플레이어로 시작, 현재 상태를 "윷 던지기 전"으로 설정
    yutGui = gui; //GUI 객체 저장
  }

  //테스트용 윷 결과를 강제로 지정해 입력
  public void rollYutTest(int res) {
    if (yutnoriSet.getInGameFlag() == 0) { //상태가 윷 던지기 전일 때만 동작
      yutnoriSet.addPlayerResult(currentTurn, res);
      numCanMove++;
      //결과 저장, 이동 가능 횟수 증가
      if (res != 4 && res != 5) {
        yutnoriSet.setInGameFlag(1);
      }
      //윷이나 모가 아니면 상태를 말 선택으로 변경
    }
  }

  //실제 게임 중 윷 던지기 처리
  public void rollYutProcess() {
    if (yutnoriSet.getInGameFlag() == 0) { //상태가 윷 던지기 전일 때만 실행
      currentTurn = yutnoriSet.getPlayerTurn();
      int result = yutnoriSet.getYutSet().rollYut();
      //현재 턴 설정 후 윷을 실제로 던짐

      numCanMove++;
      yutnoriSet.addPlayerResult(currentTurn, result);
      //결과 저장, 이동 가능 횟수 증가

      if (result != 4 && result != 5) {
        yutnoriSet.setInGameFlag(1);
      } //윷이나 모가 아니면 상태를 말 선택으로 변경
    }
  }

  //보드 밖에 있는 말을 클릭했을시 처리
  public void selectOutOfBoardPieceProcess(int turn){
    if (yutnoriSet.getInGameFlag() == 1 && (turn == currentTurn)) { //말 선택 & 본인의 턴
      if (yutnoriSet.getPlayer().getLeftNumOfPieceOfPlayer(currentTurn) > 0) { //남은 말이 있다면 하나 선택
        chosenPiece = yutnoriSet.getPlayer().getPieceIdFromOutOfBoard(currentTurn);
        yutnoriSet.showMovable(chosenPiece);
        yutnoriSet.setInGameFlag(2);
        //이동 가능한 위치 표시, 상태를 이동으로 변경
      } else {
        yutnoriSet.setInGameFlag(1);
      }
    }
  }

  //보드에 위에 있는 말을 클릭했을시
  public void selectInTheBoardPieceProcess(int row, int col) {
    int ownerId;
    try {
      ownerId = yutnoriSet.getPlayer().getPieceByLocation(row, col).getOwnerId();
    } catch (NullPointerException e) {
      return;
    } //선택한 위치에 말이 없다면 예외 처리

    if (yutnoriSet.getInGameFlag() == 1 && currentTurn == ownerId) {
      chosenPiece = yutnoriSet.getBoard().getCircleByLocation(row, col).getOccupyingPieces().get(0);
      yutnoriSet.showMovable(chosenPiece);
      yutnoriSet.setInGameFlag(2);
    } //본인 말이면 선택 완료, 이동 가능 상태로 전환
  }

  //이동 후 남은 결과나 잡은 말 유무에 따라 게임 상태를 결정
  public void decisionMaking() {
    //경우 1: 아직 이동 가능
    if (numCanMove >= 1 && catchPoint == 0) {
      yutnoriSet.setInGameFlag(1);
      yutnoriSet.setBoardUnchangeable();
      //경우 2: 이동 불가능 -> 턴 넘김
    } else if (numCanMove == 0 && catchPoint == 0) {
      yutnoriSet.setPlayerTurn(((yutnoriSet.getPlayerTurn() + 1) % yutnoriSet.getNumOfPlayer()));
      yutnoriSet.setInGameFlag(0);
      yutnoriSet.setBoardUnchangeable();
      currentTurn = yutnoriSet.getPlayerTurn();
      //경우 3: 말을 잡았으면 다시 윷 던지기
    } else if (catchPoint > 0) {
      catchPoint--;
      yutnoriSet.setInGameFlag(0);
      yutnoriSet.setBoardUnchangeable();
    }
  }

  //윷 결과가 여러 개일 때 팝업에서 하나를 선택하면 호출
  public void multiPossibleEnd(int result) {
    try {
      yutnoriSet.deletePlayerResult(currentTurn, result);
      decisionMaking();
    } catch (NullPointerException e) {
    }
  }

  //선택된 말을 해당 위치로 이동시키는 메서드
  public void movePieceProcess(int row, int col) {
    Integer resultValue;
    int numOfReachable = 0;
    boolean gameOver = false;
    boolean removeSuceed;

    if (yutnoriSet.getInGameFlag() == 2 && yutnoriSet.getBoard().getCircleByLocation(row, col).isChangeable()) {
      //이동 가능 상태 && 이동 가능한 위치일 경우
      if (yutnoriSet.tryCatch(chosenPiece, row, col)) {
        catchPoint++;
      } //상대 말을 잡았다면 catchPoint 증가

      numCanMove--;
      resultValue = yutnoriSet.getClickedResult(chosenPiece, row, col);
      yutnoriSet.move(chosenPiece, row, col);
      yutnoriSet.getMovable().clear();
      //실제 말 이동 및 결과 처리

      //도착지 도달 처리
      if (row == 7 && col == 7) {
        for (int i : yutnoriSet.getBoard().getCircleByLocation(7, 7).getOccupyingPieces()) {
          yutnoriSet.getPlayer().getPieceByPieceId(i).setGone();
        }

        if (yutnoriSet.getPlayer().getWinnerPlayerId() == currentTurn) {
          yutGui.setupExitGUI();
          gameOver = true;
          yutnoriSet.resetBoard();
        }

        for (int i : yutnoriSet.getPlayer().getPlayerResult(currentTurn)) {
          if (resultValue == 0) break;
          if (i >= resultValue) numOfReachable++;
        }

        if (numOfReachable > 1 && !gameOver) {
          yutGui.popUp(currentTurn, resultValue);
        } else {
          yutnoriSet.deletePlayerResult(currentTurn, resultValue);
          decisionMaking();
        }
      }
      else { //일반 이동 처리
        removeSuceed = yutnoriSet.deletePlayerResult(currentTurn, resultValue);
        if (!removeSuceed && resultValue == 0) {
          yutnoriSet.getPlayer().getPlayerResult(currentTurn).remove((Integer) 1);
        }
        decisionMaking();
      }

      yutnoriSet.getBoard().getCircleByLocation(7, 7).resetCircle();
    }

    //이동 불가능한 위치 클릭 처리
    else if (!yutnoriSet.getMovable().contains(yutnoriSet.getBoard().getCircleByLocation(row, col).getId())) {
      yutnoriSet.setInGameFlag(1);
      yutnoriSet.getMovable().clear();
    }

    yutnoriSet.setBoardUnchangeable(); //보드 상태 초기화
  }
}
