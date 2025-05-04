package display;

import javax.swing.*;
import java.awt.*;

public class MalButton extends JButton {
    private final int playerId;
    private final int malId;

    public MalButton(int playerId, int malId, Color color) {
        this.playerId = playerId;
        this.malId = malId;

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
}