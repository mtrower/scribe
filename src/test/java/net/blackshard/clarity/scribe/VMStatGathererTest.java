package net.blackshard.clarity.scribe;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for VMStatParser.
 */
public class VMStatGathererTest {
    VMStatGatherer gatherer;

    @Before
    public void setUp() throws IOException {
        gatherer = new VMStatGatherer();
        gatherer.open();
    }

    @After
    public void tearDown() {
        gatherer.close();
    }

    @Test public void shouldReturnStringWith22Fields() throws IOException {
        String[] fields = gatherer.read().trim().split("\\s+");

        assertThat(fields.length, equalTo(22));
    }
}
