package net.blackshard.clarity.scribe;

/**
 * @author Matthew R. Trower
 * class RAMScribblet
 * 
 * Scribblet to log basic RAM status.
 */
public class RAMScribblet implements Runnable {

    public RAMScribblet() {
        System.out.println("RAM Scribblet: starting up!");
    }

    public void run() {
       for (int i = 0; i < 5; i++) {
           System.out.println("RAM Scribblet: tick");

           try { Thread.sleep(1000); }
           catch (InterruptedException ie) { return; }
       }
    }
}
