package com.hanyeah.hcanvas;

import com.hanyeah.HEngine;
import com.hanyeah.display.HBitmap;
import com.hanyeah.display.HGraphics;
import com.hanyeah.display.HShape;
import com.hanyeah.display.HSprite;
import com.hanyeah.display.HStage;
import com.hanyeah.display.HTextField;
import com.hanyeah.events.HEvent;
import com.hanyeah.events.HIListener;
import com.hanyeah.events.HTouchEvent;
import com.hanyeah.geom.HRectangle;
import com.hanyeah.hengine.R;
import com.hanyeah.motion.HEasing;
import com.hanyeah.motion.HTweenLite;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;

/**
 * Demo。测试了对象的外接矩形，HTweenLite缓动
 * @author hanyeah
 * @date 2015-12-15
 */
public class RectTest extends Activity {

	private static HStage stage;
	private HEngine hengine;
	private final String TAG="hanyeah";
	private int co=0xff00ff00;
	private boolean flag=false;
	public RectTest() {
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
	        
	        HSprite sp=new HSprite();
	        stage.addChild(sp);
	        sp.x=100;
	        sp.y=100;
	        
	        Bitmap bmp=BitmapFactory.decodeResource(this.getResources(), R.drawable.cube);
	        Bitmap bmp2=BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
	        
	        final HBitmap hbmp=new HBitmap(bmp);
	        sp.addChild(hbmp);
	        hbmp.x=300;
	        hbmp.y=300;
	        hbmp.rotation=45;
	        
	        final HShape sp01=new HShape();
	        sp.addChild(sp01);
	        sp01.x=100;
	        sp01.y=100;
	        
	        final HSprite sp02=new HSprite();
	        sp.addChild(sp02);
	        sp02.x=100;
	        sp02.y=300;
	        sp02.rotation=-90;
	        //HBitmap hbmp2=new HBitmap(bmp);
	        //sp02.addChild(hbmp2);
	        
	        final HTextField fpsTf=new HTextField();
	        stage.addChild(fpsTf);
	        fpsTf.x=10;
	        //fpsTf.y=50;
	        fpsTf.size=30;
	        fpsTf.color=0xffff0000;
	        Log.e(TAG, fpsTf.getRect().toString());
	        
	        stage.addEventListener(HEvent.ENTER_FRAME,new HIListener() {
				
				@Override
				public boolean execute(HEvent e) {
					// TODO Auto-generated method stub
					hbmp.rotation++;
					HRectangle rect=hbmp.getRect(stage);
					stage.graphics.clear();
			        drawRect(stage.graphics,(float)rect.x,(float)rect.y,(float)rect.width,(float)rect.height,co,0);
			        
			        /*
			        sp01.rotation+=1.1;
			        drawRect(sp01.graphics,0f,0f,100f,100f,0xff00ff00,1);
			        HRectangle rect01=sp01.getRect(stage);
			        drawRect(stage.graphics,(float)rect01.x,(float)rect01.y,(float)rect01.width,(float)rect01.height,co,0);
			        
			        sp02.rotation+=1.2;
			        drawRect(sp02.graphics,0f,0f,100f,100f,0xff00ff00,1);
			        HRectangle rect02=sp02.getRect(stage);
			        drawRect(stage.graphics,(float)rect02.x,(float)rect02.y,(float)rect02.width,(float)rect02.height,co,0);
			        */
			        
			        fpsTf.text="fps:"+stage.getFPS();
					return false;
				}
			});
	        sp.addEventListener(HTouchEvent.CLICK, new HIListener() {
				
				@Override
				public boolean execute(HEvent e) {
					// TODO Auto-generated method stub
					co= 0xff000000+(int)(0xffffff*Math.random());
					return false;
				}
			});
	        
	        //tweenlite
	        final HBitmap bmp01=new HBitmap(bmp2);
	        stage.addChild(bmp01);
	        final HTweenLite htweenLite=new HTweenLite();
	        stage.addEventListener(HTouchEvent.CLICK, new HIListener() {
				@Override
				public boolean execute(HEvent e) {
					// TODO Auto-generated method stub
					htweenLite.execute(bmp01, 
							1.0f, 
							0.5f,
							0.02f,
							new double[]{bmp01.x,bmp01.y},
							new double[]{stage.mouseX,stage.mouseY}, 
							new HEasing.None.easeIn(),
							new HTweenLite.HIUpdate() {
								@Override
								public void execute(double[] cur, boolean complete) {
									// TODO Auto-generated method stub
									bmp01.x=cur[0];
									bmp01.y=cur[1];
								}
					});
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
