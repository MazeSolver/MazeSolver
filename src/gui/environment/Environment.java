/**
 * @file Environment.java
 * @date 25/10/2014
 */
package gui.environment;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JInternalFrame;

import maze.Direction;
import maze.Maze;
import maze.MazeCell;
import util.Pair;
import agent.Agent;

/**
 * Una instancia de esta clase representa un entorno de ejecución, formado por
 * un laberinto y por un conjunto de agentes.
 */
public abstract class Environment extends JInternalFrame {
  private static final long serialVersionUID = 1L;
  private static final int WINDOW_BORDER_WIDTH = 11;
  private static final int WINDOW_BORDER_HEIGHT = 34;
  private static final int WINDOWS_OFFSET = 20;

  private static int s_instance = 0;
  private static Point start_pos = new Point();

  protected Maze m_maze;

  /**
   * Constructor para las clases de tipo entorno.
   * @param maze Laberinto en el que se basa el entorno. Puede ser compartido
   * entre varios entornos.
   */
  protected Environment (Maze maze) {
    super("Environment " + (++s_instance), false, false, false, false);
    setMaze(maze);

    setVisible(true);
    setContentPane(new EnvironmentPanel(this));
    updateSize();

    setLocation(start_pos);
    start_pos.x += WINDOWS_OFFSET;
    start_pos.y += WINDOWS_OFFSET;

    // FIXME Esto debería poner el entorno delante de todos los demás que haya
    // abiertos, pero no lo hace.
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
    if (maze != null)
      m_maze = maze;
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

    Pair <Integer, Integer> desp = dir.decompose();
    Point n_pos = new Point(pos.x + desp.first, pos.y + desp.second);
    if (n_pos.x < 0 || n_pos.y < 0 || n_pos.x >= m_maze.getWidth() ||
        n_pos.y >= m_maze.getHeight())
      return MazeCell.Vision.OFFLIMITS;

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

  /**
   * @param ag Agente que se quiere añadir al entorno.
   * @return Una referencia al propio entorno. Si se trata de un entorno simple
   * y se añade un agente, este método devolverá un entorno de agentes múltiples
   * con la misma información que el anterior. En otro caso devuelve una
   * referencia al propio entorno. Actualizando las variables que referencian a
   * este objeto con el valor devuelto hacemos que se pueda tratar de manera
   * uniforme todos los entornos.
   */
  public abstract Environment addAgent (Agent ag);

  /**
   * @param ag Referencia al agente que se quiere eliminar.
   * @return Una referencia al propio entorno tras la modificación. Si se
   * elimina un agente de un entorno múltiple donde sólo hay 2 agentes, el
   * entorno devuelto será un entorno simple.
   */
  public abstract Environment removeAgent (Agent ag);

  /**
   * @return Agente seleccionado actualmente en el entorno o null si no hay
   * ninguno seleccionado.
   */
  public abstract Agent getSelectedAgent ();

  /**
   * @param index Índice del agente que se quiere consultar.
   * @return Agente número 'index' dentro del entorno.
   */
  public abstract Agent getAgent (int index);

  /**
   * @return Copia de la lista de agentes dentro del entorno.
   */
  public abstract ArrayList <Agent> getAgents ();

  /**
   * @return Número de agentes actualmente en el entorno.
   */
  public abstract int getAgentCount ();

  /**
   * Ejecuta un paso de la simulación de ejecución de los agentes en el entorno.
   * @return true si algún agente ha salido del laberinto y false en otro caso.
   */
  public abstract boolean runStep ();

}
