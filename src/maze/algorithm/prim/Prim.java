/**
 * @file Prim.java
 * @date 26 Oct 2014
 */
package maze.algorithm.prim;

import java.util.ArrayList;

import maze.Direction;
import maze.MazeCell;
import maze.MazeCreationAlgorithm;

/**
 *
 */
public class Prim extends MazeCreationAlgorithm {

  private ArrayList <ArrayList <MazeCell>> borderCheck;
  private ArrayList <ArrayList <Boolean>> IncludedCells;
  private ArrayList <ArrayList <MazeCell>> m_maze;
  private short nCellVisited = 1;

  /**
   *
   * @param rows
   * @param columns
   */
  public Prim (int rows, int columns) {
    super(rows, columns);
    IncludedCells = new ArrayList <ArrayList <Boolean>>(rows);
    m_maze = new ArrayList <ArrayList <MazeCell>>(rows);
    borderCheck = new ArrayList <ArrayList <MazeCell>>(rows);
    for (int i = 0; i < IncludedCells.size(); i++) {
      IncludedCells.add(new ArrayList <Boolean>(columns));
      m_maze.add(new ArrayList <MazeCell>(columns));
      borderCheck.add(new ArrayList <MazeCell>(columns));
      for (int j = 0; j < IncludedCells.get(i).size(); j++) {
        if (i == 0) {
          borderCheck.get(i).get(j).unsetWall(Direction.UP);
        }
        if (j == 0) {
          borderCheck.get(i).get(j).unsetWall(Direction.LEFT);
        }
        if (i == (this.m_rows - 1)) {
          borderCheck.get(i).get(j).unsetWall(Direction.DOWN);
        }
        if (j == (this.m_columns - 1)) {
          borderCheck.get(i).get(j).unsetWall(Direction.RIGHT);
        }
      }
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
    Neighbour auxNeighbout = null;
    // Empezar el laberinto con todo lleno de paredes y selecionar una celda.
    IncludedCells.get(i).set(j, true);
    // Mientras haya celdas sin visitar, seguir visitando.
    while (nCellVisited < (this.m_columns - 1) * (this.m_rows - 1) - 1) {
      // Seleccionar un vecino valido para explorar
      auxNeighbout = getNeighbour();
      // Tirar el muro en una direccion y en la direccion de vuelta.
      throwWall(auxNeighbout.i, auxNeighbout.j, auxNeighbout.dir);
    }
    return this.m_maze;
  }

  /**
   *
   * @return Un vecino que sea válido (evita cruzar los bordes)
   */
  private Neighbour getNeighbour () {
    short MAX_NEIGHBOUR = 4;
    Direction neighbour = null;
    Boolean next = false;
    short i = 0;
    short j = 0;
    while (i < IncludedCells.size() && !next) {
      while (j < IncludedCells.get(i).size() && !next && IncludedCells.get(i).get(j)) {
        if (!IncludedCells.get(i - 1).get(j) || !IncludedCells.get(i).get(j - 1)
            || !IncludedCells.get(i + 1).get(j) || !IncludedCells.get(i).get(j + 1)) {
          do {
            neighbour = to_dir((short) (0 + (Math.random() * MAX_NEIGHBOUR)));
          }       //Si voy MOV entonces MOV tiene que haber una celda y
                  //la celda de MOV no esta visitada
          while ((neighbour == Direction.UP &&
                  !borderCheck.get(i).get(j).hasWall(Direction.UP) &&
                  !IncludedCells.get(i - 1).get(j)) ||
                 (neighbour == Direction.LEFT &&
                  !borderCheck.get(i).get(j).hasWall(Direction.LEFT) &&
                  !IncludedCells.get(i).get(j - 1)) ||
                 (neighbour == Direction.DOWN &&
                  !borderCheck.get(i).get(j).hasWall(Direction.DOWN) &&
                  !IncludedCells.get(i + 1).get(j)) ||
                 (neighbour == Direction.RIGHT &&
                  !borderCheck.get(i).get(j).hasWall(Direction.RIGHT) &&
                  !IncludedCells.get(i).get(j + 1) ));
          next = true;
          return new Neighbour(i, j, neighbour);
        }
        j++;
        if (j == IncludedCells.size()) {
          i++;
          j = 0;
        }
      }
    }
    return null;
  }

  /**
   *
   * @param i
   * @param j
   * @param auxNeighbout
   *
   *          Abre el muro en una direccion en una posicion determinada, ademas
   *          ayuda al getNeighbour a hacer su trabajo marcando los muros como
   *          tirados.
   *
   */
  private void throwWall (final short i, final short j, final Direction neighbour) {
    if (neighbour == Direction.UP) {
      // Marcando celda destino como visitada
      IncludedCells.get(i - 1).set(j, true);
      nCellVisited++;
      // Tirando el muro hacia celda destino
      m_maze.get(i).get(j).setWall(Direction.UP);
      // Tirando el muro desde celda destino
      m_maze.get(i - 1).get(j).setWall(Direction.DOWN);
    }
    else if (neighbour == Direction.LEFT) {
      // Marcando celda destino como visitada
      IncludedCells.get(i).set(j - 1,true);
      nCellVisited++;
      // Tirando el muro hacia celda destino
      m_maze.get(i).get(j).setWall(Direction.LEFT);
      // Tirando el muro desde celda destino
      m_maze.get(i).get(j - 1).setWall(Direction.RIGHT);
    }
    else if (neighbour == Direction.DOWN) {
      // Marcando celda destino como visitada
      IncludedCells.get(i + 1).set(j,true);
      nCellVisited++;
      // Tirando el muro hacia celda destino
      m_maze.get(i).get(j).setWall(Direction.DOWN);
      // Marcando muro hacia celda destino como tirado
      m_maze.get(i + 1).get(j).setWall(Direction.UP);
    }
    else if (neighbour == Direction.RIGHT) {
      // Marcando celda destino como visitada
      IncludedCells.get(i).set(j + 1,true);
      nCellVisited++;
      // Tirando el muro hacia celda destino
      m_maze.get(i).get(j).setWall(Direction.RIGHT);
      // Tirando el muro desde celda destino
      m_maze.get(i).get(j + 1).setWall(Direction.LEFT);
    }
  }

  private Direction to_dir( short number )
  {
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
