package org.opensjp.openbigpipe.factory;

import java.util.concurrent.ThreadPoolExecutor;

public interface ThreadPoolExecutorFactory {

    ThreadPoolExecutor instanceOfDefaultConfig();
}
