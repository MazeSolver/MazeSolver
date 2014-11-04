/**
 * @file EnvironmentFrame.java
 * @date 29/10/2014
 */
package gui.environment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import maze.Direction;
import maze.Maze;
import maze.MazeCell;
import agent.Agent;

/**
 * Clase que permite dibujar un entorno en un panel.
 */
public class EnvironmentPanel extends JPanel {
  private static final long serialVersionUID = 1L;
  private static final int CELL_SIZE_PX = 5;

  private static double s_zoom = 1.0;
  private Environment m_env;

  /**
   * @param env
   *          Entorno que se dibujará en el panel.
   */
  public EnvironmentPanel (Environment env) {
    setEnvironment(env);
  }

  /**
   * @param env
   *          Nuevo entorno que se dibujará en el panel.
   */
  public void setEnvironment (Environment env) {
    m_env = env;
    updateSize();
  }

  /**
   * Actualiza el tamaño del panel si hay un entorno cargado y el entorno tiene
   * un laberinto válido cargado.
   */
  public void updateSize () {
    if (m_env != null) {
      Maze maze = m_env.getMaze();
      if (maze != null)
        setSize((int) Math.round(maze.getWidth() * CELL_SIZE_PX * s_zoom),
            (int) Math.round(maze.getHeight() * CELL_SIZE_PX * s_zoom));
    }
  }

  /**
   * @param zoom
   *          Nivel de escala que se quiere aplicar a la visualización de los
   *          paneles.
   */
  public static void setZoom (double zoom) {
    if (zoom >= 0.0)
      s_zoom = zoom;
  }

  /**
   * Traduce una coordenada de ratón en la pantalla (local al panel) a una
   * dirección de celda dentro de un laberinto.
   *
   * @param coord
   *          Coordenadas (x, y) del ratón.
   * @return Posición (x, y) dentro de la matriz del laberinto.
   */
  public static Point screenCoordToGrid (final Point coord) {
    return new Point((int) Math.round(coord.x / (CELL_SIZE_PX * s_zoom)),
        (int) Math.round(coord.y / (CELL_SIZE_PX * s_zoom)));
  }

  /*
   * (non-Javadoc)
   *
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent (Graphics g) {
    super.paintComponent(g);

    // Si no hay entorno asignado o el entorno no tiene laberinto, no dibujamos
    // nada.
    if (m_env == null)
      return;
    Maze maze = m_env.getMaze();
    if (maze == null)
      return;

    // Configuramos la paleta.
    g.setColor(Color.BLACK);

    int width = maze.getWidth();
    int height = maze.getHeight();

    double cell_size = CELL_SIZE_PX * s_zoom;

    // Dibujamos el laberinto.
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        final MazeCell actual = maze.get(y, x);
        Point pos = new Point((int) Math.round(x * cell_size), (int) Math.round(y * cell_size));

        if (actual.hasWall(Direction.UP))
          g.drawLine(pos.x, pos.y, (int) Math.round(pos.x + cell_size), pos.y);
        if (actual.hasWall(Direction.DOWN))
          g.drawLine(pos.x, (int) Math.round(pos.y + cell_size),
              (int) Math.round(pos.x + cell_size), (int) Math.round(pos.y + cell_size));
        if (actual.hasWall(Direction.LEFT))
          g.drawLine(pos.x, pos.y, pos.x, (int) Math.round(pos.y + cell_size));
        if (actual.hasWall(Direction.RIGHT))
          g.drawLine((int) Math.round(pos.x + cell_size), pos.y,
              (int) Math.round(pos.x + cell_size), (int) Math.round(pos.y + cell_size));
      }
    }

    // Dibujamos los agentes.
    g.setColor(Color.ORANGE);
    for (int i = 0; i < m_env.getAgentCount(); i++) {
      Agent ag = m_env.getAgent(i);
      g.fillOval((int) Math.round(ag.getX() * cell_size), (int) Math.round(ag.getY() * cell_size),
          (int) Math.round(cell_size - 1), (int) Math.round(cell_size - 1));
    }

    // Dibujamos el agente seleccionado con otro color para resaltarlo.
    Agent selected = m_env.getSelectedAgent();
    if (selected != null) {
      g.setColor(Color.RED);
      g.fillOval((int) Math.round(selected.getX() * cell_size),
          (int) Math.round(selected.getY() * cell_size), (int) Math.round(cell_size - 1),
          (int) Math.round(cell_size - 1));
    }
  }

}
