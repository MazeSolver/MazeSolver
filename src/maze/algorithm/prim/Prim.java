/**
 * @file Prim.java
 * @date 26 Oct 2014
 */
package maze.algorithm.prim;

import java.util.ArrayList;

import maze.MazeCell;
import maze.MazeCreationAlgorithm;

/**
 *
 */
public class Prim extends MazeCreationAlgorithm {

  private ArrayList <ArrayList <cellPrimHelper>> mazeHelperGenerator;
  private ArrayList <ArrayList <MazeCell>> m_maze;

  /**
   *
   * @param rows
   * @param columns
   */
  public Prim (int rows, int columns) {
    super(rows, columns);
    this.mazeHelperGenerator = new ArrayList <ArrayList <cellPrimHelper>>(rows);
    this.m_maze = new ArrayList <ArrayList<MazeCell>>(rows);
    for (int i = 0; i < this.mazeHelperGenerator.size(); i++) {
      this.mazeHelperGenerator.add(new ArrayList <cellPrimHelper>(columns));
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
    // TODO Auto-generated method stub
    return this.m_maze;
  }

}
