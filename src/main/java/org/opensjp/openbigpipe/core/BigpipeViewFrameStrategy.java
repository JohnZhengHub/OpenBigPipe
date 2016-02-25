package org.opensjp.openbigpipe.core;

/**
 * 
 * BigPipe的视图框架的处理策略，采用策略模式．
 * 主要是负责对于视图框架的处理，如加入js解析函数.
 * @author 
 *
 */
public interface BigpipeViewFrameStrategy {

    String execute(String view);
}
