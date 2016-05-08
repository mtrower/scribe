package net.blackshard.clarity.scribe;
import net.blackshard.clarity.tome.CPUReading;
import net.blackshard.clarity.tome.ReadingDAOHibernate;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Matthew R. Trower
 * class CPUScribblet
 * 
 * Scribblet to log basic CPU activity.
 */
public class CPUScribblet extends VMStatScribblet {
    public CPUScribblet() {
        log = LogManager.getLogger(CPUScribblet.class);
        dao = new ReadingDAOHibernate<CPUReading>();
        fields = new VMStatField[] {
                  VMStatField.CPU_USER
                , VMStatField.CPU_SYS
                , VMStatField.CPU_IDLE
        };

        name = "CPU Scribblet";
        reading = new CPUReading();
    }

    protected LinkedHashMap<String, Integer>
            toMetricsMap(Map<VMStatField, Integer> stats) {

        LinkedHashMap<String, Integer> metrics = new LinkedHashMap();

        metrics.put("user", stats.get(VMStatField.CPU_USER));
        metrics.put("system", stats.get(VMStatField.CPU_SYS));
        metrics.put("idle", stats.get(VMStatField.CPU_IDLE));

        return metrics;
    }
}
