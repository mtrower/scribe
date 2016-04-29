package net.blackshard.clarity.scribe;

import net.blackshard.clarity.tome.Library;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Matthew R. Trower
 * class App
 * 
 * Main driver class for Scribe.  Loads up scribblets and runs them, asynchronously.
 */
public class App {
    private static final Logger log = LogManager.getLogger(App.class);
    private static List<Runnable> scribblets;

    static {
        log.info("Driver: initializing...");

        Library.open();

        scribblets = new ArrayList();
        scribblets.add(new CPUScribblet());
        scribblets.add(new MemScribblet());

        log.info("Driver: initialization complete");
    }

    public static void main( String[] args ) {
        log.info("Driver: launching scribblets...");

        for (Runnable r: scribblets) {
            Thread t = new Thread(r);
            threads.add(t);
            t.start();
        }

        log.info("Driver: all scribblets launched");

        try {
            for (Thread t: threads)
                t.join();
        } catch (InterruptedException ie) { }

        log.info("Driver: cleaning up");

        Library.close();
    }
}
