/**
 * @file SimpleEnvironment.java
 * @date 27/10/2014
 */
package gui.environment;

import java.awt.Point;
import java.util.ArrayList;

import maze.Direction;
import maze.Maze;
import agent.Agent;

/**
 * Entorno formado por como máximo un único agente.
 */
public class SimpleEnvironment extends Environment {
  private static final long serialVersionUID = 1L;
  private Agent m_agent;

  /**
   * @param maze Laberinto base del entorno.
   */
  public SimpleEnvironment (Maze maze) {
    super(maze);
  }

  /* (non-Javadoc)
   * @see gui.Environment#addAgent(agent.Agent)
   */
  @Override
  public Environment addAgent (Agent ag) {
    if (ag == null)
      throw new IllegalArgumentException("El agente especificado es inválido");

    if (m_agent == null) {
      m_agent = ag;
      return this;
    }
    else {
      Environment multiple = new MultipleEnvironment(getMaze());
      multiple.addAgent(m_agent);
      multiple.addAgent(ag);
      return multiple;
    }
  }

  /* (non-Javadoc)
   * @see gui.Environment#removeAgent(agent.Agent)
   */
  public Environment removeAgent (Agent ag) {
    if (m_agent == ag)
      m_agent = null;

    return this;
  }

  /* (non-Javadoc)
   * @see gui.Environment#getSelectedAgent()
   */
  @Override
  public Agent getSelectedAgent () {
    return m_agent;
  }

  /* (non-Javadoc)
   * @see gui.Environment#getAgent(int)
   */
  @Override
  public Agent getAgent (int index) {
    if (index != 0)
      throw new IllegalArgumentException("El índice está fuera de rango");

    return m_agent;
  }

  /* (non-Javadoc)
   * @see gui.Environment#getAgents()
   */
  @Override
  public ArrayList <Agent> getAgents () {
    ArrayList <Agent> agents = new ArrayList <Agent>();
    agents.add(m_agent.duplicate());
    return agents;
  }

  /* (non-Javadoc)
   * @see gui.Environment#getAgentCount()
   */
  @Override
  public int getAgentCount () {
    return m_agent == null? 0 : 1;
  }

  /* (non-Javadoc)
   * @see gui.environment.Environment#movementAllowed(java.awt.Point, maze.Direction)
   */
  @Override
  public boolean movementAllowed (Point pos, Direction dir) {
    return !m_maze.get(pos.y, pos.x).hasWall(dir);
  }

  /* (non-Javadoc)
   * @see gui.Environment#runStep()
   */
  @Override
  public boolean runStep () {
    if (m_agent == null)
      throw new IllegalStateException("No se puede mover un agente si no existe");

    m_agent.doMovement(m_agent.getNextMovement());
    return m_agent.getX() < 0 ||
           m_agent.getY() < 0 ||
           m_agent.getX() >= m_maze.getWidth() ||
           m_agent.getY() >= m_maze.getHeight();
  }

}
