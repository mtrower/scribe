package net.blackshard.clarity.scribe;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.List;
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

    @Test public void fieldsAreSetProperly() {
        parser.parse("10255984 0 0 10817544 0 303 1457 23 0 0 0 824 35 -0 36 -0 2629 68237 2931 1 1 97");

        List<Integer> pFields = parser.getStats();

        Integer[] fields = new Integer[22];
        fields[0]  = 10255984;
        fields[1]  = 0;     fields[2]  = 0;     fields[3]  = 10817544;
        fields[4]  = 0;     fields[5]  = 303;   fields[6]  = 1457;
        fields[7]  = 23;    fields[8]  = 0;     fields[9]  = 0;
        fields[10] = 0;     fields[11] = 824;   fields[12] = 35;
        fields[13] = 0;     fields[14] = 36;    fields[15] = 0;
        fields[16] = 2629;  fields[17] = 68237; fields[18] = 2931;
        fields[19] = 1;     fields[20] = 1;     fields[21] = 97;

        for (int i = 0; i < 22; i++)
            assertThat(pFields.get(i), equalTo(fields[i]));

        assertThat(parser.getStat(0), equalTo(10255984));
        assertThat(parser.getStat(21), equalTo(97));

    }
}
