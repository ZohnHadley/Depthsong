package game;

import game.JFrame_UI.SimpleInterface;
import game.models.Corporation;
import game.models.Market.IndustryType;
import game.models.Player;
import game.services.ServiceCorporation;
import game.services.ServicePlayer;

public class Main
{
    static GameEngine gameEngine = GameEngine.getInstance();
    static GameScheduler gameScheduler = GameScheduler.getInstance();

    static Player player = ServicePlayer.getInstance().createPlayer("Zohn",100);
    static Corporation corporation = ServiceCorporation.getInstance().createCompany("APPL", "Apple Inc.", IndustryType.TECHNOLOGY, 1000, 500);
    static Corporation corporation2 = ServiceCorporation.getInstance().createCompany("TSLA", "TESLA", IndustryType.TECHNOLOGY, 1, 500);

    private static void game(){

        System.out.println("Player: " + player.getName());
        player.addCompany(corporation);
        player.addCompany(corporation2);
        gameScheduler.start();
        SimpleInterface.getInstance().initWindow();
    }

    public static void main(String[] args) {
        game();
    }
}
