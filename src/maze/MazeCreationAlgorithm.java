/**
 * @file MazeCreationAlgorithm.java
 * @date 21/10/2014
 */
package maze;

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

  /**
   * @param rows Número de filas del laberinto.
   * @param columns Número de columnas del laberinto.
   */
  public MazeCreationAlgorithm (int rows, int columns) {
    if (rows <= MIN_ROWS || columns <= MIN_COLUMNS)
      throw new IllegalArgumentException("El número de filas o columnas es demasiado pequeño");
    m_rows = rows;
    m_columns = columns;
    m_maze = initializeMaze();
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

  protected void openPassage (final int i, final int j, final Direction dir) {
    Pair <Integer, Integer> desp = dir.decompose();
    m_maze.get(i).get(j).unsetWall(dir);
    m_maze.get(i + desp.second).get(j + desp.first).unsetWall(dir.getOpposite());
  }

  /**
   * Convierte un número entre 1 y 4 en una dirección.
   *
   * @param number
   *          Número a convertir.
   * @return Dirección asociada al número.
   */
  protected static Direction toDir (short number) {
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
}
