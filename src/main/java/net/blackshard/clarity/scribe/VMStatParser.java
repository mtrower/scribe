package net.blackshard.clarity.scribe;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Matthew R. Trower
 * class VMStatParser
 *
 * Tools for parsing vmstat output
 */
public class VMStatParser {
    private final int VMSTAT_FIELD_COUNT = 22;
    private Integer[] stats;

    public VMStatParser() {
        stats = new Integer[VMSTAT_FIELD_COUNT];
    }

    public void parse(String line) throws IllegalArgumentException {
        if (line.isEmpty())
            throw new IllegalArgumentException("Can't parse an empty string!");

        String[] fields = line.trim().split("\\s+");

        if (fields.length != VMSTAT_FIELD_COUNT)
            throw new IllegalArgumentException(
                    "Incorrect field count: " + fields.length);

        for (int i = 0; i < VMSTAT_FIELD_COUNT; i++)
            stats[i] = Integer.valueOf(fields[i]);
    }

    public Integer getStat(int index) {
        return stats[index];
    }

    public Integer getStat(VMStatFields field) {
        return stats[field.ordinal()];
    }

    public Integer[] getStats() {
        return stats.clone();
    }

    public Integer[] getStats(VMStatFields[] fields) {
        Integer[] statList = new Integer[fields.length];

        for (int i = 0; i < fields.length; i++)
            statList[i] = stats[fields[i].ordinal()];

        return statList;
    }
}
