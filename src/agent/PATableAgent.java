/**
 * @file PATableAgent.java
 * @date 20/11/2014
 */
package agent;

import gui.environment.Environment;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

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

  private static int N_FIELDS = 5; // {U|D|L|R} + {ACTION}
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
    JPanel panel = new JPanel(new BorderLayout());

    TableModel model = new PerceptionActionTableModel(this);
    JTable table = new JTable(model);
    JComboBox <String> editor = new JComboBox <String>(new String[]{
        "MOVE " + Direction.UP.toString(),
        "MOVE " + Direction.DOWN.toString(),
        "MOVE " + Direction.LEFT.toString(),
        "MOVE " + Direction.RIGHT.toString(),
        "MOVE " + Direction.NONE.toString()
    });

    Enumeration<TableColumn> c = table.getColumnModel().getColumns();
    while (c.hasMoreElements()) {
      c.nextElement().setCellEditor(new DefaultCellEditor(editor));
    }

    JPanel controls = new JPanel(new FlowLayout());

    JButton accept = new JButton("OK");
    JButton cancel = new JButton("Cancel");

    controls.add(accept);
    controls.add(cancel);

    panel.add(table, BorderLayout.CENTER);
    panel.add(controls, BorderLayout.SOUTH);

    return panel;
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

  /**
   * Modelo para almacenar los datos de una tabla de percepción-acción.
   */
  private static class PerceptionActionTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final String[] COLUMN_NAMES = {"UP", "DOWN", "LEFT", "RIGHT", "ACTION"};

    private Vector<Vector <String>> m_data;

    /**
     * Construye el modelo de la tabla y lo rellena con los datos del agente.
     * @param ag Agente a partir del cual cargar los datos inicialmente.
     */
    public PerceptionActionTableModel (PATableAgent ag) {
      m_data = new Vector <Vector<String>>(N_ENTRIES);

      for (Map.Entry <TableKey, Direction> e: ag.m_table.entrySet()) {
        Vector<String> row = new Vector<String>(N_FIELDS);
        TableKey key = e.getKey();
        row.add(key.up.toString());
        row.add(key.down.toString());
        row.add(key.left.toString());
        row.add(key.right.toString());
        row.add("MOVE " + e.getValue().toString());

        m_data.add(row);
      }
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount () {
      return N_ENTRIES;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount () {
      return N_FIELDS;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName (int column) {
      return COLUMN_NAMES[column];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class <?> getColumnClass (int columnIndex) {
      return String.class;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable (int row, int column) {
      return column == N_FIELDS-1;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt (int row, int column) {
      return m_data.get(row).get(column);
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
    public void setValueAt (Object value, int row, int column) {
      if (column == N_FIELDS-1) {
        m_data.get(row).set(column, (String) value);
      }
    }

  }
}
