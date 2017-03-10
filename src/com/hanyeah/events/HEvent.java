/**
 * 
 */
package com.hanyeah.events;


import com.hanyeah.HObject;

/**
 * 事件的基类。
 * @author hanyeah
 *
 */
public class HEvent extends HObject {
	
	protected String _type;
	protected boolean _bubbles;
	protected boolean _cancelable;
	protected HEventDispatcher _target;
	protected HEventDispatcher _currentTarget;
	public Object data;
	public static final String ENTER_FRAME="enterFrame";
	public static final String CHANGE="change";
	public static final String ADDED="added";
	public static final String ACTIVATE="activate";
	public static final String CLEAR="clear";
	public static final String CLOSE="close";
	public static final String COMPLETE="complete";
	public static final String RESIZE="resize";
	public static final String SELECT="select";
	public static final String ADDED_TO_STAGE="addedToStage";
	public static final String STAGE_CREATED="stageCreated";
	
	/**
	 * HEvent 类作为创建 HEvent 对象的基类，当发生事件时，HEvent 对象将作为参数传递给事件侦听器。 
	 * @param type			事件类型
	 * @param data			事件传递的数据
	 * @param bubbles		是否冒泡
	 * @param cancelable	是否已可取消
	 */
	public HEvent(String type,Object data,boolean bubbles,boolean cancelable) {
		// TODO Auto-generated constructor stub
		_type = type;
		_bubbles = bubbles;
		_cancelable = cancelable;
		this.data=data;
	}
	public HEvent(String type,Object data,boolean bubbles) {
		// TODO Auto-generated constructor stub
		this(type,data,bubbles,false);
	}
	public HEvent(String type,Object data) {
		// TODO Auto-generated constructor stub
		this(type,data,false,false);
	}
	public HEvent(String type) {
		// TODO Auto-generated constructor stub
		this(type,null,false,false);
	}
	/**
	 * 创建事件的副本
	 */
	public HEvent clone(){
		HEvent e=new HEvent(_type,data,_bubbles,_cancelable);
		e._target=this._target;
		return e;
	}
	/**
	 * 事件的类型。
	 * @return String
	 */
	public String getType(){
		return _type;
	}
	/**
	 * 指示事件是否为冒泡事件。 
	 * @return boolean
	 */
	public boolean getBubbles(){
		return _bubbles;
	}
	/**
	 * 指示是否可以阻止与事件相关联的行为。
	 * @return boolean
	 */
	public boolean getCancelable(){
		return _cancelable;
	}
	/**
	 * 事件目标。
	 * @return
	 */
	public HEventDispatcher getTarget(){
		return _target;
	}
	/**
	 * 当前正在使用某个事件侦听器处理 Event 对象的对象。
	 * @return
	 */
	public HEventDispatcher getCurrentTarget(){
		return _currentTarget;
	}
	/**
	 * 
	 */
	public String toString(){
		return "[ HEvent type="+_type+" ,data="+data.toString()+" , bubbles="+_bubbles+" , cancelable="+_cancelable+" ]";
	}
	
}
