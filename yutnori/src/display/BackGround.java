package display;

import javax.swing.*;


public class BackGround extends JPanel {
    //게임 시작 화면의 배경을 표시하고, setting하는 class
    private JFrame frame;

    public BackGround() {
        // 전체 화면 초기 세팅
        frame = new JFrame("윷놀이");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(840, 630);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // 예: 말 이미지 보여주는 테스트 (말 위치 (100,100))
        ImageIcon icon = MalManage.loadAndResize("/img/team_1.png", 50, 50);
        JLabel label = new JLabel(icon);
        label.setBounds(200, 200, 50, 50);
        add(label);

        // 말 클릭 이동 기능 추가
        new MouseClick(label);

        frame.setContentPane(this);
        frame.setVisible(true);
    }
}

