/**
 * 
 */
package com.hanyeah.geom;

import com.hanyeah.HObject;

/**
 * Point �����ʾ��ά����ϵͳ�е�ĳ��λ�ã����� x ��ʾˮƽ�ᣬy ��ʾ��ֱ�ᡣ
 * @author hanyeah
 *
 */
public class HPoint extends HObject {
	/**x������**/
	public double x;
	/**y������**/
	public double y;
	
	/**
	 * ���캯��
	 * @param x		x������
	 * @param y		y������
	 */
	public HPoint(double x,double y) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
	}
	/**
	 * ���캯��,Ĭ��ʹ��(0,0)�㡣
	 */
	public HPoint(){
		this(0,0);
	}
	/**
	 * �� (0,0) ���˵���߶γ��ȡ�
	 * @return
	 */
	public double getLength(){
		return Math.sqrt(x*x+y*y);
	}
	/**
	 * ����һ�����������ӵ��˵�������Դ���һ���µ㡣
	 * @param p
	 * @return
	 */
	public HPoint add(HPoint p){
		return new HPoint(x+p.x,y+p.y);
	}

	/**
	 * ������ HPoint ����ĸ�����
	 * @return
	 */
	public HPoint clone(){
		return new HPoint(x,y);
	}
	/**
	 * [��̬] ���� pt1 �� pt2 ֮��ľ��롣
	 * @param pt1
	 * @param pt2
	 * @return
	 */
	public static double distance(HPoint pt1,HPoint pt2){
		double dx=pt1.x-pt2.x;
		double dy=pt1.y-pt2.y;
		return Math.sqrt(dx*dx+dy*dy);
	}
	/**
	 * ȷ���������Ƿ���ͬ�� 
	 * @param toCompare
	 * @return
	 */
	public boolean equals(HPoint toCompare){
		return x==toCompare.x&&y==toCompare.y;
	}
	/**
	 * ȷ������ָ����֮��ĵ㡣 ���� f ȷ���µ��ڲ������ڲ��� pt1 �� pt2 ָ���������˵�������λ�á� ���� f ��ֵԽ�ӽ� 1.0�����ڲ���Խ�ӽ���һ���㣨���� pt1���� ���� f ��ֵԽ�ӽ� 0�����ڲ���Խ�ӽ��ڶ����㣨���� pt2���� 
	 * @param pt1
	 * @param pt2
	 * @param f
	 * @return
	 */
	public HPoint interpolate(HPoint pt1,HPoint pt2,double f){
		return new HPoint(pt2.x+(pt1.x-pt2.x)*f,pt2.y+(pt1.y-pt2.y)*f );
	}
	/**
	 * �� (0,0) �͵�ǰ��֮����߶�����Ϊ�趨�ĳ��ȡ������ǰ��Ϊ (0,0) ,��ʲô��������
	 * @param thickness
	 */
	public void normalize(double thickness){
		double l=Math.sqrt(x*x+y*y);
		if(l==0)return;
		double f=thickness/l;
		x*=f;
		y*=f;
	}
	/**
	 * ��ָ����ƫ�� HPoint ����
	 * @param dx
	 * @param dy
	 */
	public void offset(double dx,double dy){
		x+=dx;
		y+=dy;
	}
	/**
	 * ��ָ����ƫ�� HPoint ����
	 * @param pt
	 */
	public void offset(HPoint pt){
		x+=pt.x;
		y+=pt.y;
	}
	/**
	 * [��̬] ��һ�Լ�����ת��Ϊ�ѿ��������ꡣ
	 * @param len
	 * @param angle
	 * @return
	 */
	public static HPoint polar(double len,double angle){
		return new HPoint(len*Math.cos(angle),len*Math.sin(angle));
	}
	/**
	 * �Ӵ˵�������м�ȥ��һ����������Դ���һ���µ㡣
	 * @param v
	 * @return
	 */
	public HPoint subtract(HPoint v){
		return new HPoint(x-v.x,y-v.y);
	}
	/**
	 * @Override
	 * @return
	 */
	public String toString(){
		return "( x="+x+", y="+y+" )";
	}
}
