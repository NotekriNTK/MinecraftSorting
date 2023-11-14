package ntk.notekri.sorteringsalgo.sorteringslogik;

import ntk.notekri.sorteringsalgo.SorteringsAlgo;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TowerOfHanoi {

    private static final Map<Integer, Integer> colourMap = new HashMap<Integer, Integer>() {{
        put(1, 4);
        put(2, 1);
        put(3, 14);
        put(4, 2);
        put(5, 10);
        put(6, 11);
        put(7, 9);
        put(8, 3);
        put(9, 5);
    }};

    private static final Map<String, Integer> map = new HashMap<String, Integer>() {{
        put("A", 0);
        put("B", 1);
        put("C", 2);
    }};
    public static void towerOfHanoi(Player player, SorteringsAlgo main) {
        int size = 8;
        Location location = player.getLocation();
        location.setX(location.getBlockX() + 20);
        location.setY(location.getBlockY() - ((double) (int) size / 2));
        List<String[]> operations = new ArrayList<String[]>();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        for(int i = 0; i <size;i++){
            for(int _z = -size+i; _z < size-i-2;_z++){
                Block block = location.getWorld().getBlockAt(x,y+i,-(size*2)+1+z+_z);
                block.setType(Material.CONCRETE);
                int colour = colourMap.get(8-i);
                block.setData((byte) colour);
            }
        }


        sort(location, size, "A", "C", "B", operations, player);
        int delay = 3;
        new BukkitRunnable() {

            int idx = 0;
            int max = operations.size();
            int width = (size*2)+1;
            int[] middleList = {z-width,z,z+width};


            public void run() {
                if (idx >= max) {
                    player.sendMessage(ChatColor.BLUE + "Done sorting :)");
                    cancel();
                    return;
                }

                String[] operation = operations.get(idx);
                int _size = Integer.parseInt(operation[0]);
                int from = map.get(operation[1]);
                int to = map.get(operation[2]);
                int middleFrom = middleList[from];
                int middleTo = middleList[to];
                //player.sendMessage("From "+middleFrom+" -> "+middleTo);
                int yFrom = location.getWorld().getHighestBlockYAt(x,middleFrom)-1;
                int yTo = location.getWorld().getHighestBlockYAt(x,middleTo);
                if (yTo < y){
                    yTo = y;
                }
                for(int _z = -_size; _z <= _size;_z++){
                    Block toBlock = location.getWorld().getBlockAt(x,yTo,middleTo+_z);
                    toBlock.setType(Material.CONCRETE);
                    int colour = colourMap.get(_size);
                    toBlock.setData((byte) colour);
                    location.getWorld().getBlockAt(x,yFrom,middleFrom+_z).setType(Material.AIR);
                }


                idx++;
            }
        }.runTaskTimer(main, delay, delay);
    }

    public static void sort(Location location, int n, String from,
                String to, String aux, List<String[]> operations, Player player){

        if (n == 1){
            //  Move ring 1 from 'from' to 'to'.
            String[] operation = {"1", from, to};
            operations.add(operation);
            return;
        }

        sort(location, n-1, from, aux, to, operations, player);
        //  Move ring 'n' from 'from' to 'to'.
        String[] operation = {n+"", from, to};
        operations.add(operation);
        sort(location, n-1, aux, to, from, operations, player);
    }

}
