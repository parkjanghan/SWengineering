package display;

import GameController.YutnoriSet;
import assets.BoardGraph4;
import assets.BoardGraph5;
import assets.BoardGraph6;
import play.YutResult;
import assets.BoardGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class BoardPanel extends JPanel {

    private BoardGraph boardGraph;
    private YutnoriSet yutnoriSet; // YutnoriSet 객체를 통해 게임 상태를 관리
    private Map<Integer, Point> malPositions = new HashMap<>(); // key = playerId * 10 + malId
    private final Map<Integer, Color> playerColors = Map.of(0, Color.RED, 1, Color.BLUE, 2, Color.GREEN, 3, Color.MAGENTA);
    private int selectedPlayerId = -1;
    private int selectedMalId = -1;
    private Set<Integer> highlightedNodes = new HashSet<>();
    private final Map<Integer, NodeButton> nodeButtons = new HashMap<>();
    private final List<MalButton> malButtons = new ArrayList<>();

    public BoardPanel(YutnoriSet yutnoriSet) {

        //추가 - 보드 모양 선택
        switch (yutnoriSet.getBoardType()) {
            case 4 -> this.boardGraph = new BoardGraph4();
            case 5 -> this.boardGraph = new BoardGraph5();
            case 6 -> this.boardGraph = new BoardGraph6();
            default -> throw new IllegalArgumentException("Invalid board type");
        }
        
        this.yutnoriSet = yutnoriSet;
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        // 모든 노드 버튼 생성 및 추가
        for (Map.Entry<Integer, Point> entry : boardGraph.getNodePositions().entrySet()) {
            int nodeId = entry.getKey();
            Point pos = entry.getValue();
            NodeButton btn = new NodeButton(nodeId, pos);
            btn.setEnabled(false); // 클릭 불가로 시작
            add(btn);
            nodeButtons.put(nodeId, btn);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoardGraph((Graphics2D) g);

        // 말 그리기
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Map.Entry<Integer, Point> entry : malPositions.entrySet()) {
            int key = entry.getKey();
            int playerId = key / 10;
            Point pos = entry.getValue();
            Color color = playerColors.getOrDefault(playerId, Color.BLACK);

            int r = 14;
            g2d.setColor(color);
            g2d.fillOval(pos.x - r / 2, pos.y - r / 2, r, r);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(pos.x - r / 2, pos.y - r / 2, r, r);
        }
    }

    private void drawBoardGraph(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.GRAY);

        for (int[] edge : boardGraph.getEdges()) {
            Point from = boardGraph.getNodePositions().get(edge[0]);
            Point to = boardGraph.getNodePositions().get(edge[1]);
            if (from != null && to != null) {
                g2.drawLine(from.x, from.y, to.x, to.y);
            }
        }

        for (Map.Entry<Integer, Point> entry : boardGraph.getNodePositions().entrySet()) {
            int id = entry.getKey();
            Point p = entry.getValue();
            g2.setColor(Color.WHITE);
            g2.fillOval(p.x - 15, p.y - 15, 30, 30);
            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - 15, p.y - 15, 30, 30);
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            g2.drawString(String.valueOf(id), p.x - 6, p.y + 4);
        }
    }

    public void updateMalPosition(int[] data) {
        int playerId = data[0];
        int malId = data[1];
        int nodeId = data[2];

        Point pos = boardGraph.getNodePositions().get(nodeId);
        if (pos == null)
            return;

        removeMalButton(playerId, malId);

        MalButton malBtn = new MalButton(playerId, malId, playerColors.get(playerId));
        malBtn.setLocation(pos.x - 10, pos.y - 10);
        malBtn.addActionListener(e -> handleMalClick(playerId, malId, nodeId));

        add(malBtn);
        setComponentZOrder(malBtn, 0); // ✅ 항상 최상단으로

        malButtons.add(malBtn);
    }

    private void removeMalButton(int playerId, int malId) {
        malButtons.removeIf(btn -> {
            if (btn.getPlayerId() == playerId && btn.getMalId() == malId) {
                remove(btn);
                return true;
            }
            return false;
        });
    }

    private void handleMalClick(int playerId, int malId, int currentNode) {
        this.selectedPlayerId = playerId;
        this.selectedMalId = malId;

        // 🔁 모든 노드를 비활성화 & 하이라이트 해제 & 이벤트 제거
        for (NodeButton b : nodeButtons.values()) {
            b.setHighlighted(false);
            b.setEnabled(false);
            for (ActionListener l : b.getActionListeners()) {
                b.removeActionListener(l);
            }
        }

        // 🎯 현재 플레이어의 이동 가능한 윷 결과 조회
        List<YutResult> results = yutnoriSet.getPlayerResults();
        if (results.isEmpty())
            return;

        YutResult result = results.getFirst(); // 첫 번째 윷 결과 사용
        List<Integer> moveable = yutnoriSet.showMoveableNodeId(currentNode, result);

        // 🟡 이동 가능한 노드를 강조 & 클릭 가능하게 설정
        for (int nodeId : moveable) {
            NodeButton btn = nodeButtons.get(nodeId);
            if (btn != null) {
                btn.setHighlighted(true);
                btn.setEnabled(true); // ✅ 클릭 가능하도록 활성화
                btn.addActionListener(e -> handleNodeClick(nodeId, result));
            }
        }

        repaint();
    }

    private void handleNodeClick(int nodeId, YutResult result) {
        // 🎯 이동 처리
        yutnoriSet.tryCatchMal(selectedPlayerId, nodeId);
        yutnoriSet.moveMal(selectedPlayerId, selectedMalId, nodeId, result);
        yutnoriSet.deletePlayerResult(result);

        updateMalPosition(new int[] { selectedPlayerId, selectedMalId, nodeId });

        // 🧹 노드 상태 초기화
        for (NodeButton btn : nodeButtons.values()) {
            btn.setHighlighted(false);
            btn.setEnabled(false); // 🔒 다시 클릭 불가
            for (ActionListener l : btn.getActionListeners()) {
                btn.removeActionListener(l);
            }
        }

        repaint();
    }

    public void removeMalAt(int[] data) {
        int playerId = data[0];
        int malId = data[1];
        malPositions.remove(playerId * 10 + malId);
        repaint();
    }

    public void enablePieceSelection(int currentTurn) {
        for (MalButton malBtn : malButtons) {
            if (malBtn.getPlayerId() == currentTurn) {
                malBtn.setEnabled(true);
                malBtn.addActionListener(e -> handleMalClick(currentTurn, malBtn.getMalId(), 0));
            }
        }
    }

    public void highlightOutOfBoardPiece(int currentTurn, int outOfBoardMalId) {
        for (MalButton malBtn : malButtons) {
            if (malBtn.getPlayerId() == currentTurn && malBtn.getMalId() == outOfBoardMalId) {
                malBtn.setEnabled(true);
                malBtn.setBackground(Color.YELLOW); // 강조 색상
                malBtn.addActionListener(e -> handleMalClick(currentTurn, outOfBoardMalId, 0));
            }
        }
    }

    public void highlightPossibleMoves(ArrayList<Integer> possibleMoves) {
        for (int nodeId : possibleMoves) {
            NodeButton btn = nodeButtons.get(nodeId);
            if (btn != null) {
                btn.setHighlighted(true);
                btn.setEnabled(true); // 클릭 가능하도록 활성화
            }
        }
    }

    public int getSelectedMalId() {
        return selectedMalId;
    }

    public void clearSelection() {
        selectedPlayerId = -1;
        selectedMalId = -1;

        for (NodeButton btn : nodeButtons.values()) {
            btn.setHighlighted(false);
            btn.setEnabled(false); // 다시 클릭 불가
            for (ActionListener l : btn.getActionListeners()) {
                btn.removeActionListener(l);
            }
        }

        repaint();
    }

    public void clearBoard() {
        for (MalButton malBtn : malButtons) {
            remove(malBtn);
        }
        malButtons.clear();
        malPositions.clear();
        highlightedNodes.clear();

        for (NodeButton btn : nodeButtons.values()) {
            btn.setHighlighted(false);
            btn.setEnabled(false); // 다시 클릭 불가
            for (ActionListener l : btn.getActionListeners()) {
                btn.removeActionListener(l);
            }
        }

        repaint();
    }


    // 말 버튼 클릭 시 이벤트 처리
    // 나중에 외부로 빼야함



}
