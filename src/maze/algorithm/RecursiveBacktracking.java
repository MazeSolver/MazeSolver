/**
 * @file RecursiveBacktracking.java
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
public class RecursiveBacktracking extends MazeCreationAlgorithm {

  private final static short MAX_NEIGHBOUR = 4;
  private ArrayList <ArrayList <Boolean>> m_included_cells;
  private ArrayList <Point> stack_positions;

  /**
   * @param rows
   * @param columns
   */
  public RecursiveBacktracking (int rows, int columns) {
    super(rows, columns);
    m_included_cells = new ArrayList <ArrayList <Boolean>>(rows);
    // Creamos una matriz de visitados para saber en cada momento cuáles son
    // las celdas que no se han visitado todavía.
    for (int i = 0; i < rows; i++) {
      m_included_cells.add(new ArrayList <Boolean>(columns));
      for (int j = 0; j < columns; j++)
        m_included_cells.get(i).add(false);
    }
    stack_positions = new ArrayList <Point>();
  }

  /*
   * (non-Javadoc)
   *
   * @see maze.MazeCreationAlgorithm#createMaze()
   */
  @Override
  public ArrayList <ArrayList <MazeCell>> createMaze () {
    Point p = new Point(0, 0);
    ArrayList <Direction> dir;
    stack_positions.add(0, p);
    while (!stack_positions.isEmpty()) {
      dir = getDirections(stack_positions.get(0).x, stack_positions.get(0).y);
      while (dir.isEmpty() && !stack_positions.isEmpty()) {
        stack_positions.remove(0);
        dir = getDirections(stack_positions.get(0).x, stack_positions.get(0).y);
      }
      if (!dir.isEmpty()) {
        int k = (int) Math.round(0 + (Math.random() * (dir.size() - 1)));
        p = stack_positions.get(0);
        Pair <Integer, Integer> desp = dir.get(k).decompose();
        if (!m_included_cells.get(p.x + desp.second).get(p.y + desp.first)) {
          openPassage(p.x, p.y, dir.get(k));
          m_included_cells.get(p.x + desp.second).set(p.y + desp.first, true);
          p.x = p.x + desp.second;
          p.y = p.y + desp.first;
          stack_positions.add(0, p);
        }
      }
    }
    return m_maze;
  }

  /**
   *
   * @param i
   * @param j
   * @return retorna un vector con las direcciones posibles a las que ir en la
   *         casilla dada por las posiciones i y j
   */
  private ArrayList <Direction> getDirections (int i, int j) {
    ArrayList <Direction> directions = new ArrayList <Direction>();
    for (short k = 0; k < MAX_NEIGHBOUR; k++) {
      Direction dir = toDir(k);
      Pair <Integer, Integer> desp = dir.decompose();
      if ((i + desp.second >= 0) && (j + desp.first >= 0) && (i + desp.second < m_rows)
          && (j + desp.first < m_columns)
          && !m_included_cells.get(i + desp.second).get(j + desp.first)) {
        directions.add(toDir(k));
      }
    }
    return directions;
  }

}
