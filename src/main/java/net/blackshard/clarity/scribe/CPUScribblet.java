package net.blackshard.clarity.scribe;

/**
 * @author Matthew R. Trower
 * class CPUScribblet
 * 
 * Scribblet to log basic CPU activity.
 */
public class CPUScribblet implements Runnable {

    public CPUScribblet() {
        System.out.println("CPU Scribblet: starting up!");
    }

    public void run() {
       for (int i = 0; i < 5; i++) {
           System.out.println("CPU Scribblet: tick");

           try { Thread.sleep(1000); }
           catch (InterruptedException ie) { return; }
       }
    }
}
