package main;

import GameModel.YutnoriSet;
import display.Yutnori;
import javafx.application.Application;

public class Start {
    public static void main(String[] args) {
        //JavaFX Version
        Application.launch(Yutnori.class, args);

        //Java Swing Version
        //new swing.Yutnori();
    }
}
