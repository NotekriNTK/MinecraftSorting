package ntk.notekri.sorteringsalgo;

import org.bukkit.plugin.java.JavaPlugin;

public final class SorteringsAlgo extends JavaPlugin {
    Handler handler;
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("(!) LETS GET IT STARTED YEA!");
        handler = new Handler(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
