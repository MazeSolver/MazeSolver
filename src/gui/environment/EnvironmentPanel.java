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
 * @file EnvironmentFrame.java
 * @date 29/10/2014
 */
package gui.environment;

import gui.PopupTip;
import gui.PopupTip.CloseOperationListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
  private Agent m_last_hovered;

  /**
   * @param env
   *          Entorno que se dibujará en el panel.
   */
  public EnvironmentPanel (Environment env) {
    setEnvironment(env);
    addMouseListener(new MouseAdapter() {
      /* (non-Javadoc)
       * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
       */
      @Override
      public void mouseExited (MouseEvent e) {
        resetPopup();
        super.mouseExited(e);
      }
    });
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
        setSize((int) Math.round((maze.getWidth() + 2) * CELL_SIZE_PX * s_zoom),
            (int) Math.round((maze.getHeight() + 2) * CELL_SIZE_PX * s_zoom));
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
    return new Point((int)(coord.x / (CELL_SIZE_PX * s_zoom)) - 1,
                     (int)(coord.y / (CELL_SIZE_PX * s_zoom)) - 1);
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

    // Calculamos una vez el tamaño de las celdas para utilizarlo en todos los
    // cálculos de dimensiones
    double cell_size = CELL_SIZE_PX * s_zoom;

    // Dibujamos el laberinto.
    g.setColor(Color.BLACK);
    for (int x = 0; x < maze.getWidth(); x++) {
      for (int y = 0; y < maze.getHeight(); y++) {
        final MazeCell actual = maze.get(y, x);
        Point pos = new Point((int) Math.round((x+1) * cell_size), (int) Math.round((y+1) * cell_size));

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
    for (int i = 0; i < m_env.getAgentCount(); i++)
      drawAgent(m_env.getAgent(i), Color.BLUE, g, cell_size);

    // Dibujamos el agente seleccionado con otro color para resaltarlo.
    Agent selected = m_env.getSelectedAgent();
    if (selected != null)
      drawAgent(selected, Color.RED, g, cell_size);

    // Dibujamos un marcador al agente sobre el que se encuentra el ratón y un
    // popup con su nombre.
    Agent hovered = m_env.getHoveredAgent();
    if (hovered != null) {
      g.setColor(Color.GREEN);
      g.drawOval((int) Math.round((hovered.getX()+1) * cell_size),
          (int) Math.round((hovered.getY()+1) * cell_size), (int) Math.round(cell_size - 1),
          (int) Math.round(cell_size - 1));

      if (m_last_hovered != hovered) {
        String name = hovered.getName();
        Point p = MouseInfo.getPointerInfo().getLocation();

        PopupTip.hide();
        // Hacemos esto para que después de que el popup se cierre, se vuelva
        // a abrir porque todavía el usuario quiere verlo
        PopupTip.setNextCloseOperationListener(new CloseOperationListener() {
          @Override
          public void onPopupClose () {
            m_last_hovered = null;
          }
        });
        PopupTip.show(this, name, p.x + 15, p.y);

        m_last_hovered = hovered;
      }
    }
    else
      resetPopup();
  }

  /**
   * Cierra el popup si está abierto y deja todo listo para detectar de nuevo
   * la selección de los agentes.
   */
  private synchronized void resetPopup () {
   PopupTip.hide();
    m_last_hovered = null;
  }

  /**
   * Dibuja un agente en el panel.
   * @param ag Agente que dibujar.
   * @param col Color en el que dibujar el agente.
   * @param g "Pincel" con el que hacer el dibujado.
   * @param cell_size Tamaño de las celdas en el panel.
   */
  private void drawAgent (Agent ag, Color col, Graphics g, double cell_size) {
    g.setColor(col);
    g.fillOval((int) Math.round((ag.getX()+1) * cell_size),
               (int) Math.round((ag.getY()+1) * cell_size),
               (int) Math.round(cell_size - 1),
               (int) Math.round(cell_size - 1));
  }
}
