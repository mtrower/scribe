package net.blackshard.clarity.scribe;

import java.io.*;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author Matthew R. Trower
 * class CPUScribblet
 * 
 * Scribblet to log basic CPU activity.
 */
public class CPUScribblet implements Runnable {
    private static final Logger log
            = LogManager.getLogger("net.blackshard.clarity.scribe");

    String name = "CPU Scribblet";
    Map<VMStatField, Integer> stats;

    Gatherer gatherer;
    VMStatParser parser;

    public CPUScribblet() {
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
            VMStatField.CPU_USER
            , VMStatField.CPU_SYS
            , VMStatField.CPU_IDLE
        });
    }

    private void writeStats() throws IOException {
        log.debug(String.format("%s: user %d \tsystem %d \t idle %d", name
                                , stats.get(VMStatField.CPU_USER)
                                , stats.get(VMStatField.CPU_SYS)
                                , stats.get(VMStatField.CPU_IDLE)
        ));
    }
}
