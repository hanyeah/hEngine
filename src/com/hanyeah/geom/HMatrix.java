/**
 * 
 */
package com.hanyeah.geom;

import android.util.Log;

import com.hanyeah.HMath;
import com.hanyeah.HObject;

/**
 * ת�����󡣲μ�AS3.0�е�Matrix��
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
	 * ʹ��ָ�����������µ� HMatrix ����
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
	 * ʹ��Ĭ�ϲ��� (1,0,0,1,0,0) �����µ� HMatrix ����
	 */
	public HMatrix(){
		this(1,0,0,1,0,0);
	}
	/**
	 * ����һ���µ� HMatrix �������Ǵ˾���Ŀ�¡������������������ȫ��ͬ�ĸ�����
	 * @return
	 */
	public HMatrix clone(){
		return new HMatrix(a,b,c,d,tx,ty);
	}
	/**
	 * ��ĳ�������뵱ǰ�������ӣ��Ӷ�������������ļ���Ч����Ч�ؽ����һ�𡣣�this*m��
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
	 * ���ؽ� HMatrix �����ʾ�ļ���ת��Ӧ����ָ�����������Ľ���� 
	 * @param point
	 * @return
	 */
	public HPoint transformPoint(HPoint point){
		return new HPoint(a*point.x+b*point.y+tx,c*point.x+d*point.y+ty);
	}
	/**
	 * �������Ԥת������ռ��еĵ㣬��˷������ط���ת����õ�����ꡣ ��ʹ�� transformPoint() ����Ӧ�õı�׼ת����ͬ��deltaTransformPoint() ������ת��������ת������ tx �� ty�� 
	 * @param point
	 * @return
	 */
	public HPoint deltaTransformPoint(HPoint point){
		return new HPoint(a*point.x+b*point.y,c*point.x+d*point.y);
	}
	/**
	 * �����������š���ת��ת���Ĳ����� ��Ӧ���ھ���ʱ���÷����������Щ�������þ����ֵ��
	 * @param scaleX
	 * @param scaleY
	 * @param rotation	����
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
	 * ���� identity() ���������ɵľ�������������ԣ�a=1��b=0��c=0��d=1��tx=0 �� ty=0��
	 */
	public void identity(){
		a=1;b=0;c=0;d=1;tx=0;ty=0;
	}
	/**
	 * �� HMatrix ����Ӧ����תת���� 
	 * @param angle		�Ի���Ϊ��λ����ת�Ƕȡ�
	 */
	public void rotate(double angle){
		//flash���Matrix����ʱ����ת��rotation��˳ʱ����ת����������Ƕ�ȡ��ֵ
		double sin=Math.sin(-angle);
		double cos=Math.cos(-angle);
		HMatrix m=new HMatrix(cos,sin,-sin,cos,0,0);
		concat(m);
	}
	/**
	 * �Ծ���Ӧ������ת���� x ����� sx��y ����� sy�� 
	 * @param sx
	 * @param sy
	 */
	public void scale(double sx,double sy){
		HMatrix m=new HMatrix(sx,0,0,sy,0,0);
		concat(m);
	}
	/**
	 * �� x �� y ��ƽ�ƾ����� dx �� dy ����ָ����
	 * @param dx
	 * @param dy
	 */
	public void translate(double dx,double dy){
		tx+=dx;
		ty+=dy;
	}
	/**
	 * ִ��ԭʼ�������ת����
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
	 * ���ؽ� HMatrix �����ʾ�ļ���ת��Ӧ����ָ�������������Ľ����
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
