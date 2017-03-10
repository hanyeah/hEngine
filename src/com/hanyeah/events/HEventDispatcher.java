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
 * 发送事件的基类。
 * @author hanyeah
 *
 */
public class HEventDispatcher extends HObject {

	private List<Obj> _listenerList=new ArrayList<Obj>();
	private List<HEvent> _dispatchList=new ArrayList<HEvent>();
	/**
	 * 构造函数
	 */
	public HEventDispatcher() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 使用 EventDispatcher 对象注册事件侦听器对象，以使侦听器能够接收事件通知。
	 * @param type
	 * @param listener
	 */
	public void addEventListener(String type,HIListener listener){
		if(!hasEventListener(type,listener)){
			_listenerList.add(new Obj(type,listener));
		}
	}
	/**
	 * 检查 EventDispatcher 对象是否为特定事件类型注册了任何侦听器。
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
	 * 从 EventDispatcher 对象中删除侦听器。
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
	 * 将事件调度到事件流中。 
	 * @param e
	 */
	public void dispatchEvent(HEvent e){
		_dispatchList.add(e);
	}
	/**
	 * 用于存储事件的类型和侦听器的引用
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
	//------------------------------以下方法虽然声明为public，但是仅供引擎调用-------------------------
	/**
	 * 事件捕获阶段
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
	 * 事件目标阶段还是冒泡阶段
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
