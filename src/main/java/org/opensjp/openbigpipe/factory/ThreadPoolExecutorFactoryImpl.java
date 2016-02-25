package org.opensjp.openbigpipe.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorFactoryImpl implements ThreadPoolExecutorFactory {
    private static ThreadPoolExecutorFactory instance = new ThreadPoolExecutorFactoryImpl();
    
    private static final int CORE_POOL_SIZE = 50;
    private static final int MAX_POOL_SIZE = 500;
    private static final int KEEP_ALIVE_TIME = 2000;
    private BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MICROSECONDS, workQueue);
    private ThreadPoolExecutorFactoryImpl() {
    }

    public static ThreadPoolExecutorFactory newInstance() {
        return instance;
    }

    public ThreadPoolExecutor instanceOfDefaultConfig() {
        return executor;
    }
}
