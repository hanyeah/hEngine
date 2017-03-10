/**
 * 
 */
package com.hanyeah.display;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.hanyeah.events.HEvent;
import com.hanyeah.events.HTouchEvent;
import com.hanyeah.geom.HPoint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * @author hanyeah
 * @date 2015-12-3
 */
public class HStage extends HSprite {
	
	/**设置的帧频**/
	public double frameRate=30;
	/**舞台背景颜色**/
	public int backGroundColor=Color.BLACK;
	
	public double mouseX;
	public double mouseY;
	private Canvas canvas;
	private Paint paint;
	private long t;
	private int n;
	private int fps=1;
	
	/**
	 * 构造函数，只能由引擎调用。
	 */
	public HStage() {
		// TODO Auto-generated constructor stub
		n=0;
		t=System.currentTimeMillis();
	}
	/**
	 * 舞台宽
	 * @return
	 */
	public double getStageWidth(){
		if(canvas==null)return 0;
		return canvas.getWidth();
	}
	/**
	 * 舞台高
	 * @return
	 */
	public double getStageHeight(){
		if(canvas==null)return 0;
		return canvas.getHeight();
	}
	/**
	 * 获取实际帧频
	 * @return
	 */
	public int getFPS(){
		return fps;
	}
	/**
	 * SurfaceView中循环调用
	 */
	public void render(Canvas canvas,Paint paint){
		
		this.canvas=canvas;
		this.paint=paint;
		canvas.drawColor(backGroundColor);
		//事件处理
		_hcatch();
		//渲染
		_render(canvas,paint);
		long start =System.currentTimeMillis();
		n++;
		if(start-t>=1000){
			fps=(int)(1000*n/(start-t));
			t=start;
			n=0;
		}
	}
	
	/**
	 * 渲染，只供引擎调用。请不要在自定义类中调用。
	 */
	protected void _render(Canvas canvas,Paint paint){
		super._render(canvas, paint);
	}
	/**
	 * @override
	 * 事件捕获阶段
	 */
	protected void _hcatch(){
		super._hcatch();
	}
	//----------------------touch事件处理-----------------------------
	private HInteractiveObject downTarget;
	private HInteractiveObject lastTarget;
	/**
	 * 触摸事件，只能由引擎调用。
	 */
	public void _touchUp() {
		// TODO Auto-generated method stub
		HInteractiveObject target=_hcatchMouse(this);
		if(target==null){
			target=this;
		}
		target.dispatchEvent(new HTouchEvent(HTouchEvent.TOUCH_END,null,true,true));
		if(target==downTarget){
			target.dispatchEvent(new HTouchEvent(HTouchEvent.CLICK,null,true,true));
		}
		lastTarget=target;
	}
	/**
	 * 触摸事件，只能由引擎调用。
	 */
	public void _touchDown() {
		// TODO Auto-generated method stub
		HInteractiveObject target=_hcatchMouse(this);
		if(target==null){
			target=this;
		}
		downTarget=target;
		lastTarget=target;
		target.dispatchEvent(new HTouchEvent(HTouchEvent.TOUCH_START,null,true,true));
	}
	/**
	 * 触摸事件，只能由引擎调用。
	 */
	public void _touchMove() {
		// TODO Auto-generated method stub
		HInteractiveObject target=_hcatchMouse(this);
		if(target==null){
			target=this;
		}
		if(lastTarget==null){
			lastTarget=target;
		}
		else if(target!=lastTarget){
			//target对象变了
			//touchover/touchout必发
			target.dispatchEvent(new HTouchEvent(HTouchEvent.TOUCH_OVER,null,true,true));
			lastTarget.dispatchEvent(new HTouchEvent(HTouchEvent.TOUCH_OUT,null,true,true));
			
			//判断rollover/rollout
			HInteractiveObject mpar=target;
			//target以及target的父级元素，如果包含lastTarget则不用发送roolover，否则发送rollover
			while(mpar!=null){
				if(mpar instanceof HDisplayObjectContainer){
					if(!((HDisplayObjectContainer)mpar).contains(lastTarget)){
						mpar.dispatchEvent(new HTouchEvent(HTouchEvent.ROLL_OVER,null,false,true));
						mpar=mpar.parent;
					}
					else{
						break;
					}
				}
				else{
					mpar=mpar.parent;
				}
			}
			//rollout
			HInteractiveObject opar=lastTarget;
			while (opar!=null) {
				if(opar instanceof HDisplayObjectContainer){
					if(!((HDisplayObjectContainer)opar).contains(lastTarget)){
						opar.dispatchEvent(new HTouchEvent(HTouchEvent.ROLL_OUT,null,false,true));
						opar=opar.parent;
					}
					else{
						break;
					}
				}
				else{
					opar=opar.parent;
				}
			}
			lastTarget=target;
		}
		else{
			//target对象没变
		}
		target.dispatchEvent(new HTouchEvent(HTouchEvent.TOUCH_MOVE,null,true,true));
	}
	/**
	 * 鼠标事件捕获，返回鼠标事件的目标
	 * @param interDis
	 * @return
	 */
	private HInteractiveObject _hcatchMouse(HInteractiveObject interDis){
		
		if(!interDis.mouseEnabled){
			return null;
		}
		if(interDis instanceof HDisplayObjectContainer){
			HDisplayObjectContainer con=(HDisplayObjectContainer)interDis;
			if(con.mouseChildren){
				for(int i=con.getNumChildren()-1;i>=0;i--){
					HDisplayObject child = con.getChildAt(i);
					if(child instanceof HInteractiveObject){
						HInteractiveObject result=_hcatchMouse((HInteractiveObject)child);
						if(result!=null){
							return result;
						}
					}
				}
			}
			if(con.hitTestPoint(mouseX, mouseY)){
				return con;
			}
		}
		else{
			if(interDis.hitTestPoint(mouseX, mouseY)){
				return interDis;
			}
		}
		return null;
		
	}

}
