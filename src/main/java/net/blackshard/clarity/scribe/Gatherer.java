package net.blackshard.clarity.scribe;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Matthew R. Trower
 * interface Gatherer
 *
 * A Gatherer gathers system data in raw form.
 */
public interface Gatherer extends Closeable {
    public void open() throws IOException;
    public String read() throws IOException;
    public void close();
}
