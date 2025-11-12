package game.models;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Player {
    String name = "Player";
    List<Corporation> companies = new ArrayList<>();
    double totalNetWorth = 0.0;

    public Player(){
    }

    public Player(String param_name){
        this.name = param_name;
    }

    public Player(String param_name, double param_netWorth){
        this.name = param_name;
        this.totalNetWorth = param_netWorth;
    }

    public void addCompany(Corporation corporation) {
        companies.add(corporation);
        updateNetWorth();
    }

    public void removeCompany(Corporation corporation) {
        companies.remove(corporation);
        updateNetWorth();
    }

    public void updateNetWorth() {
        totalNetWorth = companies.stream()
                .mapToDouble(Corporation::getIncome)
                .sum();
    }
}
