package net.blackshard.clarity.scribe;

import java.io.*;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author Matthew R. Trower
 * class RAMScribblet
 * 
 * Scribblet to log basic memory status.
 */
public class MemScribblet implements Runnable {
    private static final Logger log = LogManager.getLogger("net.blackshard.clarity.scribe");

    String name = "Memory Scribblet";
    Map<VMStatField, Integer> stats;

    Process proc;
    BufferedReader pin;
    VMStatParser parser;

    public MemScribblet() {
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
                break;
            }
        }

        System.out.println(name + ": shutting down!");

        cleanUpGatherer();
    }

    private void cleanUpGatherer() {
        try {
            pin.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
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
                  VMStatField.MEM_SWAP
                , VMStatField.MEM_FREE
            });
        }
    }

    private void writeStats() {
        if (stats != null)
            System.out.println(
                    String.format("%s: total swap %d \tfree %d"
                                    , name
                                    , stats.get(VMStatField.MEM_SWAP)
                                    , stats.get(VMStatField.MEM_FREE)
            ));
    }
}
