package co.px.depthsong.network.Local.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class PlayerObj {
    private @JsonProperty("clientServer_id") int clientServer_id;
    private @JsonProperty("username") String username = "N/A";
    private @JsonProperty("spriteKey") String spriteKey = "mage";
    private @JsonProperty("x") int x;
    private @JsonProperty("y") int y;
    private @JsonProperty("localChannelAddress") String localChannelAddress = "N/A"; //TODO make so it throws error when null

    private @JsonProperty("hasServerID") boolean hasServerID = false;

    public PlayerObj() {
    }

    public PlayerObj(String username, String spriteKey, int x, int y) {
        this.username = username;
        this.spriteKey = spriteKey;
        this.x = x;
        this.y = y;

    }

    public PlayerObj(String username, String spriteKey, int x, int y, boolean hasServerID) {

        this.username = username;
        this.spriteKey = spriteKey;
        this.x = x;
        this.y = y;
        this.hasServerID = hasServerID;
    }

    public PlayerObj(int clientServerid, String username, String spriteKey, int x, int y) {
        this.clientServer_id = clientServerid;
        this.username = username;
        this.spriteKey = spriteKey;
        this.x = x;
        this.y = y;
    }


    public int getClientServer_id() {
        return this.clientServer_id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getSpriteKey() {
        return this.spriteKey;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getLocalChannelAddress() {
        return this.localChannelAddress;
    }

    public boolean getHasServerID() {
        return hasServerID;
    }

    public void setClientServer_id(int clientServer_id) {
        this.clientServer_id = clientServer_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSpriteKey(String spriteKey) {
        this.spriteKey = spriteKey;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setLocalChannelAddress(String localChannelAddress) {
        this.localChannelAddress = localChannelAddress;
    }

    public void setHasServerID(boolean hasServerID) {
        this.hasServerID = hasServerID;
    }

    @JsonIgnore
    public String toString() {
        return "PlayerObj(clientServer_id=" + this.getClientServer_id() + ", username=" + this.getUsername() + ", x=" + this.getX() + ", y=" + this.getY() + ")";
    }

    @JsonIgnore
    public static ObjectMapper mapper = new ObjectMapper();

    @JsonIgnore
    public String toJson() {
        //converts the object to a json string first
        try {
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
