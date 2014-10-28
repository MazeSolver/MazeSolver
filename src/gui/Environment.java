/**
 * @file Environment.java
 * @date 25/10/2014
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;

import maze.Direction;
import maze.Maze;
import maze.MazeCell;
import agent.Agent;

/**
 * Una instancia de esta clase representa un entorno de ejecución, formado por
 * un laberinto y por un conjunto de agentes.
 */
public abstract class Environment extends JInternalFrame {
  private static final long serialVersionUID = 1L;
  protected static final int CELL_SIZE_PX = 5;

  private static int s_instance = 0;
  protected Maze m_maze;

  /**
   * Constructor para las clases de tipo entorno.
   * @param maze Laberinto en el que se basa el entorno. Puede ser compartido
   * entre varios entornos.
   */
  protected Environment (Maze maze) {
    super("Environment " + (++s_instance), true, true, false, false);
    setMaze(maze);

    setVisible(true);
    ((JComponent) getContentPane()).setOpaque(false);
    setSize(CELL_SIZE_PX * m_maze.getWidth(), CELL_SIZE_PX * m_maze.getHeight());
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

  /* (non-Javadoc)
   * @see javax.swing.JInternalFrame#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent (Graphics g) {
    super.paintComponent(g);

    // Configuramos la paleta
    g.setColor(Color.BLACK);

    int width = m_maze.getWidth();
    int height = m_maze.getHeight();

    // Dibujamos sólo el laberinto. Los agentes se dibujan en las respectivas
    // clases derivadas.
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final MazeCell actual = m_maze.get(y, x);
        Point pos = new Point(x * CELL_SIZE_PX, y * CELL_SIZE_PX);

        if (actual.hasWall(Direction.UP))
          g.drawLine(pos.x, pos.y, pos.x + CELL_SIZE_PX, pos.y);
        if (actual.hasWall(Direction.DOWN))
          g.drawLine(pos.x, pos.y + CELL_SIZE_PX, pos.x + CELL_SIZE_PX, pos.y + CELL_SIZE_PX);
        if (actual.hasWall(Direction.LEFT))
          g.drawLine(pos.x, pos.y, pos.x, pos.y + CELL_SIZE_PX);
        if (actual.hasWall(Direction.RIGHT))
          g.drawLine(pos.x + CELL_SIZE_PX, pos.y, pos.x + CELL_SIZE_PX, pos.y + CELL_SIZE_PX);
      }
    }
  }

}
