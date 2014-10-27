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

  private ArrayList <ArrayList <CellPrimHelper>> IncludedCells;
  private ArrayList <ArrayList <MazeCell>> m_maze;
  private short nCellVisited = 1;

  /**
   *
   * @param rows
   * @param columns
   */
  public Prim (int rows, int columns) {
    super(rows, columns);
    this.IncludedCells = new ArrayList <ArrayList <CellPrimHelper>>(rows);
    this.m_maze = new ArrayList <ArrayList <MazeCell>>(rows);
    for (int i = 0; i < this.IncludedCells.size(); i++) {
      this.IncludedCells.add(new ArrayList <CellPrimHelper>(columns));
      this.m_maze.add(new ArrayList <MazeCell>(columns));
      for (int j = 0; j < IncludedCells.get(i).size(); j++) {
        if (i == 0) {
          IncludedCells.get(i).get(j).UP = true;
        }
        if (j == 0) {
          IncludedCells.get(i).get(j).LEFT = true;
        }
        if (i == (this.m_rows - 1)) {
          IncludedCells.get(i).get(j).DOWN = true;
        }
        if (j == (this.m_columns - 1)) {
          IncludedCells.get(i).get(j).RIGHT = true;
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
    //Empezar el laberinto con todo lleno de paredes y selecionar una celda.
    IncludedCells.get(i).get(j).visited = true;
    //Mientras haya celdas sin visitar, seguir visitando.
    while (nCellVisited < (this.m_columns - 1) * (this.m_rows - 1) - 1) {
      //Seleccionar un vecino valido para explorar
      auxNeighbout = getNeighbour();
      //Tirar el muro en una direccion y en la direccion de vuelta.
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
    short random = 0;
    Neighbour neighbour = null;
    Boolean next = false;
    short i = 0;
    short j = 0;
    while (i < IncludedCells.size() && !next) {
      while (j < IncludedCells.get(i).size() && !next && IncludedCells.get(i).get(j).visited) {
        do {
          random = (short) (0 + (Math.random() * MAX_NEIGHBOUR));
        }
        while ((random == neighbour.dir.UP.val && !IncludedCells.get(i).get(j).UP)
            || (random == neighbour.dir.LEFT.val && !IncludedCells.get(i).get(j).LEFT)
            || (random == neighbour.dir.DOWN.val && !IncludedCells.get(i).get(j).DOWN)
            || (random == neighbour.dir.RIGHT.val && !IncludedCells.get(i).get(j).RIGHT));
        next = true;
        DirectionPrimHelper aux = null;
        aux.val = random;
        neighbour = new Neighbour(i, j, aux);
      }
      j++;
      if (j == IncludedCells.size()) {
        i++;
        j = 0;
      }

    }
    return neighbour;
  }

  /**
   *
   * @param i
   * @param j
   * @param auxNeighbout
   *
   * Abre el muro en una direccion en una posicion determinada, ademas ayuda al
   * getNeighbour a hacer su trabajo marcando los muros como tirados.
   *
   */
  private void throwWall (final short i, final short j, final DirectionPrimHelper auxNeighbour) {
    Direction auxDirection = null;
    if (auxNeighbour == auxNeighbour.UP) {
      //Marcando celda destino como visitada
      IncludedCells.get(i - 1).get(j).visited = true;
      nCellVisited++;
      //Tirando el muro hacia celda destino
      m_maze.get(i).get(j).setWall(auxDirection.UP);
      //Marcando muro hacia celda destino como tirado
      IncludedCells.get(i).get(j).UP = true;
      //Tirando el muro desde celda destino
      m_maze.get(i - 1).get(j).setWall(auxDirection.DOWN);
      //Marcando muro desde celda destino como tirado
      IncludedCells.get(i - 1).get(j).DOWN = true;
    }
    else if (auxNeighbour == auxNeighbour.LEFT) {
      //Marcando celda destino como visitada
      IncludedCells.get(i).get(j - 1).visited = true;
      nCellVisited++;
      //Tirando el muro hacia celda destino
      m_maze.get(i).get(j).setWall(auxDirection.LEFT);
      //Marcando muro hacia celda destino como tirado
      IncludedCells.get(i).get(j).LEFT = true;
      //Tirando el muro desde celda destino
      m_maze.get(i).get(j - 1).setWall(auxDirection.RIGHT);
      //Marcando muro desde celda destino como tirado
      IncludedCells.get(i).get(j - 1).UP = true;
    }
    else if (auxNeighbour == auxNeighbour.DOWN) {
      //Marcando celda destino como visitada
      IncludedCells.get(i + 1).get(j).visited = true;
      nCellVisited++;
      //Tirando el muro hacia celda destino
      m_maze.get(i).get(j).setWall(auxDirection.DOWN);
      //Marcando muro hacia celda destino como tirado
      IncludedCells.get(i).get(j).DOWN = true;
      //Tirando el muro desde celda destino
      m_maze.get(i + 1).get(j).setWall(auxDirection.UP);
      //Marcando muro desde celda destino como tirado
      IncludedCells.get(i + 1).get(j).UP = true;
    }
    else if (auxNeighbour == auxNeighbour.RIGHT) {
      //Marcando celda destino como visitada
      IncludedCells.get(i).get(j + 1).visited = true;
      nCellVisited++;
      //Tirando el muro hacia celda destino
      m_maze.get(i).get(j).setWall(auxDirection.RIGHT);
      //Marcando muro hacia celda destino como tirado
      IncludedCells.get(i).get(j).RIGHT = true;
      //Tirando el muro desde celda destino
      m_maze.get(i).get(j + 1).setWall(auxDirection.LEFT);
      //Marcando muro desde celda destino como tirado
      IncludedCells.get(i).get(j + 1).UP = true;
    }
  }

}
