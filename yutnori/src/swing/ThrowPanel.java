package swing;

import GameModel.YutnoriSet;
import play.YutResult;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class ThrowPanel extends JPanel implements PropertyChangeListener {

    private final YutnoriSet yutnoriSet;
    private JLabel resultLabel;
    private JPanel resultPanel;
    private List<JButton> allButtons = new ArrayList<>();


    public ThrowPanel(YutnoriSet yutnoriSet) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 220));

        this.yutnoriSet = yutnoriSet;

        // 결과 패널 초기화
        resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        add(resultPanel, BorderLayout.SOUTH);

        resultLabel = new JLabel("남은 결과: 없음");
        resultLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));

        // 랜덤 윷 던지기 버튼
        JPanel topPanel = new JPanel(new FlowLayout());
        JButton randomBtn = new JButton("🎲 랜덤 윷 던지기");
        allButtons.add(randomBtn);
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
        String[] names = {"빽", "도", "개", "걸", "윷", "모"};
        YutResult[] results = {
                YutResult.BACK_DO, YutResult.DO, YutResult.GAE,
                YutResult.GEOL, YutResult.YUT, YutResult.MO
        };

        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            YutResult result = results[i];

            JButton btn = new JButton(name);
            allButtons.add(btn);
            btn.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
            btn.addActionListener(e -> {
                System.out.println("[ThrowPanel] 🖐 수동 윷 결과 선택: " + result.getName());
                yutnoriSet.rollYutforTest(result);
            });

            yutButtons.add(btn);
        }

        // 옵저버 등록
        this.yutnoriSet.addObserver(this);

        add(topPanel, BorderLayout.NORTH);
        add(yutButtons, BorderLayout.CENTER);
    }

    // 옵저버 콜백 구현
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String property = evt.getPropertyName();

        if ("사용자의 결과 추가됨".equals(property) || "사용자의 결과 삭제됨".equals(property))
        {
            updateResultDisplay();
        }
        else if("말 이동됨".equals(property))
        {
            for (Component comp : resultPanel.getComponents()) {
                if (comp instanceof JButton) {
                    comp.setEnabled(true);
                }
            }
        }
    }

    // 결과 화면 업데이트
    void updateResultDisplay() {
        //System.out.println("[ThrowPanel] 📢 updateResultDisplay() 호출됨");

        // 기존 결과 패널의 모든 컴포넌트 제거
        resultPanel.removeAll();
        System.out.println("[ThrowPanel] 📢 updateResultDisplay() -" +
                "현재 playerResults: " + yutnoriSet.getPlayerResults());
        List<YutResult> results = yutnoriSet.getPlayerResults();

        if (results == null || results.isEmpty()) {
            JLabel noResultLabel = new JLabel("남은 결과: 없음");
            noResultLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            resultPanel.add(noResultLabel);
        }
        else {
            JLabel titleLabel = new JLabel("사용할 결과: ");
            titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            resultPanel.add(titleLabel);

            for (YutResult result : results) {
                JButton resultBtn = new JButton(result.getName());
                resultBtn.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
                resultBtn.addActionListener(e -> {
                    //버튼 클릭 불가능하게 변경
                    for (Component comp : resultPanel.getComponents()) {
                        if (comp instanceof JButton) {
                            comp.setEnabled(false);
                        }
                    }
                    //System.out.println("[ThrowPanel] ✅ 사용할 결과 버튼 클릭됨: " + result.getName());
                    // 결과 사용 - 여기서 사용할 윷 결과 boardPanel에 전달
                    yutnoriSet.setYutResult_to_use(result);
                    //결과를 패널에서 삭제
                    resultPanel.remove(resultBtn);
                    resultPanel.remove(resultBtn);
                    // 패널 다시 그리기
                    resultPanel.revalidate();
                    resultPanel.repaint();


                });
                resultPanel.add(resultBtn);
            }
        }

        // 패널 갱신
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    public void showYutResult(YutResult result) {
        System.out.println("[ThrowPanel] 📢 showYutResult() 호출됨");
        // 결과 표시 화면 업데이트
        updateResultDisplay();
    }

    public void enableAllButtons(boolean enable) {
        for (JButton btn : allButtons) {
            btn.setEnabled(enable);
        }
    }

}