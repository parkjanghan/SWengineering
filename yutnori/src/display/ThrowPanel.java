package display;

import GameController.YutnoriSet;
import play.YutResult;

import javax.swing.*;
import java.awt.*;

public class ThrowPanel extends JPanel {

    private final YutnoriSet yutnoriSet;
    private JLabel resultLabel;

    public ThrowPanel(YutnoriSet yutnoriSet) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 220));

        this.yutnoriSet = yutnoriSet;

        resultLabel = new JLabel("결과: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));

        // 윗쪽 버튼: 랜덤 윷 던지기
        JPanel topPanel = new JPanel(new FlowLayout());
        JButton randomBtn = new JButton("🎲 랜덤 윷 던지기");
        randomBtn.addActionListener(e -> {
            System.out.println("[ThrowPanel] 🎲 랜덤 윷 던지기 버튼 클릭됨");
            yutnoriSet.rollYut(); // 실제 게임 로직에 윷 던지기 요청
        });
        topPanel.add(randomBtn);

        // 아래쪽 버튼: 백도~모
        JPanel yutButtons = new JPanel(new GridLayout(1, 6, 5, 5)); // 6개 버튼

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
                yutnoriSet.rollYutforTest(result); // 수동 지정 윷 결과 전달
            });
        
            yutButtons.add(btn);
        }

        add(topPanel, BorderLayout.NORTH);
        add(yutButtons, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }

    public void showYutResult(YutResult result) {
        System.out.println("[ThrowPanel] 📢 showYutResult() 호출됨");
    
        java.util.List<YutResult> results = yutnoriSet.getPlayerResults();
    
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

    /*
    public void reset() {
        System.out.println("[ThrowPanel] 🔄 reset() 호출됨");
        resultLabel.setText("결과: ");
        yutnoriSet.clearPlayerResults(); // 결과 초기화
    }
    */

    public void enableRollButton(boolean b) {
        System.out.println("[ThrowPanel] 윷 던지기 버튼 활성화: " + b);
        for (Component comp : getComponents()) {
            if (comp instanceof JPanel) {
                for (Component btn : ((JPanel) comp).getComponents()) {
                    if (btn instanceof JButton) {
                        btn.setEnabled(b);
                    }
                }
            }
        }
    }

    public YutResult getSelectedYutResult() {
        for (YutResult result : yutnoriSet.getPlayerResults()) {
            if (result != null) {
                return result;
            }
        }
        return null; // 결과가 없을 경우
    }

    /*
    public void clearYutResultSelection() {
        System.out.println("[ThrowPanel] 🔄 clearYutResultSelection() 호출됨");
        resultLabel.setText("결과: ");
        yutnoriSet.clearPlayerResults(); // 결과 초기화
    }

     */
}
