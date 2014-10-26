/**
 * @file DirectionPrimHelper.java
 * @date 26 Oct 2014
 */
package maze.algorithm.prim;

public enum DirectionPrimHelper {
  UP    ((short) 0),
  DOWN  ((short) 2),
  LEFT  ((short) 1),
  RIGHT ((short) 3);

  public short val;

  private DirectionPrimHelper (short val) {
    this.val = val;
  }

}