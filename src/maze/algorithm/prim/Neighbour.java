/**
 * @file Neighbour.java
 * @date 27 Oct 2014
 */
package maze.algorithm.prim;

/**
 *
 */
public class Neighbour {
  public short i;
  public short j;
  public DirectionPrimHelper dir;

  public Neighbour (short i, short j, DirectionPrimHelper dir){
    this.i = i;
    this.j = j;
    this.dir = dir;
  }
}
