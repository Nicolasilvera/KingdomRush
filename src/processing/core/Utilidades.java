//2017
package processing.core;
import java.util.*;


public class Utilidades {

	public static final float PI = (float) Math.PI;
  public static final float HALF_PI = (float) (Math.PI / 2.0);
  public static final float THIRD_PI = (float) (Math.PI / 3.0);


public static final float TWO_PI = (float) (2.0 * Math.PI);
  public static final float TAU = (float) (2.0 * Math.PI);

  public static final float DEG_TO_RAD = PI/180.0f;
  public static final float RAD_TO_DEG = 180.0f/PI;
    public static float constrain(float v,float min , float max){
		float ret=v;

		if (v<=min){ret=min;}
		if (v>=max){ret=max;}
		return ret;
	}


/**
   * ( begin auto-generated from degrees.xml )
   *
   * Converts a radian measurement to its corresponding value in degrees.
   * Radians and degrees are two ways of measuring the same thing. There are
   * 360 degrees in a circle and 2*PI radians in a circle. For example,
   * 90&deg; = PI/2 = 1.5707964. All trigonometric functions in Processing
   * require their parameters to be specified in radians.
   *
   * ( end auto-generated )
   * @webref math:trigonometry
   * @param radians radian value to convert to degrees
   * @see PApplet#radians(float)
   */
  static public final float degrees(float radians) {
    return radians * RAD_TO_DEG;
  }

/**
   * ( begin auto-generated from radians.xml )
   *
   * Converts a degree measurement to its corresponding value in radians.
   * Radians and degrees are two ways of measuring the same thing. There are
   * 360 degrees in a circle and 2*PI radians in a circle. For example,
   * 90&deg; = PI/2 = 1.5707964. All trigonometric functions in Processing
   * require their parameters to be specified in radians.
   *
   * ( end auto-generated )
   * @webref math:trigonometry
   * @param degrees degree value to convert to radians
   * @see PApplet#degrees(float)
   */
  static public final float radians(float degrees) {
    return degrees * DEG_TO_RAD;
  }

/**
   * ( begin auto-generated from ceil.xml )
   *
   * Calculates the closest int value that is greater than or equal to the
   * value of the parameter. For example, <b>ceil(9.03)</b> returns the value 10.
   *
   * ( end auto-generated )
   * @webref math:calculation
   * @param n number to round up
   * @see PApplet#floor(float)
   * @see PApplet#round(float)
   */
  static public final int ceil(float n) {
    return (int) Math.ceil(n);
  }

/**
   * ( begin auto-generated from floor.xml )
   *
   * Calculates the closest int value that is less than or equal to the value
   * of the parameter.
   *
   * ( end auto-generated )
   * @webref math:calculation
   * @param n number to round down
   * @see PApplet#ceil(float)
   * @see PApplet#round(float)
   */
  static public final int floor(float n) {
    return (int) Math.floor(n);
  }

/**
   * ( begin auto-generated from round.xml )
   *
   * Calculates the integer closest to the <b>value</b> parameter. For
   * example, <b>round(9.2)</b> returns the value 9.
   *
   * ( end auto-generated )
   * @webref math:calculation
   * @param n number to round
   * @see PApplet#floor(float)
   * @see PApplet#ceil(float)
   */
  static public final int round(float n) {
    return Math.round(n);
  }


  static public final float mag(float a, float b) {
    return (float)Math.sqrt(a*a + b*b);
  }

/**
   * ( begin auto-generated from mag.xml )
   *
   * Calculates the magnitude (or length) of a vector. A vector is a
   * direction in space commonly used in computer graphics and linear
   * algebra. Because it has no "start" position, the magnitude of a vector
   * can be thought of as the distance from coordinate (0,0) to its (x,y)
   * value. Therefore, mag() is a shortcut for writing "dist(0, 0, x, y)".
   *
   * ( end auto-generated )
   * @webref math:calculation
   * @param a first value
   * @param b second value
   * @param c third value
   * @see PApplet#dist(float, float, float, float)
   */
  static public final float mag(float a, float b, float c) {
    return (float)Math.sqrt(a*a + b*b + c*c);
  }


  static public final float dist(float x1, float y1, float x2, float y2) {
    return sqrt(sq(x2-x1) + sq(y2-y1));
  }

/**
   * ( begin auto-generated from dist.xml )
   *
   * Calculates the distance between two points.
   *
   * ( end auto-generated )
   * @webref math:calculation
   * @param x1 x-coordinate of the first point
   * @param y1 y-coordinate of the first point
   * @param z1 z-coordinate of the first point
   * @param x2 x-coordinate of the second point
   * @param y2 y-coordinate of the second point
   * @param z2 z-coordinate of the second point
   */
  static public final float dist(float x1, float y1, float z1,
                                 float x2, float y2, float z2) {
    return sqrt(sq(x2-x1) + sq(y2-y1) + sq(z2-z1));
  }

/**
   * ( begin auto-generated from lerp.xml )
   *
   * Calculates a number between two numbers at a specific increment. The
   * <b>amt</b> parameter is the amount to interpolate between the two values
   * where 0.0 equal to the first point, 0.1 is very near the first point,
   * 0.5 is half-way in between, etc. The lerp function is convenient for
   * creating motion along a straight path and for drawing dotted lines.
   *
   * ( end auto-generated )
   * @webref math:calculation
   * @param start first value
   * @param stop second value
   * @param amt float between 0.0 and 1.0
   * @see PGraphics#curvePoint(float, float, float, float, float)
   * @see PGraphics#bezierPoint(float, float, float, float, float)
   * @see PVector#lerp(PVector, float)
   * @see PGraphics#lerpColor(int, int, float)
   */
  static public final float lerp(float start, float stop, float amt) {
    return start + (stop-start) * amt;
  }

  /**
   * ( begin auto-generated from norm.xml )
   *
   * Normalizes a number from another range into a value between 0 and 1.
   * <br/> <br/>
   * Identical to map(value, low, high, 0, 1);
   * <br/> <br/>
   * Numbers outside the range are not clamped to 0 and 1, because
   * out-of-range values are often intentional and useful.
   *
   * ( end auto-generated )
   * @webref math:calculation
   * @param value the incoming value to be converted
   * @param start lower bound of the value's current range
   * @param stop upper bound of the value's current range
   * @see PApplet#map(float, float, float, float, float)
   * @see PApplet#lerp(float, float, float)
   */
  static public final float norm(float value, float start, float stop) {
    return (value - start) / (stop - start);
  }

  /**
   * ( begin auto-generated from map.xml )
   *
   * Re-maps a number from one range to another. In the example above,
   * the number '25' is converted from a value in the range 0..100 into
   * a value that ranges from the left edge (0) to the right edge (width)
   * of the screen.
   * <br/> <br/>
   * Numbers outside the range are not clamped to 0 and 1, because
   * out-of-range values are often intentional and useful.
   *
   * ( end auto-generated )
   * @webref math:calculation
   * @param value the incoming value to be converted
   * @param start1 lower bound of the value's current range
   * @param stop1 upper bound of the value's current range
   * @param start2 lower bound of the value's target range
   * @param stop2 upper bound of the value's target range
   * @see PApplet#norm(float, float, float)
   * @see PApplet#lerp(float, float, float)
   */
  static public final float map(float value,
                                float start1, float stop1,
                                float start2, float stop2) {
    return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
  }


  /*
  static public final double map(double value,
                                 double istart, double istop,
                                 double ostart, double ostop) {
    return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
  }
  */


  static public final float sq(float n) {
    return n*n;
  }

    static public final float sqrt(float n) {
    return (float)Math.sqrt(n);

 }

	public static float randomInRange(float min, float max) {
		Random rand = new Random();

	  	float range = max - min;
	  	float scaled = rand.nextFloat() * range;
	  	float shifted = scaled + min;
	  	return shifted; // == (rand.nextDouble() * (max-min)) + min;
	}
}