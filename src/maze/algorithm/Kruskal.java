/**
 * @file Kruskal.java
 * @date 3 Nov 2014
 */
package maze.algorithm;

import java.util.ArrayList;

import maze.Direction;
import maze.MazeCell;
import maze.MazeCreationAlgorithm;
import util.Pair;

/**
 * Implementación del algoritmo de Kruskal para la generación aleatoria de
 * laberintos.
 */
public class Kruskal extends MazeCreationAlgorithm {
  private ArrayList <Integer> disjoint_set;
  private ArrayList <short []> walls;

  /**
   * @param rows Número de filas del laberinto.
   * @param columns Número de columnas del laberinto.
   */
  public Kruskal (int rows, int columns) {
    super(rows, columns);
    walls = new ArrayList <short []>();
    disjoint_set = new ArrayList <Integer>();

    // Creamos una matriz de visitados para saber en cada momento cuáles son
    // las celdas que no se han visitado todavía.
    for (int i = 0; i < m_rows * m_columns; i++)
      disjoint_set.add(i);
    addAll();
  }

  /*
   * (non-Javadoc)
   *
   * @see maze.MazeCreationAlgorithm#createMaze()
   */
  @Override
  public ArrayList <ArrayList <MazeCell>> createMaze () {
    int nextWall, i, j;
    while (!walls.isEmpty()) {
      // Seleccionamos una celda y una direccion de dentro de las posibles que
      // no hemos escogido aun.
      nextWall = (int) Math.round(0 + (Math.random() * (walls.size() - 1)));
      i = walls.get(nextWall)[0];
      j = walls.get(nextWall)[1];
      Direction dir = Direction.fromValue(walls.get(nextWall)[2]);
      Pair <Integer, Integer> desp = dir.decompose();
      // Si la celda vecina a la posicion i,j +dir pertenece a otro conjunto
      // entonces, la marcamos del mismo conjunto (y a cada elemento del mismo)
      // y abrimos el pasillo por ahi.
      if (value(i, j) != value(i + desp.second, j + desp.first)) {
        openPassage(j, i, dir);
        union(value(i, j), value(i + desp.second, j + desp.first));
      }
      walls.remove(nextWall);
    }
    return m_maze;
  }

  /**
   * Añade todos los muros a la lista de muros a elegir aleatoriamente
   */
  private void addAll () {
    for (int i = 0; i < m_rows; i++)
      for (int j = 0; j < m_columns; j++)
        for (short k = 1; k < Direction.MAX_DIRECTIONS; k++) {
          Direction dir = Direction.fromIndex(k);
          Pair <Integer, Integer> desp = dir.decompose();
          if ((i + desp.second >= 0) && (j + desp.first >= 0) && (i + desp.second < m_rows)
              && (j + desp.first < m_columns)) {
            short [] aux = {(short) i, (short) j, dir.val};
            walls.add(aux);
          }
        }
  }

  /**
   * @param x Posición en el eje X.
   * @param y Posición en el eje Y.
   * @return la posicion del vector dado por el punto x,y
   */
  private int pos (final int i, final int j) {
    return (i * m_columns) + j;
  }

  /**
   *
   * @param value_from valor representativo del conjunto que se va a ser unido
   *                   al otro conjunto
   * @param value_to   valor representativo del conjunto al que se va a unir el
   *                   otro conjunto
   *
   */
  private void union (final int value_from, final int value_to) {
    /*
     * Une dos conjuntos-disjuntos, de la forma siguiente, todo conjunto que
     * tenga como valor representativo value_from, lo mueve al conjunto de valor
     * value_to
     */
    for (int k = 0; k < disjoint_set.size(); k++)
      if (disjoint_set.get(k) == value_from)
        disjoint_set.set(k, value_to);
  }

  /**
   *
   * @param x Posición en el eje X.
   * @param y Posición en el eje Y.
   * @return valor, del conjunto del elemento i,j. Esta función es simple y
   *         llanamente para hacer el código mas corto y mas fácil de leer.
   */
  private int value (final int i, final int j) {
    return disjoint_set.get(pos(i, j));
  }

}
