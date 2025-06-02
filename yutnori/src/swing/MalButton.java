package swing;

import javax.swing.*;
import java.awt.*;

public class MalButton extends JButton {
    private final int playerId;
    private final int malId;
    private  int nodeId;
    private boolean moved;

    public MalButton(int playerId, int malId, Color color) {
        this.playerId = playerId;
        this.malId = malId;

        this.moved = false;

        setBackground(color);
        setBorderPainted(false);
        setOpaque(true);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setSize(20, 20);
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getMalId() {
        return malId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    public boolean isMoved() {
        
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}