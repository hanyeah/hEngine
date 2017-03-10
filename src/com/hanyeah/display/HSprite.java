/**
 * 
 */
package com.hanyeah.display;

import com.hanyeah.geom.HRectangle;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * ��һ������ʵ��������ʾ����������
 * @author hanyeah
 * @date 2015-12-3
 */
public class HSprite extends HDisplayObjectContainer {
	/**����ʸ��ͼ����**/
	public HGraphics graphics;
	/**
	 * ���캯��
	 */
	public HSprite() {
		// TODO Auto-generated constructor stub
		graphics=new HGraphics();
	}
	/**
	 * ����ʵ��
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
	 * �������Ⱦ����Ҫ������д��ֻ��������á��벻Ҫ���Զ������е��á�
	 * @param canvas
	 * @param paint
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		graphics._selfRender(canvas, paint);
		super._selfRender(canvas, paint);
	}
}
