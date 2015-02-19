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
 * @file HuntAndKill.java
 * @date 10 Nov 2014
 */
package es.ull.mazesolver.maze.algorithm;

import java.awt.Point;
import java.util.ArrayList;

import es.ull.mazesolver.maze.MazeCreationAlgorithm;
import es.ull.mazesolver.util.Direction;

/**
 * Implementación del algoritmo de Hunt And Kill para la generación aleatoria de
 * laberintos perfectos.
 */
public class HuntAndKill extends MazeCreationAlgorithm {
  private ArrayList <ArrayList <Boolean>> m_included_cells;

  /**
   * @param rows
   *          Número de filas del laberinto.
   * @param columns
   *          Número de columnas del laberinto.
   */
  public HuntAndKill (int rows, int columns) {
    super(rows, columns);

    // Creamos una matriz de visitados para saber en cada momento cuáles son
    // las celdas que no se han visitado todavía.
    m_included_cells = new ArrayList <ArrayList <Boolean>>(rows);

    for (int y = 0; y < rows; y++) {
      m_included_cells.add(new ArrayList <Boolean>(columns));
      for (int x = 0; x < columns; x++)
        m_included_cells.get(y).add(false);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see maze.MazeCreationAlgorithm#runCreationAlgorithm()
   */
  @Override
  public void runCreationAlgorithm () {
    int x = (int) (Math.random() * m_rows);
    int y = (int) (Math.random() * m_columns);

    Point p = new Point(x, y);
    while (p != null) {
      walk(p);
      p = hunt();
    }
  }

  /**
   * Dada una posición de inicio va explorando dicho camino mientras no
   * encuentre un nuevo camino.
   *
   * @param p
   *          punto con las coordenadas x e y de las cuales se quiere empezar a
   *          buscar un nuevo camino nuevo
   */
  private void walk (Point p) {
    Direction dir = getRandomDirection(p.y, p.x);
    while (dir != Direction.NONE) {
      openPassage(p.y, p.x, dir);
      p = dir.movePoint(p);
      m_included_cells.get(p.y).set(p.x, true);
      dir = getRandomDirection(p.y, p.x);
    }
  }

  /**
   * Busca por todo el tablero una casilla explorada y que puede ser el origen
   * de una nueva exploracion (el metodo walk puede empezar por esa casilla)
   * Dicha casilla con ese posible camino es explorado por el metodo hunt (kill)
   *
   * @return devuelve una posición por la cual el "cursor" puede seguir
   *         explorando
   */
  private Point hunt () {
    for (int y = 0; y < m_rows; y++) {
      for (int x = 0; x < m_columns; x++) {
        Direction dir = getRandomDirection(y, x);
        if (dir != Direction.NONE) {
          openPassage(y, x, dir);
          Point p = dir.movePoint(new Point(x, y));
          m_included_cells.get(p.y).set(p.x, true);
          return p;
        }
      }
    }
    return null;
  }

  /**
   * @param y
   *          Posición en el eje Y desde la que se quiere partir.
   * @param x
   *          Posición en el eje X desde la que se quiere partir.
   * @return retorna una direccion aleatoria dentro de las posibles a las que ir
   *         en la casilla dada por las posiciones i y j
   */
  private Direction getRandomDirection (final int y, final int x) {
    ArrayList <Direction> directions = new ArrayList <Direction>();
    Point actual = new Point(x, y);

    // Comprobamos qué posiciones de alrededor son válidas y no se han visitado
    // Suponemos que la posición proporcionada es válida para empezar
    for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
      Direction dir = Direction.fromIndex(i);
      Point next = dir.movePoint(actual);

      if (next.y >= 0 && next.y < m_rows && next.x >= 0 && next.x < m_columns
          && !m_included_cells.get(next.y).get(next.x))
        directions.add(dir);
    }

    if (directions.isEmpty())
      return Direction.NONE;
    else
      return directions.get((int) (Math.random() * directions.size()));
  }

}
