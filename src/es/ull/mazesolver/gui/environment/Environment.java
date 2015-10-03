/*
 * This file is part of MazeSolver.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2014 MazeSolver
 * Sergio M. Afonso Fumero <theSkatrak@gmail.com>
 * Kevin I. Robayna Hernández <kevinirobaynahdez@gmail.com>
 */

/**
 * @file Environment.java
 * @date 25/10/2014
 */
package es.ull.mazesolver.gui.environment;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import com.tomtessier.scrollabledesktop.BaseInternalFrame;

import es.ull.mazesolver.agent.Agent;
import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.maze.Maze;
import es.ull.mazesolver.maze.MazeCell;
import es.ull.mazesolver.util.BlackboardManager;
import es.ull.mazesolver.util.Direction;
import es.ull.mazesolver.util.InteractionMode;
import es.ull.mazesolver.util.MessageManager;
import es.ull.mazesolver.util.Pair;
import es.ull.mazesolver.util.SimulationResults;

/**
 * Una instancia de esta clase representa un entorno de ejecución, formado por
 * un laberinto y por un conjunto de agentes.
 */
public class Environment extends BaseInternalFrame {
  private static final long serialVersionUID = 1L;
  private static final int WINDOW_BORDER_WIDTH = 11;
  private static final int WINDOW_BORDER_HEIGHT = 33;
  private static final int WINDOWS_OFFSET = 20;

  private static int s_instance = 0;
  private static Point s_start_pos = new Point();

  private Maze m_maze;
  private ArrayList <Agent> m_agents;
  private int m_selected, m_hovered;

  private BlackboardManager m_blackboard_mgr;
  private MessageManager m_message_mgr;

  private MouseListener m_agent_click = new MouseAdapter() {
    @Override
    public void mousePressed (MouseEvent e) {
      m_selected = getAgentIndexUnderMouse(e.getPoint());
      repaint();
      super.mousePressed(e);
    }
  };

  private MouseMotionListener m_agent_hover_drag = new MouseMotionListener() {
    @Override
    public void mouseMoved (MouseEvent e) {
      m_hovered = getAgentIndexUnderMouse(e.getPoint());
      repaint();
    }

    @Override
    public void mouseDragged (MouseEvent e) {
      Agent ag = getSelectedAgent();
      if (ag != null) {
        Point grid_pos = EnvironmentPanel.screenCoordToGrid(e.getPoint());
        if (m_maze.containsPoint(grid_pos)) {
          ag.setPosition(grid_pos);
          repaint();
        }
      }
    }
  };

  private MouseListener m_wall_click = new MouseAdapter() {
    @Override
    public void mousePressed (MouseEvent e) {
      EnvironmentEditionPanel panel = (EnvironmentEditionPanel) getContentPane();
      Pair<Point, Direction> selected = panel.getWallAt(e.getPoint());

      if (selected != null) {
        Point pos = selected.first;
        Direction dir = selected.second;
        Point adj = dir.movePoint(pos);

        // Si las dos celdas están dentro, se crea/eliminan las dos paredes que
        // las unen
        if (m_maze.containsPoint(adj)) {
          m_maze.get(pos.y, pos.x).toggleWall(dir);
          m_maze.get(adj.y, adj.x).toggleWall(dir.getOpposite());
        }
        // Si sólo una de las dos celdas está dentro hay que cambiar la posición
        // de la salida a ese punto
        else {
          if (dir.isVertical())
            m_maze.setExit(pos.x, dir);
          else // Horizontal
            m_maze.setExit(pos.y, dir);
        }
      }

      repaint();
      super.mousePressed(e);
    }
  };

  /**
   * Constructor para las clases de tipo entorno.
   *
   * @param maze
   *          Laberinto en el que se basa el entorno. Puede ser compartido entre
   *          varios entornos.
   * @param name
   *          Nombre del entorno.
   */
  public Environment (Maze maze, String name) {
    super(name, false, false, false, false);
    ++s_instance;

    setMaze(maze);
    setVisible(true);

    setLocation(s_start_pos);
    s_start_pos.x += WINDOWS_OFFSET;
    s_start_pos.y += WINDOWS_OFFSET;

    m_selected = m_hovered = -1;
    m_agents = new ArrayList <Agent>();

    m_blackboard_mgr = new BlackboardManager();
    m_message_mgr = new MessageManager();

    moveToFront();
  }

  /**
   * Constructor para las clases de tipo entorno.
   *
   * @param maze
   *          Laberinto en el que se basa el entorno. Puede ser compartido entre
   *          varios entornos.
   */
  public Environment (Maze maze) {
    this(maze, "Env " + (s_instance + 1));
  }

  /**
   * Cambia el nombre del entorno.
   *
   * @param name
   *          Nuevo nombre del entorno.
   */
  public void setEnvName (String name) {
    setTitle(name);
  }

  /**
   * @return El nombre del entorno.
   */
  public String getEnvName () {
    return getTitle();
  }

  /**
   * Cambia el modo de interacción con el entorno.
   *
   * @param mode
   *          Nuevo modo de interacción con el entorno.
   */
  public void setInteractionMode (InteractionMode mode) {
    Container panel = null;

    switch (mode) {
      case SIMULATION:
        panel = new EnvironmentSimulationPanel(this);
        panel.addMouseListener(m_agent_click);
        panel.addMouseMotionListener(m_agent_hover_drag);
        break;
      case EDITION:
        panel = new EnvironmentEditionPanel(this);
        panel.addMouseListener(m_wall_click);
        break;
    }

    setContentPane(panel);
    updateSize();
  }

  /**
   * Obtiene el laberinto contenido en el entorno.
   *
   * @return Laberinto base del entorno.
   */
  public Maze getMaze () {
    return m_maze;
  }

  /**
   * Cambia el laberinto del entorno.
   * <br><br>
   * Esta operación invalidaría la memoria almacenada del/los agentes en caso
   * de que éstos contuvieran información sobre la ruta a llevar a cabo en el
   * entorno, pero no cuando la memoria fuera un conjunto de reglas o una tabla
   * de percepción-acción o cuando el agente no tuviera ninguna memoria.
   *
   * @param maze
   *          Laberinto en el que se basa el entorno.
   */
  public void setMaze (Maze maze) {
    if (maze != null) {
      m_maze = maze;
      repaint();
    }
    else
      throw new IllegalArgumentException(
          MainWindow.getTranslations().exception().invalidMaze());
  }

  /**
   * Actualiza el tamaño de la ventana con respecto al tamaño de su contenido,
   * que es la representación del entorno.
   */
  public void updateSize () {
    EnvironmentPanel panel = ((EnvironmentPanel) getContentPane());
    panel.updateSize();
    setSize(panel.getWidth() + WINDOW_BORDER_WIDTH, panel.getHeight() + WINDOW_BORDER_HEIGHT);
    repaint();
  }

  /**
   * Este método permite saber lo que puede ver un agente si mira en una
   * dirección específica. La versión en la clase principal no maneja agentes,
   * por lo que debe ser sobrecargada para gestionarlos.
   *
   * @param pos
   *          Posición desde la que mirar.
   * @param dir
   *          Dirección hacia la que mirar.
   * @return Lo que vería un agente en la posición especificada si mirara hacia
   *         la dirección indicada.
   */
  public MazeCell.Vision look (Point pos, Direction dir) {
    // Si el agente está fuera del laberinto, no dejamos que se mueva. De esta
    // forma, cuando un agente sale del laberinto se queda quieto fuera del
    // mismo y no vuelve a entrar ni se va lejos de la salida.
    if (!m_maze.containsPoint(pos) || m_maze.get(pos.y, pos.x).hasWall(dir))
      return MazeCell.Vision.WALL;

    Point n_pos = dir.movePoint(pos);
    if (!m_maze.containsPoint(n_pos))
      return MazeCell.Vision.OFFLIMITS;

    for (Agent ag: m_agents) {
      if (ag.getX() == n_pos.getX() && ag.getY() == n_pos.getY())
        return MazeCell.Vision.AGENT;
    }

    return MazeCell.Vision.EMPTY;
  }

  /**
   * Indica si a partir de una posición, el movimiento hacia una determinada
   * posición es posible o no.
   *
   * @param pos
   *          Posición de partida.
   * @param dir
   *          Dirección de movimiento.
   * @return true si se puede y false si no.
   */
  public boolean movementAllowed (Point pos, Direction dir) {
    MazeCell.Vision vision = look(pos, dir);
    return vision == MazeCell.Vision.EMPTY || vision == MazeCell.Vision.OFFLIMITS;
  }

  /**
   * Añade un agente al entorno.
   *
   * @param ag
   *          Agente que se quiere añadir al entorno.
   */
  public void addAgent (Agent ag) {
    if (ag == null)
      throw new IllegalArgumentException(
          MainWindow.getTranslations().exception().invalidAgent());

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
  }

  /**
   * Elimina un agente del entorno.
   *
   * @param ag
   *          Referencia al agente que se quiere eliminar.
   */
  public void removeAgent (Agent ag) {
    // Si se encuentra el agente, se elimina de la lista de agentes y si estaba
    // seleccionado se quita el estado de selección
    if (m_agents.contains(ag)) {
      if (getSelectedAgent() == ag)
        m_selected = -1;
      m_agents.remove(ag);
    }
    else
      throw new IllegalArgumentException(
          MainWindow.getTranslations().exception().agentNotInEnvironment());

    repaint();
  }

  /**
   * Extrae una referencia al agente seleccionado dentro del entorno.
   *
   * @return Agente seleccionado actualmente en el entorno o null si no hay
   *         ninguno seleccionado.
   */
  public Agent getSelectedAgent () {
    if (m_selected == -1)
      return null;
    else
      return m_agents.get(m_selected);
  }

  /**
   * Extrae una referencia al agente dentro del entorno que tiene el cursor
   * situado encima.
   *
   * @return Agente sobre el que se tiene el cursor actualmente en el entorno o
   *         null si el cursor no está encima de ningún agente.
   */
  public Agent getHoveredAgent () {
    if (m_hovered == -1)
      return null;
    else
      return m_agents.get(m_hovered);
  }

  /**
   * Extrae una referencia a un agente del entorno.
   *
   * @param index
   *          Índice del agente que se quiere consultar.
   * @return Agente número 'index' dentro del entorno.
   */
  public Agent getAgent (int index) {
    if (index < 0 || index >= m_agents.size())
      throw new IllegalArgumentException(
          MainWindow.getTranslations().exception().indexOutOfRange());

    return m_agents.get(index);
  }

  /**
   * Extrae una copia profunda de la lista de agentes que hay dentro del
   * entorno. Hay que tener en cuenta que cualquier modificación en esta lista
   * no va a tener ninguna repercusión en los agentes o el entorno original.
   *
   * @return Copia de la lista de agentes dentro del entorno.
   */
  public ArrayList <Agent> getAgents () {
    ArrayList <Agent> agents = new ArrayList <Agent>();

    for (Agent i: m_agents)
      agents.add((Agent) i.clone());

    return agents;
  }

  /**
   * Obtiene el número de agentes que se encuentran en el entorno.
   *
   * @return Número de agentes actualmente en el entorno.
   */
  public int getAgentCount () {
    return m_agents.size();
  }

  /**
   * Obtiene el gestor de pizarras del entorno.
   *
   * @return El gestor de pizarras del entorno.
   */
  public BlackboardManager getBlackboardManager () {
    return m_blackboard_mgr;
  }

  /**
   * Obtiene el gestor de mensajes del entorno.
   *
   * @return El gestor de mensajes del entorno.
   */
  public MessageManager getMessageManager () {
    return m_message_mgr;
  }

  /**
   * Ejecuta un paso de la simulación de ejecución de los agentes en el entorno
   * y devuelve el resultado de la ejecución.
   *
   * @param results
   *          Objeto que representa el resultado de la simulación, que será
   *          actualizado en este método para notificar de agentes que han
   *          llegado a la salida y para contar los pasos que han dado.
   * @return true si todos los agentes han salido del laberinto y false en otro
   *         caso.
   */
  public boolean runStep (SimulationResults results) {
    m_message_mgr.flushMessageQueues();
    boolean ended = true;

    for (Agent i: m_agents) {
      // Si el agente ya salió del laberinto no lo movemos más, pero si no ha
      // salido hacemos que calcule su siguiente movimiento
      Direction dir;
      if (m_maze.containsPoint(i.getPos()))
        dir = i.getNextMovement();
      else
        dir = Direction.NONE;

      // Restringimos el movimiento del agente para que no atraviese paredes
      // u otros agentes independientemente de errores que se hayan podido
      // cometer a la hora de programar a los agentes
      if (movementAllowed(i.getPos(), dir)) {
        i.doMovement(dir);
        results.agentWalked(i);
      }

      if (m_maze.containsPoint(i.getPos()))
        ended = false;
      else
        results.agentFinished(i);
    }

    repaint();
    return ended;
  }

  /**
   * @return El número de instancias de la clase que han sido creadas.
   */
  public static int getInstancesCreated () {
    return s_instance;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals (Object obj) {
    return this == obj;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode () {
    return new Integer(s_instance).hashCode();
  }

  /**
   * Obtiene el índice de la lista de agentes del agente que está situado bajo
   * el cursor.
   *
   * @param mouse_pos
   *          Posición del ratón.
   * @return Índice del agente colocado en la posición indicada dentro del
   *         entorno.
   */
  private int getAgentIndexUnderMouse (Point mouse_pos) {
    Point maze_pos = EnvironmentPanel.screenCoordToGrid(mouse_pos);
    int ag_index = -1;

    for (int i = 0; i < m_agents.size(); i++) {
      Agent current = m_agents.get(i);
      if (current.getX() == maze_pos.getX() && current.getY() == maze_pos.getY()) {
        ag_index = i;
        break;
      }
    }

    return ag_index;
  }

}
