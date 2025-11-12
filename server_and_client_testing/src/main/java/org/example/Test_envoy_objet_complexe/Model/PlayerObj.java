package org.example.Test_envoy_objet_complexe.Model;

public class PlayerObj {
    private Long clientServer_id;
    private String username;

    private int x;
    private int y;

    public PlayerObj() {
    }

    public PlayerObj(Long clientServer_id, String username, int x, int y) {
        this.clientServer_id = clientServer_id;
        this.username = username;
        this.x = x;
        this.y = y;
    }
    
    public Long getClientServer_id() {
        return this.clientServer_id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setClientServer_id(Long clientServer_id) {
        this.clientServer_id = clientServer_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return "PlayerObj(clientServer_id=" + this.getClientServer_id() + ", username=" + this.getUsername() + ", x=" + this.getX() + ", y=" + this.getY() + ")";
    }
}
