package game.services;

import game.models.Market;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceMarket {
    private static ServiceMarket instance;

    //TODO minimise characters of these variables names
    //TODO clean this shi up (smelly code)
    HashMap<String, HashMap<String,Double>> marketBaseValues = new HashMap<>();
    // Base values for each market

    private double TECH_DEMAND = 100, TECH_SUPPLY = 50, TECH_income_MULTIPLAYER = 1.5;
    private double RETAIL_DEMAND = 80, RETAIL_SUPPLY = 40, RETAIL_income_MULTIPLAYER = 1.2;
    private double REAL_ESTATE_DEMAND = 60, REAL_ESTATE_SUPPLY = 30, REAL_ESTATE_income_MULTIPLAYER = 1.3;
    private double ENERGY_DEMAND = 90, ENERGY_SUPPLY = 45, ENERGY_income_MULTIPLAYER = 1.4;
    private double MANUFACTURING_DEMAND = 70, MANUFACTURING_SUPPLY = 35, MANUFACTURING_income_MULTIPLAYER = 1.1;

    private Market technologyMarket = new Market(Market.IndustryType.TECHNOLOGY, TECH_DEMAND, TECH_SUPPLY, TECH_income_MULTIPLAYER);
    private Market retailMarket =  new Market(Market.IndustryType.RETAIL, RETAIL_DEMAND, RETAIL_SUPPLY, RETAIL_income_MULTIPLAYER);
    private Market realEstateMarket = new Market(Market.IndustryType.REAL_ESTATE, REAL_ESTATE_DEMAND, REAL_ESTATE_SUPPLY, REAL_ESTATE_income_MULTIPLAYER);
    private Market energyMarket = new Market(Market.IndustryType.ENERGY, ENERGY_DEMAND, ENERGY_SUPPLY, ENERGY_income_MULTIPLAYER);
    private Market manufacturingMarket = new Market(Market.IndustryType.MANUFACTURING, MANUFACTURING_DEMAND, MANUFACTURING_SUPPLY, MANUFACTURING_income_MULTIPLAYER);

    @Getter
    private List<Market> marketList;

    private ServiceMarket() {
        marketBaseValues.put("TECHNOLOGY", new HashMap<>(Map.of(
            "demand", TECH_DEMAND,
            "supply", TECH_SUPPLY,
            "incomeMultiplier", TECH_income_MULTIPLAYER
        )));
        marketBaseValues.put("RETAIL", new HashMap<>(Map.of(
            "demand", RETAIL_DEMAND,
            "supply", RETAIL_SUPPLY,
            "incomeMultiplier", RETAIL_income_MULTIPLAYER
        )));
        marketBaseValues.put("REAL_ESTATE", new HashMap<>(Map.of(
            "demand", REAL_ESTATE_DEMAND,
            "supply", REAL_ESTATE_SUPPLY,
            "incomeMultiplier", REAL_ESTATE_income_MULTIPLAYER
        )));
        marketBaseValues.put("ENERGY", new HashMap<>(Map.of(
            "demand", ENERGY_DEMAND,
            "supply", ENERGY_SUPPLY,
            "incomeMultiplier", ENERGY_income_MULTIPLAYER
        )));
        marketBaseValues.put("MANUFACTURING", new HashMap<>(Map.of(
            "demand", MANUFACTURING_DEMAND,
            "supply", MANUFACTURING_SUPPLY,
            "incomeMultiplier", MANUFACTURING_income_MULTIPLAYER
        )));

        marketList = List.of(
            technologyMarket,
            retailMarket,
            realEstateMarket,
            energyMarket,
            manufacturingMarket
        );
    }

    public static ServiceMarket getInstance() {
        if (instance == null) {
            instance = new ServiceMarket();
        }
        return instance;
    }

    public Market getMarket(Market.IndustryType industryType) {
        for (Market market : marketList) {
            if (market.getIndustry() == industryType) {
                return market;
            }
        }
        return null;
    }

    public void updateMarkets() {
        for (Market market : marketList) {
            //randomize supply and demand for each market using base value
            double baseDemand = marketBaseValues.get(market.getIndustry().name()).get("demand");
            double baseSupply = marketBaseValues.get(market.getIndustry().name()).get("supply");
            double baseIncomeMultiplier = marketBaseValues.get(market.getIndustry().name()).get("incomeMultiplier");
            //randomize the values within a range of +/- 20% of the base value
            double maxVariation = 0.1; // 10% variation
            double demandVariation = baseDemand * maxVariation;
            double supplyVariation = baseSupply * maxVariation;
            double incomeMultiplierVariation = baseIncomeMultiplier * maxVariation;

            double newDemand = baseDemand + (Math.random() * 2 - 1) * demandVariation;
            double newSupply = baseSupply + (Math.random() * 2 - 1) * supplyVariation;
            double newIncomeMultiplier = baseIncomeMultiplier + (Math.random() * 2 - 1) * incomeMultiplierVariation;

            market.setDemand(newDemand);
            market.setSupply(newSupply);
            market.setBaseincomeeMultiplier(newIncomeMultiplier);

        }
    }
}
