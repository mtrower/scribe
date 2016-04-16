package net.blackshard.clarity.scribe;

/**
 * @author Matthew R. Trower
 * class RAMScribblet
 * 
 * Scribblet to log basic RAM status.
 */
public class RAMScribblet implements Scribblet {
    /*
     *  Constructor for RAMScribblet
     */
    public RAMScribblet() {

    }

    public void run() {
        System.out.println("RAM Scribblet starting up!");
    }
}
