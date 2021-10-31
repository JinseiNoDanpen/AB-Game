package org.luca.AB_Game.server;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.luca.AB_Game.client.PacketType;

public class Controller {
    private final Main main;

    // Qua puoi avere tutte le variabili di server_panel.fxml!!
    // Ognuna va annotata e il valore non va messo, lo mette da solo il programma!!
    @FXML
    private Hyperlink startButton;

    @FXML
    private Hyperlink stopButton;

    @FXML
    private AnchorPane pane;

    // La funzione initialize viene chiamata DA SOLA, dopo che sono stati dati i valori alle variabili sopra,
    // quelle annotate con  @FXML
    @FXML
    private void initialize() {
        Image image = new Image("https://i.imgur.com/dhngeZf.png?1");
        BackgroundSize bgs = new BackgroundSize(pane.getWidth(), pane.getHeight(), false, false, true, true);
        BackgroundImage bgi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgs);
        Background bg = new Background(bgi);
        pane.setBackground(bg);

        Image start_unpressed = new Image("https://i.imgur.com/V2sxCEy.png");
        startButton.setGraphic(new ImageView(start_unpressed));
        Image start_pressed = new Image("https://i.imgur.com/d8njpn3.png");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                startButton.setGraphic(new ImageView(start_pressed));
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            public void run() {
                                PacketType packet;
                                Platform.runLater(() -> startButton.setGraphic(new ImageView(start_unpressed)));
                                onClick();
                            }
                        }, 200
                );
            }});

        Image betray_unpressed = new Image("https://i.imgur.com/sv09J8w.png");
        stopButton.setGraphic(new ImageView(betray_unpressed));
        Image betray_pressed = new Image("https://i.imgur.com/fh45vbI.png");
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                stopButton.setGraphic(new ImageView(betray_pressed));
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            public void run() {
                                PacketType packet;
                                Platform.runLater(() -> stopButton.setGraphic(new ImageView(betray_unpressed)));
                                onClick();
                            }
                        }, 200
                );
            }});
    }

    // Il costruttore viene chiamato quando ancora le variabili annotate da @FXML SONO NULLE!!!
    // Se usi qua myButton.qualcosa ti darà errore!!!
    public Controller(Main main) {
        this.main = main;
    }

    @FXML
    private void onClick() {
        if(main.isServerEnabled()) {
            main.stopServer();
            startButton.setVisible(true);
            stopButton.setVisible(false);
            return;
        }

        main.startServer();
        startButton.setVisible(false);
        stopButton.setVisible(true);
    }

}
