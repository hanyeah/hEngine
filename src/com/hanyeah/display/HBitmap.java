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
 * λͼ����
 * @author hanyeah
 * @date 2015-12-3
 */
public class HBitmap extends HDisplayObject {
	/**λͼ��Դ����**/
	public Bitmap source;
	/**Դ���ݵľ�������**/
	public HRectangle sourceRect;
	/**
	 * ����HBitmap��
	 * @param source		ԭ����λͼ����
	 * @param sourceRect	��ȡԴͼ�������
	 */
	public HBitmap(Bitmap source,HRectangle sourceRect) {
		// TODO Auto-generated constructor stub
		this.source=source;
		this.sourceRect=sourceRect;
	}
	/**
	 * ����HBitmap��
	 * @param source		ԭ����λͼ����,��ʹ������Դͼ��
	 */
	public HBitmap(Bitmap source) {
		// TODO Auto-generated constructor stub
		this(source,new HRectangle(0,0,source.getWidth(),source.getHeight()));
	}
	/**
	 * ����HBitmap��
	 * ����һ���ߴ�Ϊ0*0��λͼ��
	 */
	public HBitmap() {
		// TODO Auto-generated constructor stub
		source=Bitmap.createBitmap(0, 0, Bitmap.Config.ARGB_8888);
		sourceRect=new HRectangle();
	}
	
	/**
	 * ����ʵ��
	 * @return
	 */
	protected HRectangle _getSelfRect(){
		return _localRect2parent(sourceRect);
	}
	/**
	 * @override
	 * ��Ⱦ��ֻ��������á��벻Ҫ���Զ������е��á�
	 * @param canvas
	 * @param paint
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		Rect rec=new Rect((int)sourceRect.x,(int)sourceRect.y,(int)(sourceRect.x+sourceRect.width),(int)(sourceRect.y+sourceRect.height));
		Rect trec=new Rect(0,0,(int)sourceRect.width,(int)sourceRect.height);
		canvas.drawBitmap(source,rec, trec, paint);
	}
	
}
