/**
 * @file PATableAgent.java
 * @date 20/11/2014
 */
package agent;

import gui.MainWindow;
import gui.environment.Environment;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import maze.Direction;
import maze.MazeCell.Vision;

/**
 * Clase que representa a un agente basado en una tabla de percepción-acción.
 */
public class PATableAgent extends Agent {
  private static int N_FIELDS = 5; // {U|D|L|R} + {ACTION}
  private static int N_ENTRIES = 16; // 2 {EMPTY|WALL} ^ 4 {U|D|L|R}

  private Direction [][][][] m_table;

  /**
   * Crea el agente a partir de un entorno, con la configuración por defecto.
   * @param env Entorno en el que crear el agente.
   */
  public PATableAgent (Environment env) {
    super(env);

    m_table = new Direction[][][][]{
      {
        {
          {Direction.DOWN, Direction.LEFT}, // 0,0,0,0 - 0,0,0,1
          {Direction.RIGHT, Direction.DOWN} // 0,0,1,0 - 0,0,1,1
        },
        {
          {Direction.UP, Direction.LEFT},   // 0,1,0,0 - 0,1,0,1
          {Direction.RIGHT, Direction.UP}   // 0,1,1,0 - 0,1,1,1
        }
      },
      {
        {
          {Direction.DOWN, Direction.LEFT}, // 1,0,0,0 - 1,0,0,1
          {Direction.RIGHT, Direction.DOWN} // 1,0,1,0 - 1,0,1,1
        },
        {
          {Direction.RIGHT, Direction.LEFT},  // 1,1,0,0 - 1,1,0,1
          {Direction.RIGHT, Direction.NONE}   // 1,1,1,0 - 1,1,1,1
        }
      }
    };
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

    return m_table[visionToIndex(up)][visionToIndex(down)]
                  [visionToIndex(left)][visionToIndex(right)];
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

    final PerceptionActionTableModel model = new PerceptionActionTableModel(this);
    JTable table = new JTable(model);
    JComboBox <Direction> editor = new JComboBox<Direction>(new Direction[]{
        Direction.UP, Direction.DOWN, Direction.LEFT,
        Direction.RIGHT, Direction.NONE
    });

    Enumeration<TableColumn> c = table.getColumnModel().getColumns();
    while (c.hasMoreElements())
      c.nextElement().setCellEditor(new DefaultCellEditor(editor));

    table.setMinimumSize(table.getPreferredSize());

    JPanel controls = new JPanel(new FlowLayout());
    JButton accept = new JButton("OK");
    JButton cancel = new JButton("Cancel");

    controls.add(accept);
    controls.add(cancel);

    panel.add(table.getTableHeader(), BorderLayout.NORTH);
    panel.add(table, BorderLayout.CENTER);
    panel.add(controls, BorderLayout.SOUTH);

    accept.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        m_table = model.getData();

        MainWindow wnd = MainWindow.getInstance();
        wnd.getConsole().writeInfo("Perception-action table saved");
        wnd.closeConfigurationPanel();
      }
    });

    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        MainWindow.getInstance().closeConfigurationPanel();
      }
    });

    return panel;
  }

  /* (non-Javadoc)
   * @see agent.Agent#clone()
   */
  @Override
  public Object clone () throws CloneNotSupportedException {
    PATableAgent ag = new PATableAgent(m_env);
    ag.m_table = deepDataCopy(m_table);
    return ag;
  }

  /**
   * Traduce una visión a índice entre 0 y 1.
   * @param vision Visión a traducir.
   * @return Índice asociado a la visión.
   */
  private static int visionToIndex (Vision vision) {
    switch (vision) {
      case EMPTY:
      case OFFLIMITS:
        return 0;
      case WALL:
      case AGENT:
        return 1;
      default:
        return -1;
    }
  }

  /**
   * Traduce un valor de índice en la visión asociada.
   * @param index Índice a traducir.
   * @return Visión asociada al índice.
   */
  private static Vision indexToVision (int index) {
    return index == 0? Vision.EMPTY : Vision.WALL;
  }

  /**
   * Hace una copia profunda de la configuración de un agente.
   * @param data Array tetra-dimensional con las direcciones asociadas a cada
   *        percepción del agente.
   * @return Copia profunda del array de datos de entrada.
   */
  private static Direction[][][][] deepDataCopy (Direction[][][][] data) {
    Direction [][][][] result = new Direction[2][2][2][2];
    for (int i = 0; i < 2; i++)
      for (int j = 0; j < 2; j++)
        for (int k = 0; k < 2; k++)
          for (int l = 0; l < 2; l++)
            result[i][j][k][l] = data[i][j][k][l];

    return result;
  }

  /**
   * Modelo para almacenar los datos de una tabla de percepción-acción.
   */
  private static class PerceptionActionTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final String[] COLUMN_NAMES = {"UP", "DOWN", "LEFT", "RIGHT", "ACTION"};

    private Direction [][][][] m_data;

    /**
     * Construye el modelo de la tabla y lo rellena con los datos del agente.
     * @param ag Agente a partir del cual cargar los datos inicialmente.
     */
    public PerceptionActionTableModel (PATableAgent ag) {
      m_data = deepDataCopy(ag.m_table);
    }

    /**
     * Accede a los datos almacenados en la tabla, listos para su uso por el
     * agente.
     * @return Array tetra-dimensional con las acciones asociadas a cada
     *         percepción
     */
    public Direction[][][][] getData () {
      return m_data;
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
    public Class <?> getColumnClass (int column) {
      return column != N_FIELDS - 1? Vision.class : Direction.class;
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
      int[] dec = decodeRow(row);

      if (column != N_FIELDS-1)
        return indexToVision(dec[column]);
      else
        return m_data[dec[0]][dec[1]][dec[2]][dec[3]];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
    public void setValueAt (Object value, int row, int column) {
      if (column == N_FIELDS-1) {
        int[] dec = decodeRow(row);
        m_data[dec[0]][dec[1]][dec[2]][dec[3]] = (Direction) value;
      }
    }

    /**
     * Calcula a partir de la fila la asignación de visiones (U,D,L,R)
     * que le corresponde.
     * @param row Fila que decodificar.
     * @return Array con la asignación para cada dirección. Un valor de 1
     *         significa que en esa dirección se ve un obstáculo. Un 0 significa
     *         que en esa dirección se ve una celda vacía.
     */
    private static int[] decodeRow (int row) {
      int[] assignation = new int[4];
      for (int column = 0; column < N_FIELDS-1; column++)
        assignation[column] = (row / (int) Math.pow(2, N_FIELDS-2 - column)) % 2;
      return assignation;
    }
  }
}
