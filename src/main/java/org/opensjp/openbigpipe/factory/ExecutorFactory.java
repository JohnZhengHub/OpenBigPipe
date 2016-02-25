package org.opensjp.openbigpipe.factory;

import org.opensjp.openbigpipe.annotation.ExecuteType;
import org.opensjp.openbigpipe.core.concurrent.BigPipeExecutor;
import org.opensjp.openbigpipe.core.concurrent.ConcurrentPipeExecutor;
import org.opensjp.openbigpipe.core.concurrent.Executor;
/**
 * 执行器工厂
 * @author John Zheng
 *
 */
public class ExecutorFactory {
	/**
	 * 根据执行类型　获取一个执行器
	 * @param executeType
	 * @return
	 */
	public static Executor getExecutor(ExecuteType executeType){
		switch (executeType) {
        case CONCURRNET:
        	return getConcurrentPipeExecutor();
        case BIGPIPE:
        	return getBigPipeExecutor();
        }	
		return null;
	} 
	/**
	 * 获取串行执行器
	 * @return
	 */
	public static Executor getConcurrentPipeExecutor(){
		return new ConcurrentPipeExecutor();
	}
	/**
	 * 获取BigPipe方式的执行器
	 * @return
	 */
	public static Executor getBigPipeExecutor(){
		return new BigPipeExecutor();
	}
	
}
