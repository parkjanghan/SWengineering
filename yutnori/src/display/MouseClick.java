package display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class MouseClick extends MouseAdapter {
    //마우스 클릭 이벤트를 처리하여 mal을 선택 및 이동시키는 class
    private Point offset;
    private JComponent target;

    public MouseClick(JComponent target) {
        this.target = target;

        // 마우스 이벤트 등록
        target.addMouseListener(this);
        target.addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // 마우스 클릭한 좌표 저장
        Point clickPoint = e.getPoint();
        offset = new Point(clickPoint);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // 말 움직이기
        Point parentPoint = SwingUtilities.convertPoint(target, e.getPoint(), target.getParent());

        int newX = parentPoint.x - offset.x;
        int newY = parentPoint.y - offset.y;

        target.setLocation(newX, newY);
    }

}

