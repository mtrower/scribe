package net.blackshard.clarity.scribe;

/**
 * @author Matthew R. Trower
 * enum VMStatFields
 */
public enum VMStatFields {
    KTHREAD_RUN, KTHREAD_BLOCK, KTHREAD_WAIT,
    MEM_SWAP, MEM_FREE,
    PAGE_RECLAIM, PAGE_MINOR_FAULT, PAGE_IN, PAGE_OUT,
    PAGE_FREED, PAGE_SHORTFALL, PAGE_SCAN,
    DISK0, DISK1, DISK2, DISK3,
    FAULT_IN, FAULT_SYSCALL, FAULT_CTX,
    CPU_USER, CPU_SYS, CPU_IDLE
}
