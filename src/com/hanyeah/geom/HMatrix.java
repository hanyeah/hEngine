/**
 * 
 */
package com.hanyeah.geom;

import android.util.Log;

import com.hanyeah.HMath;
import com.hanyeah.HObject;

/**
 * 转换矩阵。参见AS3.0中的Matrix。
 * @author hanyeah
 *
 */
public class HMatrix extends HObject {
	
	public double a;
	public double b;
	public double c;
	public double d;
	public double tx;
	public double ty;
	/**
	 * 使用指定参数创建新的 HMatrix 对象。
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param tx
	 * @param ty
	 */
	public HMatrix(double a,double b,double c,double d,double tx,double ty) {
		// TODO Auto-generated constructor stub
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
		this.tx=tx;
		this.ty=ty;
	}
	/**
	 * 使用默认参数 (1,0,0,1,0,0) 创建新的 HMatrix 对象。
	 */
	public HMatrix(){
		this(1,0,0,1,0,0);
	}
	/**
	 * 返回一个新的 HMatrix 对象，它是此矩阵的克隆，带有与所含对象完全相同的副本。
	 * @return
	 */
	public HMatrix clone(){
		return new HMatrix(a,b,c,d,tx,ty);
	}
	/**
	 * 将某个矩阵与当前矩阵连接，从而将这两个矩阵的几何效果有效地结合在一起。（this*m）
	 * @param m
	 */
	public void concat(HMatrix m){
		double a0=a*m.a+b*m.c;
		double b0=a*m.b+b*m.d;
		double c0=c*m.a+d*m.c;
		double d0=c*m.b+d*m.d;
		double tx0=a*m.tx+b*m.ty+tx;
		double ty0=c*m.tx+d*m.ty+ty;
		a=a0;
		b=b0;
		c=c0;
		d=d0;
		tx=tx0;
		ty=ty0;
	}
	/**
	 * 返回将 HMatrix 对象表示的几何转换应用于指定点所产生的结果。 
	 * @param point
	 * @return
	 */
	public HPoint transformPoint(HPoint point){
		return new HPoint(a*point.x+b*point.y+tx,c*point.x+d*point.y+ty);
	}
	/**
	 * 如果给定预转换坐标空间中的点，则此方法返回发生转换后该点的坐标。 与使用 transformPoint() 方法应用的标准转换不同，deltaTransformPoint() 方法的转换不考虑转换参数 tx 和 ty。 
	 * @param point
	 * @return
	 */
	public HPoint deltaTransformPoint(HPoint point){
		return new HPoint(a*point.x+b*point.y,c*point.x+d*point.y);
	}
	/**
	 * 包括用于缩放、旋转和转换的参数。 当应用于矩阵时，该方法会基于这些参数设置矩阵的值。
	 * @param scaleX
	 * @param scaleY
	 * @param rotation	弧度
	 * @param tx
	 * @param ty
	 */
	public void createBox(double scaleX,double scaleY,double rotation,double tx,double ty){
		identity();
		rotate(rotation);
		scale(scaleX,scaleY);
		translate(tx,ty);
	}
	/**
	 * 调用 identity() 方法后，生成的矩阵具有以下属性：a=1、b=0、c=0、d=1、tx=0 和 ty=0。
	 */
	public void identity(){
		a=1;b=0;c=0;d=1;tx=0;ty=0;
	}
	/**
	 * 对 HMatrix 对象应用旋转转换。 
	 * @param angle		以弧度为单位的旋转角度。
	 */
	public void rotate(double angle){
		//flash里边Matrix是逆时针旋转，rotation是顺时针旋转，所以这里角度取负值
		double sin=Math.sin(-angle);
		double cos=Math.cos(-angle);
		HMatrix m=new HMatrix(cos,sin,-sin,cos,0,0);
		concat(m);
	}
	/**
	 * 对矩阵应用缩放转换。 x 轴乘以 sx，y 轴乘以 sy。 
	 * @param sx
	 * @param sy
	 */
	public void scale(double sx,double sy){
		HMatrix m=new HMatrix(sx,0,0,sy,0,0);
		concat(m);
	}
	/**
	 * 沿 x 和 y 轴平移矩阵，由 dx 和 dy 参数指定。
	 * @param dx
	 * @param dy
	 */
	public void translate(double dx,double dy){
		tx+=dx;
		ty+=dy;
	}
	/**
	 * 执行原始矩阵的逆转换。
	 */
	public void invert(){
		double c0=c/(b*c-a*d);
		double a0=d/(a*d-b*c);
		double d0=a/(a*d-b*c);
		double b0=b/(b*c-a*d);
		double tx0=(b*ty-d*tx)/(a*d-b*c);
		double ty0=(a*ty-c*tx)/(b*c-a*d);
		a=a0;
		b=b0;
		c=c0;
		d=d0;
		tx=tx0;
		ty=ty0;
	}
	/**
	 * 返回将 HMatrix 对象表示的几何转换应用于指定矩形所产生的结果。
	 * @param rect
	 * @return
	 */
	public HRectangle transleteRect(HRectangle rect){
		HPoint ltp=new HPoint(rect.x,rect.y);
		HPoint rtp=new HPoint(rect.x+rect.width,rect.y);
		HPoint lbp=new HPoint(rect.x,rect.y+rect.height);
		HPoint rbp=new HPoint(rect.x+rect.width,rect.y+rect.height);
		
		ltp=transformPoint(ltp);
		rtp=transformPoint(rtp);
		lbp=transformPoint(lbp);
		rbp=transformPoint(rbp);
		
		HRectangle rect2=new HRectangle();
		rect2.x=HMath.min(ltp.x, rtp.x, lbp.x,rbp.x);
		rect2.y=HMath.min(ltp.y, rtp.y, lbp.y,rbp.y);
		rect2.width=HMath.max(ltp.x, rtp.x, lbp.x,rbp.x)-rect2.x;
		rect2.height=HMath.max(ltp.y, rtp.y, lbp.y,rbp.y)-rect2.y;
		return rect2;
	}
	/**
	 *
	 */
	public String toString(){
		return "( a="+a+", b="+b+" ,c="+c+" ,d="+d+" ,tx="+tx+" ,ty="+ty+" )";
	}
}
