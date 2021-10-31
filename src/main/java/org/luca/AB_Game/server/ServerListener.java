package org.luca.AB_Game.server;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ServerListener extends Thread {
    private static final Logger logger = LogManager.getLogManager().getLogger("ServerListener");

    private Main main;
    private ServerSocket server;
    private Socket socket;


    public ServerListener(Main main, ServerSocket server) {
        super("ServerListener");

        this.main = main;
        this.server = server;
    }

    @Override
    public void run() {
        while(true) {
            if(!main.isServerEnabled()) {
                break;
            }

            try {

                socket = server.accept();

                Client client = new Client(socket.getInetAddress().getHostAddress(), socket);

                ClientHandler handler = new ClientHandler(client, main);
                handler.start(); // Handle client connection
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
