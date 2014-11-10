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
  private ArrayList <ArrayList <MazeCell>> m_maze;

  /**
   * @param rows Filas en el laberinto resultado.
   * @param columns Columnas en el laberinto resultado.
   */
  public Prim (int rows, int columns) {
    super(rows, columns);
    walls = new ArrayList <short []>();
    m_included_cells = new ArrayList <ArrayList <Boolean>>(rows);
    m_maze = initializeMaze();

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
    return this.m_maze;
  }

  /**
   * Busca aleatoriamente un muro de los encontrados al abrir caminos.
   * @return Un muro que sea válido (evita celdas ya visitadas).
   */
  private int getWall () {
    int nextWall = 0;
    do {
      nextWall = (int) Math.round(0 + (Math.random() * (walls.size() - 1) ));
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
   * Elimina la pared colocada en la dirección [3] a partir de la
   * celda (i, j).
   * @param indx_wall indice del vector que almacena los muros disposibles
   * el indice siempre será un muro valido hacia una casilla sin visitar
   * ya que el metodo getWall lo comprueba, para eliminar los muros de la lista
   * que van hacia celdas ya visitadas.
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
      m_included_cells.get(i + desp.second).set(j + desp.first, true);
      m_maze.get(i + desp.second).get(j + desp.first).unsetWall(dir.getOpposite());
      addCell(i + desp.second, j + desp.first);
    }
  }

  /**
   * Convierte un número entre 1 y 4 en una dirección.
   * @param number Número a convertir.
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
   * Añade a la lista de muros, los muros disponibles de la celda i,j
   * @param i
   * @param j
   */
  private void addCell (final int i, final int j)
  {
    for (short k = 0; k < MAX_NEIGHBOUR; k++) {
      if (i != 0 && toDir(k) == Direction.UP &&
          !m_included_cells.get(i-1).get(j)) {
        short [] aux = {(short) i, (short) j,Direction.UP.val};
        walls.add(aux);
      }
      else if (j != 0 && toDir(k) == Direction.LEFT &&
          !m_included_cells.get(i).get(j-1)) {
        short [] aux = {(short) i, (short) j,Direction.LEFT.val};
        walls.add(aux);
      }
      else if (i != m_rows-1 && toDir(k) == Direction.DOWN &&
          !m_included_cells.get(i+1).get(j)) {
        short [] aux = {(short) i, (short) j,Direction.DOWN.val};
        walls.add(aux);
      }
      else if (j != m_columns-1 && toDir(k) == Direction.RIGHT &&
          !m_included_cells.get(i).get(j+1)) {
        short [] aux = {(short) i, (short) j,Direction.RIGHT.val};
        walls.add(aux);
      }
    }
  }

  /**
   *
   * @param inx_wall indice del muro a comprobar si la celda colidante sigue
   * estando disponible o ya no y hay que eliminarla
   * @return
   */
  private Boolean checkWall (final int inx_wall )
  {
    short i = walls.get(inx_wall)[0];
    short j = walls.get(inx_wall)[1];
    Direction dir = Direction.fromValue(walls.get(inx_wall)[2]);

    switch (dir) {
      case UP:
        if (!m_included_cells.get(i-1).get(j)) {
          return true;
        }
        break;
      case DOWN:
        if (!m_included_cells.get(i+1).get(j)) {
          return true;
        }
        break;
      case LEFT:
        if (!m_included_cells.get(i).get(j-1)) {
          return true;
        }
        break;
      case RIGHT:
        if (!m_included_cells.get(i).get(j+1)) {
          return true;
        }
        break;
    }
    walls.remove(inx_wall);
    return false;
  }

}