package com.hanyeah.motion;

import com.hanyeah.events.HEvent;

/**
 * 缓动函数类。
 * easeIn() 方法开始时往后运动，然后反向朝目标移动。
 * easeOut() 方法开始运动时是朝目标移动，稍微过冲，再倒转方向回来朝着目标。
 * easeInOut() 方法结合了 easeIn() 和 easeOut() 方法的运动，开始运动时是向后跟踪，再倒转方向并朝目标移动，稍微过冲目标，然后再次倒转方向，回来朝目标移动。
 * @author hanyeah
 * @date 2015-12-12
 */
public class HEasing {
	
	private HEasing(){
		//
	}
	
	public interface HIEaseFunction{
		/**
		* 参数解释
		* @param t	当前时间，即缓动经过的时间，0-d
		* @param b	用于缓动的属性的初始值
		* @param c	用于缓动的属性的改变总量
		* @param d	缓动总持续时间
		*/
		public double execute(double t,double b,double c,double d,double... args);
	}
	public static class None{
		public static class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return b+t*c/d;
			}
		}
	}
	/**
	 *Quad
	 */
	public static class Quad{
		public static class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c*(t/=d)*t+b;
			}
		}
		public static class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return -c *(t/=d)*(t-2) + b;
			}
		}
		public static class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				if ((t/=d/2) < 1) return c/2*t*t + b;
				return -c/2 * ((--t)*(t-2) - 1) + b;
			}
		}
	}
	/**
	 * 
	 * Cubic
	 *
	 */
	public static class Cubic{
		public static class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c*(t/=d)*t*t + b;
			}
		}
		public static class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c*((t=t/d-1)*t*t + 1) + b;
			}
		}
		public static class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				if ((t/=d/2) < 1) return c/2*t*t*t + b;
				return c/2*((t-=2)*t*t + 2) + b;
			}
		}
	}
	/**
	 * 
	 * Quart
	 *
	 */
	public static class Quart{
		public static class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c*(t/=d)*t*t*t + b;
			}
		}
		public static class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return -c * ((t=t/d-1)*t*t*t - 1) + b;
			}
		}
		public static class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
				return -c/2 * ((t-=2)*t*t*t - 2) + b;
			}
		}
	}
	/**
	 * 
	 * Quint
	 *
	 */
	public static class Quint{
		public static class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c*(t/=d)*t*t*t*t + b;
			}
		}
		public static class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c*((t=t/d-1)*t*t*t*t + 1) + b;
			}
		}
		public static class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
				return c/2*((t-=2)*t*t*t*t + 2) + b;
			}
		}
	}
	/**
	 * 
	 * Sine
	 *
	 */
	public static class Sine{
		public class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return -c * Math.cos(t/d * (Math.PI/2)) + c + b;
			}
		}
		public static class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c * Math.sin(t/d * (Math.PI/2)) + b;
			}
		}
		public static class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;
			}
		}
	}
	/**
	 * 
	 * Strong
	 *
	 */
	public static class Strong{
		public static class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c*(t/=d)*t*t*t*t + b;
			}
		}
		public static class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c*((t=t/d-1)*t*t*t*t + 1) + b;
			}
		}
		public static class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
				return c/2*((t-=2)*t*t*t*t + 2) + b;
			}
		}
	}
	/**
	 * 
	 * Expo
	 *
	 */
	public static class Expo{
		public static class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return (t==0) ? b : c * Math.pow(2, 10 * (t/d - 1)) + b;
			}
		}
		public static class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b;
			}
		}
		public static class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				if (t==0) return b;
				if (t==d) return b+c;
				if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
				return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
			}
		}
	}
	/**
	 * 
	 * Circ
	 *
	 */
	public static class Circ{
		public static class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return -c * (Math.sqrt(1 - (t/=d)*t) - 1) + b;
			}
		}
		public static class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c * Math.sqrt(1 - (t=t/d-1)*t) + b;
			}
		}
		public static class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				if ((t/=d/2) < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
				return c/2 * (Math.sqrt(1 - (t-=2)*t) + 1) + b;
			}
		}
	}
	/**
	 * 
	 * Elastic(未实现)
	 *
	 */
	public class Elastic{
		public class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return 0;
			}
		}
		public class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return 0;
			}
		}
		public class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return 0;
			}
		}
	}
	/**
	 * 
	 * Back
	 *
	 */
	public static class Back{
		public static class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				double s;
				if(args.length>0){
					s=args[0];
				}
				else{
					s = 1.70158;
				}
				return c*(t/=d)*t*((s+1)*t - s) + b;
			}
		}
		public static class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				double s;
				if(args.length>0){
					s=args[0];
				}
				else{
					s = 1.70158;
				}
				return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
			}
		}
		public static class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				double s;
				if(args.length>0){
					s=args[0];
				}
				else{
					s = 1.70158;
				}
				if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b;
				return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b;
			}
		}
	}
	/**
	 * 
	 * Bounce
	 *
	 */
	public static class Bounce{
		public static class easeIn implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				return c - new HEasing.Bounce.easeOut().execute(d-t, 0, c, d) + b;
			}
		}
		public static class easeOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				if ((t/=d) < (1/2.75)) {
					return c*(7.5625*t*t) + b;
				} else if (t < (2/2.75)) {
					return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b;
				} else if (t < (2.5/2.75)) {
					return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b;
				} else {
					return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b;
				}
			}
		}
		public static class easeInOut implements HIEaseFunction{
			public double execute(double t,double b,double c,double d,double... args){
				if (t < d/2) return new HEasing.Bounce.easeIn().execute(t*2, 0, c, d) * .5 + b;
				else return new HEasing.Bounce.easeOut().execute(t*2-d, 0, c, d) * .5 + c*.5 + b;
			}
		}
	}
}
