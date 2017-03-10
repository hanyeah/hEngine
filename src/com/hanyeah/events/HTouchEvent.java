/**
 * 
 */
package com.hanyeah.events;

/**
 * @author hanyeah
 * @date 2015-12-3
 */
public class HTouchEvent extends HEvent {
	
	public static final String TOUCH_START="touchStart";
	
	public static final String TOUCH_MOVE="touchMove";
	
	public static final String TOUCH_END="touchEnd";
	
	public static final String ROLL_OVER="rollOver";
	
	public static final String ROLL_OUT="rollOut";
	
	public static final String CLICK="click";
	
	public static final String TOUCH_OVER="touchOver";
	
	public static final String TOUCH_OUT="touchOut";
	

	/**
	 * �����¼���
	 * @param type			�¼�����
	 * @param data			�¼����ݵ�����
	 * @param bubbles		�Ƿ�ð��
	 * @param cancelable	�Ƿ��ѿ�ȡ��
	 */
	public HTouchEvent(String type, Object data, boolean bubbles,boolean cancelable) {
		super(type, data, bubbles, cancelable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ���캯����
	 * @param type
	 * @param data
	 * @param bubbles
	 */
	public HTouchEvent(String type, Object data, boolean bubbles) {
		super(type, data, bubbles);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ���캯����
	 * @param type
	 * @param data
	 */
	public HTouchEvent(String type, Object data) {
		super(type, data);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ���캯����
	 * @param type
	 */
	public HTouchEvent(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * �����¼��ĸ���
	 */
	public HTouchEvent clone(){
		HTouchEvent e=new HTouchEvent(_type,data,_bubbles,_cancelable);
		e._target=_target;
		return e;
	}
	
	/**
	 *
	 */
	public String toString(){
		return "[ HTouchEvent type="+_type+" ,data="+data.toString()+" , bubbles="+_bubbles+" , cancelable="+_cancelable+" ]";
	}
	
}
