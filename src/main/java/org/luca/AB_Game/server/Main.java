package org.luca.AB_Game.server;

import com.dosse.upnp.UPnP;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger logger = LogManager.getLogManager().getLogger("Main");

    public static final int PORT = 49654;
    public static final boolean FORCE_START = false;

    private ServerSocket server;
    private ServerListener listener;
    private boolean opened = false;
    private boolean started = false;



    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getClassLoader().getResource("server_panel.fxml"));
        loader.setControllerFactory(c -> new Controller(this));
        Parent root = loader.load();

        primaryStage.setTitle("AB Game");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("https://i.imgur.com/50XPa3m.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            // Queste azioni vengono svolte quando viene stoppata la applicazione tramite la X
            this.stopServer();
            System.exit(0);
        });

        if (UPnP.isUPnPAvailable()) {
            opened = UPnP.openPortTCP(PORT, "AB Game");
        } else {
            logger.severe("UPnP not Available!");
        }

        if(FORCE_START) {
            logger.warning("Ignoring UPnP!");
            opened = true;
        }

        // Lo metti qua
        //this.startServer();
    }

    public void startServer() {
        if (started) {
            logger.severe("The server is already enabled!");
            return;
        }

        if (!opened && !FORCE_START) {
            logger.severe("Port closed!");
            return;
        }

        try {
            logger.info("Starting server on port" + PORT + "...");
            started = true;
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("Failed to start plugin on level 2!!");
            return;
        }

        listener = new ServerListener(this, server);
        listener.start(); // Start clients listener
    }

    public void stopServer() {
        if (!started) {
            logger.severe("The server is already disabled!");
            return;
        }

        try {
            logger.info("Stopping server...");
            started = false;
            listener.interrupt();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isServerEnabled() {
        return started;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
