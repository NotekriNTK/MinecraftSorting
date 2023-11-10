package ntk.notekri.sorteringsalgo.sorteringslogik;

import ntk.notekri.sorteringsalgo.SorteringsAlgo;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;

public class SortingQuick {


    public static void sort(Player player, SorteringsAlgo main){
        Location location = player.getLocation();

        //  All start coordinates, with the offset from the player.
        int coordinateY = location.getBlockY();
        int startX = location.getBlockX() + (256-coordinateY) + 3;
        int startZ = location.getBlockZ() - ((int)(0.5 * (255 - coordinateY)));
        final int[] startingCoordinates = {startX,coordinateY,startZ};

        //  Creating all the heights in a list.
        int[] allHeights = new int[(255-coordinateY)];
        int _z = 0;
        for (int y = coordinateY; y < 255; y++){allHeights[_z] = y ; _z++;}

        //  Shuffling the list.
        Random rand = new Random();

        for (int i = 0; i < allHeights.length; i++) {
            int randomIndexToSwap = rand.nextInt(allHeights.length);
            int temp = allHeights[randomIndexToSwap];
            allHeights[randomIndexToSwap] = allHeights[i];
            allHeights[i] = temp;
        }

        //  Showing the list as pillars in Minecraft.
        for (int z = 0; z<allHeights.length;z++){
            for (int y = 1; y<256;y++){
                player.getWorld().getBlockAt(startingCoordinates[0],y,startingCoordinates[2]+z).setType(Material.AIR);}}
        for (int z = 0; z < allHeights.length; z++){
            int y = allHeights[z];
            int posZ = startZ+z;
            for (int _y = y; _y>1; _y--){
                player.getWorld().getBlockAt(startX,_y,posZ).setType(Material.IRON_BLOCK);
            }
        }

        //  Now we sort the shit
        player.sendMessage(ChatColor.RED + Arrays.toString(allHeights));

        //  Since Runnables are a bit tricky to use in loops, I save
        //      all moves in a list. The list contains two moves at
        //      a time, since quicksort swaps elements.
        List<int[][]> order = new ArrayList<int[][]>();
        quickSort(allHeights, order);

        //  The same concept for an order list is used with mergesort,
        //      with the exception that mergesort doesn't swap elements.
        //List<int[]> order = new ArrayList<int[]>();
        //Mergesort.mergeSort(allHeights,order);


        //  Beause the Runnables run in parallel, I need an initial delay
        //      that's the equal to the total delay of the sorting animation.
        long normalDelay = 1L;
        long initDelay = (order.size() * normalDelay) + normalDelay;

        //  Animate the moves
        new BukkitRunnable() {
            final int[] coordinateOffset = startingCoordinates;

            int idx = 0;
            final int max = order.size();
            int[][] prevSet = null;
            public void run() {
                if (idx >= max){
                    player.sendMessage(ChatColor.BLUE+"Done sorting :)");
                    cancel();
                    return;
                }
                if (prevSet != null) {
                    createPillars(prevSet, coordinateOffset, Material.IRON_BLOCK, player);
                }

                int[][] set = order.get(idx);
                createPillars(set,coordinateOffset,Material.REDSTONE_BLOCK,player);
                prevSet = set;
                player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT,5f,2f);

                idx ++;
            }
        }.runTaskTimer(main,normalDelay, normalDelay);

        //  Green animation shit
        new BukkitRunnable() {
            final int[] coordinateOffset = startingCoordinates;
            int idx = 0;
            final int max = allHeights.length;

            //  The pitch goes from 0.5f -> 2.0f, therefore I
            //      divide the 1.5f difference with the amount
            //      of elements in the list.
            final float interval = 1.5f/max;
            int height = 0;

            public void run() {
                if (idx >= max) {
                    cancel();
                    return;
                }
                height = allHeights[idx];
                int[] set = {idx,height};
                createPillar(set, coordinateOffset, Material.EMERALD_BLOCK, player);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,5f,0.5f+(interval*idx));
                idx ++;
            }

        }.runTaskTimer(main,initDelay, 1L);
    }

    private static void createPillars(int[][] set, int[] coords, Material type, Player player){
        int zI = set[0][0];int zJ = set[1][0];
        int yI = set[0][1];int yJ = set[1][1];
        Material matI;
        Material matJ;
        for (int y = 1; y<256;y++){
            matI = Material.AIR;
            matJ = Material.AIR;
            if (y <= yI){matI = type;}
            if (y <= yJ){matJ = type;}

            player.getWorld().getBlockAt(coords[0],y,coords[2]+zI).setType(matI);
            player.getWorld().getBlockAt(coords[0],y,coords[2]+zJ).setType(matJ);}
    }

    private static void createPillar(int[] set, int[] coords, Material type, Player player){
        int z = set[0]; int _y = set[1];
        Material mat;
        for (int y = 1; y<256;y++) {
            mat = Material.AIR;
            if (y <= _y){mat = type;}
            player.getWorld().getBlockAt(coords[0],y,coords[2]+z).setType(mat);
        }
    }


    public static void quickSort(int[] arr, List<int[][]> order) {
        if (arr == null || arr.length == 0) {
            return;
        }
        quickSort(arr, 0, arr.length - 1, order);
    }

    private static void quickSort(int[] arr, int low, int high, List<int[][]> order) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high,order);
            quickSort(arr, low, pivotIndex - 1,order);
            quickSort(arr, pivotIndex + 1, high,order);
        }
    }

    private static int partition(int[] arr, int low, int high, List<int[][]> order) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j,order);
            }
        }

        swap(arr, i + 1, high,order);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j, List<int[][]> order) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        int[][] set = {{i,arr[i]},{j,arr[j]}};
        order.add(set);
    }
}
