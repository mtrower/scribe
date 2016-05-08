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
 * @author Matthew R. Trower
 * abstract class VMStatScribblet
 * 
 * Scribblet to log a subset of vmstat(1) readings.
 */
public abstract class VMStatScribblet implements Runnable {
    protected Logger log;
    protected ReadingDAOHibernate dao;

    protected String name;
    protected VMStatField[] fields;

    protected Reading reading;
    protected Gatherer gatherer;
    protected VMStatParser parser;

    public VMStatScribblet() {
        gatherer = new VMStatGatherer();
        parser = new VMStatParser();
    }

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

    protected void gatherStats() throws IOException {
        parser.parse(gatherer.read());
        Map<VMStatField, Integer> stats = parser.getStats(fields);

        reading.setMachineId(0);
        reading.setTimestamp(new Date());
        reading.setMetrics(toMetricsMap(stats));
    }

    protected void writeStats() throws IOException {
        log.debug(String.format("%s: %s", name, reading.getMetrics()));
        dao.insert(reading);
    }

    protected abstract LinkedHashMap<String, Integer>
            toMetricsMap(Map<VMStatField, Integer> stats);
}
