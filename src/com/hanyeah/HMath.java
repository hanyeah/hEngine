package com.hanyeah;

/**
 * �ֲ���׿�Դ�Math��Ĳ��㡣
 * @author hanyeah
 * @date 2015-12-15
 */
public final class HMath extends HObject {

	private HMath() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * ��Сֵ�����Դ���2�������ϲ���
	 * @param value1	����1
	 * @param value2	����2
	 * @param args		����������
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
	 * ���ֵ�����Դ���2�������ϲ���
	 * @param value1	����1
	 * @param value2	����2
	 * @param args		����������
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
