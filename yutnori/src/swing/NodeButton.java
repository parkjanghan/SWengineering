package swing;

import javax.swing.*;
import java.awt.*;
import assets.Point;

public class NodeButton extends JButton {
    private final int nodeId;
    private boolean isHighlighted;
    private final int size = 30;

    public NodeButton(int nodeId, Point pos) {
        this.nodeId = nodeId;
        this.isHighlighted = false;

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setBounds(pos.x - size / 2, pos.y - size / 2, size, size);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setHighlighted(boolean highlighted) {
        this.isHighlighted = highlighted;
        repaint();
    }

    public int getNodeId() {
        return nodeId;
    }

    @Override
    public boolean contains(int x, int y) {
        int r = size / 2;
        return Math.pow(x - r, 2) + Math.pow(y - r, 2) <= r * r;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isHighlighted) {
            g2.setColor(Color.YELLOW);
            g2.fillOval(0, 0, size - 1, size - 1);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillOval(0, 0, size - 1, size - 1);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(0, 0, size - 1, size - 1);
        }

        g2.dispose();
    }
}
