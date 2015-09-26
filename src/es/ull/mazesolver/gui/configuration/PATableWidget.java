/*
 * This file is part of MazeSolver.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2014 MazeSolver
 * Sergio M. Afonso Fumero <theSkatrak@gmail.com>
 * Kevin I. Robayna Hernández <kevinirobaynahdez@gmail.com>
 */

/**
 * @file PATableWidget.java
 * @date 25/9/2015
 */
package es.ull.mazesolver.gui.configuration;

import java.awt.BorderLayout;
import java.util.Enumeration;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import es.ull.mazesolver.maze.MazeCell.Vision;
import es.ull.mazesolver.util.Direction;

/**
 * Panel de configuración que permite manipular una tabla de percepción-acción.
 */
public class PATableWidget extends JPanel {
  private static final long serialVersionUID = 1L;

  private Direction[][][][] m_table;

  /**
   * Crea el widget que permite especificar una tabla de percepción-acción.
   *
   * @param pa_table La tabla que se utilizará para rellenar inicialmente el
   * widget y donde se llevarán a cabo todas las modificaciones.
   */
  public PATableWidget (Direction[][][][] pa_table) {
    m_table = pa_table;
    PerceptionActionTableModel m_model = new PerceptionActionTableModel(pa_table);

    JTable table = new JTable(m_model);
    JComboBox <Direction> editor = new JComboBox<Direction>(new Direction[]{
        Direction.UP, Direction.DOWN, Direction.LEFT,
        Direction.RIGHT, Direction.NONE
    });

    Enumeration<TableColumn> c = table.getColumnModel().getColumns();
    while (c.hasMoreElements())
      c.nextElement().setCellEditor(new DefaultCellEditor(editor));

    table.setMinimumSize(table.getPreferredSize());

    setLayout(new BorderLayout());
    add(table.getTableHeader(), BorderLayout.NORTH);
    add(table, BorderLayout.CENTER);
  }

  /**
   * @return La tabla de percepción-acción con la información que puede observar
   * o modificar el usuario.
   */
  public Direction[][][][] getTable () {
    return m_table;
  }

  /**
   * Modelo para almacenar los datos de una tabla de percepción-acción.
   */
  private static class PerceptionActionTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final int N_FIELDS = 5; // {U|D|L|R} + {ACTION}
    private static final int N_ENTRIES = 16; // 2 {EMPTY|WALL} ^ 4 {U|D|L|R}
    private static final String[] COLUMN_NAMES = {"UP", "DOWN", "LEFT", "RIGHT", "ACTION"};

    private Direction [][][][] m_data;

    /**
     * Construye el modelo de la tabla y lo rellena con los datos del agente.
     *
     * @param table Tabla de percepción-acción con la que se rellena el modelo.
     * Sus contenidos pueden ser modificados directamente por el usuario.
     */
    public PerceptionActionTableModel (Direction[][][][] table) {
      m_data = table;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount () {
      return N_ENTRIES;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount () {
      return N_FIELDS;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName (int column) {
      return COLUMN_NAMES[column];
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class <?> getColumnClass (int column) {
      return column != N_FIELDS - 1? Vision.class : Direction.class;
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable (int row, int column) {
      return column == N_FIELDS-1;
    }

    /*
     * (non-Javadoc)
     *
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

    /*
     * (non-Javadoc)
     *
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
     *
     * @param row Fila que decodificar.
     * @return Array con la asignación para cada dirección. Un valor de 1
     *         significa que en esa dirección se ve un obstáculo. Un 0 significa
     *         que en esa dirección se ve una celda vacía.
     */
    private static int[] decodeRow (int row) {
      int[] assignation = new int[4];
      for (int column = 0; column < N_FIELDS-1; column++)
        assignation[column] = (row / (1 << N_FIELDS-2 - column)) % 2;
      return assignation;
    }

    /**
     * Traduce un valor de índice en la visión asociada.
     *
     * @param index Índice a traducir.
     * @return Visión asociada al índice.
     */
    private static Vision indexToVision (int index) {
      return index == 0? Vision.EMPTY : Vision.WALL;
    }

  }

}
