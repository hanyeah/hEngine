package com.hanyeah;

/**
 * 弥补安卓自带Math类的不足。
 * @author hanyeah
 * @date 2015-12-15
 */
public final class HMath extends HObject {

	private HMath() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 最小值。可以传入2个及以上参数
	 * @param value1	参数1
	 * @param value2	参数2
	 * @param args		任意多个参数
	 * @return
	 */
	public static double min(double value1,double value2,double...args){
		int len=args.length;
		double result=value2<value1?value2:value1;
		for(int i=0;i<len;i++){
			if(args[i]<result){
				result=args[i];
			}
		}
		return result;
	}
	/**
	 * 最大值。可以传入2个及以上参数
	 * @param value1	参数1
	 * @param value2	参数2
	 * @param args		任意多个参数
	 * @return			double
	 */
	public static double max(double value1,double value2,double...args){
		int len=args.length;
		double result=value2>value1?value2:value1;
		for(int i=0;i<len;i++){
			if(args[i]>result){
				result=args[i];
			}
		}
		return result;
	}
	
}
