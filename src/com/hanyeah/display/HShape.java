/**
 * 
 */
package com.hanyeah.display;

import com.hanyeah.geom.HRectangle;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 用于创建矢量图。
 * @author hanyeah
 * @date 2015-12-3
 */
public class HShape extends HDisplayObject {

	public HGraphics graphics;
	/**
	 * 构造函数。
	 */
	public HShape() {
		// TODO Auto-generated constructor stub
		graphics=new HGraphics();
	}
	/**
	 * 子类实现
	 * @return
	 */
	protected HRectangle _getSelfRect(){
		HRectangle rect=graphics._getSelfRect();
		return _localRect2parent(rect);
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
