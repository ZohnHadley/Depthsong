package game.services;

import game.models.Corporation;
import game.models.Market.IndustryType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCorporation {
    private static ServiceCorporation instance = null;
    private List<Corporation> companies;
    private ServiceCorporation() {
        companies = new ArrayList<>();
    }

    public static ServiceCorporation getInstance() {
        if (instance == null) {
            instance = new ServiceCorporation();
        }
        return instance;
    }

    public Corporation createCompany(String ticker, String name) {
        Corporation corporation = new Corporation(ticker, name);
        companies.add(corporation);
        return corporation;
    }

    public Corporation createCompany(String ticker, String name, IndustryType industry, double revenue, double expenses) {
        Corporation corporation = new Corporation(ticker, name, industry, revenue, expenses);
        companies.add(corporation);
        return corporation;
    }

    public void deleteCompany(Corporation corporation) {
        if (corporation != null) {
            companies.remove(corporation);
            if (corporation.getParentCorporation() != null) {
                corporation.getParentCorporation().removeSubsidiary(corporation);
            }
            for (Corporation subsidiary : corporation.getSubsidiaries()) {
                subsidiary.setParentCorporation(null);
            }
        }
    }
}
