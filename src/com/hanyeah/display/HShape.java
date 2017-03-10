/**
 * 
 */
package com.hanyeah.display;

import com.hanyeah.geom.HRectangle;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * ���ڴ���ʸ��ͼ��
 * @author hanyeah
 * @date 2015-12-3
 */
public class HShape extends HDisplayObject {

	public HGraphics graphics;
	/**
	 * ���캯����
	 */
	public HShape() {
		// TODO Auto-generated constructor stub
		graphics=new HGraphics();
	}
	/**
	 * ����ʵ��
	 * @return
	 */
	protected HRectangle _getSelfRect(){
		HRectangle rect=graphics._getSelfRect();
		return _localRect2parent(rect);
	}
	/**
	 * �������Ⱦ����Ҫ������д��ֻ��������á��벻Ҫ���Զ������е��á�
	 * @param canvas
	 * @param paint
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		graphics._selfRender(canvas, paint);
		super._selfRender(canvas, paint);
	}
}
