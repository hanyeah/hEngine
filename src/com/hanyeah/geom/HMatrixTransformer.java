/**
 * 
 */
package com.hanyeah.geom;

import com.hanyeah.HObject;

/**
 * HMatrixTransformer ������޸�ת�����󵥸����Եķ�����ˮƽ�ʹ�ֱ���š�ˮƽ�ʹ�ֱ��б���Լ���ת�� ���໹����Χ�Ƹ������ε�����ǳ����� (0, 0) ��ת�ķ�����
 * @author hanyeah
 * @date 2015-12-3
 */
public class HMatrixTransformer extends HObject {

	/**
	 * ������ʵ������
	 */
	private HMatrixTransformer(){
		// TODO Auto-generated constructor stub
		
	}
	/**
	 * [��̬] ��������е���ת�Ƕȣ��Զ�Ϊ��λ���� ���ˮƽ�ʹ�ֱ��б����ȣ���ʹ�ô�ֱ��бֵ��
	 * @param m
	 * @return
	 */
	public static double getRotation(HMatrix m){
		return getRotationRadians(m)*(180/Math.PI);
	}
	/**
	 * [��̬] ��������е���ת�Ƕȣ��Ի���Ϊ��λ���� ���ˮƽ�ʹ�ֱ��б����ȣ���ʹ�ô�ֱ��бֵ�� 
	 * @param m
	 * @return
	 */
	public static double getRotationRadians(HMatrix m){
		return getSkewYRadians(m);
	}
	/**
	 * [��̬] ��������е�ˮƽ���š�
	 * @param m
	 * @return
	 */
	public static double getScaleX(HMatrix m){
		return Math.sqrt(m.a*m.a+m.b*m.b);
	}
	/**
	 * [��̬] ��������еĴ�ֱ���š�
	 * @param m
	 * @return
	 */
	public static double getScaleY(HMatrix m){
		return Math.sqrt(m.c*m.c+m.d*m.d);
	}
	/**
	 * [��̬] ��������е�ˮƽ��б�Ƕȣ��Զ�Ϊ��λ����
	 * @param m
	 * @return
	 */
	public static double getSkewX(HMatrix m){
		return Math.atan2(-m.c, m.d) * (180/Math.PI);
	}
	/**
	 * [��̬] ��������е�ˮƽ��б�Ƕȣ��Ի���Ϊ��λ����
	 * @param m
	 * @return
	 */
	public static double getSkewXRadians(HMatrix m){
		return Math.atan2(-m.c, m.d);
	}
	/**
	 * [��̬] ��������еĴ�ֱ��б�Ƕȣ��Զ�Ϊ��λ����
	 * @param m
	 * @return
	 */
	public static double getSkewY(HMatrix m){
		return Math.atan2(m.b, m.a) * (180/Math.PI);
	}
	/**
	 * [��̬] ��������еĴ�ֱ��б�Ƕȣ��Ի���Ϊ��λ����
	 * @param m
	 * @return
	 */
	public static double getSkewYRadians(HMatrix m){
		return Math.atan2(m.b, m.a);
	}
	/**
	 * [��̬] ������Ҫ�ƶ����󣬽��ڲ������ⲿ����롣 �˹��ܿ������ڽ�ת����ӰƬ�����еĵ����丸���еĵ�ƥ�䡣 
	 * @param m
	 * @param internalPoint
	 * @param externalPoint
	 */
	public static void matchInternalPointWithExternal(HMatrix m,HPoint internalPoint,HPoint externalPoint){
		HPoint internalPointTransformed = m.transformPoint(internalPoint);
		double dx = externalPoint.x - internalPointTransformed.x;
		double dy = externalPoint.y - internalPointTransformed.y;	
		m.tx += dx;
		m.ty += dy;
	}
	/**
	 * [��̬] Χ�ƾ���ת���ռ����ⶨ��ĵ���ת���� �˹��ܿ������ڽ�ӰƬ����Χ���丸���еı��ε������ת��
	 * @param m
	 * @param x
	 * @param y
	 * @param angleDegrees
	 */
	public static void rotateAroundExternalPoint(HMatrix m,double x,double y,double angleDegrees){
		m.tx -= x;
		m.ty -= y;
		m.rotate(angleDegrees*(Math.PI/180));
		m.tx += x;
		m.ty += y;
	}
	/**
	 * [��̬] Χ�ƾ���ת���ռ��ж���ĵ���ת���� �˹��ܿ������ڽ�ӰƬ����Χ�����ڲ��ı��ε������ת��
	 * @param m
	 * @param x
	 * @param y
	 * @param angleDegrees
	 */
	public static void rotateAroundInternalPoint(HMatrix m,double x,double y,double angleDegrees){
		HPoint point = new HPoint(x, y);
		point = m.transformPoint(point);
		m.tx -= point.x;
		m.ty -= point.y;
		m.rotate(angleDegrees*(Math.PI/180));
		m.tx += point.x;
		m.ty += point.y;
	}
	/**
	 * [��̬] ���ľ����е���ת�Ƕȡ� ���ˮƽ�ʹ�ֱ��б����ȣ���ֱ��б����Ϊ��תֵ��ˮƽ��б�����Ͼ���ת������ת֮��Ĳ
	 * @param m
	 * @param rotation
	 */
	public static void setRotation(HMatrix m,double rotation){
		setRotationRadians(m, rotation*(Math.PI/180));
	}
	/**
	 * [��̬] ���ľ����е���ת�Ƕȡ� ���ˮƽ�ʹ�ֱ��б����ȣ���ֱ��б����Ϊ��תֵ��ˮƽ��б�����Ͼ���ת������ת֮��Ĳ
	 * @param m
	 * @param rotation
	 */
	public static void setRotationRadians(HMatrix m,double rotation){
		double oldRotation = getRotationRadians(m);
		double oldSkewX = getSkewXRadians(m);
		setSkewXRadians(m, oldSkewX + rotation-oldRotation);
		setSkewYRadians(m, rotation);
	}
	/**
	 * [��̬] ���ľ����е�ˮƽ���š�
	 * @param m
	 * @param scaleX
	 */
	public static void setScaleX(HMatrix m,double scaleX){
		double oldValue=getScaleX(m);
		if(oldValue!=0){
			double ratio=scaleX/oldValue;
			m.a*=ratio;
			m.b*=ratio;
		}
		else{
			double skewYRad=getSkewYRadians(m);
			m.a = Math.cos(skewYRad) * scaleX;
			m.b = Math.sin(skewYRad) * scaleX;
		}
	}
	/**
	 * [��̬] ���ľ����еĴ�ֱ���š�
	 * @param m
	 * @param scaleX
	 */
	public static void setScaleY(HMatrix m,double scaleY){
		double oldValue=getScaleY(m);
		if(oldValue!=0){
			double ratio=scaleY/oldValue;
			m.c*=ratio;
			m.d*=ratio;
		}
		else{
			double skewXRad=getSkewXRadians(m);
			m.c = -Math.sin(skewXRad) * scaleY;
			m.d = Math.cos(skewXRad) * scaleY;
		}
	}
	/**
	 * [��̬] ���ľ����е�ˮƽ��б��
	 * @param m
	 * @param skewX
	 */
	public static void setSkewX(HMatrix m,double skewX){
		setSkewXRadians(m, skewX*(Math.PI/180));
	}
	/**
	 * [��̬] ���ľ����е�ˮƽ��б
	 * @param m
	 * @param skewX
	 */
	public static void setSkewXRadians(HMatrix m,double skewX){
		double scaleY=getScaleY(m);
		m.c = -scaleY * Math.sin(skewX);
		m.d =  scaleY * Math.cos(skewX);
	}
	/**
	 * [��̬] ���ľ����еĴ�ֱ��б��
	 * @param m
	 * @param skewY
	 */
	public static void setSkewY(HMatrix m,double skewY){
		setSkewYRadians(m, skewY*(Math.PI/180));
	}
	/**
	 * [��̬] ���ľ����еĴ�ֱ��б
	 * @param m
	 * @param skewY
	 */
	public static void setSkewYRadians(HMatrix m,double skewY){
		double scaleX = getScaleX(m);
		m.a = scaleX * Math.cos(skewY);
		m.b = scaleX * Math.sin(skewY);
	}
}
