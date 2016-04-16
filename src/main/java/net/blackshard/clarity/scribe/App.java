package net.blackshard.clarity.scribe;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Matthew R. Trower
 * class App
 * 
 * Main driver class for Scribe.  Loads up scribblets and runs them, asynchronously.
 */
public class App {
    private static List<Runnable> scribblets;

    static {
        scribblets = new ArrayList();
        scribblets.add(new CPUScribblet());
        scribblets.add(new RAMScribblet());
    }

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        for (Runnable scribblet: scribblets)
            (new Thread(scribblet)).start();
    }
}
