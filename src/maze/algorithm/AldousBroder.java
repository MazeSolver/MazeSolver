/**
 * @file AldousBroder.java
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
public class AldousBroder extends MazeCreationAlgorithm {
  private final static short MAX_NEIGHBOUR = 4;
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
    int i = (int) Math.round(0 + (Math.random() * (m_rows - 1)));
    int j = (int) Math.round(0 + (Math.random() * (m_columns - 1)));
    while (cellVisitedCount < (m_columns * m_rows)) {
      Direction dir = getRandomDirection(i, j);
      Pair <Integer, Integer> desp = dir.decompose();
      if (!m_included_cells.get(i + desp.second).get(j + desp.first)) {
        openPassage(i, j, dir);
        m_included_cells.get(i + desp.second).set(j + desp.first, true);
        cellVisitedCount++;
      }
      i += desp.second;
      j += desp.first;
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
  private Direction getRandomDirection (final int i, final int j) {
    ArrayList <Direction> directions = new ArrayList <Direction>();
    for (short k = 0; k < MAX_NEIGHBOUR; k++) {
      Pair <Integer, Integer> desp = toDir(k).decompose();
      if ((i + desp.second >= 0) && (j + desp.first >= 0) && (i + desp.second < m_rows)
          && (j + desp.first < m_columns))
        directions.add(toDir(k));
    }
    int nextDir = (int) Math.round(0 + (Math.random() * (directions.size() - 1)));
    return directions.get(nextDir);
  }

}
