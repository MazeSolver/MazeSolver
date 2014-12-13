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
 * @file EmptyMaze.java
 * @date 10/12/2014
 */
package es.ull.mazesolver.maze.algorithm;

import es.ull.mazesolver.maze.MazeCreationAlgorithm;
import es.ull.mazesolver.util.Direction;

/**
 * Algoritmo que crea un laberinto que no contiene paredes. No se pueden añadir
 * ciclos al laberinto generado.
 */
public class EmptyMaze extends MazeCreationAlgorithm {

  /**
   * @param rows Número de filas del laberinto que se genere.
   * @param columns Número de columnas del laberinto que se genere.
   */
  public EmptyMaze (int rows, int columns) {
    super(rows, columns);
  }

  /* (non-Javadoc)
   * @see maze.MazeCreationAlgorithm#runCreationAlgorithm()
   */
  @Override
  protected void runCreationAlgorithm () {
    // Quitamos todas las paredes, incluidos los bordes
    for (int i = 0; i < m_rows; i++) {
      for (int j = 0; j < m_columns; j++) {
        m_maze.get(i).get(j).unsetWall(Direction.UP);
        m_maze.get(i).get(j).unsetWall(Direction.DOWN);
        m_maze.get(i).get(j).unsetWall(Direction.LEFT);
        m_maze.get(i).get(j).unsetWall(Direction.RIGHT);
      }
    }
  }

}
