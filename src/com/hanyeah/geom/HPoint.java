/**
 * 
 */
package com.hanyeah.geom;

import com.hanyeah.HObject;

/**
 * Point 对象表示二维坐标系统中的某个位置，其中 x 表示水平轴，y 表示垂直轴。
 * @author hanyeah
 *
 */
public class HPoint extends HObject {
	/**x轴坐标**/
	public double x;
	/**y轴坐标**/
	public double y;
	
	/**
	 * 构造函数
	 * @param x		x轴坐标
	 * @param y		y轴坐标
	 */
	public HPoint(double x,double y) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
	}
	/**
	 * 构造函数,默认使用(0,0)点。
	 */
	public HPoint(){
		this(0,0);
	}
	/**
	 * 从 (0,0) 到此点的线段长度。
	 * @return
	 */
	public double getLength(){
		return Math.sqrt(x*x+y*y);
	}
	/**
	 * 将另一个点的坐标添加到此点的坐标以创建一个新点。
	 * @param p
	 * @return
	 */
	public HPoint add(HPoint p){
		return new HPoint(x+p.x,y+p.y);
	}

	/**
	 * 创建此 HPoint 对象的副本。
	 * @return
	 */
	public HPoint clone(){
		return new HPoint(x,y);
	}
	/**
	 * [静态] 返回 pt1 和 pt2 之间的距离。
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
	 * 确定两个点是否相同。 
	 * @param toCompare
	 * @return
	 */
	public boolean equals(HPoint toCompare){
		return x==toCompare.x&&y==toCompare.y;
	}
	/**
	 * 确定两个指定点之间的点。 参数 f 确定新的内插点相对于参数 pt1 和 pt2 指定的两个端点所处的位置。 参数 f 的值越接近 1.0，则内插点就越接近第一个点（参数 pt1）。 参数 f 的值越接近 0，则内插点就越接近第二个点（参数 pt2）。 
	 * @param pt1
	 * @param pt2
	 * @param f
	 * @return
	 */
	public HPoint interpolate(HPoint pt1,HPoint pt2,double f){
		return new HPoint(pt2.x+(pt1.x-pt2.x)*f,pt2.y+(pt1.y-pt2.y)*f );
	}
	/**
	 * 将 (0,0) 和当前点之间的线段缩放为设定的长度。如果当前点为 (0,0) ,则什么都不做。
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
	 * 按指定量偏移 HPoint 对象。
	 * @param dx
	 * @param dy
	 */
	public void offset(double dx,double dy){
		x+=dx;
		y+=dy;
	}
	/**
	 * 按指定量偏移 HPoint 对象。
	 * @param pt
	 */
	public void offset(HPoint pt){
		x+=pt.x;
		y+=pt.y;
	}
	/**
	 * [静态] 将一对极坐标转换为笛卡尔点坐标。
	 * @param len
	 * @param angle
	 * @return
	 */
	public static HPoint polar(double len,double angle){
		return new HPoint(len*Math.cos(angle),len*Math.sin(angle));
	}
	/**
	 * 从此点的坐标中减去另一个点的坐标以创建一个新点。
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
