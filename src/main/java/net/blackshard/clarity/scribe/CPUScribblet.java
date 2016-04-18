package net.blackshard.clarity.scribe;

import java.io.*;
import java.util.Map;

/**
 * @author Matthew R. Trower
 * class CPUScribblet
 * 
 * Scribblet to log basic CPU activity.
 */
public class CPUScribblet implements Runnable {
    String name = "CPU Scribblet";
    Map<VMStatField, Integer> stats;

    Process proc;
    BufferedReader pin;
    VMStatParser parser;

    public CPUScribblet() {
        parser = new VMStatParser();
    }

    public void run() {
        System.out.println(name + ": starting up!");

        initializeGatherer();

        for (int i = 0; i < 5; i++) {
            //System.out.println(name + ": tick");

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

            stats = parser.getStats(new VMStatField[] {
                  VMStatField.CPU_USER
                , VMStatField.CPU_SYS
                , VMStatField.CPU_IDLE
            });
        }
    }

    private void writeStats() {
        if (stats != null)
            System.out.println(
                    String.format("%s: user %d \tsystem %d \t idle %d"
                                    , name
                                    , stats.get(VMStatField.CPU_USER)
                                    , stats.get(VMStatField.CPU_SYS)
                                    , stats.get(VMStatField.CPU_IDLE)
            ));
    }
}
