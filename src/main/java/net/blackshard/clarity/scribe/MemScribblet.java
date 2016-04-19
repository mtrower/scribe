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
    private static final Logger log
            = LogManager.getLogger("net.blackshard.clarity.scribe");

    String name = "Memory Scribblet";
    Map<VMStatField, Integer> stats;

    Gatherer gatherer;
    VMStatParser parser;

    public MemScribblet() {
        gatherer = new VMStatGatherer();
        parser = new VMStatParser();
    }

    public void run() {
        log.info(name + ": starting up!");

        try {
            gatherer.open();

            for (int i = 0; i < 5; i++) {
                log.trace(name + ": tick");

                gatherStats();
                writeStats();

                Thread.sleep(1000);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) { }

        log.info(name + ": shutting down!");

        gatherer.close();
    }

    private void gatherStats() throws IOException {
        parser.parse(gatherer.read());

        stats = parser.getStats(new VMStatField[] {
              VMStatField.MEM_SWAP
            , VMStatField.MEM_FREE
        });
    }

    private void writeStats() throws IOException {
        log.debug(String.format("%s: total swap %d \tfree %d", name
                                , stats.get(VMStatField.MEM_SWAP)
                                , stats.get(VMStatField.MEM_FREE)
        ));
    }
}
