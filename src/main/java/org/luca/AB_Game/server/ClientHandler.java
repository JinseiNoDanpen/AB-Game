package org.luca.AB_Game.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ClientHandler extends Thread {
    private static final Logger logger = LogManager.getLogManager().getLogger("ClientHandler");

    private Main main;
    private Socket connection;

    public Map<String, Client> clients;

    public ClientHandler(Client client, Main main) {
        super("ClientHandler");
        logger.info("New client initialized! " + connection.getInetAddress().getHostAddress());
        this.main = main;
        this.connection = client.getSocket();

        clients.put(client.getSocket().getInetAddress().getHostAddress(), client);
    }

    @Override
    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            DataInputStream in = new DataInputStream(connection.getInputStream());
            boolean isClientOpen = true;
            while (isClientOpen) {
                try {
                    boolean hasGameStarted = false;
                    Packet packet_in = decode(in);
                    Packet packet_out = null;
                    InetAddress address = connection.getInetAddress();
                    switch(packet_in.getId()) {
                        case 1: {
                            String command = packet_in.getMessage();
                            logger.info("Command [" + command + "] requested by client with address: " + address);
                            out.writeUTF("Command acknowledged.");
                            //main.getServer().getScheduler().runTask(plugin, () -> plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command));
                            break;
                        }
                        case 2: {
                            String command = packet_in.getMessage();
                            logger.info("Command [" + command + "] requested by client with address: " + address);
                            out.writeUTF("Command acknowledged.");
                            //Runtime.getRuntime().exec(command);
                            break;
                        }
                        case 3: {
                            String command = packet_in.getMessage();
                            logger.info("Command [" + command + "] requested by client with address: " + address);
                            packet_out.setId(0);

                            if (hasGameStarted){
                                //TODO
                            }
                            hasGameStarted = true;
                            //Runtime.getRuntime().exec("cmd /c " + command);
                            break;
                        }
                        default: {
                            logger.info("Received packet from " + connection.getInetAddress().getHostAddress() + " with id " + packet_in.getId() + " message " + packet_in.getMessage());
                            break; // Unknow packet
                        }
                    }
                } catch (IOException e) {
                    // The client may have closed the socket.
                    isClientOpen = false;
                    e.printStackTrace();
                } catch (Exception e) {
                    // Every exception type...
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Packet decode(DataInputStream in) throws IOException {
        return new Packet( in.readInt(), in.readUTF());
    }

}
