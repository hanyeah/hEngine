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
 * ��ʾ����Ļ��ࡣ������ʵ������
 * @author hanyeah
 */
public abstract class HDisplayObject extends HEventDispatcher {
	
	/**ָʾ HDisplayObject ʵ������ڸ��� HDisplayObjectContainer ��������� x ���ꡣ**/
	public double x=0;
	
	/**ָʾ DisplayObject ʵ������ڸ��� DisplayObjectContainer ��������� y ���ꡣ**/
	public double y=0;
	
	/**��ʾ�����Ƿ�ɼ���**/
	public boolean visible=true;
	
	/**͸����**/
	public double alpha=1.0;
	
	/**ˮƽ���ű������ٷֱȣ���**/
	public double scaleX=1.0;
	
	/**��ֱ���ű������ٷֱȣ���**/
	public double scaleY=1.0;
	
	/**��ת�Ƕȣ��ȣ�**/
	public double rotation=0;
	
	/**ʵ����**/
	public String name;
	
	/**����(����ʹ�ü򵥵�ͼ����Ϊ���֣��������׳�����)**/
	private HGraphics mask=null;
	
	/**������**/
	protected HDisplayObjectContainer parent;
	
	
	/**
	 * ���캯����������ʵ������
	 */
	public HDisplayObject() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ��ȡ��ʾ����Ŀ�ȣ�������Ϊ��λ����Ӱ��Ч�ʣ�
	 * @return
	 */
	public double getWidth(){
		return getSize().x;
	}
	/**
	 * ������ʾ����Ŀ�ȣ�������Ϊ��λ����Ӱ��Ч�ʣ�
	 * @param value
	 */
	public void setWidth(double value){
		double w=getWidth();
		if(w!=0){
			scaleX*=value/w;
		}
	}
	/**
	 * ��ȡ��ʾ����ĸ߶ȣ�������Ϊ��λ����Ӱ��Ч�ʣ�
	 * @return
	 */
	public double getHeight(){
		return getSize().y;
	}
	/**
	 * ������ʾ����ĸ߶ȣ�������Ϊ��λ����Ӱ��Ч�ʣ�
	 * @param value
	 */
	public void setHeight(double value){
		double h=getHeight();
		if(h!=0){
			scaleY*=value/h;
		}
	}
	/**
	 * ��ȡ�ߴ磬һ���Ի�ȡ��ߣ�Ӱ��Ч�ʣ�
	 * @return
	 */
	public HPoint getSize(){
		HRectangle rect=_getSelfRect();
		return new HPoint(rect.width,rect.height);
	}
	
	/**
	 * ����һ�����Σ��þ��ζ�������� targetCoordinateSpace ��������ϵ����ʾ�������򣬵���������״�ϵ��καʴ�����Ӱ��Ч�ʣ�
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
	 * ��ȡ��Ӿ���
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
	 * ����ʵ��
	 * @return
	 */
	protected HRectangle _getSelfRect(){
		return new HRectangle();
	}
	/**
	 * ���������еľ���ת���ɸ������еľ���
	 * @param rect
	 * @return
	 */
	protected HRectangle _localRect2parent(HRectangle rect){
		HMatrix m=getMatrix();
		HRectangle rect2=m.transleteRect(rect);
		return rect2;
	}
	/**
	 * ����һ�����Σ��þ��ζ�������� targetCoordinateSpace ��������ϵ����ʾ�������򡣣�δʵ�֣�
	 * @param targetCoordinateSpace
	 * @return
	 */
	public HRectangle getBounds(HDisplayObject targetCoordinateSpace){
		return new HRectangle();
	}
	
	/**
	 * �� point �������̨��ȫ�֣�����ת��Ϊ��ʾ����ģ����أ����ꡣ
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
	 * �� point �������ʾ����ģ����أ�����ת��Ϊ��̨��ȫ�֣����ꡣ
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
	 * ��̨���굽��������ı任����
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
	 * ������ʾ������ȷ�����Ƿ��� x �� y ����ָ���ĵ��ص����ཻ�� x �� y ����ָ����̨������ռ��еĵ㣬�����ǰ�����ʾ�������ʾ���������еĵ㣨������ʾ������������̨����
	 * ���ؼ��жϡ����Ҫ���Ч�ʣ����Կ���ʹ�ü�����ײ�� 
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
		//��׿�����mCanvas.setBitmap(mBitmap)���� java.lang.IllegalStateException����http://blog.sina.com.cn/s/blog_714ed4b30101g7nd.html
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
			//��͸��
			return true;
		}
		return false;
	}
	/**
	 * ������ʾ������ȷ�����Ƿ��� obj ��ʾ�����ص����ཻ����δʵ�֣�
	 * @param obj	HDisplayObject
	 * @return
	 */
	public boolean hitTestObject(HDisplayObject obj){
		return false;
	}
	/**
	 * ��ȡ��ʾ����ı任����
	 * @return
	 */
	public HMatrix getMatrix(){
		HMatrix matrix=new HMatrix();
		matrix.createBox(scaleX, scaleY, rotation*Math.PI/180, x, y);
		return matrix;
	}
	/**
	 * ������ʾ����ı任����
	 * ������Ⱦʱ��֧����б����У�������ʹ�á����ò��������׳����⣻�뾡��ʹ��x��y��rotation��scaleX��scaleY���ԡ�
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
	 * ��ȡ��ʾ����ĸ�����
	 * @return
	 */
	public HDisplayObjectContainer getParent(){
		return parent;
	}
	/**
	 * ��ȡ��̨
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
	 * �¼�����׶�
	 */
	protected void _hcatch(){
		_hlisten(new HEvent(HEvent.ENTER_FRAME));
		super._hcatch();
	}
	/**
	 * �¼�Ŀ��׶λ���ð�ݽ׶�
	 */
	protected void _hlisten(HEvent e){
		super._hlisten(e);
		if(e.getBubbles()&&parent!=null){
			parent._hlisten(e);
		}
	}
	/**
	 * ��Ⱦ��ֻ��������á��벻Ҫ���Զ������е��á�
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
	 * �������Ⱦ����Ҫ������д��ֻ��������á��벻Ҫ���Զ������е��á�
	 * @param canvas
	 * @param paint
	 */
	protected void _selfRender(Canvas canvas,Paint paint){
		
	}
	
}
