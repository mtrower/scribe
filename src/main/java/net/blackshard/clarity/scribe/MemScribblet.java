package net.blackshard.clarity.scribe;
import net.blackshard.clarity.tome.MemReading;
import net.blackshard.clarity.tome.ReadingDAOHibernate;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Matthew R. Trower
 * class RAMScribblet
 * 
 * Scribblet to log basic memory status.
 */
public class MemScribblet extends VMStatScribblet {
    public MemScribblet() {
        log = LogManager.getLogger(MemScribblet.class);
        dao = new ReadingDAOHibernate<MemReading>();
        fields = new VMStatField[] {
                  VMStatField.MEM_SWAP
                , VMStatField.MEM_FREE
        };

        name = "Memory Scribblet";
        reading = new MemReading();
    }

    protected LinkedHashMap<String, Integer>
            toMetricsMap(Map<VMStatField, Integer> stats) {

        LinkedHashMap<String, Integer> metrics = new LinkedHashMap();

        metrics.put("swap", stats.get(VMStatField.MEM_SWAP));
        metrics.put("free", stats.get(VMStatField.MEM_FREE));

        return metrics;
    }
}
