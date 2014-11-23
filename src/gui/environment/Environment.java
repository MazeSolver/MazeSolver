/**
 * @file Environment.java
 * @date 25/10/2014
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

import com.tomtessier.scrollabledesktop.BaseInternalFrame;

/**
 * Una instancia de esta clase representa un entorno de ejecución, formado por
 * un laberinto y por un conjunto de agentes.
 */
public class Environment extends BaseInternalFrame {
  private static final long serialVersionUID = 1L;
  private static final int WINDOW_BORDER_WIDTH = 11;
  private static final int WINDOW_BORDER_HEIGHT = 34;
  private static final int WINDOWS_OFFSET = 20;

  private static int s_instance = 0;
  private static Point start_pos = new Point();

  private Maze m_maze;
  private ArrayList<Agent> m_agents;
  private int m_selected, m_hovered;

  /**
   * Constructor para las clases de tipo entorno.
   * @param maze Laberinto en el que se basa el entorno. Puede ser compartido
   * entre varios entornos.
   */
  public Environment (Maze maze) {
    super("Env " + (++s_instance), false, false, false, false);
    setMaze(maze);

    setVisible(true);
    setContentPane(new EnvironmentPanel(this));
    updateSize();

    setLocation(start_pos);
    start_pos.x += WINDOWS_OFFSET;
    start_pos.y += WINDOWS_OFFSET;

    m_selected = m_hovered = -1;
    m_agents = new ArrayList<Agent>();

    // Añadimos la escucha del cursor para permitir al usuario seleccionar un
    // agente.
    getContentPane().addMouseListener(new MouseAdapter() {
      /* (non-Javadoc)
       * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
       */
      @Override
      public void mousePressed (MouseEvent e) {
        m_selected = getAgentIndexUnderMouse(e.getPoint());
        repaint();
        super.mousePressed(e);
      }
    });

    getContentPane().addMouseMotionListener(new MouseMotionListener() {
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
          if (grid_pos.x >= 0 && grid_pos.x < m_maze.getWidth() &&
              grid_pos.y >= 0 && grid_pos.y < m_maze.getHeight()) {
            ag.setPosition(grid_pos);
            repaint();
          }
        }
      }
    });

    moveToFront();
  }

  /**
   * @return Laberinto base del entorno.
   */
  public Maze getMaze () {
    return m_maze;
  }

  /**
   * Cambia el laberinto del entorno. La memoria almacenada del/los agentes
   * sería inválida en caso de que contuviera información sobre la ruta a llevar
   * a cabo en el entorno, pero no cuando la memoria fuera un conjunto de reglas
   * o una tabla de percepción-acción.
   * @param maze Laberinto en el que se basa el entorno.
   */
  public void setMaze (Maze maze) {
    if (maze != null) {
      m_maze = maze;
      repaint();
    }
    else
      throw new IllegalArgumentException("El laberinto debe ser válido");
  }

  /**
   * Actualiza el tamaño de la ventana con respecto al tamaño de su contenido,
   * que es la representación del entorno.
   */
  public void updateSize () {
    EnvironmentPanel panel = ((EnvironmentPanel) getContentPane());
    panel.updateSize();
    setSize(panel.getWidth() + WINDOW_BORDER_WIDTH,
            panel.getHeight() + WINDOW_BORDER_HEIGHT);
    repaint();
  }

  /**
   * Este método permite saber lo que puede ver un agente si mira en una
   * dirección específica. La versión en la clase principal no maneja agentes,
   * por lo que debe ser sobrecargada para gestionarlos.
   * @param pos Posición desde la que mirar.
   * @param dir Dirección hacia la que mirar.
   * @return Lo que vería un agente en la posición especificada si mirara hacia
   * la dirección indicada.
   */
  public MazeCell.Vision look (Point pos, Direction dir) {
    if (m_maze.get(pos.y, pos.x).hasWall(dir))
      return MazeCell.Vision.WALL;

    Point n_pos = dir.movePoint(pos);
    if (n_pos.x < 0 || n_pos.y < 0 || n_pos.x >= m_maze.getWidth() ||
        n_pos.y >= m_maze.getHeight())
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
   * @param pos Posición de partida.
   * @param dir Dirección de movimiento.
   * @return true si se puede y false si no.
   */
  public boolean movementAllowed (Point pos, Direction dir) {
    MazeCell.Vision vision = look (pos, dir);
    return vision == MazeCell.Vision.EMPTY ||
           vision == MazeCell.Vision.OFFLIMITS;
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
    return new Integer(s_instance).hashCode();
  }

  /**
   * Añade un agente al entorno.
   * @param ag Agente que se quiere añadir al entorno.
   */
  public void addAgent (Agent ag) {
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
  }

  /**
   * Elimina un agente del entorno.
   * @param ag Referencia al agente que se quiere eliminar.
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
      throw new IllegalArgumentException("El agente no se encuentra en el entorno");

    repaint();
  }

  /**
   * Extrae una referencia al agente seleccionado dentro del entorno.
   * @return Agente seleccionado actualmente en el entorno o null si no hay
   * ninguno seleccionado.
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
   * @return Agente sobre el que se tiene el cursor actualmente en el entorno o
   * null si el cursor no está encima de ningún agente.
   */
  public Agent getHoveredAgent () {
    if (m_hovered == -1)
      return null;
    else
      return m_agents.get(m_hovered);
  }

  /**
   * Extrae una referencia a un agente del entorno.
   * @param index Índice del agente que se quiere consultar.
   * @return Agente número 'index' dentro del entorno.
   */
  public Agent getAgent (int index) {
    if (index < 0 || index >= m_agents.size())
      throw new IllegalArgumentException("El índice está fuera de rango");

    return m_agents.get(index);
  }

  /**
   * Extrae una copia profunda de la lista de agentes que hay dentro del
   * entorno. Hay que tener en cuenta que cualquier modificación en esta lista
   * no va a tener ninguna repercusión en los agentes o el entorno original.
   * @return Copia de la lista de agentes dentro del entorno.
   */
  public ArrayList <Agent> getAgents () {
    ArrayList <Agent> agents = new ArrayList <Agent>();

    try {
      for (Agent i: m_agents)
        agents.add((Agent) i.clone());
    }
    catch (CloneNotSupportedException e) {}

    return agents;
  }

  /**
   * @return Número de agentes actualmente en el entorno.
   */
  public int getAgentCount () {
    return m_agents.size();
  }

  /**
   * Ejecuta un paso de la simulación de ejecución de los agentes en el entorno
   * y devuelve el resultado de la ejecución.
   * @return true si todos los agentes han salido del laberinto y false en otro caso.
   */
  public boolean runStep () {
    boolean ended = true;

    for (Agent i: m_agents) {
      Direction dir = i.getNextMovement();
      if (movementAllowed(new Point(i.getX(),  i.getY()), dir))
        i.doMovement(dir);

      if (i.getX() >= 0 && i.getY() >= 0 && i.getX() < m_maze.getWidth() &&
          i.getY() < m_maze.getHeight())
        ended = false;
    }

    repaint();
    return ended;
  }

  /**
   * @param mouse_pos Posición del ratón.
   * @return Índice del agente colocado en la posición indicada dentro del
   *         entorno.
   */
  private int getAgentIndexUnderMouse (Point mouse_pos) {
    Point maze_pos = EnvironmentPanel.screenCoordToGrid(mouse_pos);
    int ag_index = -1;

    for (int i = 0; i < m_agents.size(); i++) {
      Agent current = m_agents.get(i);
      if (current.getX() == maze_pos.getX() &&
          current.getY() == maze_pos.getY()) {
        ag_index = i;
        break;
      }
    }

    return ag_index;
  }

}
