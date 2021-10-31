package org.luca.AB_Game.server;

public class Packet {
    private int id;
    private String message;

    public Packet(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }

}
