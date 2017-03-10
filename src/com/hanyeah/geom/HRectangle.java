/**
 * 
 */
package com.hanyeah.geom;

import com.hanyeah.HObject;

/**
 * HRectangle �����ǰ���λ�ã��������Ͻǵĵ� (x, y) ȷ�����Լ���Ⱥ͸߶ȶ��������
 * @author hanyeah
 *
 */
public class HRectangle extends HObject {

	public double x;
	public double y;
	public double width;
	public double height;
	/**
	 * ���캯����
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
	 * ���캯�����൱��new HRectangle(0,0,0,0)��
	 */
	public HRectangle() {
		// TODO Auto-generated constructor stub
		this(0,0,0,0);
	}
	/**
	 * ����һ���µ� HRectangle ������ x��y��width �� height ���Ե�ֵ��ԭʼ Rectangle ����Ķ�Ӧֵ��ͬ��
	 */
	public HRectangle clone(){
		return new HRectangle(x,y,width,height);
	}
	/**
	 * ȷ���ɴ� HRectangle ������ľ����������Ƿ����ָ���ĵ㡣
	 * @param x
	 * @param y
	 * @param boundFlag		�Ƿ��Ǳ߽�
	 * @return
	 */
	public boolean contains(double x,double y,boolean boundFlag){
		return boundFlag?(x>=this.x&&x<=this.x+this.width&&y>=this.y&&y<=this.y+this.height):(x>this.x&&x<this.x+this.width&&y>this.y&&y<this.y+this.height);
	}
	/**
	 * ȷ���ɴ� HRectangle ������ľ����������Ƿ����ָ���ĵ㡣
	 * @param p	
	 * @param boundFlag		�Ƿ��Ǳ߽�
	 * @return
	 */
	public boolean contains(HPoint p,boolean boundFlag){
		return contains(p.x,p.y,boundFlag);
	}
	/**
	 * ȷ���� HRectangle �������Ƿ������ rect ����ָ���� HRectangle ����
	 * @param rect
	 * @return
	 */
	public boolean containsRect(HRectangle rect){
		return contains(rect.x,rect.y,true)&&contains(rect.x+rect.width,rect.y+rect.height,true);
	}
	/**
	 * ȷ���� toCompare ������ָ���Ķ����Ƿ���ڴ� HRectangle ����
	 * @param toCompare
	 * @return
	 */
	public boolean equals(HRectangle toCompare){
		return toCompare.x==this.x&&toCompare.y==this.y&&toCompare.width==this.width&&toCompare.height==this.height;
	}
	/**
	 * ��ָ�������� HRectangle ����Ĵ�С��������Ϊ��λ����
	 * @param dx
	 * @param dy
	 */
	public void inflate(double dx,double dy){
		this.width+=dx;
		this.height+=dy;
	}
	/**
	 * ��ָ�������� HRectangle ����Ĵ�С��������Ϊ��λ����
	 * @param p
	 */
	public void inflate(HPoint p){
		this.width+=p.x;
		this.height+=p.y;
	}
	/**
	 * ����� toIntersect ������ָ���� HRectangle ������� HRectangle �����ཻ���򷵻ؽ���������Ϊ HRectangle ���� ������β��ཻ����˷�������һ���յ� HRectangle ��������������Ϊ 0��
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
	 * ȷ���� toIntersect ������ָ���Ķ����Ƿ���� HRectangle �����ཻ��
	 * @param toIntersect
	 * @return
	 */
	public boolean intersects(HRectangle toIntersect){
		return Math.min(x+width, toIntersect.x+toIntersect.width)>Math.max(x, toIntersect.x)&&Math.min(y+height, toIntersect.y+toIntersect.height)>Math.max(y, toIntersect.y);
	}
	/**
	 * ȷ���� HRectangle �����Ƿ�Ϊ�ա���ȫ�����Զ�Ϊ0��
	 * @return
	 */
	public boolean isEmpty(){
		return x==0&&y==0&&width==0&&height==0;
	}
	/**
	 * ��ָ�������� HRectangle �����λ�ã��������Ͻ�ȷ������
	 * @param dx
	 * @param dy
	 */
	public void offset(double dx,double dy){
		x+=dx;
		y+=dy;
	}
	/**
	 * ��ָ�������� HRectangle �����λ�ã��������Ͻ�ȷ������
	 * @param p
	 */
	public void offset(HPoint p){
		x+=p.x;
		y+=p.y;
	}
	/**
	 * �� Rectangle �����������������Ϊ 0��
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
	 * ͨ�������������֮���ˮƽ�ʹ�ֱ�ռ䣬�����������������һ���Դ���һ���µ� HRectangle ����
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
