package ntk.notekri.sorteringsalgo;

import ntk.notekri.sorteringsalgo.listeners.Cmds;
import ntk.notekri.sorteringsalgo.listeners.EListener;
import ntk.notekri.sorteringsalgo.sorteringslogik.SortingQuick;

public class Handler {

    EListener listener;
    Cmds cmds;
    SorteringsAlgo main;
    SortingQuick qSort;

    public Handler(SorteringsAlgo main){
        this.main = main;
        this.cmds = new Cmds(this);
    }

    //      GETTERS AND SETTERS

    public EListener getListener(){
        return this.listener;
    }
    public SorteringsAlgo getMain(){
        return this.main;
    }
    public Cmds getCmds(){
        return this.cmds;
    }
    public SortingQuick getQuickSort(){
        return this.qSort;
    }


}
