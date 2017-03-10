/**
 * 
 */
package com.hanyeah.display;

import com.hanyeah.geom.HRectangle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * 第一个可以实例化的显示对象容器。
 * @author hanyeah
 * @date 2015-12-3
 */
public class HSprite extends HDisplayObjectContainer {
	/**用于矢量图操作**/
	public HGraphics graphics;
	/**
	 * 构造函数
	 */
	public HSprite() {
		// TODO Auto-generated constructor stub
		graphics=new HGraphics();
	}
	/**
	 * 子类实现
	 * @return
	 */
	protected HRectangle _getSelfRect(){
		HRectangle rect=super._getSelfRect();
		HRectangle rect0=graphics._getSelfRect();
		if(!rect0.isEmpty()){
			rect0=_localRect2parent(rect0);
			if(rect.isEmpty()){
				rect=rect0;
			}
			else{
				rect=rect.union(rect0);
			}
		}
		return rect;
	}
	/**
	 * 自身的渲染，需要子类重写，只供引擎调用。请不要在自定义类中调用。
	 * @param canvas
	 * @param paint
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		graphics._selfRender(canvas, paint);
		super._selfRender(canvas, paint);
	}
}
