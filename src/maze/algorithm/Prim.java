/**
 * @file Prim.java
 * @date 26 Oct 2014
 */
package maze.algorithm;

import java.util.ArrayList;

import maze.Direction;
import maze.MazeCell;
import maze.MazeCreationAlgorithm;
import util.Pair;

/**
 * Implementación del algoritmo de Prim para la generación aleatoria de
 * laberintos.
 */
public class Prim extends MazeCreationAlgorithm {

  private final static short MAX_NEIGHBOUR = 4;
  private final static short UERROR = -1;
  private ArrayList <ArrayList <Boolean>> m_included_cells;
  private ArrayList <short []> walls;

  /**
   * @param rows
   *          Filas en el laberinto resultado.
   * @param columns
   *          Columnas en el laberinto resultado.
   */
  public Prim (int rows, int columns) {
    super(rows, columns);
    walls = new ArrayList <short []>();
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
    short i = 0;
    short j = 0;
    // Empezar el laberinto con todo lleno de paredes y selecionar una celda.
    m_included_cells.get(i).set(j, true);
    addCell(i, j);
    // Mientras haya celdas sin visitar, seguir visitando.
    while (!walls.isEmpty()) {
      // Seleccionar un vecino valido para explorar
      // Tirar el muro en una direccion y en la direccion de vuelta.
      throwWall(getWall());
    }
    return m_maze;
  }

  /**
   * Busca aleatoriamente un muro de los encontrados al abrir caminos.
   *
   * @return Un muro que sea válido (evita celdas ya visitadas).
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
   */
  private void throwWall (final int indx_wall) {
    if (indx_wall != UERROR) {
      short i = walls.get(indx_wall)[0];
      short j = walls.get(indx_wall)[1];
      Direction dir = Direction.fromValue(walls.get(indx_wall)[2]);
      walls.remove(indx_wall);

      // Dependiendo de la dirección eliminamos los 2 muros que separan las 2
      // celdas que queremos unir y marcamos la celda de destino como visitada.
      Pair <Integer, Integer> desp = dir.decompose();
      openPassage(i, j, dir);
      m_included_cells.get(i + desp.second).set(j + desp.first, true);
      addCell(i + desp.second, j + desp.first);
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
      Direction dir = toDir(k);
      Pair <Integer, Integer> desp = dir.decompose();
      if ((i + desp.second >= 0) && (j + desp.first >= 0) && (i + desp.second < m_rows)
          && (j + desp.first < m_columns)
          && !m_included_cells.get(i + desp.second).get(j + desp.first)) {
        short [] aux = {(short) i, (short) j, dir.val};
        walls.add(aux);
      }
    }
  }

  /**
   *
   * @param inx_wall
   *          indice del muro a comprobar si la celda colidante sigue estando
   *          disponible o ya no y hay que eliminarla
   * @return
   */
  private Boolean checkWall (final int inx_wall) {
    short i = walls.get(inx_wall)[0];
    short j = walls.get(inx_wall)[1];
    Direction dir = Direction.fromValue(walls.get(inx_wall)[2]);

    Pair <Integer, Integer> desp = dir.decompose();
    if (!m_included_cells.get(i + desp.second).get(j + desp.first)) {
      return true;
    }
    walls.remove(inx_wall);
    return false;
  }

}