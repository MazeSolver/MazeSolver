/**
 * @file MultipleEnvironment.java
 * @date 27/10/2014
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import maze.Maze;
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
    // agente
    addMouseListener(new MouseAdapter() {
      /* (non-Javadoc)
       * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
       */
      @Override
      public void mouseClicked (MouseEvent e) {
        Point maze_point = new Point(e.getX() / CELL_SIZE_PX,
                                     e.getY() / CELL_SIZE_PX);

        m_selected = -1;
        for (int i = 0; i < m_agents.size(); i++) {
          Agent current = m_agents.get(i);
          if (current.getX() == maze_point.getX() &&
              current.getY() == maze_point.getY()) {
            m_selected = i;
            break;
          }
        }

        super.mouseClicked(e);
      }

      /* (non-Javadoc)
       * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
       */
      @Override
      public void mouseDragged (MouseEvent e) {
        // TODO Permitir arrastrar los agentes a otras posiciones.
        super.mouseDragged(e);
      }

      /* (non-Javadoc)
       * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
       */
      @Override
      public void mouseMoved(MouseEvent e) {
        // TODO Controlar el movimiento de ratón para resaltar el objeto
        // actualmente bajo el cursor.
        super.mouseMoved(e);
      }

    });
  }

  /* (non-Javadoc)
   * @see gui.Environment#addAgent(agent.Agent)
   */
  @Override
  public Environment addAgent (Agent ag) {
    if (ag == null)
      throw new IllegalArgumentException("El agente especificado es inválido");

    if (!m_agents.contains(ag))
      m_agents.add(ag);

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
    for (Agent i: m_agents)
      agents.add(i.duplicate());

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
    boolean ended = false;

    for (Agent i: m_agents) {
      i.doMovement(i.getNextMovement());
      if (i.getX() < 0 || i.getY() < 0 || i.getX() >= m_maze.getWidth() ||
          i.getY() >= m_maze.getHeight())
        ended = true;
    }

    return ended;
  }

  /* (non-Javadoc)
   * @see gui.Environment#paintComponent(java.awt.Graphics)
   */
  protected void paintComponent (Graphics g) {
    super.paintComponent(g);

    g.setColor(Color.ORANGE);
    for (Agent i: m_agents)
      g.fillOval(i.getX() * CELL_SIZE_PX, i.getY() * CELL_SIZE_PX,
                 CELL_SIZE_PX-1, CELL_SIZE_PX-1);

    // Pintamos el agente seleccionado con otro color para resaltarlo
    if (m_selected != -1) {
      g.setColor(Color.RED);
      g.fillOval(m_agents.get(m_selected).getX() * CELL_SIZE_PX,
                 m_agents.get(m_selected).getY() * CELL_SIZE_PX,
                 CELL_SIZE_PX-1, CELL_SIZE_PX-1);
    }
  }

}
