package org.opensjp.openbigpipe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pagelet　注解．该注解放在方法上表示该该方法是一个pagelet.
 * 使用该注解时，所在类必须含有注解　@PageletSet (该注解表示指定的类含有一个或多个pagelet)
 * @see org.opensjp.openbigpipe.annotation.PageletSet
 * @author John Zheng
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pagelet {
	/**
	 * Pagelet　对应的key,可以和div对应的id不同
	 * @return
	 */
	String key();
	/**
	 * 定义每个pagelet的优先级，有三种优先级
	 * @see org.opensjp.openbigpipe.annotation.Priority
	 * @return
	 */
	Priority priority() default Priority.NORMALL;	
}
