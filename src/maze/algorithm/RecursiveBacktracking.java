/**
 * @file RecursiveBacktracking.java
 * @date 10 Nov 2014
 */
package maze.algorithm;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

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
  }

  /*
   * (non-Javadoc)
   *
   * @see maze.MazeCreationAlgorithm#createMaze()
   */
  @Override
  public ArrayList <ArrayList <MazeCell>> createMaze () {
    Stack<Point> stack = new Stack<Point>();
    Pair <Integer, Integer> desp;
    Point p = new Point(0, 0);
    Direction dir;
    stack.push(p);
    m_included_cells.get(p.x).set(p.y, true);
    while (!stack.isEmpty()) {
      p = stack.peek();
      dir = getDirections(p.x, p.y);
      if (dir == Direction.NONE) {
        stack.pop();
      }
      else {
        desp = dir.decompose();
        openPassage(p.x, p.y, dir);
        p.x = p.x + desp.second;
        p.y = p.y + desp.first;
        m_included_cells.get(p.x).set(p.y, true);
        stack.push(p);
      }
    }
    return m_maze;
  }

  /**
   *
   * @param i
   * @param j
   * @return retorna una direccion posible a la que ir en la casilla dada por
   *         las posiciones i y j
   */
  private Direction getDirections (int i, int j) {
    ArrayList <Direction> directions = new ArrayList <Direction>();
    for (short k = 0; k < MAX_NEIGHBOUR; k++) {
      Pair <Integer, Integer> desp = toDir(k).decompose();
      if ((i + desp.second >= 0) && (j + desp.first >= 0) && (i + desp.second < m_rows)
          && (j + desp.first < m_columns)
          && !m_included_cells.get(i + desp.second).get(j + desp.first)) {
        directions.add(toDir(k));
      }
    }
    if (directions.isEmpty()) {
      return Direction.NONE;
    }
    return directions.get((int) Math.round(0 + (Math.random() * (directions.size() - 1))));
  }

}
