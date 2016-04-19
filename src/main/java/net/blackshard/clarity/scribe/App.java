package net.blackshard.clarity.scribe;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author Matthew R. Trower
 * class App
 * 
 * Main driver class for Scribe.  Loads up scribblets and runs them, asynchronously.
 */
public class App {
    private static final Logger log = LogManager.getLogger("net.blackshard.clarity.scribe");
    private static List<Runnable> scribblets;

    static {
        log.info("Driver: initializing...");

        scribblets = new ArrayList();
        scribblets.add(new CPUScribblet());
        scribblets.add(new MemScribblet());

        log.info("Driver: initialization complete");
    }

    public static void main( String[] args ) {
        log.info("Driver: launching scribblets...");

        for (Runnable scribblet: scribblets)
            (new Thread(scribblet)).start();

        log.info("Driver: all scribblets launched");
    }
}
