package org.opensjp.openbigpipe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *	openPipe的属性注入注解．在标有@PageletSet 类中若含带有该注解的属性，那么插件会自动获取与该属性名字和类型相同的值，并为注入该值．
 *应用场景：
 *1. 在action中注入　BigPipeController,则需要在该属性上注入该值.
 *2. pagelet所在类，pagelet运行所需的数据和变量来自action的属性，则需要在pagelet中填写相同类型和相同名字的属性，
 *	并在属性上加入该注解．插件会为其自动注入值．
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Param{
}
