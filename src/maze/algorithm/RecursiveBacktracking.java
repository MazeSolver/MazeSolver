/**
 * @file RecursiveBacktracking.java
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
public class RecursiveBacktracking extends MazeCreationAlgorithm {

  private final static short MAX_NEIGHBOUR = 4;
  private ArrayList <ArrayList <Boolean>> m_included_cells;
  private ArrayList <ArrayList <MazeCell>> m_maze;

  /**
   * @param rows
   * @param columns
   */
  public RecursiveBacktracking (int rows, int columns) {
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
    explore(0, 0);
    return m_maze;
  }

  /**
   * Explora los multiples caminos de la casilla i,j
   * @param i
   * @param j
   */
  private void explore (int i, int j) {
    ArrayList <Direction> dir = getDirections(i, j);
    for (int k = 0; k < dir.size(); k++) {
      Direction auxDir = dir.get(k);
      Pair <Integer, Integer> desp = dir.get(k).decompose();
      if (checkDir(i, j, auxDir)) {
        throwWall(i, j, dir.get(k));
        explore(i + desp.second, j + desp.first);
      }
    }
  }

  /**
   *
   * @param i
   * @param j
   * @param dir
   * @return devuelve true si la dirección dir en la casilla i,j es viable para
   *         ir. Es decir, si en esa dirección hay una casilla que no ha sido
   *         visitada aún.
   */
  private Boolean checkDir (final int i, final int j, final Direction dir) {
    switch (dir) {
      case UP:
        if (!m_included_cells.get(i - 1).get(j)) {
          return true;
        }
        break;
      case DOWN:
        if (!m_included_cells.get(i + 1).get(j)) {
          return true;
        }
        break;
      case LEFT:
        if (!m_included_cells.get(i).get(j - 1)) {
          return true;
        }
        break;
      case RIGHT:
        if (!m_included_cells.get(i).get(j + 1)) {
          return true;
        }
        break;
    }
    return false;
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
      if (i != 0 && toDir(k) == Direction.UP && !m_included_cells.get(i - 1).get(j))
        directions.add(Direction.UP);
      else if (j != 0 && toDir(k) == Direction.LEFT && !m_included_cells.get(i).get(j - 1))
        directions.add(Direction.LEFT);
      else if (i != m_rows - 1 && toDir(k) == Direction.DOWN && !m_included_cells.get(i + 1).get(j))
        directions.add(Direction.DOWN);
      else if (j != m_columns - 1 && toDir(k) == Direction.RIGHT
          && !m_included_cells.get(i).get(j + 1))
        directions.add(Direction.RIGHT);
    }
    return toRand(directions);
  }

  /**
   *
   * @param vector
   * @return dado un vector de direcciones posibles para visitar, devuelve un
   *         vector con esas mismas direcciones pero desordenadas aleatoriamente
   */
  private ArrayList <Direction> toRand (ArrayList <Direction> vector) {
    ArrayList <Direction> rand = new ArrayList <Direction>();
    int nextDir = 0;
    while (!vector.isEmpty()) {
      nextDir = (int) Math.round(0 + (Math.random() * (vector.size() - 1)));
      rand.add(vector.get(nextDir));
      vector.remove(nextDir);
    }
    return rand;
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
