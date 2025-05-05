package display;

import GameController.YutnoriSet;
import play.YutResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private final YutnoriSet yutnoriSet;
    private BoardPanel boardPanel;
    private ThrowPanel throwPanel;
    private PlayerInfoPanel infoPanel;

    public GamePanel(YutnoriSet yutnoriSet) {
        setLayout(new BorderLayout());
        this.yutnoriSet = yutnoriSet;
        this.yutnoriSet.startGame(2, 4); // 2인용, 말 4개로 초기화 추가

        // 1. 각 서브 패널에 yutnoriSet 전달
        boardPanel = new BoardPanel(yutnoriSet);
        throwPanel = new ThrowPanel(yutnoriSet);
        infoPanel = new PlayerInfoPanel(yutnoriSet);

        // 2. 오른쪽 묶음 패널 생성 (수직 배치), 패널 추가
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout()); // 또는 BoxLayout으로도 가능
        rightPanel.add(infoPanel, BorderLayout.CENTER);
        rightPanel.add(throwPanel, BorderLayout.SOUTH);

        // 게임 진행 컨트롤 패널 추가
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        JButton nextTurnButton = new JButton("다음 턴");
        nextTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yutnoriSet.nextTurn();
                yutnoriSet.setInGameFlag(YutnoriSet.NEED_TO_ROLL);
                throwPanel.reset();
            }
        });
        controlPanel.add(nextTurnButton);
        rightPanel.add(controlPanel, BorderLayout.NORTH);

        add(boardPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // 3. 옵저버 등록 (게임 로직에서 UI 상태 갱신받기)
        yutnoriSet.addObserver(evt -> {
            switch (evt.getPropertyName()) {
                case "윷 던지기 결과" -> {
                    YutResult result = (YutResult) evt.getNewValue();
                    throwPanel.showYutResult(result);
                    // 윷/모가 나왔을 때는 추가 턴이 있으므로 UI 업데이트
                    if (result == YutResult.YUT || result == YutResult.MO) {
                        throwPanel.enableRollButton(true);
                    } else {
                        // 말 선택 UI 활성화
                        enablePieceSelection();
                    }
                }
                case "말 이동됨" -> {
                    int[] data = (int[]) evt.getNewValue(); // [playerId, malId, destNodeId]
                    boardPanel.updateMalPosition(data); // BoardPanel에 말 위치 반영
                    checkGameState();
                }
                case "말 잡힘" -> {
                    int[] data = (int[]) evt.getNewValue();
                    boardPanel.removeMalAt(data);
                    // 상대 말을 잡았을 때 추가 턴 처리
                    throwPanel.enableRollButton(true);
                }
                case "턴 변경됨" -> {
                    int currentTurn = (int) evt.getNewValue();
                    infoPanel.updatePlayerTurn(currentTurn);
                    throwPanel.enableRollButton(true);
                }
                case "목적지 노드 선택됨" -> {
                    int nodeId = (int) evt.getNewValue();
                    handleMalMovement(nodeId);
                }
                case "말 선택됨" -> {
                    int[] data = (int[]) evt.getNewValue();
                    showPossibleMoves(data[0], data[1]);
                }
                case "게임 시작됨" -> resetGameUI();
            }
        });

        // 모든 말을 시작 위치(예: 노드 0)에 배치
        initializeGamePieces();

        // 4. 첫 번째 턴 UI 초기화
        infoPanel.updatePlayerTurn(yutnoriSet.getPlayerTurn());
        throwPanel.enableRollButton(true);
    }

    private void initializeGamePieces() {
        System.out.println("[GamePanel] 플레이어 수: " + yutnoriSet.getPlayers().size());

        for (int playerId = 0; playerId < yutnoriSet.getPlayers().size(); playerId++) {
            System.out.println("플레이어 " + playerId + " 말 수: " +
                    yutnoriSet.getPlayers().get(playerId).getMalList().size());
        }

        for (int playerId = 0; playerId < yutnoriSet.getPlayers().size(); playerId++) {
            for (int malId = 0; malId < yutnoriSet.getPlayers().get(playerId).getMalList().size(); malId++) {
                // 항상 존재하는 노드 ID로 초기화 (예: 노드 0)
                yutnoriSet.getPlayers().get(playerId).getMalList().get(malId).setPosition(0);
                boardPanel.updateMalPosition(new int[] { playerId, malId, 0 });
            }
        }
    }

    private void enablePieceSelection() {
        // 현재 턴의 플레이어 말 선택 가능하게 설정
        int currentTurn = yutnoriSet.getPlayerTurn();
        boardPanel.enablePieceSelection(currentTurn);

        // 보드 밖 말도 선택 가능하게 함
        int outOfBoardMalId = yutnoriSet.selectOutOfBoardPiece(currentTurn);
        if (outOfBoardMalId != -1) {
            // 보드 밖 말이 있으면 해당 말 선택 UI 활성화
            boardPanel.highlightOutOfBoardPiece(currentTurn, outOfBoardMalId);
        }
    }

    private void showPossibleMoves(int playerId, int malId) {
        // 선택된 말이 이동할 수 있는 모든 위치 계산
        if (yutnoriSet.getInGameFlag() == YutnoriSet.NEED_TO_MOVE) {
            int currentPosition = yutnoriSet.getPlayers().get(playerId).getMalList().get(malId).getPosition();

            for (YutResult result : yutnoriSet.getPlayerResults()) {
                ArrayList<Integer> possibleMoves = yutnoriSet.showMoveableNodeId(currentPosition, result);
                boardPanel.highlightPossibleMoves(possibleMoves);
            }
        }
    }

    private void handleMalMovement(int destNodeId) {
        int currentTurn = yutnoriSet.getPlayerTurn();
        int selectedMalId = boardPanel.getSelectedMalId();

        if (selectedMalId != -1) {
            // 목적지에 상대 말이 있는지 확인하고 잡기 시도
            boolean caught = yutnoriSet.tryCatchMal(currentTurn, destNodeId);

            // 선택한 윷 결과로 말 이동
            YutResult selectedYutResult = throwPanel.getSelectedYutResult();
            if (selectedYutResult != null) {
                yutnoriSet.moveMal(currentTurn, selectedMalId, destNodeId, selectedYutResult);

                // 윷/모나 상대말을 잡았을 때는 다시 윷을 던질 수 있음
                if (caught || selectedYutResult == YutResult.YUT || selectedYutResult == YutResult.MO) {
                    throwPanel.enableRollButton(true);
                } else if (yutnoriSet.getPlayerResults().isEmpty()) {
                    // 남은 결과가 없으면 다음 턴으로
                    yutnoriSet.nextTurn();
                } else {
                    // 아직 사용할 수 있는 결과가 있으면 다시 말 선택 단계로
                    enablePieceSelection();
                }
            }

            // 선택 상태 초기화
            boardPanel.clearSelection();
            throwPanel.clearYutResultSelection();
        }
    }

    private void checkGameState() {
        // 현재 플레이어의 승리 조건 확인 (모든 말이 골인했는지)
        int currentTurn = yutnoriSet.getPlayerTurn();
        boolean allFinished = true;

        for (int i = 0; i < yutnoriSet.getPlayers().get(currentTurn).getMalList().size(); i++) {
            if (!yutnoriSet.getPlayers().get(currentTurn).getMalList().get(i).getFinished())
            {
                allFinished = false;
                break;
            }
        }

        if (allFinished) {
            JOptionPane.showMessageDialog(this,
                    "플레이어 " + (currentTurn + 1) + " 승리!",
                    "게임 종료",
                    JOptionPane.INFORMATION_MESSAGE);

            // 게임 재시작
            int restart = JOptionPane.showConfirmDialog(this,
                    "게임을 다시 시작하시겠습니까?",
                    "게임 재시작",
                    JOptionPane.YES_NO_OPTION);

            if (restart == JOptionPane.YES_OPTION) {
                yutnoriSet.startGame(yutnoriSet.getPlayers().size(),
                        yutnoriSet.getPlayers().get(0).getMalList().size());
            }
        }
    }

    private void resetGameUI() {
        // 게임 UI 초기화 로직
        boardPanel.clearBoard();
        throwPanel.reset();
        initializeGamePieces();
        infoPanel.updatePlayerTurn(yutnoriSet.getPlayerTurn());
    }
}