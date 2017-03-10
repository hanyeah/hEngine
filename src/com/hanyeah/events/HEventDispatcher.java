/**
 * 
 */
package com.hanyeah.events;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.hanyeah.HObject;
import com.hanyeah.display.HDisplayObject;
import com.hanyeah.display.HDisplayObjectContainer;

/**
 * �����¼��Ļ��ࡣ
 * @author hanyeah
 *
 */
public class HEventDispatcher extends HObject {

	private List<Obj> _listenerList=new ArrayList<Obj>();
	private List<HEvent> _dispatchList=new ArrayList<HEvent>();
	/**
	 * ���캯��
	 */
	public HEventDispatcher() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * ʹ�� EventDispatcher ����ע���¼�������������ʹ�������ܹ������¼�֪ͨ��
	 * @param type
	 * @param listener
	 */
	public void addEventListener(String type,HIListener listener){
		if(!hasEventListener(type,listener)){
			_listenerList.add(new Obj(type,listener));
		}
	}
	/**
	 * ��� EventDispatcher �����Ƿ�Ϊ�ض��¼�����ע�����κ���������
	 * @param type
	 * @param listener
	 * @return
	 */
	public boolean hasEventListener(String type,HIListener listener){
		for(int i=0;i<_listenerList.size();i++){
			Obj obj=_listenerList.get(i);
			if(obj.type==type&&obj.listener==listener){
				return true;
			}
		}
		return false;
	}
	/**
	 * �� EventDispatcher ������ɾ����������
	 * @param type
	 * @param listener
	 */
	public void removeEventListener(String type,HIListener listener){
		for(int i=0;i<_listenerList.size();i++){
			Obj obj=_listenerList.get(i);
			if(obj.type==type&&obj.listener==listener){
				_listenerList.remove(i);
				break;
			}
		}
	}
	/**
	 * ���¼����ȵ��¼����С� 
	 * @param e
	 */
	public void dispatchEvent(HEvent e){
		_dispatchList.add(e);
	}
	/**
	 * ���ڴ洢�¼������ͺ�������������
	 * @author FengZhaoYang
	 *
	 */
	private class Obj{
		public String type;
		public HIListener listener;
		public Obj(String type,HIListener listener){
			this.type=type;
			this.listener=listener;
		}
	}
	//------------------------------���·�����Ȼ����Ϊpublic�����ǽ����������-------------------------
	/**
	 * �¼�����׶�
	 */
	protected void _hcatch(){
		for(int i=0;i<_dispatchList.size();i++){
			HEvent e=_dispatchList.get(i);
			e._target=this;
			_hlisten(e);
		}
		if(this instanceof HDisplayObjectContainer){
			HDisplayObjectContainer con=(HDisplayObjectContainer)this;
			for(int j=0;j<con.getNumChildren();j++){
				((HEventDispatcher)con.getChildAt(j))._hcatch();
			}
		}
		_dispatchList.clear();
	}
	/**
	 * �¼�Ŀ��׶λ���ð�ݽ׶�
	 * @param e
	 */
	protected void _hlisten(HEvent e){
		for(int i=0,len=_listenerList.size();i<len;i++){
			
			if(e.getType()==_listenerList.get(i).type){
				e._currentTarget=this;
				_listenerList.get(i).listener.execute(e.clone());
			}
		}
	}
}
