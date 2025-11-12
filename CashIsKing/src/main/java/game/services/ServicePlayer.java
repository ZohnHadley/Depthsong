package game.services;

import game.models.Player;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class ServicePlayer {
    private static ServicePlayer instance = null;
    @Getter
    private Player player;
    private ServicePlayer() {
    }

    public static ServicePlayer getInstance() {
        if (instance == null) {
            instance = new ServicePlayer();
        }
        return instance;
    }

    public Player createPlayer(String name) {
        player = new Player(name);
        return player;
    }

    public Player createPlayer(String name, double netWorth) {
        createPlayer(name).setTotalNetWorth(netWorth);
        return player;
    }


}
