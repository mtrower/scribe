package net.blackshard.clarity.scribe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * @author Matthew R. Trower
 * class VMStatGatherer
 *
 * Gather system data from the vmstat(1M) command.
 */
public class VMStatGatherer implements Gatherer {
    Process proc;
    BufferedReader pin;

    public void open() throws IOException {
        proc = Runtime.getRuntime().exec("vmstat 1");
        pin = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        // Throw away header and summary data
        for (int i = 0; i < 3; i++)
            pin.readLine();
    }

    public String read() throws IOException {
        return pin.readLine();
    }

    public void close() {
        try { pin.close(); }
        catch (IOException ioe) { ioe.printStackTrace(); }

        proc.destroy();
    }
}
