/**
 * @file cellPrimHelper.java
 * @date 26 Oct 2014
 */
package maze.algorithm.prim;

/**
 * Clase auxiliar para el algoritmo de Prim
 */
public class cellPrimHelper {

  public short UP;
  public short DOWN;
  public short RIGHT;
  public short LEFT;
  public Boolean visited;

  /**
   * Asigna un valor aleatorio entre 0 y max Short a cada posición
   * Asigna como la celda como no visitada (false)
   */
  public cellPrimHelper () {
    visited = false;
    UP = (short) (0 + (Math.random()*Short.MAX_VALUE));
    DOWN = (short) (0 + (Math.random()*Short.MAX_VALUE));
    RIGHT = (short) (0 + (Math.random()*Short.MAX_VALUE));
    LEFT = (short) (0 + (Math.random()*Short.MAX_VALUE));
  }

}
