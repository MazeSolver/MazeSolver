/**
 * @file Prim.java
 * @date 26 Oct 2014
 */
package maze.algorithm;

import java.util.ArrayList;

import maze.Direction;
import maze.MazeCell;
import maze.MazeCreationAlgorithm;

/**
 * Implementación del algoritmo de Prim para la generación aleatoria de
 * laberintos.
 */
public class Prim extends MazeCreationAlgorithm {

  private ArrayList <ArrayList <Boolean>> m_included_cells;
  private ArrayList <ArrayList <MazeCell>> m_maze;
  private short nCellVisited = 1;

  /**
   * @param rows Filas en el laberinto resultado.
   * @param columns Columnas en el laberinto resultado.
   */
  public Prim (int rows, int columns) {
    super(rows, columns);

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
    // Mientras haya celdas sin visitar, seguir visitando.
    while (nCellVisited < this.m_columns * this.m_rows) {
      // Seleccionar un vecino valido para explorar
      // Tirar el muro en una direccion y en la direccion de vuelta.
      throwWall(getNeighbour());
    }
    return this.m_maze;
  }

  /**
   * Busca aleatoriamente una celda visitada para explorar sus vecinos.
   * @return Un vecino que sea válido (evita cruzar los bordes).
   */
  private short[] getNeighbour () {
    final short MAX_NEIGHBOUR = 4;
    Direction neighbour = null;

    for (short i = 0; i < m_included_cells.size(); i++) {
      for (short j = 0; j < m_included_cells.get(i).size(); j++) {
        if (m_included_cells.get(i).get(j)) {
          if ((i > 0 && !m_included_cells.get(i-1).get(j)) ||
              (j > 0 && !m_included_cells.get(i).get(j-1)) ||
              (i < m_rows-1 && !m_included_cells.get(i+1).get(j)) ||
              (j < m_columns-1 && !m_included_cells.get(i).get(j+1))) {
            do {
              neighbour = toDir((short) Math.round(0 + (Math.random() * MAX_NEIGHBOUR)));
            } // Si voy MOV entonces MOV tiene que haber una celda y la celda de
              // MOV no está visitada.
            while (!(neighbour == Direction.UP && i > 0 && !m_included_cells.get(i-1).get(j))
                && !(neighbour == Direction.LEFT && j > 0 && !m_included_cells.get(i).get(j-1))
                && !(neighbour == Direction.DOWN && i < m_rows-1 && !m_included_cells.get(i+1).get(j))
                && !(neighbour == Direction.RIGHT && j < m_columns-1 && !m_included_cells.get(i).get(j+1)));

            short[] aux = {i,j,neighbour.val};
            return aux;
          }
        }
      }
    }
    return null;
  }


  /**
   * Elimina la pared colocada en la dirección [3] a partir de la
   * celda ([1], [2]).
   * @param neighbour Array de 3 posiciones: i, j y la dirección en la que
   * eliminar la pared.
   */
  private void throwWall (final short[] neighbour) {
    short i = neighbour[0];
    short j = neighbour[1];
    Direction dir = Direction.fromValue(neighbour[2]);

    // Dependiendo de la dirección eliminamos los 2 muros que separan las 2
    // celdas que queremos unir y marcamos la celda de destino como visitada.
    m_maze.get(i).get(j).unsetWall(dir);
    switch (dir) {
      case UP:
        m_included_cells.get(i - 1).set(j, true);
        m_maze.get(i - 1).get(j).unsetWall(Direction.DOWN);
        break;
      case DOWN:
        m_included_cells.get(i + 1).set(j, true);
        m_maze.get(i + 1).get(j).unsetWall(Direction.UP);
        break;
      case LEFT:
        m_included_cells.get(i).set(j - 1, true);
        m_maze.get(i).get(j - 1).unsetWall(Direction.RIGHT);
        break;
      case RIGHT:
        m_included_cells.get(i).set(j + 1, true);
        m_maze.get(i).get(j + 1).unsetWall(Direction.LEFT);
        break;
    }
    nCellVisited++;
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

}
