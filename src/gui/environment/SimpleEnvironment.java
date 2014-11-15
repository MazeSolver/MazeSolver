/**
 * @file SimpleEnvironment.java
 * @date 27/10/2014
 */
package gui.environment;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import maze.Direction;
import maze.Maze;
import maze.MazeCell;
import agent.Agent;

/**
 * Entorno formado por como máximo un único agente.
 */
public class SimpleEnvironment extends Environment {
  private static final long serialVersionUID = 1L;

  private boolean m_selected;
  private Agent m_agent;

  /**
   * @param maze Laberinto base del entorno.
   */
  public SimpleEnvironment (Maze maze) {
    super(maze);

    getContentPane().addMouseListener(new MouseAdapter() {
      /* (non-Javadoc)
       * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
       */
      @Override
      public void mousePressed (MouseEvent e) {
        if (m_agent != null) {
          Point maze_point = EnvironmentPanel.screenCoordToGrid(e.getPoint());
          m_selected = m_agent.getX() == maze_point.getX() &&
                       m_agent.getY() == maze_point.getY();
          repaint();
        }
        super.mousePressed(e);
      }
    });

    getContentPane().addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseMoved (MouseEvent e) {
        // TODO Controlar el movimiento de ratón para resaltar el objeto
        // actualmente bajo el cursor.
      }

      @Override
      public void mouseDragged (MouseEvent e) {
        Agent ag = getSelectedAgent();
        if (ag != null) {
          Point grid_pos = EnvironmentPanel.screenCoordToGrid(e.getPoint());
          if (grid_pos.x >= 0 && grid_pos.x < m_maze.getWidth() &&
              grid_pos.y >= 0 && grid_pos.y < m_maze.getHeight()) {
            ag.setPosition(grid_pos);
            repaint();
          }
        }
      }
    });
  }

  /* (non-Javadoc)
   * @see gui.environment.Environment#look(java.awt.Point, maze.Direction)
   */
  @Override
  public MazeCell.Vision look (Point pos, Direction dir) {
    MazeCell.Vision vision = super.look(pos, dir);
    if (vision == MazeCell.Vision.EMPTY) {
      Point new_pos = dir.movePoint(pos);
      if (m_agent.getX() == new_pos.getX() && m_agent.getY() == new_pos.getY())
        return MazeCell.Vision.AGENT;
    }
    return vision;
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
      m_agent.setEnvironment(this);
      m_agent.setPosition(new Point(0, 0));
      repaint();
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
    if (ag != null && m_agent == ag) {
      m_agent = null;
      repaint();
    }
    else
      throw new IllegalArgumentException("El agente no se encuentra en el entorno");

    return this;
  }

  /* (non-Javadoc)
   * @see gui.Environment#getSelectedAgent()
   */
  @Override
  public Agent getSelectedAgent () {
    return m_selected? m_agent : null;
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

    try {
      agents.add((Agent) m_agent.clone());
    }
    catch (CloneNotSupportedException e) {}

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
   * @see gui.Environment#runStep()
   */
  @Override
  public boolean runStep () {
    if (m_agent == null)
      return true;

    m_agent.doMovement(m_agent.getNextMovement());
    repaint();
    return m_agent.getX() < 0 ||
           m_agent.getY() < 0 ||
           m_agent.getX() >= m_maze.getWidth() ||
           m_agent.getY() >= m_maze.getHeight();
  }

}
