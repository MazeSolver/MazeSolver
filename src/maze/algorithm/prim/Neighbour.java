/**
 * @file Neighbour.java
 * @date 27 Oct 2014
 */
package maze.algorithm.prim;

import maze.Direction;

/**
 *
 */
public class Neighbour {
  public short i;
  public short j;
  public Direction dir;

  public Neighbour (short i, short j, Direction dir){
    this.i = i;
    this.j = j;
    this.dir = dir;
  }
}
