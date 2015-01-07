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
 * @file RecursiveDivision.java
 * @date Jan 7, 2015
 */
package es.ull.mazesolver.maze.algorithm;

import java.awt.Point;

import es.ull.mazesolver.maze.MazeCreationAlgorithm;
import es.ull.mazesolver.util.Direction;
import es.ull.mazesolver.util.Pair;

/**
 * Implementación de algoritmo Division Recursiva para la generación aleatoria
 * de laberintos perfectos. (implementado de forma iterativa)
 */
public class RecursiveDivision extends MazeCreationAlgorithm {

  private final static int HORIZONTAL = 0;
  private final static int VERTICAL = 1;

  /**
   * @param rows
   *          Número de filas del laberinto.
   * @param columns
   *          Número de columnas del laberinto.
   */
  public RecursiveDivision (int rows, int columns) {
    super(rows, columns);
    for (int y = 0; y < m_rows; y++)
      for (int x = 0; x < m_columns; x++)
        for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
          Direction dir = Direction.fromIndex(i);
          Point next = dir.movePoint(new Point(x, y));

          if (next.y >= 0 && next.y < m_rows && next.x >= 0 && next.x < m_columns)
            m_maze.get(y).get(x).toggleWall(dir);
        }
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.maze.MazeCreationAlgorithm#runCreationAlgorithm()
   */
  @Override
  protected void runCreationAlgorithm () {
    divide(0, 0, m_columns, m_rows, chooseOrientation(m_columns, m_rows));
  }

  /**
   * Funcion principal recursiva que va generando sub laberintos
   *
   * @param x
   *          Posición en el eje X desde la que se quiere partir.
   * @param y
   *          Posición en el eje Y desde la que se quiere partir.
   * @param width
   *          Número de columnas del sub laberinto.
   * @param height
   *          Número de filas del sub Laberinto.
   * @param orientation
   *          Orientación a seguir para generar el sub laberinto
   */
  private void divide (int x, int y, int width, int height, int orientation) {
    if (height > 1 && width > 1) {

      // Inicio del muro del sub laberinto
      int wx = x, wy = y;
      wx += (orientation == HORIZONTAL)? 0 : (int) (Math.random() * (width - 1));
      wy += (orientation == HORIZONTAL)? (int) (Math.random() * (height - 1)) : 0;

      // Posicion de la puerta
      int px = wx, py = wy;
      px += (orientation == HORIZONTAL)? (int) (Math.random() * width) : 0;
      py += (orientation == HORIZONTAL)? 0 : (int) (Math.random() * height);

      // Direccion a la que moverse
      int dx = (orientation == HORIZONTAL)? 1 : 0;
      int dy = (orientation == HORIZONTAL)? 0 : 1;

      // Longitud del muro
      int length = (orientation == HORIZONTAL)? width : height;

      // Direccion donde dibujar el muro
      Direction dir = (orientation == HORIZONTAL)? Direction.DOWN : Direction.RIGHT;

      for (int i = 0; i < length; i++) {
        if (wx != px || wy != py) {
          Pair <Integer, Integer> desp = dir.decompose();
          m_maze.get(wy).get(wx).setWall(dir);
          m_maze.get(wy + desp.second).get(wx + desp.first).setWall(dir.getOpposite());
        }
        wx += dx;
        wy += dy;
      }

      int nx = x, ny = y;
      int w = (orientation == HORIZONTAL)? width : wx - x + 1;
      int h = (orientation == HORIZONTAL)? wy - y + 1 : height;
      divide(nx, ny, w, h, chooseOrientation(w, h));

      nx = (orientation == HORIZONTAL)? x : wx + 1;
      ny = (orientation == HORIZONTAL)? wy + 1 : y;
      w = (orientation == HORIZONTAL)? width : x + width - wx - 1;
      h = (orientation == HORIZONTAL)? y + height - wy - 1 : height;
      divide(nx, ny, w, h, chooseOrientation(w, h));
    }
  }

  /**
   * Función auxiliar para elegir la orientación por la que ir dividiendo el
   * laberinto
   *
   * @param width
   *          Número de columnas del Laberinto.
   * @param height
   *          Número de filas del laberinto.
   * @return orientación a seguir para ir dividiendo el laberinto.
   */
  private int chooseOrientation (int width, int height) {
    if (width < height)
      return HORIZONTAL;
    else if (height < width)
      return VERTICAL;
    else
      return (int) (Math.random() * 2);
  }

}
