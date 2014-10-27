/**
 * @file Agent.java
 * @date 21/10/2014
 */
package agent;

import java.awt.Point;

import maze.Direction;
import maze.Maze;

/**
 * Clase que representa un agente abstracto que se encuentra en algún laberinto.
 */
public abstract class Agent {
  protected Point m_pos;
  protected Maze m_maze;

  /**
   * @param maze
   */
  protected Agent (Maze maze) {
    m_pos = new Point();
    m_maze = null;
    setMaze(maze);
  }

  /**
   * @param pos
   *          Nueva posición del agente. Este método se puede sobrecargar en las
   *          clases derivadas para mantener coherente la memoria de la que
   *          disponga el mismo.
   */
  public void setPosition (Point pos) {
    m_pos.x = pos.x;
    m_pos.y = pos.y;
  }

  /**
   * @return Posición en el eje X del agente.
   */
  public int getX () {
    return m_pos.x;
  }

  /**
   * @return Posición en el eje Y de agente.
   */
  public int getY () {
    return m_pos.y;
  }

  /**
   * Este método debería sobrecargarse en las clases derivadas que contengan
   * información acerca del camino a seguir por el agente (un plan) de forma que
   * éste siga siendo coherente tras el cambio de laberinto.
   * @param maze Laberinto donde colocar el agente.
   */
  public void setMaze (Maze maze) {
    if (maze != null)
      m_maze = maze;
    else
      throw new IllegalArgumentException("El laberinto debe ser válido");
  }

  /**
   * @return Una copia de sí mismo.
   */
  public abstract Agent duplicate ();

  /**
   * @return La dirección en la que el agente quiere realizar el siguiente
   *         movimiento.
   */
  public abstract Direction getNextMovement ();

  /**
   * @param dir Dirección hacia la que mover el agente.
   */
  public abstract void doMovement (Direction dir);

}
