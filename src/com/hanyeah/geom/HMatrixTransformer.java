/**
 * 
 */
package com.hanyeah.geom;

import com.hanyeah.HObject;

/**
 * HMatrixTransformer 类包含修改转换矩阵单个属性的方法：水平和垂直缩放、水平和垂直倾斜，以及旋转。 该类还具有围绕给定变形点而不是常见点 (0, 0) 旋转的方法。
 * @author hanyeah
 * @date 2015-12-3
 */
public class HMatrixTransformer extends HObject {

	/**
	 * 不可以实例化。
	 */
	private HMatrixTransformer(){
		// TODO Auto-generated constructor stub
		
	}
	/**
	 * [静态] 计算矩阵中的旋转角度（以度为单位）。 如果水平和垂直倾斜不相等，则使用垂直倾斜值。
	 * @param m
	 * @return
	 */
	public static double getRotation(HMatrix m){
		return getRotationRadians(m)*(180/Math.PI);
	}
	/**
	 * [静态] 计算矩阵中的旋转角度（以弧度为单位）。 如果水平和垂直倾斜不相等，则使用垂直倾斜值。 
	 * @param m
	 * @return
	 */
	public static double getRotationRadians(HMatrix m){
		return getSkewYRadians(m);
	}
	/**
	 * [静态] 计算矩阵中的水平缩放。
	 * @param m
	 * @return
	 */
	public static double getScaleX(HMatrix m){
		return Math.sqrt(m.a*m.a+m.b*m.b);
	}
	/**
	 * [静态] 计算矩阵中的垂直缩放。
	 * @param m
	 * @return
	 */
	public static double getScaleY(HMatrix m){
		return Math.sqrt(m.c*m.c+m.d*m.d);
	}
	/**
	 * [静态] 计算矩阵中的水平倾斜角度（以度为单位）。
	 * @param m
	 * @return
	 */
	public static double getSkewX(HMatrix m){
		return Math.atan2(-m.c, m.d) * (180/Math.PI);
	}
	/**
	 * [静态] 计算矩阵中的水平倾斜角度（以弧度为单位）。
	 * @param m
	 * @return
	 */
	public static double getSkewXRadians(HMatrix m){
		return Math.atan2(-m.c, m.d);
	}
	/**
	 * [静态] 计算矩阵中的垂直倾斜角度（以度为单位）。
	 * @param m
	 * @return
	 */
	public static double getSkewY(HMatrix m){
		return Math.atan2(m.b, m.a) * (180/Math.PI);
	}
	/**
	 * [静态] 计算矩阵中的垂直倾斜角度（以弧度为单位）。
	 * @param m
	 * @return
	 */
	public static double getSkewYRadians(HMatrix m){
		return Math.atan2(m.b, m.a);
	}
	/**
	 * [静态] 根据需要移动矩阵，将内部点与外部点对齐。 此功能可以用于将转换的影片剪辑中的点与其父级中的点匹配。 
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
	 * [静态] 围绕矩阵转换空间以外定义的点旋转矩阵。 此功能可以用于将影片剪辑围绕其父级中的变形点进行旋转。
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
	 * [静态] 围绕矩阵转换空间中定义的点旋转矩阵。 此功能可以用于将影片剪辑围绕其内部的变形点进行旋转。
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
	 * [静态] 更改矩阵中的旋转角度。 如果水平和垂直倾斜不相等，垂直倾斜将设为旋转值，水平倾斜将加上旧旋转和新旋转之间的差。
	 * @param m
	 * @param rotation
	 */
	public static void setRotation(HMatrix m,double rotation){
		setRotationRadians(m, rotation*(Math.PI/180));
	}
	/**
	 * [静态] 更改矩阵中的旋转角度。 如果水平和垂直倾斜不相等，垂直倾斜将设为旋转值，水平倾斜将加上旧旋转和新旋转之间的差。
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
	 * [静态] 更改矩阵中的水平缩放。
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
	 * [静态] 更改矩阵中的垂直缩放。
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
	 * [静态] 更改矩阵中的水平倾斜。
	 * @param m
	 * @param skewX
	 */
	public static void setSkewX(HMatrix m,double skewX){
		setSkewXRadians(m, skewX*(Math.PI/180));
	}
	/**
	 * [静态] 更改矩阵中的水平倾斜
	 * @param m
	 * @param skewX
	 */
	public static void setSkewXRadians(HMatrix m,double skewX){
		double scaleY=getScaleY(m);
		m.c = -scaleY * Math.sin(skewX);
		m.d =  scaleY * Math.cos(skewX);
	}
	/**
	 * [静态] 更改矩阵中的垂直倾斜。
	 * @param m
	 * @param skewY
	 */
	public static void setSkewY(HMatrix m,double skewY){
		setSkewYRadians(m, skewY*(Math.PI/180));
	}
	/**
	 * [静态] 更改矩阵中的垂直倾斜
	 * @param m
	 * @param skewY
	 */
	public static void setSkewYRadians(HMatrix m,double skewY){
		double scaleX = getScaleX(m);
		m.a = scaleX * Math.cos(skewY);
		m.b = scaleX * Math.sin(skewY);
	}
}
