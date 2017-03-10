/**
 * 
 */
package com.hanyeah.motion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.hanyeah.HObject;
import com.hanyeah.events.HEvent;
import com.hanyeah.motion.HEasing.HIEaseFunction;

/**
 * @author hanyeah
 * @date 2015-12-12
 * 缓动类。
 * 类本身只能对数据进行缓动，对象的状态改变需要自己在update参数中完成。
 */
public class HTweenLite extends HObject {
	private List<HTween> list=new ArrayList<HTween>();
	/**
	 * 缓动方法
	 * @param tar		缓动目标,一个目标只能同时指定一个缓动
	 * @param dur		缓动持续时间
	 * @param delay		延时启动缓动
	 * @param from		缓动初始值
	 * @param to		缓动目标值
	 * @param ease		缓动类型
	 * @param update	缓动更新，缓动只是实现了数据的缓动，对象的状态变换还需要自己在中实现
	 */
	public void execute(Object tar,float dur,float delay,float timeStep,double[] from,double[] to,HIEaseFunction ease,HIUpdate update){
		HTween tween=new HTween(tar ,dur,delay,timeStep, from,to,ease,update);
		list.add(tween);
	}
	/**
	 * 取消指定对象的缓动
	 * @param tar
	 * @param complete
	 */
	public void kill(Object tar,boolean complete){
		for(int i=list.size()-1;i>=0;i--){
			HTween tween=list.get(i);
			if(tween.target==tar){
				tween.kill(complete);
				list.remove(i);
			}
		}
	}
	/**
	 * 取消所有缓动
	 * @param complete
	 */
	public void killAll(boolean complete){
		while(list.size()>0){
			list.remove(0).kill(complete);
		}
	}
	/**
	 * 移除tween
	 * @param htween
	 */
	private void removeTween(HTween htween){
		list.remove(htween);
	}
	/**
	 * 更新
	 *
	 */
	public interface HIUpdate{
		public void execute(double[] cur,boolean complete);
	}
	/**
	 * 
	 * HTween
	 *
	 */
	class HTween{
		private Object target;
		private float duration;
		private double[] from;
		private double[] to;
		private double[] cur;
		private float t=0;
		private HIUpdate update;
		private Timer timer;
		private HIEaseFunction ease;
		private double TIME_STEP=0.04;
		
		public HTween(Object tar,float duration,float delay,float timeStep,double[] from,double[] to,HIEaseFunction ease,HIUpdate update){
			this.target=tar;
			this.duration=duration;
			this.TIME_STEP=timeStep;
			this.from=from;
			this.to=to;
			this.update=update;
			this.ease=ease;
			cur=from.clone();
			
			timer = new Timer();
			timer.schedule(tt, (int)(1000*delay),(int)(1000*TIME_STEP));
		}
		private TimerTask tt=new TimerTask() { 
	            @Override
	            public void run() {
	            	t+=TIME_STEP;
	            	if(t>=duration){
	            		kill(true);
	            	}
	            	else{
	            		for(int i=0,len=from.length;i<len;i++){
	            			//计算
	            			cur[i]=ease.execute(t, from[i], to[i]-from[i], duration);
	            		}
	            		update.execute(cur,false);
	            	}
	            }
	        };
		public void kill(boolean complete){
			if(complete){
				update.execute(to,true);
			}
			timer.cancel();
			HTweenLite.this.removeTween(this);
		}
	}
	
}
