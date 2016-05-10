package net.blackshard.clarity.scribe;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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
    private static final Logger log = LogManager.getLogger(VMStatGatherer.class);

    private Process proc;
    private BufferedReader pin;

    public void open() throws IOException {
        if (proc != null)
            return;

        proc = Runtime.getRuntime().exec("vmstat 1");
        pin = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        // Throw away summary data
        read();
    }

    public String read() throws IOException {
        String line = null;

        do { line = pin.readLine(); }
        while (VMStatParser.isHeader(line));

        return line;
    }

    public void close() {
        try { pin.close(); }
        catch (IOException ioe) { log.error("", ioe); }

        proc.destroy();
        proc = null;
    }
}
