package ntk.notekri.sorteringsalgo.listeners;

import ntk.notekri.sorteringsalgo.Handler;
import ntk.notekri.sorteringsalgo.SorteringsAlgo;
import ntk.notekri.sorteringsalgo.sorteringslogik.SortingQuick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmds implements CommandExecutor {

    private Handler handler;
    private SorteringsAlgo main;

    public Cmds(Handler handler){
        this.handler = handler;
        this.main = this.handler.getMain();
        setup();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("You must be a player to execute this command!");
            return true;
        }
        Player player = (Player)sender;
        if (label.equalsIgnoreCase("fuck")){
            player.sendMessage("asdiausd :)");
            return true;
        }

        if (label.equalsIgnoreCase("quicksort")){
            player.sendMessage("Start sortering :)");
            SortingQuick.sort(player,main);
            return true;
        }

        return true;
    }


    private void setup(){
        main.getCommand("fuck").setExecutor(this);
        main.getCommand("quicksort").setExecutor(this);
    }
}
