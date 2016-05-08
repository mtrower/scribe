package net.blackshard.clarity.scribe;
import net.blackshard.clarity.tome.CPUReading;
import net.blackshard.clarity.tome.ReadingDAOHibernate;

import java.io.*;
import java.util.Date;
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
    private static final Logger log = LogManager.getLogger(CPUScribblet.class);
    private static ReadingDAOHibernate dao
            = new ReadingDAOHibernate<CPUReading>();

    String name = "CPU Scribblet";
    CPUReading reading;

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

            while (true) {
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
            VMStatField.CPU_USER
            , VMStatField.CPU_SYS
            , VMStatField.CPU_IDLE
        });

        reading = new CPUReading(0, new Date()
                , stats.get(VMStatField.CPU_USER)
                , stats.get(VMStatField.CPU_SYS)
                , stats.get(VMStatField.CPU_IDLE)
        );
    }

    private void writeStats() throws IOException {
        dao.insert(reading);

        log.debug(String.format("%s: user %d \tsystem %d \t idle %d", name
                                , reading.getMetricUser()
                                , reading.getMetricSystem()
                                , reading.getMetricIdle()
        ));
    }
}
