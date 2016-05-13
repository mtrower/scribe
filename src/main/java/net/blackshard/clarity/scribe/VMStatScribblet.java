package net.blackshard.clarity.scribe;
import net.blackshard.clarity.tome.Reading;
import net.blackshard.clarity.tome.ReadingDAOHibernate;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract base Scribblet to log a subset of vmstat(1) readings.
 *
 * @author Matthew R. Trower
 * @since 1.0
 */
public abstract class VMStatScribblet implements Runnable {
    protected Logger log;
    protected ReadingDAOHibernate dao;

    protected String name;
    protected VMStatField[] fields;

    protected Reading reading;
    protected Gatherer gatherer;
    protected VMStatParser parser;

    /**
     * Default Constructor
     *
     * @since 1.0
     */
    public VMStatScribblet() {
        gatherer = new VMStatGatherer();
        parser = new VMStatParser();
    }

    /**
     * Run the scribblet.  It will initialize its gathering method and proceed
     * to gather stats at a set interval, writing them out to the database.
     *
     * @since 1.0
     */
    public void run() {
        log.info(name + ": starting up!");

        while (true)
            try { 
                gatherer.open();

                log.trace(name + ": tick");

                gatherStats();
                writeStats();

                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                break;
            } catch (Exception e) {
                log.error("", e);
                gatherer.close();
            }

        log.info(name + ": shutting down!");

        gatherer.close();
    }

    /**
     * Gather and parse stats from the selected gatherer.
     *
     * @throws IOException
     * @since 1.0
     */
    protected void gatherStats() throws IOException {
        parser.parse(gatherer.read());
        Map<VMStatField, Integer> stats = parser.getStats(fields);

        reading.setMachineId(0);
        reading.setTimestamp(new Date());
        reading.setMetrics(toMetricsMap(stats));
    }

    /**
     * Write the last set of stats collected to the database.
     *
     * @throws IOException
     * @since 1.0
     */
    protected void writeStats() throws IOException {
        log.debug(String.format("%s: %s", name, reading.getMetrics()));
        dao.insert(reading);
    }

    /**
     * Convert an array of stats (obtained from VMStatParser) into a metrics map that a Reading DTO will understand.
     *
     * @param stats the output from VMStatParser
     * @return a metrics map that can be fed to Reading.setMetrics()
     */
    protected abstract LinkedHashMap<String, Integer>
            toMetricsMap(Map<VMStatField, Integer> stats);
}
