/**
 * @file AldousBroder.java
 * @date 10 Nov 2014
 */
package maze.algorithm;

import java.awt.Point;
import java.util.ArrayList;

import maze.Direction;
import maze.MazeCell;
import maze.MazeCreationAlgorithm;

/**
 *
 */
public class AldousBroder extends MazeCreationAlgorithm {
  private short cellVisitedCount = 0;
  private ArrayList <ArrayList <Boolean>> m_included_cells;

  /**
   * @param rows
   * @param columns
   */
  public AldousBroder (int rows, int columns) {
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
    int x = (int)(Math.random() * m_columns);
    int y = (int)(Math.random() * m_rows);
    Point p = new Point(x, y);

    while (cellVisitedCount < (m_columns * m_rows)) {
      Direction dir = getRandomDirection(p.x, p.y);
      p = dir.movePoint(p);
      if (!m_included_cells.get(p.y).get(p.x)) {
        openPassage(p.x, p.y, dir.getOpposite());
        m_included_cells.get(p.y).set(p.x, true);
        cellVisitedCount++;
      }
    }
    return m_maze;
  }

  /**
   *
   * @param i
   * @param j
   * @return retorna una direccion aleatoria dentro de las posibles a las que ir
   *         en la casilla dada por las posiciones i y j
   */
  private Direction getRandomDirection (int x, int y) {
    Point p = new Point(x, y);
    Point next_pos;
    Direction dir;

    do {
      dir = Direction.random();
      next_pos = dir.movePoint(p);
    } while (next_pos.y < 0 || next_pos.y >= m_rows ||
             next_pos.x < 0 || next_pos.x >= m_columns);

    return dir;
  }

}
