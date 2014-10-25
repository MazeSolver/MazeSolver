/**
 * @file Agent.java
 * @date 21/10/2014
 */
package agent;

import java.awt.Point;

import javax.swing.JPanel;

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
    m_maze = maze;
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
   * @return La dirección en la que el agente quiere realizar el siguiente
   *         movimiento.
   */
  public abstract Direction getNextMovement ();

  /**
   * @param dir Dirección hacia la que mover el agente.
   */
  public abstract void doMovement (Direction dir);

  /**
   * @return Un panel de configuración para el agente.
   */
  public abstract JPanel getConfigurationPanel ();

}
