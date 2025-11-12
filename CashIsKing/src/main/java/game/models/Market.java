package game.models;
import lombok.Setter;

import lombok.Getter;


@Getter
@Setter
public class Market {
    public enum IndustryType {
        TECHNOLOGY,
        RETAIL,
        REAL_ESTATE,
        ENERGY,
        MANUFACTURING
    }

    private IndustryType industry = IndustryType.RETAIL; // default industry
    private double demand = 0.0; // default demand
    private double supply = 0.0; // default supply
    private double baseincomeeMultiplier = 1; // affects all companies in this market

    public Market(){
    }

    public Market(IndustryType param_industry, double param_demand, double param_supply, double param_baseincomeeMultiplier) {
        this.industry = param_industry;
        this.demand = param_demand;
        this.supply = param_supply;
        this.baseincomeeMultiplier = param_baseincomeeMultiplier;
    }
}
