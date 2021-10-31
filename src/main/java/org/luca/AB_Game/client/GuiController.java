package org.luca.AB_Game.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.*;
import java.net.Socket;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.luca.AB_Game.client.PacketType.*;

public class GuiController {
    private static final Logger logger = LogManager.getLogManager().getLogger("GuiController");

    private Socket client;

    @FXML
    private AnchorPane pane;

    @FXML
    private Hyperlink button_Start;

    @FXML
    private MenuItem option_connect;

    @FXML
    private MenuItem option_disconnect;

    @FXML
    private Hyperlink button_Ally;

    @FXML
    private Hyperlink button_Betray;

    @FXML
    private Label text_Counter;

    @FXML
    private Label info0;

    @FXML
    private Label info1;

    @FXML
    private Label info2;

    @FXML
    private Label info3;

    @FXML
    private Label confirmation0;

    @FXML
    private Label confirmation1;

    //@FXML
    //ImageView imageView = new ImageView("https://i.imgur.com/dhngeZf.png?1");

    @FXML
    private void onConnect(ActionEvent e) {
        if (client != null) {
            this.disconnect(e);
            option_connect.setDisable(false);
            option_disconnect.setDisable(true);
            return;
        }

        this.connect(e);
    }

    private void connect(ActionEvent e) {
        option_connect.setDisable(false);
        option_disconnect.setDisable(true);

        String server_ip = "151.63.51.37"; //(String) obj.get("server_ip")
        if (server_ip == null || server_ip.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must a target ip!");
            alert.show();
        }

        int port;
        try {
            port = Main.PORT; // obj.get("server_port")
        } catch (NumberFormatException ex) { // Not a number
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must insert a target port!");
            alert.show();
            return;
        }

        try {
            this.client = new Socket(server_ip, port);
        } catch (IOException ex) { // No connection
            this.client = null;
            ex.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR, "Connection refused!");
            alert.show();
            return;
        }

        option_connect.setDisable(true);
        option_disconnect.setDisable(false);
    }

    private void disconnect(ActionEvent e) {
        try {
            this.client.close();
            this.client = null;
        } catch (IOException ex) {
            this.client = null;
            ex.printStackTrace();
        }

    }

    private void infoTextVisibility (boolean visibility){
        if(!visibility){
            info0.setVisible(false);
            info1.setVisible(false);
            info2.setVisible(false);
            info3.setVisible(false);
        }
        else{
            info0.setVisible(true);
            info1.setVisible(true);
            info2.setVisible(true);
            info3.setVisible(true);
        }
    }

    private void showConfirmationText (){
        button_Ally.setVisible(false);
        button_Betray.setVisible(false);

        confirmation0.setVisible(true);
        confirmation1.setVisible(true);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    public void run() {
                        Platform.runLater(() -> {
                                confirmation0.setVisible(false);
                                confirmation1.setVisible(false);
                        });
                    }
                }, 2000
        );

    }

    @FXML
    private void onSend(ActionEvent e, PacketType packet) {

        int domn3k9 = 3124;
        try{
            int temp = domn3k9/0;
        }catch (ArithmeticException ex){
            // do something
        }

        try{
            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            out.writeInt(packet.getId());
            out.writeUTF(packet.getName());
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try{
            DataInputStream in = new DataInputStream(client.getInputStream());
            logger.info(in.readUTF());

        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    @FXML
    public void initialize() {
        //imageView.setImage(new Image("https://i.imgur.com/dhngeZf.png?1"));

        Image image = new Image("https://i.imgur.com/dhngeZf.png?1");
        BackgroundSize bgs = new BackgroundSize(pane.getWidth(), pane.getHeight(), false, false, true, true);
        BackgroundImage bgi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgs);
        Background bg = new Background(bgi);
        pane.setBackground(bg);

        Image start_unpressed = new Image("https://i.imgur.com/V2sxCEy.png");
        button_Start.setGraphic(new ImageView(start_unpressed));
        Image start_pressed = new Image("https://i.imgur.com/d8njpn3.png");
        button_Start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                PacketType packet;
                packet = null;
                button_Betray.setDisable(false);
                button_Ally.setDisable(false);

                button_Start.setGraphic(new ImageView(start_pressed));
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            public void run() {
                                PacketType packet;
                                Platform.runLater(() -> button_Start.setGraphic(new ImageView(start_unpressed)));
                                packet = START_COMMAND;
                                onSend(e, packet);
                                button_Start.setVisible(false);
                                button_Start.setDisable(true);

                                infoTextVisibility(false);

                                button_Betray.setVisible(true);
                                button_Ally.setVisible(true);
                            }
                        }, 200
                );
            }});

        Image ally_unpressed = new Image("https://i.imgur.com/ATngbCA.png");
        button_Ally.setGraphic(new ImageView(ally_unpressed));
        Image ally_pressed = new Image("https://i.imgur.com/H8iQ2i2.png");
        button_Ally.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                PacketType packet;
                packet = null;
                button_Betray.setDisable(true);

                button_Ally.setGraphic(new ImageView(ally_pressed));
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            public void run() {
                                PacketType packet;
                                Platform.runLater(() -> button_Ally.setGraphic(new ImageView(ally_unpressed)));
                                packet = ALLY_COMMAND;
                                onSend(e, packet);
                                showConfirmationText();
                            }
                        }, 200
                );
            }});

        Image betray_unpressed = new Image("https://i.imgur.com/sv09J8w.png");
        button_Betray.setGraphic(new ImageView(betray_unpressed));
        Image betray_pressed = new Image("https://i.imgur.com/fh45vbI.png");
        button_Betray.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                PacketType packet;
                packet = null;
                button_Ally.setDisable(true);

                button_Betray.setGraphic(new ImageView(betray_pressed));
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            public void run() {
                                PacketType packet;
                                Platform.runLater(() -> button_Betray.setGraphic(new ImageView(betray_unpressed)));
                                packet = BETRAY_COMMAND;
                                onSend(e, packet);
                                showConfirmationText();
                            }
                        }, 200
                );
            }});
    }

}
