package org.luca.AB_Game.server;

import java.net.Socket;

class Client
{
    private String ipAddress;
    private Socket socket = null;

    public Client (String ipAddress, Socket socket)
    {
        this.ipAddress = ipAddress;
        this.socket = socket;
    }

    public Socket getSocket()
    {
        return this.socket;
    }
}
