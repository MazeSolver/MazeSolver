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

  private final static short MAX_NEIGHBOUR = 4;
  private final static short UERROR = -1;
  private ArrayList <Integer> disjoint_set;
  private ArrayList <short []> walls;
  private ArrayList <ArrayList <MazeCell>> m_maze;

  /**
   * @param rows
   * @param columns
   */
  public Kruskal (int rows, int columns) {
    super(rows, columns);
    walls = new ArrayList <short []>();
    disjoint_set = new ArrayList <Integer>();
    m_maze = initializeMaze();

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
    while (!walls.isEmpty()) {
      // Seleccionar un vecino valido para explorar
      // Tirar el muro en una direccion y en la direccion de vuelta.
      throwWall(getWall());
    }
    return this.m_maze;
  }

  /**
   * Busca aleatoriamente un muro de los encontrados al abrir caminos.
   *
   * @return Un muro que sea válido (evita celdas ya visitadas).
   * @throws Exception
   */
  private int getWall () {
    int nextWall = 0;
    do {
      nextWall = (int) Math.round(0 + (Math.random() * (walls.size() - 1)));
    }
    while (!checkWall(nextWall) && !walls.isEmpty());
    if (walls.isEmpty()) {
      return UERROR;
    }
    else {
      return nextWall;
    }
  }

  /**
   * Elimina la pared colocada en la dirección [3] a partir de la celda (i, j).
   *
   * @param indx_wall
   *          indice del vector que almacena los muros disposibles el indice
   *          siempre será un muro valido hacia una casilla sin visitar ya que
   *          el metodo getWall lo comprueba, para eliminar los muros de la
   *          lista que van hacia celdas ya visitadas.
   * @throws Exception
   */
  private void throwWall (final int indx_wall) {
    if (indx_wall != UERROR) {
      short i = walls.get(indx_wall)[0];
      short j = walls.get(indx_wall)[1];
      Direction dir = Direction.fromValue(walls.get(indx_wall)[2]);
      walls.remove(indx_wall);

      // Dependiendo de la dirección eliminamos los 2 muros que separan las 2
      // celdas que queremos unir y marcamos la celda de destino como visitada.
      m_maze.get(i).get(j).unsetWall(dir);
      Pair<Integer, Integer> desp = dir.decompose();
      union(value(i, j), value(i + desp.second, j + desp.first));
      m_maze.get(i + desp.second).get(j + desp.first).unsetWall(dir.getOpposite());
    }
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
   * Añade todos los muros a la lista de muros a elegir aleatoriamente
   */
  private void addAll () {
    for (int i = 0; i < m_rows; i++) {
      for (int j = 0; j < m_columns; j++)
        addCell(i, j);
    }
  }

  /**
   * Añade a la lista de muros, los muros disponibles de la celda i,j
   *
   * @param i
   * @param j
   */
  private void addCell (final int i, final int j) {
    for (short k = 0; k < MAX_NEIGHBOUR; k++) {
      if (i != 0 && toDir(k) == Direction.UP) {
        short [] aux = {(short) i, (short) j, Direction.UP.val};
        walls.add(aux);
      }
      else if (j != 0 && toDir(k) == Direction.LEFT) {
        short [] aux = {(short) i, (short) j, Direction.LEFT.val};
        walls.add(aux);
      }
      else if (i != m_rows - 1 && toDir(k) == Direction.DOWN) {
        short [] aux = {(short) i, (short) j, Direction.DOWN.val};
        walls.add(aux);
      }
      else if (j != m_columns - 1 && toDir(k) == Direction.RIGHT) {
        short [] aux = {(short) i, (short) j, Direction.RIGHT.val};
        walls.add(aux);
      }
    }
  }

  /**
   *
   * @param inx_wall
   *          indice del muro a comprobar si la celda colidante o la propia
   *          celda estan en diferentes conjuntos o hay que eliminarla
   * @return
   * @throws Exception
   */
  private Boolean checkWall (final int inx_wall) {
    short i = walls.get(inx_wall)[0];
    short j = walls.get(inx_wall)[1];
    Direction dir = Direction.fromValue(walls.get(inx_wall)[2]);

    Pair <Integer, Integer> desp = dir.decompose();
    if (value(i, j) != value(i + desp.second, j + desp.first)) {
      return true;
    }
    walls.remove(inx_wall);
    return false;
  }

  /**
   *
   * @param i
   * @param j
   * @return la posicion del vector dado por los parametros i y j que
   *         seleccionan la fila y columna
   */
  private int pos (final int i, final int j) {
    return (i * m_columns) + j;
  }

  /**
   *
   * @param value_from
   * @param value_to
   *
   *          Une dos conjuntos-disjuntos, de la forma siguiente, todo conjunto
   *          que tenga como valor representativo value_from, lo mueve al
   *          conjunto de valor value_to
   */
  private void union (final int value_from, final int value_to) {
    for (int k = 0; k < disjoint_set.size(); k++)
      if (disjoint_set.get(k) == value_from)
        disjoint_set.set(k, value_to);
  }

  /**
   *
   * @param i
   * @param j
   * @return valor, del conjunto del elemento i,j. Esta función es simple y
   *         llanamente para hacer el código mas corto y mas fácil de leer.
   */
  private int value (final int i, final int j) {
    return disjoint_set.get(pos(i, j));
  }

}
