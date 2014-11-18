/**
 * @file HuntAndKill.java
 * @date 10 Nov 2014
 */
package maze.algorithm;

import java.awt.Point;
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

  /**
   * @param rows
   * @param columns
   */
  public HuntAndKill (int rows, int columns) {
    super(rows, columns);
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
    Point p = new Point(x, y);
    while (cellVisitedCount < (m_columns * m_rows)) {
      walk(p);
      p = hunt();
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
  private void walk (Point p) {
    Pair <Integer, Integer> desp;
    Direction dir = getRandomDirection(p.x, p.y);
    while (dir != Direction.NONE) {
      openPassage(p.x, p.y, dir);
      cellVisitedCount++;
      desp = dir.decompose();
      p.x += desp.second;
      p.y += desp.first;
      m_included_cells.get(p.x).set(p.y, true);
      dir = getRandomDirection(p.x, p.y);
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
    Pair <Integer, Integer> desp;
    Point p = new Point();
    for (int i = 0; i < m_rows; i++) {
      for (int j = 0; j < m_columns; j++) {
        Direction dir = getRandomDirection(i, j);
        if (dir != Direction.NONE) {
          cellVisitedCount++;
          desp = dir.decompose();
          p.x = i + desp.second;
          p.y = j + desp.first;
          openPassage(i, j, dir);
          m_included_cells.get(p.x).set(p.y, true);
          return p;
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
      if (x >= 0 && y >= 0 && x < m_rows && y < m_columns && !m_included_cells.get(x).get(y)) {
        directions.add(toDir(k));
      }
    }
    if (directions.isEmpty()) {
      return Direction.NONE;
    }
    int nextDir = (int) Math.round(0 + (Math.random() * (directions.size() - 1)));
    return directions.get(nextDir);
  }

}
