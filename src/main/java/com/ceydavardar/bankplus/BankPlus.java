package com.ceydavardar.bankplus;

import com.ceydavardar.bankplus.commands.BankPlusCMD;
import com.ceydavardar.bankplus.configurations.Resource;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class BankPlus extends JavaPlugin {

    private Resource balances;
    private HashMap<String, Double> balancesHash = new HashMap<>();

    public void onEnable() {

        this.balances = new Resource(this, "balances.yml");
        this.balances.load();

        getCommand("bank").setExecutor(new BankPlusCMD(this));

        loadBalancesFromFile(balances);

    }

    public void onDisable() {

        saveBalancesToFile(balancesHash, balances);

    }

    private void loadBalancesFromFile(Resource balances) {

        for (String uuid : balances.getKeys(false)) {

            balancesHash.put(uuid, balances.getDouble(uuid));

        }

    }

    private void saveBalancesToFile(HashMap<String, Double> balancesHash, Resource balances) {

        for (String key : balancesHash.keySet()) {
            balances.set(key, balancesHash.get(key));
        }

        balances.save();

    }

    public HashMap<String, Double> getBalancesHash() {
        return balancesHash;
    }

}
