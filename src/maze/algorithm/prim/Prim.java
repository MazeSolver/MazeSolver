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

  private ArrayList <ArrayList <Boolean>> visited;
  private ArrayList <ArrayList <MazeCell>> m_maze;

  /**
   *
   * @param rows
   * @param columns
   */
  public Prim (int rows, int columns) {
    super(rows, columns);
    this.visited = new ArrayList <ArrayList <Boolean>>(rows);
    this.m_maze = new ArrayList <ArrayList <MazeCell>>(rows);
    for (int i = 0; i < this.visited.size(); i++) {
      this.visited.add(new ArrayList <Boolean>(columns));
      this.m_maze.add(new ArrayList <MazeCell>(columns));
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
    short nCellVisited = 1;
    DirectionPrimHelper dummyDirection = null;
    Direction dir = null;
    visited.get(i).set(j, true);
    while (nCellVisited < (this.m_columns - 1) * (this.m_rows - 1) - 1) {
      dummyDirection = getNeighbour(i, j);
      if (dummyDirection == dummyDirection.UP && !visited.get(i - 1).get(j)) {
        visited.get(i - 1).set(j, true);
        nCellVisited++;
        m_maze.get(i).get(j).setWall(dir.UP);
        m_maze.get(i - 1).get(j).setWall(dir.DOWN);
        i--;
      }
      else if (dummyDirection == dummyDirection.LEFT && !visited.get(i).get(j - 1)) {
        visited.get(i).set(j - 1, true);
        nCellVisited++;
        m_maze.get(i).get(j).setWall(dir.LEFT);
        m_maze.get(i).get(j - 1).setWall(dir.RIGHT);
        j--;
      }
      else if (dummyDirection == dummyDirection.DOWN && !visited.get(i + 1).get(j)) {
        visited.get(i + 1).set(j, true);
        nCellVisited++;
        m_maze.get(i).get(j).setWall(dir.DOWN);
        m_maze.get(i + 1).get(j).setWall(dir.UP);
        i++;
      }
      else if (dummyDirection == dummyDirection.RIGHT && !visited.get(i).get(j + 1)) {
        visited.get(i).set(j + 1, true);
        nCellVisited++;
        m_maze.get(i).get(j).setWall(dir.RIGHT);
        m_maze.get(i).get(j + 1).setWall(dir.LEFT);
        j++;
      }
    }
    return this.m_maze;
  }

  /**
   *
   * @param i
   * @param j
   * @return
   *      Un vecino que sea válido (evita cruzar los bordes)
   */
  private DirectionPrimHelper getNeighbour (short i, short j) {
    short MAX_NEIGHBOUR = 4;
    short random = 0;
    DirectionPrimHelper neighbour = null;
    do {
      random = (short) (0 + (Math.random() * MAX_NEIGHBOUR));
    }
    while ((i == 0 && random == neighbour.UP.val) || (j == 0 && random == neighbour.LEFT.val)
        || (i == (this.m_rows - 1) && random == neighbour.DOWN.val)
        || (j == (this.m_columns - 1) && random == neighbour.RIGHT.val));
    neighbour.val = random;
    return neighbour;
  }

}
