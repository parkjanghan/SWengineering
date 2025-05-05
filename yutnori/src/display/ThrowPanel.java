package display;

import GameController.YutnoriSet;
import play.YutResult;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ThrowPanel extends JPanel implements PropertyChangeListener {

    private final YutnoriSet yutnoriSet;
    private JLabel resultLabel;

    public ThrowPanel(YutnoriSet yutnoriSet) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 220));

        this.yutnoriSet = yutnoriSet;

        resultLabel = new JLabel("결과: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));

        // 랜덤 윷 던지기 버튼
        JPanel topPanel = new JPanel(new FlowLayout());
        JButton randomBtn = new JButton("🎲 랜덤 윷 던지기");
        randomBtn.addActionListener(e -> {
            int currentPlayer = yutnoriSet.getPlayerTurn();
            if (!yutnoriSet.isCurrentPlayerCanThrow()) {
                JOptionPane.showMessageDialog(this, "지금은 플레이어 " + (currentPlayer + 1) + "의 턴입니다.");
                return;
            }
            System.out.println("[ThrowPanel] 🎲 윷 던지기 버튼 클릭됨");
            yutnoriSet.rollYut();
        });
        topPanel.add(randomBtn);

        // 수동 윷 선택 버튼들
        JPanel yutButtons = new JPanel(new GridLayout(1, 6, 5, 5));
        String[] names = {"백도", "도", "개", "걸", "윷", "모"};
        YutResult[] results = {
                YutResult.BACK_DO, YutResult.DO, YutResult.GAE,
                YutResult.GEOL, YutResult.YUT, YutResult.MO
        };

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            YutResult result = results[i];

            JButton btn = new JButton(name);
            btn.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
            btn.addActionListener(e -> {
                System.out.println("[ThrowPanel] 🖐 수동 윷 결과 선택: " + result.getName());
                yutnoriSet.rollYutforTest(result);
            });

            yutButtons.add(btn);
        }

        // 옵저버 한 번만 등록
        this.yutnoriSet.addObserver(this);

        add(topPanel, BorderLayout.NORTH);
        add(yutButtons, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }

    // 옵저버 콜백 구현
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();

        if ("사용자의 결과 추가됨".equals(property) || "사용자의 결과 삭제됨".equals(property)) {
            showYutResult(null);
        }
    }

    public void showYutResult(YutResult result) {
        System.out.println("[ThrowPanel] 📢 showYutResult() 호출됨");

        List<YutResult> results = yutnoriSet.getPlayerResults();

        if (results == null || results.isEmpty()) {
            resultLabel.setText("남은 결과: 없음");
            return;
        }

        StringBuilder sb = new StringBuilder("남은 결과: ");
        for (YutResult r : results) {
            sb.append(r.getName()).append(" ");
        }

        resultLabel.setText(sb.toString().trim());
    }
}
