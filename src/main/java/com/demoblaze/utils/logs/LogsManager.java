package com.demoblaze.utils.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.concurrent.atomic.AtomicInteger;

public final class LogsManager {
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private static final String TEST_KEY = "test";
    private static final AtomicInteger TEST_COUNTER = new AtomicInteger();

    private LogsManager() {
    }

    private static Logger logger() {
        Class<?> caller = STACK_WALKER.walk(frames -> frames
                .<Class<?>>map(StackWalker.StackFrame::getDeclaringClass)
                .filter(c -> c != LogsManager.class)
                .findFirst()
                .orElse(LogsManager.class));
        return LogManager.getLogger(caller);
    }

    public static void fatal(String... msg) {
        logger().fatal(String.join(" ", msg));
    }

    public static void debug(String... msg) {
        logger().debug(String.join(" ", msg));
    }

    public static void info(String... msg) {
        logger().info(String.join(" ", msg));
    }

    public static void warn(String... msg) {
        logger().warn(String.join(" ", msg));
    }

    public static void error(String... msg) {
        logger().error(String.join(" ", msg));
    }

    public static void startTest(String testName) {
        ThreadContext.put(TEST_KEY, testName + "#" + TEST_COUNTER.incrementAndGet());
    }

    public static String currentTest() {
        return ThreadContext.get(TEST_KEY);
    }

    public static void endTest() {
        ThreadContext.remove(TEST_KEY);
    }
}
