/**
 * 
 */
package com.hanyeah.display;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.hanyeah.HObject;
import com.hanyeah.geom.HRectangle;

/**
 * @author hanyeah
 * @date 2015-12-3
 */
public class HGraphics extends HObject {

	private List<obj> _list=new ArrayList<obj>();
	/**
	 * 构造函数
	 */
	public HGraphics() {
		// TODO Auto-generated constructor stub
		 
	}
	
	/**
	 * 添加路径
	 * @param path
	 * @param paint
	 */
	public void addPath(Path path,Paint paint){
		_list.add(new obj(path,paint));
	}
	/**
	 * 清空
	 */
	public void clear(){
		_list.clear();
	}
	
	/**
	 * 获取路径，用于遮罩
	 * @return
	 */
	public Path getPath(){
		Path p=new Path();
		for(int i=0,len=_list.size();i<len;i++){
			p.addPath(_list.get(i).path);	
		}
		return p;
	}
	/**
	 * 获取自身的外接矩形
	 * @return
	 */
	protected HRectangle _getSelfRect(){
		HRectangle rect=new HRectangle();
		for(int i=0,len=_list.size();i<len;i++){
			Path p=_list.get(i).path;
			RectF bounds=new RectF();
			p.computeBounds(bounds, false);//Compute the bounds of the path, and write the answer into bounds. If the path contains 0 or 1 points, the bounds is set to (0,0,0,0) 
			HRectangle rect0=new HRectangle(bounds.left,bounds.top,bounds.width(),bounds.height());
			if(rect.isEmpty()){
				rect=rect0;
			}
			else{
				rect=rect.union(rect0);
			}
		}
		return rect;
	}
	//
	
	private class obj{
		public Path path;
		public Paint paint;
		public obj(Path path,Paint paint){
			this.path=path;
			this.paint=paint;
		}
	}
	//--------------------------------
	/**
	 * 渲染，只供引擎调用。请不要在自定义类中调用。
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		int len=_list.size();
		for(int i=0;i<len;i++){
			obj o=_list.get(i);
			canvas.drawPath(o.path, o.paint);
		}
	}
}
