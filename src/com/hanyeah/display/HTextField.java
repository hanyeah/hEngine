/**
 * 
 */
package com.hanyeah.display;

import com.hanyeah.geom.HRectangle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

/**
 * �ı���ʾ����
 * @author hanyeah
 * @date 2015-12-3
 */
public class HTextField extends HInteractiveObject {
	/**�ı�����**/
	public String text="";
	/**������ɫֵ**/
	public int color=0x000000;
	/**���ִ�С�����أ�**/
	public int size=12;
	/**�ı�����**/
	public double width=100;
	/**�ı���߶�**/
	public double height=100;
	/**����**/
	public Typeface font;
	/**�Ƿ��Զ�����**/
	public boolean wordWrap=true;
	/**�ı����뷽ʽ**/
	public Paint.Align textAlign=Paint.Align.LEFT;
	/**�о࣬Ĭ��1.5���о�**/
	public int lineHeight=0;
	/**������ɫ��Ĭ���ޱ���ɫ**/
	public int backgroundColor=-1;
	
	/**
	 * ���캯��
	 */
	public HTextField() {
		// TODO Auto-generated constructor stub
		font=Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
	}
	
	protected HRectangle _getSelfRect(){
		HRectangle rect=_getTextRect();
		return _localRect2parent(rect);
	}
	private HRectangle _getTextRect(){
		HRectangle rect=new HRectangle();
		switch(textAlign){
			case LEFT:
				rect=new HRectangle(0,0,width,height);
				break;
			case CENTER:
				rect=new HRectangle(-width/2,0,width/2,height);
				break;
			case RIGHT:
				rect=new HRectangle(-width,0,0,height);
				break;
		}
		return rect;
	}
	
	/**
	 * @override
	 * ��Ⱦ��ֻ��������á��벻Ҫ���Զ������е��á�
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		
		int co=paint.getColor();
		float si=paint.getTextSize();
		Paint.Align al=paint.getTextAlign();
		Typeface fo=paint.getTypeface();
		
		HRectangle rect=_getTextRect();
		canvas.clipRect((float)rect.x,(float)rect.y, (float)(rect.x+rect.width), (float)(rect.y+rect.height));//������ʾ����
		if(backgroundColor>=0)canvas.drawColor(backgroundColor);
		
		paint.setColor(color);
		paint.setTextSize(size);
		paint.setTextAlign(textAlign);
		paint.setTypeface(font);
		if(lineHeight<=0)lineHeight=(int)(1.5*size);//��������и�С�ڵ���0����ʹ��1.5���о�
		//���У�����
		int lines=1;
		String[] sArr=text.split("(?:\r\n|\r|\n)");//�ֶ�
		int len=sArr.length;
		
		for(int i=0;i<len;i++){
			
			String str=sArr[i];
			int count =str.length();
			int start=0;
			for(int j=1;j<=count;j++){
				float lw=paint.measureText(str,start,j);
				if(lw>width){
					canvas.drawText(str.substring(start, j-1), 0, lineHeight*lines, paint);
					start=j-1;
					lines++;
				}
				else if(lw==width){
					canvas.drawText(str.substring(start, j), 0, lineHeight*lines, paint);
					start=j;
					lines++;
				}
			}
			if(start<count){
				canvas.drawText(str.substring(start, count), 0, lineHeight*lines, paint);
			}
			lines++;
		}
		/*
		int count =text.length();
		float[] widths=new float[count];
		for(int i=0;i<count;i++){
			paint.getTextWidths(text, 0, i, widths);
		}
		int start=0;
		float w=0;
		int lines=0;
		for(int i=0;i<count;i++){
			float lw=w+widths[i];
			if(lw>width){
				canvas.drawText(text.substring(start, i+1), 0, lineHeight*lines, paint);
				w=0;
				start=i+1;
				lines++;
			}
			else{
				w=lw;
			}
		}
		if(start<count){
			canvas.drawText(text.substring(start, count), 0, lineHeight*lines, paint);
		}
		*/
		//canvas.drawText(text, 0, 0, paint);
		
		paint.setColor(co);
		paint.setTextSize(si);
		paint.setTextAlign(al);
		paint.setTypeface(fo);
	}

}
