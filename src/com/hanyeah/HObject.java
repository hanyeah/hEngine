package com.hanyeah;

/**
 * 渲染引擎中所有类的基类。
 * @author hanyeah
 */
public class HObject {
	public HObject(){
		
	}
	
	public String toString(){
		return "[ HObject "+this.getClass().getName()+" ]";
	}
}
