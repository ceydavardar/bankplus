package com.ceydavardar.bankplus.commands;

import com.ceydavardar.bankplus.BankPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankPlusCMD implements CommandExecutor {

    private final BankPlus plugin;

    public BankPlusCMD(BankPlus plugin) {
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("bank")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("You are not player.");
                return false;
            }

            Player player = (Player)sender;

            if (args.length==0) {

                String uuid = player.getUniqueId().toString();

                if (!plugin.getBalancesHash().containsKey(uuid)) {

                    player.sendMessage("You don't have a bank account.");

                    return false;

                }

                double balance = plugin.getBalancesHash().get(uuid);

                player.sendMessage("You have $"+balance+" in your bank account.");

                return true;
            }


            if (args.length == 2 && args[0].equalsIgnoreCase("deposit")) {

                double amount;
                try {
                    amount=Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage("This is not a valid number.");
                    return false;
                }

                if (amount < 0) {
                    player.sendMessage("Number can not be lower than 0.");
                    return false;
                }

                double balance;

                if (plugin.getBalancesHash().containsKey(player.getUniqueId().toString())) {
                    balance = plugin.getBalancesHash().get(player.getUniqueId().toString());
                } else {
                    balance=0;
                }

                double newBalance = balance + amount;

                plugin.getBalancesHash().put(player.getUniqueId().toString(), newBalance);

                player.sendMessage("Your new bank balance is $"+newBalance);

                return true;

            }

            if (args.length == 2 && args[0].equalsIgnoreCase("withdraw")) {

                double amount;
                try {
                    amount = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage("This is not a valid number.");
                    return false;
                }

                if (amount < 0) {
                    player.sendMessage("Number can not be lower than 0.");
                    return false;
                }

                double balance;

                if (plugin.getBalancesHash().containsKey(player.getUniqueId().toString())) {
                    balance = plugin.getBalancesHash().get(player.getUniqueId().toString());
                } else {
                    balance=0;
                }

                double newBalance = balance - amount;

                plugin.getBalancesHash().put(player.getUniqueId().toString(), newBalance);

                player.sendMessage("Your new bank balance is $" + newBalance);

                return true;

            }

            player.sendMessage("Incorrect command usage.");

            return false;

        }

        return false;

    }
}
