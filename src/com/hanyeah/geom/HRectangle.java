/**
 * 
 */
package com.hanyeah.geom;

import com.hanyeah.HObject;

/**
 * HRectangle 对象是按其位置（由它左上角的点 (x, y) 确定）以及宽度和高度定义的区域。
 * @author hanyeah
 *
 */
public class HRectangle extends HObject {

	public double x;
	public double y;
	public double width;
	public double height;
	/**
	 * 构造函数。
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public HRectangle(double x,double y,double width,double height) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	/**
	 * 构造函数，相当于new HRectangle(0,0,0,0)。
	 */
	public HRectangle() {
		// TODO Auto-generated constructor stub
		this(0,0,0,0);
	}
	/**
	 * 返回一个新的 HRectangle 对象，其 x、y、width 和 height 属性的值与原始 Rectangle 对象的对应值相同。
	 */
	public HRectangle clone(){
		return new HRectangle(x,y,width,height);
	}
	/**
	 * 确定由此 HRectangle 对象定义的矩形区域内是否包含指定的点。
	 * @param x
	 * @param y
	 * @param boundFlag		是否考虑边界
	 * @return
	 */
	public boolean contains(double x,double y,boolean boundFlag){
		return boundFlag?(x>=this.x&&x<=this.x+this.width&&y>=this.y&&y<=this.y+this.height):(x>this.x&&x<this.x+this.width&&y>this.y&&y<this.y+this.height);
	}
	/**
	 * 确定由此 HRectangle 对象定义的矩形区域内是否包含指定的点。
	 * @param p	
	 * @param boundFlag		是否考虑边界
	 * @return
	 */
	public boolean contains(HPoint p,boolean boundFlag){
		return contains(p.x,p.y,boundFlag);
	}
	/**
	 * 确定此 HRectangle 对象内是否包含由 rect 参数指定的 HRectangle 对象。
	 * @param rect
	 * @return
	 */
	public boolean containsRect(HRectangle rect){
		return contains(rect.x,rect.y,true)&&contains(rect.x+rect.width,rect.y+rect.height,true);
	}
	/**
	 * 确定在 toCompare 参数中指定的对象是否等于此 HRectangle 对象。
	 * @param toCompare
	 * @return
	 */
	public boolean equals(HRectangle toCompare){
		return toCompare.x==this.x&&toCompare.y==this.y&&toCompare.width==this.width&&toCompare.height==this.height;
	}
	/**
	 * 按指定量增加 HRectangle 对象的大小（以像素为单位）。
	 * @param dx
	 * @param dy
	 */
	public void inflate(double dx,double dy){
		this.width+=dx;
		this.height+=dy;
	}
	/**
	 * 按指定量增加 HRectangle 对象的大小（以像素为单位）。
	 * @param p
	 */
	public void inflate(HPoint p){
		this.width+=p.x;
		this.height+=p.y;
	}
	/**
	 * 如果在 toIntersect 参数中指定的 HRectangle 对象与此 HRectangle 对象相交，则返回交集区域作为 HRectangle 对象。 如果矩形不相交，则此方法返回一个空的 HRectangle 对象，其属性设置为 0。
	 * @param toIntersect
	 * @return
	 */
	public HRectangle intersection(HRectangle toIntersect){
		HRectangle rect=new HRectangle();
		rect.x=Math.max(x, toIntersect.x);
		rect.y=Math.max(y, toIntersect.y);
		rect.width=Math.min(x+width, toIntersect.x+toIntersect.width)-rect.x;
		rect.height=Math.min(y+height, toIntersect.y+toIntersect.height)-rect.y;
		if(rect.width<=0||rect.height<=0){
			new HRectangle();
		}
		return rect;
	}
	/**
	 * 确定在 toIntersect 参数中指定的对象是否与此 HRectangle 对象相交。
	 * @param toIntersect
	 * @return
	 */
	public boolean intersects(HRectangle toIntersect){
		return Math.min(x+width, toIntersect.x+toIntersect.width)>Math.max(x, toIntersect.x)&&Math.min(y+height, toIntersect.y+toIntersect.height)>Math.max(y, toIntersect.y);
	}
	/**
	 * 确定此 HRectangle 对象是否为空。（全部属性都为0）
	 * @return
	 */
	public boolean isEmpty(){
		return x==0&&y==0&&width==0&&height==0;
	}
	/**
	 * 按指定量调整 HRectangle 对象的位置（由其左上角确定）。
	 * @param dx
	 * @param dy
	 */
	public void offset(double dx,double dy){
		x+=dx;
		y+=dy;
	}
	/**
	 * 按指定量调整 HRectangle 对象的位置（由其左上角确定）。
	 * @param p
	 */
	public void offset(HPoint p){
		x+=p.x;
		y+=p.y;
	}
	/**
	 * 将 Rectangle 对象的所有属性设置为 0。
	 */
	public void setEmpty(){
		x=y=width=height=0;
	}
	/**
	 * @return		String
	 */
	public String toString(){
		return "(x="+x+", y="+y+", w="+width+", h="+height+")";
	}
	/**
	 * 通过填充两个矩形之间的水平和垂直空间，将这两个矩形组合在一起以创建一个新的 HRectangle 对象。
	 * @param toUnion
	 * @return
	 */
	public HRectangle union(HRectangle toUnion){
		HRectangle rect=new HRectangle();
		rect.x=Math.min(x, toUnion.x);
		rect.y=Math.min(y, toUnion.y);
		rect.width=Math.max(x+width, toUnion.x+toUnion.width)-rect.x;
		rect.height=Math.max(y+height, toUnion.y+toUnion.height)-rect.y;
		return rect;
	}
}
