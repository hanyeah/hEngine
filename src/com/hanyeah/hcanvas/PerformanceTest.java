/**
 * 
 */
package com.hanyeah.hcanvas;

import com.hanyeah.HEngine;
import com.hanyeah.display.HBitmap;
import com.hanyeah.display.HDisplayObject;
import com.hanyeah.display.HGraphics;
import com.hanyeah.display.HSprite;
import com.hanyeah.display.HStage;
import com.hanyeah.display.HTextField;
import com.hanyeah.events.HEvent;
import com.hanyeah.events.HIListener;
import com.hanyeah.events.HTouchEvent;
import com.hanyeah.geom.HPoint;
import com.hanyeah.geom.HRectangle;
import com.hanyeah.hengine.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.Log;

/**
 * Demo。渲染引擎效率测试。
 * @author hanyeah
 * @date 2015-12-14
 */
public class PerformanceTest extends Activity {

	private static HStage stage;
	private HEngine hengine;
	private final String TAG="hanyeah";
	private int co=0xff00ff00;
	private HDisplayObject curDis=null;
	/**
	 * 
	 */
	public PerformanceTest() {
		// TODO Auto-generated constructor stub
	}
	 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        if(stage!=null){
	        	Log.e(TAG, "hengine:"+hengine);
	        	hengine=new HEngine(this);
	        	hengine.stage=stage;
	        	setContentView(hengine);
	        	return;
	        }
	        hengine=new HEngine(this);
	        stage=hengine.stage;
	        setContentView(hengine);
	        stage.frameRate=100;
	        
	        Bitmap bmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.cube);
	        Bitmap bmp2=BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
	        
	        final HSprite con=new HSprite();
	        stage.addChild(con);
	        con.name="con";
	        for(int i=0;i<10;i++){
	        	for(int j=0;j<10;j++){
	        		HSprite sp=new HSprite();
	        		HBitmap hbmp=new HBitmap(Math.random()>0.5?bmp:bmp2);
	        		sp.addChild(hbmp);
	        		HPoint size=hbmp.getSize();
	        		hbmp.x=-size.x/2;
	        		hbmp.y=-size.y/2;
	        		con.addChild(sp);
	        		sp.x=i*100+50;
	        		sp.y=j*100+50;
	        		//sp.mouseChildren=false;
	        		sp.name="sp_"+i+"_"+j;
	        	}
	        }
	        //fps
	        final HTextField fpsTf=new HTextField();
	        stage.addChild(fpsTf);
	        fpsTf.x=10;
	        fpsTf.size=30;
	        fpsTf.color=0xffff0000;
	        
	        stage.addEventListener(HEvent.ENTER_FRAME, new HIListener() {
				
				@Override
				public boolean execute(HEvent e) {
					// TODO Auto-generated method stub
					
					for(int i=0,len=con.getNumChildren();i<len;i++){
						HDisplayObject dis=con.getChildAt(i);
						dis.rotation++;//旋转之后，渲染非常耗时
						
					}
					if(curDis!=null){
						stage.graphics.clear();
						HRectangle rect=curDis.getRect(stage);
				        drawRect(stage.graphics,(float)rect.x,(float)rect.y,(float)rect.width,(float)rect.height,co,0);//矢量图也很耗时
					}
					fpsTf.text="fps:"+stage.getFPS();
					return false;
				}
			});
	        
	        con.addEventListener(HTouchEvent.CLICK, new HIListener() {
				
				@Override
				public boolean execute(HEvent e) {
					// TODO Auto-generated method stub
					
					
					curDis=(HDisplayObject) e.getTarget();
					
					return false;
				}
			});
	       
	 }
	 /**
	  * 画矩形
	  * @param g
	  * @param x
	  * @param y
	  * @param width
	  * @param height
	  * @param co
	  * @param style	0,笔触；1,填充；2,笔触+填充
	  */
	 private void drawRect(HGraphics g,float x,float y,float width,float height,int co,int style){
		 Path p=new Path();
		 Paint pa=new Paint();
		 p.addRect(x, y, x+width, y+height, Path.Direction.CCW);
		 pa.setStyle(style==0?Style.STROKE:style==1?Style.FILL:Style.FILL_AND_STROKE);
		 pa.setColor(co);
		 g.addPath(p, pa);
	 }
	

}
