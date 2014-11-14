/**
 * @file Agent.java
 * @date 21/10/2014
 */
package agent;

import gui.environment.Environment;

import java.awt.Point;

import javax.swing.JPanel;

import maze.Direction;
import maze.MazeCell;

/**
 * Clase que representa un agente abstracto que se encuentra en algún laberinto.
 */
public abstract class Agent implements Cloneable {
  protected Point m_pos;
  protected Environment m_env;

  /**
   * @param maze
   */
  protected Agent (Environment env) {
    m_pos = new Point();
    setEnvironment(env);
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
  public void setEnvironment (Environment env) {
    if (env != null)
      m_env = env;
    else
      throw new IllegalArgumentException("El laberinto debe ser válido");
  }

  /**
   * @return Entorno en el que se encuentra el agente.
   */
  public Environment getEnvironment () {
    return m_env;
  }

  /**
   * @param dir Dirección hacia la que mirar.
   * @return Lo que vería el agente si mira en la dirección especificada.
   */
  public MazeCell.Vision look (Direction dir) {
    return m_env.look(m_pos, dir);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals (Object obj) {
    return this == obj;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode () {
    return m_env.hashCode();
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
   * Elimina la memoria que el agente tenga sobre el entorno. No elimina su
   * configuración, sino que lo deja en el estado inicial.
   */
  public abstract void resetMemory ();

  /**
   * @return Un panel de configuración para el agente.
   */
  public abstract JPanel getConfigurationPanel ();

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public abstract Object clone () throws CloneNotSupportedException;

}
