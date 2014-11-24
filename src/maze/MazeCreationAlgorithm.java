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
 * @file MazeCreationAlgorithm.java
 * @date 21/10/2014
 */
package maze;

import java.awt.Point;
import java.util.ArrayList;

import util.Pair;

/**
 * Interfaz que encapsula un algoritmo de creación de laberintos.
 */
public abstract class MazeCreationAlgorithm {
  public static int MIN_ROWS = 5;
  public static int MIN_COLUMNS = 5;

  protected int m_rows, m_columns;
  protected ArrayList <ArrayList <MazeCell>> m_maze;
  protected Point m_maze_exit;

  /**
   * @param rows
   *          Número de filas del laberinto.
   * @param columns
   *          Número de columnas del laberinto.
   */
  public MazeCreationAlgorithm (int rows, int columns) {
    if (rows <= MIN_ROWS || columns <= MIN_COLUMNS)
      throw new IllegalArgumentException("El número de filas o columnas es demasiado pequeño");
    m_rows = rows;
    m_columns = columns;
    m_maze = initializeMaze();
  }

  /**
   * Devuelve la posición de la salida del laberinto.
   * @return La posición de la salida del laberinto.
   */
  public Point getExit () {
    return new Point(m_maze_exit);
  }

  /**
   * @return La matriz que contiene las celdas del laberinto.
   */
  public abstract ArrayList <ArrayList <MazeCell>> createMaze ();

  /**
   * Este método crea un laberinto vacío a partir del número de filas y columnas
   * especificado en el constructor. Hace más sencillo a las subclases
   * implementar el método "createMaze()".
   *
   * @return Un laberinto vacío del tamaño especificado.
   */
  protected ArrayList <ArrayList <MazeCell>> initializeMaze () {
    ArrayList <ArrayList <MazeCell>> maze = new ArrayList <ArrayList <MazeCell>>(m_rows);
    for (int i = 0; i < m_rows; i++) {
      maze.add(new ArrayList <MazeCell>(m_columns));
      for (int j = 0; j < m_columns; j++)
        maze.get(i).add(new MazeCell());
    }

    return maze;
  }

  /**
   * Abre un pasillo entre la celda (x,y) y su adyacente en la dirección
   * indicada.
   *
   * @param x
   *          Posición en el eje X (COLUMNA).
   * @param y
   *          Posición en el eje Y (FILA).
   * @param dir
   *          Dirección hacia la que abrir el camino.
   */
  protected void openPassage (int x, int y, final Direction dir) {
    Pair <Integer, Integer> desp = dir.decompose();
    m_maze.get(y).get(x).unsetWall(dir);
    m_maze.get(y + desp.second).get(x + desp.first).unsetWall(dir.getOpposite());
  }

  /**
   * Abre una salida en una casilla aleatoria por los bordes del laberinto
   */
  protected void createExit () {
    // Decidimos en qué borde vamos a crear la salida
    Direction dir = Direction.random();

    // Posición en la que se abrirá el hueco: Puede ser tanto una coordenada en
    // X como en Y
    int pos;
    if (dir == Direction.UP || dir == Direction.DOWN)
      pos = (int) (Math.random() * m_columns);
    else
      pos = (int) (Math.random() * m_rows);

    // Cogemos la celda de salida y abrimos el hueco
    m_maze_exit = new Point();
    switch (dir) {
      case UP:
        m_maze_exit.x = pos;
        m_maze_exit.y = 0;
        break;
      case DOWN:
        m_maze_exit.x = pos;
        m_maze_exit.y = m_rows - 1;
        break;
      case LEFT:
        m_maze_exit.x = 0;
        m_maze_exit.y = pos;
        break;
      case RIGHT:
        m_maze_exit.x = m_columns - 1;
        m_maze_exit.y = pos;
        break;
      default: break;
    }

    m_maze.get(m_maze_exit.y).get(m_maze_exit.x).unsetWall(dir);
  }
}
