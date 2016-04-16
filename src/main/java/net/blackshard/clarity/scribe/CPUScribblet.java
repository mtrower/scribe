package net.blackshard.clarity.scribe;

import java.io.*;

/**
 * @author Matthew R. Trower
 * class CPUScribblet
 * 
 * Scribblet to log basic CPU activity.
 */
public class CPUScribblet implements Runnable {
    String name = "CPU Scribblet";
    int[] stats;

    Process proc;
    BufferedReader pin;
    VMStatParser parser;

    public CPUScribblet() {
        stats = new int[3];
        parser = new VMStatParser();
    }

    public void run() {
        System.out.println(name + ": starting up!");

        initializeGatherer();

        for (int i = 0; i < 5; i++) {
            //System.out.println("CPU Scribblet: tick");

            gatherStats();
            writeStats();

            try { Thread.sleep(1000); }
            catch (InterruptedException ie) {
                try {
                    pin.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                return;
            }
        }

        System.out.println(name + ": shutting down!");
    }

    private void initializeGatherer() {
        try {
            proc = Runtime.getRuntime().exec("vmstat 1");
            pin = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            for (int i = 0; i < 3; i++)
                pin.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void gatherStats() {
        String line;

        try {
            line = pin.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        if (!line.isEmpty()) {
            parser.parse(line);

            for (int i = 0; i < 3; i++)
                stats[i] = Integer.valueOf(parser.getStat(i + 19));
        }
    }

    private void writeStats() {
        System.out.println(String.format("%s: user %d \tsystem %d \t idle %d", 
                                            name, stats[0], stats[1], stats[2]));
    }
}
