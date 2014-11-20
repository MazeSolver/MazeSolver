/**
 * @file PATableAgent.java
 * @date 20/11/2014
 */
package agent;

import gui.environment.Environment;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import maze.Direction;
import maze.MazeCell.Vision;

/**
 * Clase que representa a un agente basado en una tabla de percepción-acción.
 */
public class PATableAgent extends Agent {

  private class TableKey {
    public Vision up, down, left, right;
    public TableKey (Vision up, Vision down, Vision left, Vision right) {
      this.up = up;
      this.down = down;
      this.left = left;
      this.right = right;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode () {
      return up.hashCode() + down.hashCode() +
             left.hashCode() + right.hashCode();
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals (Object obj) {
      if (obj instanceof TableKey) {
        TableKey k = (TableKey) obj;
        return up.equals(k.up) && down.equals(k.down) &&
               left.equals(k.left) && right.equals(k.right);
      }
      return false;
    }
  }

  private static int N_ENTRIES = 16; // 2 {EMPTY|WALL} ^ 4 {U|D|L|R}
  private Map <TableKey, Direction> m_table;

  /**
   * Crea el agente a partir de un entorno, con la configuración por defecto.
   */
  public PATableAgent (Environment env) {
    super(env);

    m_table = new HashMap <PATableAgent.TableKey, Direction>(N_ENTRIES);
    m_table.put(new TableKey(Vision.EMPTY, Vision.EMPTY, Vision.EMPTY, Vision.EMPTY), Direction.DOWN);
    m_table.put(new TableKey(Vision.EMPTY, Vision.EMPTY, Vision.EMPTY, Vision.WALL), Direction.LEFT);
    m_table.put(new TableKey(Vision.EMPTY, Vision.EMPTY, Vision.WALL, Vision.EMPTY), Direction.RIGHT);
    m_table.put(new TableKey(Vision.EMPTY, Vision.EMPTY, Vision.WALL, Vision.WALL), Direction.DOWN);
    m_table.put(new TableKey(Vision.EMPTY, Vision.WALL, Vision.EMPTY, Vision.EMPTY), Direction.UP);
    m_table.put(new TableKey(Vision.EMPTY, Vision.WALL, Vision.EMPTY, Vision.WALL), Direction.LEFT);
    m_table.put(new TableKey(Vision.EMPTY, Vision.WALL, Vision.WALL, Vision.EMPTY), Direction.RIGHT);
    m_table.put(new TableKey(Vision.EMPTY, Vision.WALL, Vision.WALL, Vision.WALL), Direction.UP);
    m_table.put(new TableKey(Vision.WALL, Vision.EMPTY, Vision.EMPTY, Vision.EMPTY), Direction.DOWN);
    m_table.put(new TableKey(Vision.WALL, Vision.EMPTY, Vision.EMPTY, Vision.WALL), Direction.LEFT);
    m_table.put(new TableKey(Vision.WALL, Vision.EMPTY, Vision.WALL, Vision.EMPTY), Direction.RIGHT);
    m_table.put(new TableKey(Vision.WALL, Vision.EMPTY, Vision.WALL, Vision.WALL), Direction.DOWN);
    m_table.put(new TableKey(Vision.WALL, Vision.WALL, Vision.EMPTY, Vision.EMPTY), Direction.RIGHT);
    m_table.put(new TableKey(Vision.WALL, Vision.WALL, Vision.EMPTY, Vision.WALL), Direction.LEFT);
    m_table.put(new TableKey(Vision.WALL, Vision.WALL, Vision.WALL, Vision.EMPTY), Direction.RIGHT);
    m_table.put(new TableKey(Vision.WALL, Vision.WALL, Vision.WALL, Vision.WALL), Direction.NONE);
  }

  /* (non-Javadoc)
   * @see agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    // Cualquier lugar al que no nos podamos mover se considerará una "pared"
    Vision up = m_env.movementAllowed(m_pos, Direction.UP)? Vision.EMPTY : Vision.WALL;
    Vision down = m_env.movementAllowed(m_pos, Direction.DOWN)? Vision.EMPTY : Vision.WALL;
    Vision left = m_env.movementAllowed(m_pos, Direction.LEFT)? Vision.EMPTY : Vision.WALL;
    Vision right = m_env.movementAllowed(m_pos, Direction.RIGHT)? Vision.EMPTY : Vision.WALL;

    return m_table.getOrDefault(new TableKey(up, down, left, right), Direction.NONE);
  }

  /* (non-Javadoc)
   * @see agent.Agent#doMovement(maze.Direction)
   */
  @Override
  public void doMovement (Direction dir) {
    if (m_env.movementAllowed(m_pos, dir))
      m_pos = dir.movePoint(m_pos);
  }

  /* (non-Javadoc)
   * @see agent.Agent#resetMemory()
   */
  @Override
  public void resetMemory () {
    // No tiene memoria, así que no hacemos nada
  }

  /* (non-Javadoc)
   * @see agent.Agent#getConfigurationPanel()
   */
  @Override
  public JPanel getConfigurationPanel () {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see agent.Agent#clone()
   */
  @Override
  public Object clone () throws CloneNotSupportedException {
    PATableAgent ag = new PATableAgent(m_env);
    for (Map.Entry <TableKey, Direction> entry: ag.m_table.entrySet())
      entry.setValue(m_table.get(entry.getKey()));

    return ag;
  }
}
