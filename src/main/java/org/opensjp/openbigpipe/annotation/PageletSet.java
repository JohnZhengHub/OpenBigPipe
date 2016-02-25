package org.opensjp.openbigpipe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果一个类具有该注解，说明该类是一个Pagelet集合．这些Pagelet可能是为了共同完成某个逻辑．
 * 该类内部的方法若是pagelet的执行方法，则在该的方法上面加入{@link org.opensjp.openbigpipe.annotation.Pagelet @Pagelet}注解.
 * @author　John Zheng
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageletSet {
}
