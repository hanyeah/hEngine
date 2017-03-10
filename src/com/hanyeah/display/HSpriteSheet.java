/**
 * 
 */
package com.hanyeah.display;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hanyeah.events.HEvent;
import com.hanyeah.events.HIListener;
import com.hanyeah.geom.HPoint;
import com.hanyeah.geom.HRectangle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * @author hanyeah
 * @date 2015-12-8
 * 实现帧动画。
 */
public class HSpriteSheet extends HInteractiveObject {

	private JSONObject data;
	private Bitmap bitmap;
	private String animation;
	private boolean isPaused;
	private int currentFrame;
	private int totalFrames;
	private int[] frames;
	private float speed=1.0f;
	private float f=0.0f;
	
	/**
	 * 用此类创建帧动画。
	 * 描述文件格式参考<a href="http://createjs.com/docs/easeljs/classes/SpriteSheet.html">createjs中的SpriteSheet</a>.
	 * @param bitmap	一张序列图，安卓中的Bitmap对象
	 * @param data		动画描述文件
	 */
	public HSpriteSheet(Bitmap bitmap,JSONObject data) {
		// TODO Auto-generated constructor stub
		this.bitmap=bitmap;
		this.data=data;
		try {
			JSONObject jsonObj=data.getJSONObject("animations");
			Iterator<?> it = jsonObj.keys(); 
			String firstAnim="";
			if(it.hasNext()){
				firstAnim=it.next().toString();
				gotoAndPlay(firstAnim,0);
			}
			//Log.e("hanyeah_sheeet", "firstAnim:"+firstAnim);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("hanyeah_sheeet", "error:"+e);
		}
		this.addEventListener(HEvent.ENTER_FRAME, enterFrameListener);
	}
	/**
	 * 转到指定帧播放
	 * @param frame
	 */
	public void gotoAndPlay(int frame){
		f=currentFrame=frame;
		isPaused=false;
	}
	/**
	 * 转到指定场景的指定帧播放
	 * @param animation
	 * @param frame
	 */
	public void gotoAndPlay(String animation,int frame){
		frames=getAnimationFrames(animation);
		this.animation=animation;
		f=currentFrame=frame;
		totalFrames=frames.length;
		isPaused=false;
	}
	/**
	 * 每帧刷新
	 */
	private HIListener enterFrameListener=new HIListener() {
		
		@Override
		public boolean execute(HEvent e) {
			// TODO Auto-generated method stub
			if(!isPaused){
				f+=speed;
				if(f>=totalFrames){
					f=0.0f;
				}
				currentFrame=(int)f;
			}
			return false;
		}
	};
	/**
	 * 跳转到某一帧并停止
	 * @param frame
	 */
	public void gotoAndStop(int frame){
		gotoAndPlay(frame);
		stop();
	}
	/**
	 * 跳转到某个场景的某一帧并停止
	 * @param animation
	 * @param frame
	 */
	public void gotoAndStop(String animation,int frame){
		gotoAndPlay(animation,frame);
		stop();
	}
	/**
	 * 是否暂停状态
	 * @return
	 */
	public boolean getPaused(){
		return isPaused;
	}
	/**
	 * 播放
	 */
	public void play(){
		isPaused=false;
	}
	/**
	 * 暂停
	 */
	public void stop(){
		isPaused=true;
	}
	/**
	 * 下一帧
	 */
	public void nextFrame(){
		if(currentFrame<totalFrames-1){
			currentFrame++;
			f=currentFrame;
		}
	}
	/**
	 * 上一帧
	 */
	public void preFrame(){
		if(currentFrame>0){
			currentFrame--;
			f=currentFrame;
		}
	}
	/**
	 * 当前场景
	 * @return
	 */
	public String getCurrentAnimation(){
		return animation;
	}
	/**
	 * 当前帧
	 * @return
	 */
	public int getCurrentFrame(){
		return currentFrame;
	}
	/**
	 * 当前animation总共有多少帧
	 * @return
	 */
	public int getTotalFrames(){
		return totalFrames;
	}
	/**
	 * 获取尺寸
	 */
	public HPoint getSize(){
		int[] arr=getFrames(frames[currentFrame]);
		return new HPoint(arr[2],arr[3]);
	}
	/**
	 * 获取当前帧的外接矩形
	 * @return
	 */
	public HRectangle getFrameRect(){
		int[] arr=getFrames(frames[currentFrame]);
		return new HRectangle(arr[5],arr[6],arr[2],arr[3]);
	}
	/**
	 * 销毁
	 */
	public void destroy(){
		this.removeEventListener(HEvent.ENTER_FRAME, enterFrameListener);
	}
	/**
	 * 子类实现
	 * @return
	 */
	protected HRectangle _getSelfRect(){
		return _localRect2parent(getFrameRect());
	}
	/**
	 * 解析json数据，获取animation相关信息
	 * 用了好多try...catch,后来发现可以用JSONTokener解析，然后判断类型
	 * @param animation
	 * @return
	 */
	private int[] getAnimationFrames(String animation){
		try {
			JSONObject jsonObj=data.getJSONObject("animations");
			int[] arr;
			try{
				/*
				 单独一个数值
				 animations: {sit: 7}
				 */
				int a=jsonObj.getInt(animation);
				arr=new int[1];
				arr[0]=a;
				speed=1.0f;
			}
			catch(JSONException e0){
				try{
					/*数组
					如animations: {
				     		start, end
				    	run: [0, 8],
				    	jump: [9, 12]
						}
					*/
					JSONArray jArr=jsonObj.getJSONArray(animation);
					int startFrame=jArr.getInt(0);
					int endFrame=jArr.getInt(1);
					int len=endFrame-startFrame;
					arr=new int[len+1];
					for(int i=0;i<=len;i++){
						arr[i]=startFrame+i;
					}
					speed=1.0f;
					if(jArr.length()>=3){
						speed=(float)jArr.getDouble(2);
					}
				}
				catch(JSONException e){
					/*
					 不是数组
					 如animations: {
    					walk: {
        					frames: [1,2,3,3,2,1]
    					},
    					shoot: {
        					frames: [1,4,5,6],
        					speed: 0.5
    					}
					}
					*/
					JSONObject jAnim=jsonObj.getJSONObject(animation);
					JSONArray jArr=jAnim.getJSONArray("frames");
					int len=jArr.length();
					arr=new int[len];
					for(int i=0;i<len;i++){
						arr[i]=jArr.getInt(i);
					}
					speed=1.0f;
					if(jAnim.has("speed")){
						speed=(float)jAnim.getDouble("speed");
					}
				}
			}
			return arr;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("hanyeah_spriteSheet:", e.toString());
		}
		return null;
	}
	/**
	 * 解析json数据，获取某一帧
	 * @param index
	 * @return
	 */
	private int[] getFrames(int index){
		try {
			JSONObject jsonFrame=data.getJSONObject("frames");
			int width=jsonFrame.getInt("width");
			int height=jsonFrame.getInt("height");
			int count=jsonFrame.getInt("count");
			int regX=jsonFrame.getInt("regX");
			int regY=jsonFrame.getInt("regY");
			int spacing=jsonFrame.getInt("spacing");
			int margin=jsonFrame.getInt("margin");
			
			int w=(bitmap.getWidth()-2*margin+spacing)/(width+spacing);
			int jj=index/w;
			int ii=index%w;
			int y0=margin+(height+spacing)*jj;
			int x0=margin+(width+spacing)*ii;
			int[] arr=new int[]{x0,y0,width,height,0,regX,regY};
			return arr;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			try {
				JSONArray jArr=data.getJSONArray("frames");
				int[] arr=new int[7];
				JSONArray jArr0=jArr.optJSONArray(index);
				int len=jArr0.length();
				for(int i=0;i<7;i++){
					arr[i]=i<len?jArr0.getInt(i):0;
				}
				return arr;
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}
	//---------------------------------------------------
	/**
	 * 自身的渲染，需要子类重写，只供引擎调用。请不要在自定义类中调用。
	 * @param canvas
	 * @param paint
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		super._selfRender(canvas, paint);
		int[] arr=getFrames(frames[currentFrame]);
		Rect src=new Rect(arr[0],arr[1],arr[0]+arr[2],arr[1]+arr[3]);
		Rect dst=new Rect(arr[5],arr[6],arr[5]+arr[2],arr[6]+arr[3]);
		canvas.drawBitmap(bitmap, src, dst, paint);
	}
}
