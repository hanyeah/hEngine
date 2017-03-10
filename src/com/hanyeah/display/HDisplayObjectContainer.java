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
 * HDisplayObjectContainer ���ǿ�������ʾ�б�����ʾ�������������ж���Ļ��ࡣ
 * @author hanyeah
 * @date 2015-12-3 
 */
public abstract class HDisplayObjectContainer extends HInteractiveObject {

	/**ȷ������������Ƿ�֧����ꡣ**/
	public boolean mouseChildren=true;
	
	private List<HDisplayObject> _childList=new ArrayList<HDisplayObject>();
	
	/**
	 *���캯����������ʵ������
	 */
	public HDisplayObjectContainer() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * [ֻ�� (read-only)] ���ش˶����������Ŀ��
	 * @return
	 */
	public int getNumChildren(){
		return _childList.size();
	}
	/**
	 * ��һ�� HDisplayObject ��ʵ����ӵ��� HDisplayObjectContainer ʵ���С�
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
	 * ��һ�� HDisplayObject ��ʵ����ӵ��� HDisplayObjectContainer ʵ���С� ���������ӵ�ָ��������λ�á�
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
	 * �� HDisplayObjectContainer ʵ�������б���ɾ��ָ���� child HDisplayObject ʵ����
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
	 * �� HDisplayObjectContainer �����б���ָ���� index λ��ɾ���� HDisplayObject��
	 * @param index
	 * @return
	 */
	public HDisplayObject removeChildAt(int index){
		HDisplayObject child=_childList.remove(index);
		child.parent=null;
		return child;
	}
	/**
	 * ȷ��ָ����ʾ�����Ƿ��� HDisplayObjectContainer ʵ�������
	 * @param child
	 * @return
	 */
	public boolean contains(HDisplayObject child){
		return _childList.contains(child);
	}
	/**
	 * ����λ��ָ��������������ʾ����ʵ��
	 * @param index
	 * @return
	 */
	public HDisplayObject getChildAt(int index){
		return _childList.get(index);
	}
	/**
	 * ���ؾ���ָ�����Ƶ�����ʾ����
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
	 * ���� DisplayObject �� child ʵ��������λ�á�
	 * @param child
	 * @return
	 */
	public int getChildIndex(HDisplayObject child){
		return _childList.indexOf(child);
	}
	
	/**
	 * ����������������ʾ���������е�����λ�á�
	 * @param child
	 * @param index
	 */
	public void setChildIndex(HDisplayObject child, int index){
		if(_childList.remove(child)){
			_childList.add(index, child);
		}
	}
	/**
	 * ��������ָ���Ӷ���� Z ��˳�򣨴�ǰ����˳�򣩡� 
	 * @param child1
	 * @param child2
	 */
	public void swapChildren(HDisplayObject child1,HDisplayObject child2){
		Collections.swap(_childList, _childList.indexOf(child1), _childList.indexOf(child2));
	}
	/**
	 * ���Ӽ��б�������ָ��������λ�ã������Ӷ���� Z ��˳��ǰ��˳�򣩡�
	 * @param index1
	 * @param index2
	 */
	public void swapChildrenAt(int index1,int index2){
		Collections.swap(_childList, index1, index2);
	}
	
	/**
	 * ��ȡ�������Ӿ���
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
	 * ��Ⱦ��ֻ��������á��벻Ҫ���Զ������е��á�
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
