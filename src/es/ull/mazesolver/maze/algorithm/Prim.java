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
 * @file Prim.java
 * @date 26 Oct 2014
 */
package es.ull.mazesolver.maze.algorithm;

import java.util.ArrayList;

import es.ull.mazesolver.maze.MazeCreationAlgorithm;
import es.ull.mazesolver.util.Direction;
import es.ull.mazesolver.util.Pair;

/**
 * Implementación del algoritmo de Prim para la generación aleatoria de
 * laberintos perfectos.
 */
public class Prim extends MazeCreationAlgorithm {
  private ArrayList <ArrayList <Boolean>> m_included_cells;
  private ArrayList <short []> walls;

  /**
   * Constructor. Crea una nueva instancia de la clase.
   *
   * @param rows
   *          Número de filas del laberinto.
   * @param columns
   *          Número de columnas del laberinto.
   */
  public Prim (int rows, int columns) {
    super(rows, columns);
    walls = new ArrayList <short []>();
    m_included_cells = new ArrayList <ArrayList <Boolean>>(rows);

    // Creamos una matriz de visitados para saber en cada momento cuáles son
    // las celdas que no se han visitado todavía.
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
    short y = 0;
    short x = 0;
    // Empezar el laberinto con todo lleno de paredes y selecionar una celda.
    m_included_cells.get(y).set(x, true);
    addCell(y, x);
    // Mientras haya celdas sin visitar, seguir visitando.
    int nextWall = 0;
    while (!walls.isEmpty()) {
      // Seleccionamos una celda y una direccion de dentro de las posibles que
      // no hemos escogido aun.
      nextWall = (int) Math.round(0 + (Math.random() * (walls.size() - 1)));
      y = walls.get(nextWall)[0];
      x = walls.get(nextWall)[1];
      Direction dir = Direction.fromValue(walls.get(nextWall)[2]);
      Pair <Integer, Integer> desp = dir.decompose();

      // Si la celda vecina a la posicion i,j +dir sigue estando disponible
      // la elegimos y agregamos las celdas vecinas a esta al conjunto, si no
      // eliminamos dicha posicion con dicha direccion para que no vuelva
      // a salir de forma aleatoria
      if (!m_included_cells.get(y + desp.second).get(x + desp.first)) {
        openPassage(y, x, dir);
        m_included_cells.get(y + desp.second).set(x + desp.first, true);
        addCell(y + desp.second, x + desp.first);
      }
      walls.remove(nextWall);
    }
  }

  /**
   * Añade las celdas adyacentes a la (x, y) a la lista de paredes.
   *
   * @param y
   *          Posición en el eje Y desde la que se quiere partir.
   * @param x
   *          Posición en el eje X desde la que se quiere partir.
   */
  private void addCell (final int y, final int x) {
    for (short k = 1; k < Direction.MAX_DIRECTIONS; k++) {
      Direction dir = Direction.fromIndex(k);
      Pair <Integer, Integer> desp = dir.decompose();
      if ((y + desp.second >= 0) && (x + desp.first >= 0) && (y + desp.second < m_rows)
          && (x + desp.first < m_columns)
          && !m_included_cells.get(y + desp.second).get(x + desp.first)) {
        short [] aux = {(short) y, (short) x, dir.val};
        walls.add(aux);
      }
    }
  }
}