/**
 * 
 */
package com.hanyeah.display;

import com.hanyeah.events.HEvent;

/**
 * HInteractiveObject 类是用户可以使用鼠标和键盘与之交互的所有显示对象的抽象基类。
 * @author hanyeah
 * @date 2015-12-3
 * 
 */
public abstract class HInteractiveObject extends HDisplayObject {

	/**确定对象是否支持鼠标。**/
	public boolean mouseEnabled=true;
	/**
	 * 构造函数，不可以实例化。
	 */
	public HInteractiveObject() {
		// TODO Auto-generated constructor stub
	}
	
}
