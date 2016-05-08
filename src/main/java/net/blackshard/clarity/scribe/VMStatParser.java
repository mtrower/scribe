package net.blackshard.clarity.scribe;

import java.util.HashMap;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author Matthew R. Trower
 * class VMStatParser
 *
 * Tools for parsing vmstat output
 */
public class VMStatParser {
    private static final Logger log = LogManager.getLogger(VMStatParser.class);
    private static final String HEADER_REGEX = " kthr.*| r b w.*";

    public static boolean isHeader(String line) {
        return line.matches(HEADER_REGEX);
    }

    private HashMap<VMStatField, Integer> stats;
    private VMStatField fieldNames[];

    public VMStatParser() {
        stats = new HashMap<VMStatField, Integer>();
        fieldNames = VMStatField.values();
    }

    public void parse(String line) throws IllegalArgumentException {
        if (line.isEmpty())
            throw new IllegalArgumentException("Can't parse an empty string!");

        String[] fields = line.trim().split("\\s+");

        if (fields.length != fieldNames.length)
            throw new IllegalArgumentException(
                    "Incorrect field count: " + fields.length);

        for (int i = 0; i < fieldNames.length; i++)
            stats.put(fieldNames[i], Integer.valueOf(fields[i]));
    }

    public Integer getStat(VMStatField field) {
        return stats.get(field);
    }

    public HashMap<VMStatField, Integer> getStats() {
        return new HashMap<VMStatField, Integer>(stats);
    }

    public HashMap<VMStatField, Integer> getStats(VMStatField[] fields) {
        HashMap<VMStatField, Integer> pkg = new HashMap<VMStatField, Integer>();

        for (int i = 0; i < fields.length; i++)
            pkg.put(fields[i], stats.get(fields[i]));

        return pkg;
    }
}
