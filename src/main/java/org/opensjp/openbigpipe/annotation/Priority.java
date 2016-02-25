package org.opensjp.openbigpipe.annotation;
/**
 * 定义优先级
 * @author 
 *
 */
public enum Priority {
    LOW(1),NORMALL(2),HEIGHT(3);
    private int value = 0;
    Priority(int value){
    	this.value = value;
    }
    public int value(){
    	return value;
    }
}
