package game;

import game.models.*;
import game.services.ServiceCorporation;
import game.services.ServiceMarket;
import game.services.ServicePlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameEngine {
    private static GameEngine instance = null;

    private ServicePlayer servicePlayer;
    private ServiceMarket serviceMarket;
    private ServiceCorporation serviceCompany;

    private GameEngine() {
        servicePlayer = ServicePlayer.getInstance();
        serviceMarket = ServiceMarket.getInstance();
        serviceCompany = ServiceCorporation.getInstance();
    }

    public static GameEngine getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }


    public void tick() {

        if(servicePlayer.getPlayer() == null){
            throw new IllegalStateException("No player found in the game context. Please create a player before starting the game.");
        }

        Player currentPlayer = servicePlayer.getPlayer();
        for (Corporation corporation : currentPlayer.getCompanies()) {

            // Calculate incomee based on market demand and supply

            Market market = serviceMarket.getMarket(corporation.getIndustry());
            double supplyDemandRatio = market.getSupply() / market.getDemand();
            double currentEarnings = corporation.getRevenu() * (supplyDemandRatio * market.getBaseincomeeMultiplier());
            // Revenue is determined by multiplying the price of a product or service by the number of units sold. Income is calculated by subtracting all expenses and taxes from the total revenue
            double income = currentEarnings - corporation.getExpenses();

            // Update company income and capital
            corporation.setIncome(income);
            corporation.setCapital(corporation.getCapital() + income);

        }

        serviceMarket.updateMarkets();
    }




}
