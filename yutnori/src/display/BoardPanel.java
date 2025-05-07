package display;

import GameController.YutnoriSet;
import assets.BoardGraph4;
import assets.BoardGraph5;
import assets.BoardGraph6;
import play.Mal;
import play.Player;
import play.YutResult;
import assets.BoardGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.*;
import java.util.List;

public class BoardPanel extends JPanel implements PropertyChangeListener {

    private BoardGraph boardGraph;
    private YutnoriSet yutnoriSet;
    private Map<Integer, Point> malPositions = new HashMap<>();
    private final Map<Integer, Color> playerColors = Map.of(0, Color.RED, 1, Color.BLUE, 2, Color.GREEN, 3, Color.MAGENTA);
    private int selectedPlayerId = -1;
    private int selectedMalId = -1;
    private final Map<Integer, NodeButton> nodeButtons = new HashMap<>();
    private final List<MalButton> malButtons = new ArrayList<>();
    private final Map<Integer, JLabel> nodeCountLabels = new HashMap<>();


    public BoardPanel(YutnoriSet yutnoriSet) {
        switch (GameSettings.getBoardShape()) {
            case 4 -> this.boardGraph = new BoardGraph4();
            case 5 -> this.boardGraph = new BoardGraph5();
            case 6 -> this.boardGraph = new BoardGraph6();
        }

        if (this.boardGraph == null) {
            System.out.println("Board type 잘못됨: " + GameSettings.getBoardShape() + ", 기본값 4로 설정");
            this.boardGraph = new BoardGraph4();
        }


        this.yutnoriSet = yutnoriSet;
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        for (Map.Entry<Integer, Point> entry : boardGraph.getNodePositions().entrySet()) {
            int nodeId = entry.getKey();
            Point pos = entry.getValue();
            NodeButton btn = new NodeButton(nodeId, pos);
            btn.setEnabled(false);
            add(btn);
            nodeButtons.put(nodeId, btn);
        }

        yutnoriSet.addObserver(evt -> {
            if ("말 잡힘".equals(evt.getPropertyName())) {
                @SuppressWarnings("unchecked")
                ArrayList<Integer> info = (ArrayList<Integer>) evt.getNewValue();
                int playerId = info.get(0);
                int nodeId = info.get(1);

                System.out.println("[BoardPanel] 말 잡힘 알림 수신: player " + playerId + ", node " + nodeId);

                for (Player p : yutnoriSet.getPlayers()) {
                    for (Mal m : p.getMalList()) {
                        if (m.getPosition() == 0) {
                            removeMalAt(new int[] { m.getTeam(), m.getMalNumber() });
                            updateMalPosition(new int[] { m.getTeam(), m.getMalNumber(), 0 });
                        }
                    }
                }

                repaint();
            }
        });
        yutnoriSet.addObserver(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoardGraph((Graphics2D) g);

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

        if (nodeId > 0 && yutnoriSet.board.boardShape.get(nodeId).isEndPoint()) {
            removeMalButton(playerId, malId); // 혹시 남은 버튼 제거
            malPositions.remove(playerId * 10 + malId);
            return;
        }

        Point pos = (nodeId <= 0) ? new Point(800, 150 + 50 * playerId)
                : boardGraph.getNodePositions().get(nodeId);
        if (pos == null) return;

        // 기존 말 버튼 제거
        removeMalButton(playerId, malId);

        // 말 버튼 생성
        MalButton malBtn = new MalButton(playerId, malId, playerColors.get(playerId));
        malBtn.setNodeId(nodeId);

        // 노드에 같은 팀의 다른 말이 있는지 확인
        long count = malButtons.stream()
                .filter(m -> m.getNodeId() == nodeId && m.getPlayerId() == playerId)
                .count();

        if (count >= 1 && nodeId > 0) {
            // 이미 대표 말이 있으면 말 버튼은 추가하지 않고 라벨만 업데이트 (stack 처리만 로직에서 처리)
            return;
        }

        // 대표 말이므로 버튼 생성
        malBtn.setLocation(pos.x - 10, pos.y - 10);
        malBtn.addActionListener(e -> handleMalClick(playerId, malId, nodeId));
        add(malBtn);
        setComponentZOrder(malBtn, 0);
        malButtons.add(malBtn);
        malPositions.put(playerId * 10 + malId, pos);
    }



    private void removeMalButton(int playerId, int malId) {
        malButtons.removeIf(btn -> {
            if (btn.getPlayerId() == playerId && btn.getMalId() == malId) {
                remove(btn); // 화면에서 제거
                return true;
            }
            return false;
        });
    }

    public void disableAllMalButtons() {
        for (MalButton btn : malButtons) {
            btn.setEnabled(false);
        }
    }

    public void enableMalButtonsForPlayer(int playerId) {
        for (MalButton btn : malButtons) {
            if (btn.getPlayerId() == playerId) {
                btn.setEnabled(true);
            } else {
                btn.setEnabled(false);
            }
        }
    }

    private void handleMalClick(int playerId, int malId, int currentNode) {
        if (playerId != yutnoriSet.getPlayerTurn()) {
            JOptionPane.showMessageDialog(this, "지금은 플레이어 " + (yutnoriSet.getPlayerTurn() + 1) + "의 턴입니다.");
            return;
        }

        this.selectedPlayerId = playerId;
        this.selectedMalId = malId;

        for (NodeButton b : nodeButtons.values()) {
            b.setHighlighted(false);
            b.setEnabled(false);
            for (ActionListener l : b.getActionListeners()) {
                b.removeActionListener(l);
            }
        }
        System.out.println("[handleMalClick] playerResults: " + yutnoriSet.getPlayerResults());
        //움직임을 먼저 선택해야 이전에 있었던 움직임들이 반영이 안됨
        //즉 사용할 yutResult를 먼저 고르게 해야 굴린 결과들을 사용 할 수 있음
        for(MalButton btn : malButtons) {
            if (btn.getPlayerId() == playerId && btn.getMalId() == malId) {
                btn.setEnabled(false);
            }
        }//말 버튼들 비활성화

        //움직일 윷 yutResult 선택하기
       // List<YutResult> results = yutnoriSet.getPlayerResults();
        if (yutnoriSet.getPlayerResults().isEmpty()) return;

       // 여러개 선택 할 수 있게 변경
        YutResult result = yutnoriSet.getYutResult_to_use();

        List<Integer> moveable = yutnoriSet.showMoveableNodeId(currentNode, result);

        for(MalButton btn : malButtons) {
            if (btn.getPlayerId() == playerId && btn.getMalId() == malId) {
                btn.setEnabled(true);
            }
        }//말 버튼들 다시 활성화
        if(moveable == null)
        {
            System.out.println("[handleMalClick] 이동할 수 있는 노드 없음");
            JOptionPane.showMessageDialog(this, "사용할 결과를 먼저 선택하세요");
        }
        else
        {
            for (int nodeId : moveable) {

                NodeButton btn = nodeButtons.get(nodeId);
                if (btn != null) {
                    btn.setHighlighted(true);
                    btn.setEnabled(true);
                    btn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            handleNodeClick(nodeId, result);
                        }
                    });
                }
            }
        }

        repaint();
    }

    private void handleNodeClick(int nodeId, YutResult result) {
        boolean keepTurn = yutnoriSet.moveMal(selectedPlayerId, selectedMalId, nodeId, result);

        updateMalPosition(new int[]{selectedPlayerId, selectedMalId, nodeId});

        for (NodeButton btn : nodeButtons.values()) {
            btn.setHighlighted(false);
            btn.setEnabled(false);
            for (ActionListener l : btn.getActionListeners()) {
                btn.removeActionListener(l);
            }
        }

        repaint();


        if (!keepTurn) {
            System.out.println("[handleNodeClick] 턴 종료: 다음 플레이어로 넘어갑니다.");
            yutnoriSet.nextTurn();
        } else {
            System.out.println("[handleNodeClick] 턴 유지됨: 잡기 또는 윷/모로 추가 턴!");
        }
    }

    public void removeMalAt(int[] data) {
        int playerId = data[0];
        int malId = data[1];

        malPositions.remove(playerId * 10 + malId); // 말 위치 맵에서 제거
        repaint(); // 화면 다시 그리기
    }


    private final Map<Integer, Point> playerEntryPoints = Map.of(
            0, new Point(50, 250),   // 플레이어 1: 왼쪽 외부
            1, new Point(550, 250),  // 플레이어 2: 오른쪽 외부
            2, new Point(300, 50),   // 플레이어 3: 위쪽 (추후 확장용)
            3, new Point(300, 550)   // 플레이어 4: 아래쪽 (추후 확장용)
    );

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();

        // 🎯 잡기 이벤트 처리
        if (property.equals("말 잡힘")) {
            ArrayList<Integer> info = (ArrayList<Integer>) evt.getNewValue();
            int playerTurn = info.get(0);
            int nodeId = info.get(1);

            System.out.println("[BoardPanel] 말 잡힘 알림 수신: player " + playerTurn + ", node " + nodeId);

            for (Player p : yutnoriSet.getPlayers()) {
                for (Mal m : p.getMalList()) {
                    if (m.getPosition() == 0) {
                        updateMalPosition(new int[]{m.getTeam(), m.getMalNumber(), 0});
                    }
                }
            }
            repaint();
        }

        // 🎯 말 이동 시 UI 업데이트
        else if (property.equals("말 이동됨")) {
            int[] data = (int[]) evt.getNewValue();
            int playerId = data[0];
            int malId = data[1];
            int nodeId = data[2];

            updateMalPosition(new int[]{playerId, malId, nodeId});
            repaint();
        }

        // 🎯 게임 종료 처리
        else if (property.equals("게임 종료")) {
            Object value = evt.getNewValue();
            if (value instanceof int[]) {
                int[] data = (int[]) value;
                int playerTurn = data[0];

                JOptionPane.showMessageDialog(this,
                        "🎉 플레이어 " + (playerTurn + 1) + "이(가) 승리했습니다!",
                        "게임 종료", JOptionPane.INFORMATION_MESSAGE);

                System.out.println("[BoardPanel] 🎉 게임 종료 알림 수신: player " + playerTurn);
            } else {
                System.err.println("⚠️ '게임 종료' 이벤트 타입 불일치: " + value.getClass().getName());
            }
        }
    }

}


