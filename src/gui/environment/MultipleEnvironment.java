/**
 * @file MultipleEnvironment.java
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
import util.Pair;
import agent.Agent;

/**
 * Entorno formado por múltiples agentes.
 */
public class MultipleEnvironment extends Environment {
  private static final long serialVersionUID = 1L;

  private int m_selected;
  private ArrayList <Agent> m_agents;

  /**
   * @param maze Laberinto base del entorno.
   */
  public MultipleEnvironment (Maze maze) {
    super(maze);
    m_selected = -1;
    m_agents = new ArrayList<Agent>();

    // Añadimos la escucha del cursor para permitir al usuario seleccionar un
    // agente.
    getContentPane().addMouseListener(new MouseAdapter() {
      /* (non-Javadoc)
       * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
       */
      @Override
      public void mousePressed (MouseEvent e) {
        Point maze_point = EnvironmentPanel.screenCoordToGrid(e.getPoint());

        m_selected = -1;
        for (int i = 0; i < m_agents.size(); i++) {
          Agent current = m_agents.get(i);
          if (current.getX() == maze_point.getX() &&
              current.getY() == maze_point.getY()) {
            m_selected = i;
            break;
          }
        }

        repaint();
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
      Pair <Integer, Integer> desp = dir.decompose();
      Point n_pos = new Point(pos.x + desp.first, pos.y + desp.second);

      for (Agent ag: m_agents) {
        if (ag.getX() == n_pos.getX() && ag.getY() == n_pos.getY())
          return MazeCell.Vision.AGENT;
      }
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

    if (!m_agents.contains(ag)) {
      ag.setEnvironment(this);

      // Buscamos un hueco donde colocar el agente
      loops:
      for (int y = 0; y < m_maze.getHeight(); y++) {
        for (int x = 0; x < m_maze.getWidth(); x++) {
          boolean used = false;
          for (Agent i: m_agents) {
            if (i.getX() == x && i.getY() == y) {
              used = true;
              break;
            }
          }
          if (!used) {
            ag.setPosition(new Point(x, y));
            break loops;
          }
        }
      }
      m_agents.add(ag);
      repaint();
    }

    return this;
  }

  /* (non-Javadoc)
   * @see gui.Environment#removeAgent(agent.Agent)
   */
  public Environment removeAgent (Agent ag) {
    // Si se encuentra el agente, se elimina de la lista de agentes y si estaba
    // seleccionado se quita el estado de selección
    if (m_agents.contains(ag)) {
      if (getSelectedAgent() == ag)
        m_selected = -1;

      m_agents.remove(ag);
    }
    else
      throw new IllegalArgumentException("El agente no se encuentra en el entorno");

    // Si tras el borrado de un elemento el entorno tiene un solo agente, éste
    // pasa a ser un entorno simple
    if (m_agents.size() == 1) {
      Environment env = new SimpleEnvironment(m_maze);
      env.addAgent(m_agents.get(0));
      return env;
    }

    // En otro caso, devolvemos el mismo entorno
    repaint();
    return this;
  }

  /* (non-Javadoc)
   * @see gui.Environment#getSelectedAgent()
   */
  @Override
  public Agent getSelectedAgent () {
    if (m_selected == -1)
      return null;
    else
      return m_agents.get(m_selected);
  }

  /* (non-Javadoc)
   * @see gui.Environment#getAgent(int)
   */
  @Override
  public Agent getAgent (int index) {
    if (index < 0 || index >= m_agents.size())
      throw new IllegalArgumentException("El índice está fuera de rango");

    return m_agents.get(index);
  }

  /* (non-Javadoc)
   * @see gui.Environment#getAgents()
   */
  @Override
  public ArrayList <Agent> getAgents () {
    ArrayList <Agent> agents = new ArrayList <Agent>();

    try {
      for (Agent i: m_agents)
        agents.add((Agent) i.clone());
    }
    catch (CloneNotSupportedException e) {}

    return agents;
  }

  /* (non-Javadoc)
   * @see gui.Environment#getAgentCount()
   */
  @Override
  public int getAgentCount () {
    return m_agents.size();
  }

  /* (non-Javadoc)
   * @see gui.Environment#runStep()
   */
  @Override
  public boolean runStep () {
    boolean ended = true;

    for (Agent i: m_agents) {
      i.doMovement(i.getNextMovement());
      if (i.getX() >= 0 && i.getY() >= 0 && i.getX() < m_maze.getWidth() &&
          i.getY() < m_maze.getHeight())
        ended = false;
    }

    repaint();
    return ended;
  }

}
