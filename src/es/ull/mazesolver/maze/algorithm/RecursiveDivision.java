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

import es.ull.mazesolver.maze.MazeCreationAlgorithm;

/**
 * Implementación de algoritmo Division Recursiva para la generación aleatoria
 * de laberintos perfectos. (implementado de forma iterativa)
 */
public class RecursiveDivision extends MazeCreationAlgorithm {

  private final static int HORIZONTAL = 0;
  private final static int VERTICAL = 0;

  /**
   * @param rows
   *          Número de filas del laberinto.
   * @param columns
   *          Número de columnas del laberinto.
   */
  public RecursiveDivision (int rows, int columns) {
    super(rows, columns);
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.maze.MazeCreationAlgorithm#runCreationAlgorithm()
   */
  @Override
  protected void runCreationAlgorithm () {
    divide(0, 0, m_rows, m_columns, chooseOrientation(m_rows, m_columns));
  }

  /**
   * Funcion principal recursiva que va generando sub laberintos
   *
   * @param y
   *          Posición en el eje Y desde la que se quiere partir.
   * @param x
   *          Posición en el eje X desde la que se quiere partir.
   * @param rows
   *          Número de filas del sub Laberinto.
   * @param columns
   *          Número de columnas del sub laberinto.
   * @param orientation
   *          Orientación a seguir para generar el sub laberinto
   */
  private void divide (int x, int y, int rows, int columns, int orientation) {

  }

  /**
   * Función auxiliar para elegir la orientación por la que ir dividiendo el
   * laberinto
   *
   * @param rows
   *          Número de filas del Laberinto.
   * @param columns
   *          Número de columnas del laberinto.
   * @return orientación a seguir para ir dividiendo el laberinto.
   */
  private int chooseOrientation (int rows, int columns) {
    if (columns < rows)
      return VERTICAL;
    else if (columns > rows)
      return HORIZONTAL;
    else
      return (int) (Math.random() * 2);
  }

}
