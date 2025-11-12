package game.models;

import java.util.ArrayList;
import java.util.List;

import game.models.Market.IndustryType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Corporation {
    private String corporationLogoSrc = "/assets/logos/logo1.png";
    private String ticker = "DCC";
    private String companyName = "Default Company";
    private IndustryType industry = IndustryType.RETAIL;
    private double income = 0.0;
    private double revenu = 0.0;
    private double expenses = 0.0;
    private double capital = 0.0;
    private List<Corporation> subsidiaries = new ArrayList<>();
    private Corporation parentCorporation;

    public Corporation(){
    }

    public Corporation(String param_ticker, String param_name) {
        this.ticker = param_ticker;
        this.companyName = param_name;
    }

    public Corporation(String param_ticker, String param_name, IndustryType param_industry, double param_revenu, double param_expenses) {
        this.ticker = param_ticker;
        this.companyName = param_name;
        this.industry = param_industry;
        this.income = 0;
        this.revenu = param_revenu;
        this.expenses = param_expenses;
        this.capital = param_expenses;
    }

    public void addSubsidiary(Corporation subsidiary) {
        subsidiaries.add(subsidiary);
        subsidiary.setParentCorporation(this);
    }

    public void removeSubsidiary(Corporation subsidiary) {
        subsidiaries.remove(subsidiary);
        subsidiary.setParentCorporation(null);
    }

}
