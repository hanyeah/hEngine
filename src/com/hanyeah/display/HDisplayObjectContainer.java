/**
 * 
 */
package com.hanyeah.display;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.hanyeah.geom.HPoint;
import com.hanyeah.geom.HRectangle;

/**
 * HDisplayObjectContainer 类是可用作显示列表中显示对象容器的所有对象的基类。
 * @author hanyeah
 * @date 2015-12-3 
 */
public abstract class HDisplayObjectContainer extends HInteractiveObject {

	/**确定对象的子项是否支持鼠标。**/
	public boolean mouseChildren=true;
	
	private List<HDisplayObject> _childList=new ArrayList<HDisplayObject>();
	
	/**
	 *构造函数，不可以实例化。
	 */
	public HDisplayObjectContainer() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * [只读 (read-only)] 返回此对象的子项数目。
	 * @return
	 */
	public int getNumChildren(){
		return _childList.size();
	}
	/**
	 * 将一个 HDisplayObject 子实例添加到该 HDisplayObjectContainer 实例中。
	 * @param child
	 * @return
	 */
	public HDisplayObject addChild(HDisplayObject child){
		_childList.remove(child);
		_childList.add(child);
		child.parent=this;
		return child;
	}
	/**
	 * 将一个 HDisplayObject 子实例添加到该 HDisplayObjectContainer 实例中。 该子项将被添加到指定的索引位置。
	 * @param child
	 * @param index
	 * @return
	 */
	public HDisplayObject addChildAt(HDisplayObject child,int index){
		_childList.remove(child);
		_childList.add(index, child);
		child.parent=this;
		return child;
	}
	/**
	 * 从 HDisplayObjectContainer 实例的子列表中删除指定的 child HDisplayObject 实例。
	 * @param child
	 * @return
	 */
	public HDisplayObject removeChild(HDisplayObject child){
		if(_childList.remove(child)){
			child.parent=null;
		}
		return child;
	}
	/**
	 * 从 HDisplayObjectContainer 的子列表中指定的 index 位置删除子 HDisplayObject。
	 * @param index
	 * @return
	 */
	public HDisplayObject removeChildAt(int index){
		HDisplayObject child=_childList.remove(index);
		child.parent=null;
		return child;
	}
	/**
	 * 确定指定显示对象是否是 HDisplayObjectContainer 实例的子项。
	 * @param child
	 * @return
	 */
	public boolean contains(HDisplayObject child){
		return _childList.contains(child);
	}
	/**
	 * 返回位于指定索引处的子显示对象实例
	 * @param index
	 * @return
	 */
	public HDisplayObject getChildAt(int index){
		return _childList.get(index);
	}
	/**
	 * 返回具有指定名称的子显示对象。
	 * @param name
	 * @return
	 */
	public HDisplayObject getChildByName(String name){
		int len = _childList.size();
		for(int i=0;i<len;i++){
			if(_childList.get(i).name==name){
				return _childList.get(i);
			}
		}
		return null;
	}
	/**
	 * 返回 DisplayObject 的 child 实例的索引位置。
	 * @param child
	 * @return
	 */
	public int getChildIndex(HDisplayObject child){
		return _childList.indexOf(child);
	}
	
	/**
	 * 更改现有子项在显示对象容器中的索引位置。
	 * @param child
	 * @param index
	 */
	public void setChildIndex(HDisplayObject child, int index){
		if(_childList.remove(child)){
			_childList.add(index, child);
		}
	}
	/**
	 * 交换两个指定子对象的 Z 轴顺序（从前到后顺序）。 
	 * @param child1
	 * @param child2
	 */
	public void swapChildren(HDisplayObject child1,HDisplayObject child2){
		Collections.swap(_childList, _childList.indexOf(child1), _childList.indexOf(child2));
	}
	/**
	 * 在子级列表中两个指定的索引位置，交换子对象的 Z 轴顺序（前后顺序）。
	 * @param index1
	 * @param index2
	 */
	public void swapChildrenAt(int index1,int index2){
		Collections.swap(_childList, index1, index2);
	}
	
	/**
	 * 获取自身的外接矩形
	 *
	 * @return
	 */
	protected HRectangle _getSelfRect(){
		int num=getNumChildren();
		HRectangle rect=new HRectangle();
		for(int i=0;i<num;i++){
			HDisplayObject dis=getChildAt(i);
			HRectangle rect0=dis.getRect();
			if(!rect0.isEmpty()){
				if(rect.isEmpty()){
					rect=rect0;
				}
				else{
					rect=rect.union(rect0);
				}
			}
		}
		return _localRect2parent(rect);
	}
	/**
	 * 渲染，只供引擎调用。请不要在自定义类中调用。
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		for(int i=0;i<_childList.size();i++){
			HDisplayObject child=_childList.get(i);
			if(child.visible){
				child._render(canvas, paint);
			}
		}
	}
}
