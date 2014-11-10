/**
 * @file HuntAndKill.java
 * @date 10 Nov 2014
 */
package maze.algorithm;

import java.util.ArrayList;

import maze.Direction;
import maze.MazeCell;
import maze.MazeCreationAlgorithm;
import util.Pair;

/**
 *
 */
public class HuntAndKill extends MazeCreationAlgorithm {
  private final static short MAX_NEIGHBOUR = 4;
  private short cellVisitedCount = 0;
  private ArrayList <ArrayList <Boolean>> m_included_cells;
  private ArrayList <ArrayList <MazeCell>> m_maze;

  /**
   * @param rows
   * @param columns
   */
  public HuntAndKill (int rows, int columns) {
    super(rows, columns);
    m_maze = initializeMaze();
    m_included_cells = new ArrayList <ArrayList <Boolean>>(rows);
    // Creamos una matriz de visitados para saber en cada momento cuáles son
    // las celdas que no se han visitado todavía.
    for (int i = 0; i < rows; i++) {
      m_included_cells.add(new ArrayList <Boolean>(columns));
      for (int j = 0; j < columns; j++)
        m_included_cells.get(i).add(false);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see maze.MazeCreationAlgorithm#createMaze()
   */
  @Override
  public ArrayList <ArrayList <MazeCell>> createMaze () {
    int x = (int) Math.round(0 + (Math.random() * (m_rows - 1)));
    int y = (int) Math.round(0 + (Math.random() * (m_columns - 1)));
    int [] pos = {x, y};
    while (cellVisitedCount < ((m_columns * m_rows) - 1)) {
      walk(pos[0], pos[1]);
      pos = hunt();
    }
    return m_maze;
  }

  /**
   * Dada una posición de inicio va explorando dicho camino hasta que no
   * encuentre un nuevo camino.
   *
   * @param x
   * @param y
   */
  private void walk (int x, int y) {
    Pair <Integer, Integer> desp;
    Direction dir = getRandomDirection(x, y);
    while (dir.val != 0) {
      throwWall(x, y, dir);
      desp = dir.decompose();
      x = x + desp.second;
      y = y + desp.first;
      dir = getRandomDirection(x, y);
      cellVisitedCount++;
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
  private int [] hunt () {
    Pair <Integer, Integer> desp;
    int pos [] = {0, 0};
    for (int i = 0; i < m_rows; i++) {
      for (int j = 0; j < m_columns; j++) {
        Direction dir = getRandomDirection(i, j);
        if (m_included_cells.get(i).get(j) && dir.val != 0) {
          cellVisitedCount++;
          desp = dir.decompose();
          pos[0] = i + desp.second;
          pos[1] = j + desp.first;
          throwWall(i, j, dir);
          return pos;
        }
      }
    }
    return null;
  }

  /**
   *
   * @param i
   * @param j
   * @return retorna una direccion aleatoria dentro de las posibles a las que ir
   *         en la casilla dada por las posiciones i y j
   */
  private Direction getRandomDirection (final int i, final int j) {
    ArrayList <Direction> directions = new ArrayList <Direction>();
    Pair <Integer, Integer> desp;
    for (short k = 0; k < MAX_NEIGHBOUR; k++) {
      desp = toDir(k).decompose();
      int x = i + desp.second;
      int y = j + desp.first;
      if (x >= 0 && y >= 0 && x < (m_rows) && y < (m_columns) && !m_included_cells.get(x).get(y)) {
        directions.add(toDir(k));
      }
    }
    if (directions.isEmpty()) {
      short zero = 0x0000;
      return Direction.fromValue(zero);
    }
    int nextDir = (int) Math.round(0 + (Math.random() * (directions.size() - 1)));
    return directions.get(nextDir);
  }

  /**
   * Convierte un número entre 1 y 4 en una dirección.
   *
   * @param number
   *          Número a convertir.
   * @return Dirección asociada al número.
   */
  private static Direction toDir (short number) {
    switch (number) {
      case 0:
        return Direction.UP;
      case 1:
        return Direction.LEFT;
      case 2:
        return Direction.DOWN;
      case 3:
        return Direction.RIGHT;
    }
    return null;
  }

  /**
   * Elimina la pared colocada en la dirección dir a partir de la celda (i, j).
   *
   * @param i
   * @param j
   * @param dir
   */
  private void throwWall (final int i, final int j, final Direction dir) {
    m_maze.get(i).get(j).unsetWall(dir);
    Pair <Integer, Integer> desp = dir.decompose();
    m_included_cells.get(i + desp.second).set(j + desp.first, true);
    m_maze.get(i + desp.second).get(j + desp.first).unsetWall(dir.getOpposite());
  }

}
