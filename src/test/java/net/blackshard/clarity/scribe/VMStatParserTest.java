package net.blackshard.clarity.scribe;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Unit test for VMStatParser.
 */
public class VMStatParserTest {
    VMStatParser parser;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        parser = new VMStatParser();
    }

    @Test public void shouldRefuseEmptyString() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Can't parse an empty string!");
        parser.parse("");
    }

    @Test public void shouldRefuseTooFewFields() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Incorrect field count: 21");
        parser.parse("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
    }

    @Test public void shouldRefuseTooManyFields() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Incorrect field count: 23");
        parser.parse("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
    }

    @Test public void shouldAcceptExcessWhitespace() {
        parser.parse(" 0   0  0 0   0 12 0 0 0 0430 0 0 0 0 0 0 0 0 0 0 0 0 ");
    }

    @Test public void shouldRefuseNonIntegerFields() {
        exception.expect(NumberFormatException.class);
        parser.parse("0 0 0 cdc 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
        
    }
}
