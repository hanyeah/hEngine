/**
 * 
 */
package com.hanyeah.display;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.hanyeah.HMath;
import com.hanyeah.events.HEvent;
import com.hanyeah.events.HEventDispatcher;
import com.hanyeah.geom.HMatrix;
import com.hanyeah.geom.HMatrixTransformer;
import com.hanyeah.geom.HPoint;
import com.hanyeah.geom.HRectangle;

/**
 * 显示对象的基类。不可以实例化。
 * @author hanyeah
 */
public abstract class HDisplayObject extends HEventDispatcher {
	
	/**指示 HDisplayObject 实例相对于父级 HDisplayObjectContainer 本地坐标的 x 坐标。**/
	public double x=0;
	
	/**指示 DisplayObject 实例相对于父级 DisplayObjectContainer 本地坐标的 y 坐标。**/
	public double y=0;
	
	/**显示对象是否可见。**/
	public boolean visible=true;
	
	/**透明度**/
	public double alpha=1.0;
	
	/**水平缩放比例（百分比）。**/
	public double scaleX=1.0;
	
	/**垂直缩放比例（百分比）。**/
	public double scaleY=1.0;
	
	/**旋转角度（度）**/
	public double rotation=0;
	
	/**实例名**/
	public String name;
	
	/**遮罩(尽量使用简单的图形作为遮罩，否则容易出问题)**/
	private HGraphics mask=null;
	
	/**父容器**/
	protected HDisplayObjectContainer parent;
	
	
	/**
	 * 构造函数，不可以实例化。
	 */
	public HDisplayObject() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取显示对象的宽度，以像素为单位。（影响效率）
	 * @return
	 */
	public double getWidth(){
		return getSize().x;
	}
	/**
	 * 设置显示对象的宽度，以像素为单位。（影响效率）
	 * @param value
	 */
	public void setWidth(double value){
		double w=getWidth();
		if(w!=0){
			scaleX*=value/w;
		}
	}
	/**
	 * 获取显示对象的高度，以像素为单位。（影响效率）
	 * @return
	 */
	public double getHeight(){
		return getSize().y;
	}
	/**
	 * 设置显示对象的高度，以像素为单位。（影响效率）
	 * @param value
	 */
	public void setHeight(double value){
		double h=getHeight();
		if(h!=0){
			scaleY*=value/h;
		}
	}
	/**
	 * 获取尺寸，一次性获取宽高（影响效率）
	 * @return
	 */
	public HPoint getSize(){
		HRectangle rect=_getSelfRect();
		return new HPoint(rect.width,rect.height);
	}
	
	/**
	 * 返回一个矩形，该矩形定义相对于 targetCoordinateSpace 对象坐标系的显示对象区域，但不包括形状上的任何笔触。（影响效率）
	 * @param targetCoordinateSpace
	 * @return
	 */
	public HRectangle getRect(HDisplayObject targetCoordinateSpace){
		if(this.getStage()==null||targetCoordinateSpace.getStage()==null){
			return null;
		}
		HRectangle rect=_getSelfRect();
		HMatrix m1;
		if(parent!=null){
			m1=parent.getGlobalMatrix();
		}
		else{
			m1=getGlobalMatrix();
		}
		HMatrix m2=targetCoordinateSpace.getGlobalMatrix();
		m2.invert();
		
		HRectangle rect2;
		rect2=m1.transleteRect(rect);
		rect2=m2.transleteRect(rect2);
		return rect2;
	}
	/**
	 * 获取外接矩形
	 * @return
	 */
	public HRectangle getRect(){
		HRectangle rect=_getSelfRect();
		HRectangle rect0=new HRectangle();
		if(mask!=null){
			rect0=mask._getSelfRect();
		}
		if(!rect0.isEmpty()){
			rect=rect.intersection(rect0);
		}
		return rect;
	}
	/**
	 * 子类实现
	 * @return
	 */
	protected HRectangle _getSelfRect(){
		return new HRectangle();
	}
	/**
	 * 本地坐标中的矩形转换成父坐标中的矩形
	 * @param rect
	 * @return
	 */
	protected HRectangle _localRect2parent(HRectangle rect){
		HMatrix m=getMatrix();
		HRectangle rect2=m.transleteRect(rect);
		return rect2;
	}
	/**
	 * 返回一个矩形，该矩形定义相对于 targetCoordinateSpace 对象坐标系的显示对象区域。（未实现）
	 * @param targetCoordinateSpace
	 * @return
	 */
	public HRectangle getBounds(HDisplayObject targetCoordinateSpace){
		return new HRectangle();
	}
	
	/**
	 * 将 point 对象从舞台（全局）坐标转换为显示对象的（本地）坐标。
	 * @param point
	 * @return
	 */
	public HPoint globalToLocal(HPoint point){
		if(getStage()==null){
			return null;
		}
		HMatrix gm=getGlobalMatrix();
		gm.invert();
		HPoint p=gm.transformPoint(point);
		return p;
	}
	private HPoint deltaGlobalToLocal(HPoint point){
		if(getStage()==null){
			return null;
		}
		HMatrix gm=getGlobalMatrix();
		gm.invert();
		HPoint p=gm.deltaTransformPoint(point);
		return p;
	}
	/**
	 * 将 point 对象从显示对象的（本地）坐标转换为舞台（全局）坐标。
	 * @param point
	 * @return
	 */
	public HPoint localToGlobal(HPoint point){
		if(getStage()==null){
			return null;
		}
		HMatrix gm=getGlobalMatrix();
		HPoint p=gm.transformPoint(point);
		return p;
	}
	private HPoint deltaLocalToGlobal(HPoint point){
		if(getStage()==null){
			return null;
		}
		HMatrix gm=getGlobalMatrix();
		HPoint p=gm.deltaTransformPoint(point);
		return p;
	}
	/**
	 * 舞台坐标到本地坐标的变换矩阵
	 * @return
	 */
	public HMatrix getGlobalMatrix(){
		HDisplayObject dis=this;
		HMatrix m=new HMatrix();
		while(dis!=null){
			HMatrix m1=dis.getMatrix();
			m1.concat(m);
			m=m1;
			if(dis instanceof HStage){
				return m;
			}
			else{
				dis=dis.parent;
			}
		}
		return null;
	}
	/**
	 * 计算显示对象，以确定它是否与 x 和 y 参数指定的点重叠或相交。 x 和 y 参数指定舞台的坐标空间中的点，而不是包含显示对象的显示对象容器中的点（除非显示对象容器是舞台）。
	 * 像素级判断。如果要提高效率，可以考虑使用几何碰撞。 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean hitTestPoint(double x,double y){
		if(getStage()==null){
			return false;
		}
		HPoint p;
		if(this instanceof HStage){
			p= new HPoint(x,y);
		}
		else{
			p= this.parent.globalToLocal(new HPoint(x,y));
		}
		int[] co={0x00000000};
		Bitmap bitmap=Bitmap.createBitmap(co,1, 1, Bitmap.Config.ARGB_8888);
		//安卓：解决mCanvas.setBitmap(mBitmap)出现 java.lang.IllegalStateException问题http://blog.sina.com.cn/s/blog_714ed4b30101g7nd.html
	    Bitmap mBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
	    
		Canvas canvas=new Canvas();
		Paint paint=new Paint();
		
		try{
			 canvas.setBitmap(mBitmap);
		    }
		catch(Exception e){
			Log.e("hanyeah_HDisplayObject:hitTestPoint() error:",e.toString());
			return false;
		}
		
		canvas.translate(-(float)p.x, -(float)p.y);
		_render(canvas,paint);
		int color=mBitmap.getPixel(0, 0);
		if(color>>24!=0){
			//非透明
			return true;
		}
		return false;
	}
	/**
	 * 计算显示对象，以确定它是否与 obj 显示对象重叠或相交。（未实现）
	 * @param obj	HDisplayObject
	 * @return
	 */
	public boolean hitTestObject(HDisplayObject obj){
		return false;
	}
	/**
	 * 获取显示对象的变换矩阵
	 * @return
	 */
	public HMatrix getMatrix(){
		HMatrix matrix=new HMatrix();
		matrix.createBox(scaleX, scaleY, rotation*Math.PI/180, x, y);
		return matrix;
	}
	/**
	 * 设置显示对象的变换矩阵。
	 * 由于渲染时不支持倾斜或剪切，不建议使用。设置不当很容易出问题；请尽量使用x、y、rotation、scaleX、scaleY属性。
	 * @param m
	 */
	public void setMatrix(HMatrix m){
		//matrix=m;
		rotation=HMatrixTransformer.getRotation(m);
		scaleX=HMatrixTransformer.getScaleX(m);
		scaleY=HMatrixTransformer.getScaleY(m);
		x=m.tx;
		y=m.ty;
	}
	/**
	 * 获取显示对象的父容器
	 * @return
	 */
	public HDisplayObjectContainer getParent(){
		return parent;
	}
	/**
	 * 获取舞台
	 * @return
	 */
	public HStage getStage(){
		HDisplayObject dis=this;
		while(dis!=null){
			if(dis instanceof HStage){
				return (HStage)dis;
			}
			dis=dis.parent;
		}
		return null;
	}
	/**
	 * @override
	 * 事件捕获阶段
	 */
	protected void _hcatch(){
		_hlisten(new HEvent(HEvent.ENTER_FRAME));
		super._hcatch();
	}
	/**
	 * 事件目标阶段还是冒泡阶段
	 */
	protected void _hlisten(HEvent e){
		super._hlisten(e);
		if(e.getBubbles()&&parent!=null){
			parent._hlisten(e);
		}
	}
	/**
	 * 渲染，只供引擎调用。请不要在自定义类中调用。
	 */
	protected void _render(Canvas canvas,Paint paint){
		if(!visible){
			return;
		}
		
		canvas.save();
		int alp=paint.getAlpha();
		
		if(mask!=null){
			canvas.clipPath(mask.getPath());
		}
		
		paint.setAlpha((int)(alp*alpha));
		canvas.translate((float)x,(float)y);
		canvas.rotate((float)rotation);
		canvas.scale((float)scaleX,(float)scaleY);
		
		_selfRender(canvas,paint);
		
		paint.setAlpha(alp);
		canvas.restore();
	}
	/**
	 * 自身的渲染，需要子类重写，只供引擎调用。请不要在自定义类中调用。
	 * @param canvas
	 * @param paint
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		
	}
	
}
