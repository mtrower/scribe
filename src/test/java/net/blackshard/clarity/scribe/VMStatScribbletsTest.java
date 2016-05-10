package net.blackshard.clarity.scribe;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Unit test for VMStatParser.
 */
public class VMStatScribbletsTest {
    @Test public void shouldInitializeDistinctClasses() throws IOException {
        LinkedHashMap<VMStatField, Integer> cpuStats = new LinkedHashMap();
        LinkedHashMap<VMStatField, Integer> memStats = new LinkedHashMap();
        LinkedHashMap<String, Integer> cpuMetrics;
        LinkedHashMap<String, Integer> memMetrics;

        VMStatScribblet cpu = new CPUScribblet();
        VMStatScribblet mem = new MemScribblet();

        cpuStats.put(VMStatField.CPU_USER, 1);
        cpuStats.put(VMStatField.CPU_SYS, 2);
        cpuStats.put(VMStatField.CPU_IDLE, 97);
        memStats.put(VMStatField.MEM_SWAP, 50);
        memStats.put(VMStatField.MEM_FREE, 100);

        cpuMetrics = cpu.toMetricsMap(cpuStats);
        memMetrics = mem.toMetricsMap(memStats);

        assertThat(cpu.name, equalTo("CPU Scribblet"));
        assertThat(mem.name, equalTo("Memory Scribblet"));
        assertThat(cpuMetrics.get("user"), equalTo(1));
        assertThat(cpuMetrics.get("system"), equalTo(2));
        assertThat(cpuMetrics.get("idle"), equalTo(97));
        assertThat(memMetrics.get("swap"), equalTo(50));
        assertThat(memMetrics.get("free"), equalTo(100));
    }
}
