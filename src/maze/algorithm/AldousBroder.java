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
  private ArrayList <ArrayList <MazeCell>> m_maze;

  /**
   * @param rows
   * @param columns
   */
  public AldousBroder (int rows, int columns) {
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
    m_included_cells.get(0).set(0, true);
    cellVisitedCount++;
  }

  /*
   * (non-Javadoc)
   *
   * @see maze.MazeCreationAlgorithm#createMaze()
   */
  @Override
  public ArrayList <ArrayList <MazeCell>> createMaze () {
    int i = 0;
    int j = 0;
    while (cellVisitedCount < ((m_columns * m_rows) - 1)) {
      Direction dir = getRandomDirection(i, j);
      throwWall(i, j, dir);
      Pair <Integer, Integer> desp = dir.decompose();
      i = i + desp.second;
      j = j + desp.first;
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
      if (i != 0 && toDir(k) == Direction.UP)
        directions.add(Direction.UP);
      else if (j != 0 && toDir(k) == Direction.LEFT)
        directions.add(Direction.LEFT);
      else if (i != m_rows - 1 && toDir(k) == Direction.DOWN)
        directions.add(Direction.DOWN);
      else if (j != m_columns - 1 && toDir(k) == Direction.RIGHT)
        directions.add(Direction.RIGHT);
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
    Pair <Integer, Integer> desp = dir.decompose();
    if (!m_included_cells.get(i + desp.second).get(j + desp.first)) {
      m_maze.get(i).get(j).unsetWall(dir);
      m_included_cells.get(i + desp.second).set(j + desp.first, true);
      m_maze.get(i + desp.second).get(j + desp.first).unsetWall(dir.getOpposite());
      cellVisitedCount++;
    }
  }

}
