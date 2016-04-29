package net.blackshard.clarity.scribe;
import net.blackshard.clarity.tome.MemReading;
import net.blackshard.clarity.tome.ReadingDAOHibernate;

import java.io.*;
import java.util.Date;
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
    private static final Logger log = LogManager.getLogger(MemScribblet.class);
    private static ReadingDAOHibernate dao
            = new ReadingDAOHibernate<MemReading>();

    String name = "Memory Scribblet";
    MemReading reading;

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

        Map<VMStatField, Integer> stats;
        stats = parser.getStats(new VMStatField[] {
              VMStatField.MEM_SWAP
            , VMStatField.MEM_FREE
        });

        reading = new MemReading(0, new Date()
                , stats.get(VMStatField.MEM_SWAP)
                , stats.get(VMStatField.MEM_FREE)
        );
    }

    private void writeStats() throws IOException {
        dao.insert(reading);

        log.debug(String.format("%s: swap %d \tfree %d", name
                                , reading.getMetricSwap()
                                , reading.getMetricFree()
        ));
    }
}
