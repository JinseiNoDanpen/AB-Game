package org.luca.AB_Game.client;

public enum PacketType {
    START_NOTIFICATION("start-notification", 0),
    ALLY_COMMAND("ally-command", 1),
    BETRAY_COMMAND("betray-command", 2),
    START_COMMAND("start-command", 3);



    private String name;
    private int id;

    PacketType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}
