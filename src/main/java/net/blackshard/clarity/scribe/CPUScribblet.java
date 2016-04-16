package net.blackshard.clarity.scribe;

/**
 * @author Matthew R. Trower
 * class CPUScribblet
 * 
 * Scribblet to log basic CPU activity.
 */
public class CPUScribblet implements Scribblet {
    /*
     *  Constructor for CPUScribblet
     */
    public CPUScribblet() {

    }

    public void run() {
        System.out.println("CPU Scribblet starting up!");
    }
}
