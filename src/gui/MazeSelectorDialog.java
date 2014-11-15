/**
 * @file MazeSelectorDialog.java
 * @date 14/11/2014
 */
package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import maze.Maze;
import maze.MazeCreationAlgorithm;
import maze.algorithm.AldousBroder;
import maze.algorithm.HuntAndKill;
import maze.algorithm.Kruskal;
import maze.algorithm.Prim;
import maze.algorithm.RecursiveBacktracking;

/**
 * Interfaz gráfica para seleccionar el generador de laberintos y el tamaño del
 * laberinto que se desea generar.
 */
public class MazeSelectorDialog extends JDialog {
  private static final long serialVersionUID = 1L;

  private static final int MIN_MAZE_SIZE = 10;
  private static final int MAX_MAZE_SIZE = 100;

  private JComboBox<String> m_algorithms;
  private JSpinner m_rows;
  private JSpinner m_columns;

  private Maze m_result;

  public MazeSelectorDialog (Window parent) {
    super(parent,"Create a new maze");

    String[] algos = {"Aldous Broder", "Hunt and Kill", "Kruskal", "Prim",
                      "Recursive Backtracking"};
    m_algorithms = new JComboBox<String>(algos);

    m_rows = new JSpinner(new SpinnerNumberModel(MIN_MAZE_SIZE, MIN_MAZE_SIZE,
        MAX_MAZE_SIZE, 1));
    m_columns = new JSpinner(new SpinnerNumberModel(MIN_MAZE_SIZE,
        MIN_MAZE_SIZE, MAX_MAZE_SIZE, 1));

    buildInterface();
    setResizable(false);
    setModal(true);
  }

  public Maze showDialog () {
    setVisible(true);
    return m_result;
  }

  private void buildInterface () {
    setLayout(new BorderLayout());

    JLabel alg = new JLabel("Algorithm:");
    JLabel row = new JLabel("Rows:");
    JLabel col = new JLabel("Columns:");

    JPanel global = new JPanel(new BorderLayout(5, 5));
    JPanel labels = new JPanel(new GridLayout(3, 1, 5, 5));
    JPanel controls = new JPanel(new GridLayout(3, 1, 5, 5));

    labels.add(alg);
    controls.add(m_algorithms);
    labels.add(row);
    controls.add(m_rows);
    labels.add(col);
    controls.add(m_columns);

    global.add(labels, BorderLayout.WEST);
    global.add(controls, BorderLayout.CENTER);
    global.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JButton ok = new JButton("OK");
    JButton cancel = new JButton("Cancel");

    JPanel button_panel = new JPanel(new FlowLayout());
    button_panel.add(ok);
    button_panel.add(cancel);

    add(global, BorderLayout.CENTER);
    add(button_panel, BorderLayout.SOUTH);
    pack();

    ok.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        String alg_name = (String) m_algorithms.getSelectedItem();
        int rows = (Integer) m_rows.getValue();
        int columns = (Integer) m_columns.getValue();

        MazeCreationAlgorithm alg = null;

        if (alg_name.equals("Aldous Broder")) {
          alg = new AldousBroder(rows, columns);
        }
        else if (alg_name.equals("Hunt and Kill")) {
          alg = new HuntAndKill(rows, columns);
        }
        else if (alg_name.equals("Kruskal")) {
          alg = new Kruskal(rows, columns);
        }
        else if (alg_name.equals("Prim")) {
          alg = new Prim(rows, columns);
        }
        else if (alg_name.equals("Recursive Backtracking")) {
          alg = new RecursiveBacktracking(rows, columns);
        }

        if (alg != null)
          m_result = new Maze(alg);

        setVisible(false);
        dispose();
      }
    });

    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        setVisible(false);
        dispose();
      }
    });
  }

}
