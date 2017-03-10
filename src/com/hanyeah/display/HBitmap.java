/**
 * 
 */
package com.hanyeah.display;

import com.hanyeah.geom.HRectangle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * 位图对象。
 * @author hanyeah
 * @date 2015-12-3
 */
public class HBitmap extends HDisplayObject {
	/**位图的源数据**/
	public Bitmap source;
	/**源数据的矩形区域**/
	public HRectangle sourceRect;
	/**
	 * 创建HBitmap。
	 * @param source		原生的位图对象
	 * @param sourceRect	截取源图像的区域
	 */
	public HBitmap(Bitmap source,HRectangle sourceRect) {
		// TODO Auto-generated constructor stub
		this.source=source;
		this.sourceRect=sourceRect;
	}
	/**
	 * 创建HBitmap。
	 * @param source		原生的位图对象,将使用整张源图像。
	 */
	public HBitmap(Bitmap source) {
		// TODO Auto-generated constructor stub
		this(source,new HRectangle(0,0,source.getWidth(),source.getHeight()));
	}
	/**
	 * 创建HBitmap。
	 * 创建一个尺寸为0*0的位图。
	 */
	public HBitmap() {
		// TODO Auto-generated constructor stub
		source=Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888);
		sourceRect=new HRectangle();
	}
	
	/**
	 * 子类实现
	 * @return
	 */
	protected HRectangle _getSelfRect(){
		return _localRect2parent(sourceRect);
	}
	/**
	 * @override
	 * 渲染，只供引擎调用。请不要在自定义类中调用。
	 * @param canvas
	 * @param paint
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		Rect rec=new Rect((int)sourceRect.x,(int)sourceRect.y,(int)(sourceRect.x+sourceRect.width),(int)(sourceRect.y+sourceRect.height));
		Rect trec=new Rect(0,0,(int)sourceRect.width,(int)sourceRect.height);
		canvas.drawBitmap(source,rec, trec, paint);
	}
	
}
