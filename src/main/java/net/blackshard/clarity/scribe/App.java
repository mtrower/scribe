package net.blackshard.clarity.scribe;

import net.blackshard.clarity.tome.Library;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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
    private static Set<Runnable> scribblets;
    private static Set<Thread> threads;

    private static void setUp() {
        log.info("Driver: initializing...");

        Library.open();

        threads = new HashSet();
        scribblets = new HashSet();
        scribblets.add(new CPUScribblet());
        scribblets.add(new MemScribblet());

        log.info("Driver: initialization complete");
    }

    private static void launchScribblets() {
        log.info("Driver: launching scribblets...");

        for (Runnable r: scribblets) {
            Thread t = new Thread(r);
            threads.add(t);
            t.start();
        }

        log.info("Driver: all scribblets launched");
    }

    private static void waitForThreads() {
        try {
            for (Thread t: threads)
                t.join();
        } catch (InterruptedException ie) { }
    }

    private static void tearDown() {
        log.info("Driver: cleaning up");

        Library.close();
    }

    public static void main( String[] args ) {
        setUp();

        launchScribblets();
        waitForThreads();

        tearDown();
    }
}
