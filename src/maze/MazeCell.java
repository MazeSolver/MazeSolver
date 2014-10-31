/**
 * @file MazeCell.java
 * @date 21/10/2014
 */
package maze;

/**
 * Clase que representa una celda del laberinto.
 */
public class MazeCell {
  private short m_cell;

  /**
   * Constructor por defecto. La celda creada esta rodeada de muros
   * m_cell = 0x1111;
   */
  public MazeCell () {
    m_cell |= Direction.UP.val;
    m_cell |= Direction.DOWN.val;
    m_cell |= Direction.RIGHT.val;
    m_cell |= Direction.LEFT.val;
  }

  /**
   * Cambia el estado de la dirección especificada. Si no había muro, ahora lo
   * hay y viceversa.
   * @param dir Lado de la celda que se quiere modificar.
   */
  public void toggleWall (Direction dir) {
    if (hasWall(dir))
      unsetWall(dir);
    else
      setWall(dir);
  }

  /**
   * Pone un muro si no lo hay en la dirección especificada.
   * @param dir Lado de la celda que se quiere modificar.
   */
  public void setWall (Direction dir) {
    m_cell |= dir.val;
  }

  /**
   * Quita el muro si lo hay en la dirección especificada.
   * @param dir Lado de la celda que se quiere modificar.
   */
  public void unsetWall (Direction dir) {
    m_cell &= ~dir.val;
  }

  /**
   * @param dir Lado de la celda que se quiere consultar.
   * @return Si hay una celda en esa dirección o no.
   */
  public boolean hasWall (Direction dir) {
    return (m_cell & dir.val) != 0;
  }

  /**
   * Enumeración de los diferentes estados que puede tener una celda de cara a
   * un agente cualquiera.
   */
  public static enum Status {
    /**
     * Este estado significa que la celda está vacía y se puede llegar desde la
     * celda actual en un paso.
     */
    EMPTY,

    /**
     * Este estado significa que hay una pared entre el agente y la celda.
     */
    WALL,

    /**
     * Este estado significa que la celda ha sido visitada por el agente
     * previamente.
     */
    VISITED,

    /**
     * Este estado significa que hay un agente sobre esa celda actualmente.
     */
    AGENT,

    /**
     * Este estado significa que la celda referenciada está fuera del laberinto.
     */
    OFFLIMITS;
  }
}
