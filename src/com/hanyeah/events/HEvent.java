/**
 * 
 */
package com.hanyeah.events;


import com.hanyeah.HObject;

/**
 * �¼��Ļ��ࡣ
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
	 * HEvent ����Ϊ���� HEvent ����Ļ��࣬�������¼�ʱ��HEvent ������Ϊ�������ݸ��¼��������� 
	 * @param type			�¼�����
	 * @param data			�¼����ݵ�����
	 * @param bubbles		�Ƿ�ð��
	 * @param cancelable	�Ƿ��ѿ�ȡ��
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
	 * �����¼��ĸ���
	 */
	public HEvent clone(){
		HEvent e=new HEvent(_type,data,_bubbles,_cancelable);
		e._target=this._target;
		return e;
	}
	/**
	 * �¼������͡�
	 * @return String
	 */
	public String getType(){
		return _type;
	}
	/**
	 * ָʾ�¼��Ƿ�Ϊð���¼��� 
	 * @return boolean
	 */
	public boolean getBubbles(){
		return _bubbles;
	}
	/**
	 * ָʾ�Ƿ������ֹ���¼����������Ϊ��
	 * @return boolean
	 */
	public boolean getCancelable(){
		return _cancelable;
	}
	/**
	 * �¼�Ŀ�ꡣ
	 * @return
	 */
	public HEventDispatcher getTarget(){
		return _target;
	}
	/**
	 * ��ǰ����ʹ��ĳ���¼����������� Event ����Ķ���
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
