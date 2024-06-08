package Buen.Pastor.app.entity.service;

public class Location {
    private int id;
    private String room;
    private String physicalLocation;

    public Location() {
    }

    public Location(Integer id) {
        this.id = id;
    }

    public Location(Integer id, String room, String physicalLocation) {
        this.id = id;
        this.room = room;
        this.physicalLocation = physicalLocation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(String physicalLocation) {
        this.physicalLocation = physicalLocation;
    }
}
