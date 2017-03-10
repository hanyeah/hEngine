/**
 * 
 */
package com.hanyeah;

import com.hanyeah.display.HStage;
import com.hanyeah.events.HEvent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * @author hanyeah
 * @date 2015-12-10
 * ������������HStage��SurfaceView��ʵ�ֶ�ʱˢ��stage�Լ������¼��������������в��Ǳ���ģ���˿��Ը��ݾ��������Լ�ʵ�֡�
 */
public class HEngine extends SurfaceView implements Callback, Runnable {

	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private Thread th;
	private boolean flag=true;
	public HStage stage;
	/**
	 * @param context
	 */
	public HEngine(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		sfh=this.getHolder();
		sfh.addCallback(this);
		
		paint=new Paint();
		stage=new HStage();
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		stage.dispatchEvent(new HEvent(HEvent.STAGE_CREATED));
		flag=true;
		th=new Thread(this);
		th.start();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		stage.mouseX=event.getX();
		stage.mouseY=event.getY();
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				stage._touchDown();
				break;
			case MotionEvent.ACTION_MOVE:
				stage._touchMove();
				break;
			case MotionEvent.ACTION_UP:
			default:
				stage._touchUp();
				break;
		}
		//http://blog.csdn.net/wike163/article/details/7718695
		//��public boolean onTouchEvent(MotionEvent event) ������,����true��ʾ�Ѿ��������,����Ҫ�����ϴ���ontouch�¼���,false��������ϴ���ontouch�¼�,����ʹ�õ�ʱ��,����return super.onTouchEvent(event);, �ڲ��Ե�ʱ��ֻ����ACTION_DOWN;  ������ֵ��Ϊ return true; �Ϳ��Բ������е�ontouch�¼���.
		return true; 
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		flag=false;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(flag){
			long start =System.currentTimeMillis();
			try{
				canvas = sfh.lockCanvas();
				canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG));//�����
				stage.render(canvas,paint);
				sfh.unlockCanvasAndPost(canvas);
			}
			catch(Exception e){
				Log.e("hanyeah_onEnterFrame:", "error:"+e);
			}
			
			long end=System.currentTimeMillis();
			long delay=(long)(1000/stage.frameRate);
			if(end-start<delay){
				try {
					Thread.sleep(delay-(end-start));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}//end run
}
