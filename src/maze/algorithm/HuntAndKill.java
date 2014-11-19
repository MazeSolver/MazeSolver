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

/**
 *
 */
public class HuntAndKill extends MazeCreationAlgorithm {
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
    int x = (int)(Math.random() * m_rows);
    int y = (int)(Math.random() * m_columns);
    Point p = new Point(x, y);
    while (p != null) {
      walk(p);
      p = hunt();
    }
    return m_maze;
  }

  /**
   * Dada una posición de inicio va explorando dicho camino mientras no
   * encuentre un nuevo camino.
   *
   * @param x
   * @param y
   */
  private void walk (Point p) {
    Direction dir = getRandomDirection(p.x, p.y);
    while (dir != Direction.NONE) {
      openPassage(p.x, p.y, dir);
      p = dir.movePoint(p);
      m_included_cells.get(p.y).set(p.x, true);
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
    for (int i = 0; i < m_rows; i++) {
      for (int j = 0; j < m_columns; j++) {
        Direction dir = getRandomDirection(j, i);
        if (dir != Direction.NONE) {
          openPassage(j, i, dir);
          Point p = dir.movePoint(new Point(j, i));
          m_included_cells.get(p.y).set(p.x, true);
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
  private Direction getRandomDirection (final int x, final int y) {
    ArrayList <Direction> directions = new ArrayList <Direction>();
    Point actual = new Point(x, y);

    // Comprobamos qué posiciones de alrededor son válidas y no se han visitado
    // Suponemos que la posición proporcionada es válida para empezar
    for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
      Direction dir = Direction.fromIndex(i);
      Point next = dir.movePoint(actual);

      if (next.y >= 0 && next.y < m_rows &&
          next.x >= 0 && next.x < m_columns &&
          !m_included_cells.get(next.y).get(next.x))
        directions.add(dir);
    }

    if (directions.isEmpty())
      return Direction.NONE;
    else
      return directions.get((int)(Math.random() * directions.size()));
  }

}
