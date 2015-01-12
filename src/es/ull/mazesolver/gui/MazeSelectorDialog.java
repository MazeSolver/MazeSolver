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
 * @file MazeSelectorDialog.java
 * @date 14/11/2014
 */
package es.ull.mazesolver.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.ull.mazesolver.maze.Maze;
import es.ull.mazesolver.maze.MazeCreationAlgorithm;
import es.ull.mazesolver.maze.algorithm.AldousBroder;
import es.ull.mazesolver.maze.algorithm.HuntAndKill;
import es.ull.mazesolver.maze.algorithm.Kruskal;
import es.ull.mazesolver.maze.algorithm.Prim;
import es.ull.mazesolver.maze.algorithm.RecursiveBacktracking;
import es.ull.mazesolver.maze.algorithm.RecursiveDivision;
import es.ull.mazesolver.maze.algorithm.Wilson;

/**
 * Interfaz gráfica para seleccionar el generador de laberintos y el tamaño del
 * laberinto que se desea generar.
 */
public class MazeSelectorDialog extends JDialog {
  private static final long serialVersionUID = 1L;

  private static final int MIN_MAZE_SIZE = 10;
  private static final int MAX_MAZE_SIZE = 100;

  private JButton m_ok, m_cancel;
  private JComboBox<String> m_algorithms;
  private JSpinner m_rows, m_columns;

  private ButtonGroup m_type;
  private JRadioButton m_perfect, m_add_cycles, m_add_components;
  private JSpinner m_cycles, m_components;

  private Maze m_result;

  public MazeSelectorDialog (Window parent) {
    super(parent,"Create a new maze");

    String[] algos = {"Aldous Broder", "Hunt and Kill", "Kruskal", "Prim",
                      "Recursive Backtracking","Recursive Division","Wilson"};
    m_algorithms = new JComboBox<String>(algos);

    buildInterface();
    setupListeners();
    setResizable(false);
    setModal(true);
  }

  public Maze showDialog () {
    setVisible(true);
    return m_result;
  }

  private void buildInterface () {
    setLayout(new BorderLayout());

    JPanel basic_global = new JPanel(new BorderLayout(5, 5));
    JPanel basic_labels = new JPanel(new GridLayout(3, 1, 5, 5));
    JPanel basic_controls = new JPanel(new GridLayout(3, 1, 5, 5));

    m_rows = new JSpinner(new SpinnerNumberModel(MIN_MAZE_SIZE, MIN_MAZE_SIZE,
        MAX_MAZE_SIZE, 1));
    m_columns = new JSpinner(new SpinnerNumberModel(MIN_MAZE_SIZE,
        MIN_MAZE_SIZE, MAX_MAZE_SIZE, 1));

    basic_labels.add(new JLabel("Algorithm:"));
    basic_controls.add(m_algorithms);
    basic_labels.add(new JLabel("Rows:"));
    basic_controls.add(m_rows);
    basic_labels.add(new JLabel("Columns:"));
    basic_controls.add(m_columns);

    basic_global.add(basic_labels, BorderLayout.WEST);
    basic_global.add(basic_controls, BorderLayout.CENTER);
    basic_global.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createEmptyBorder(2, 5, 0, 5),
      BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
        "Basic configuration"
      )
    ));

    JPanel adv_global = new JPanel(new BorderLayout());
    JPanel adv_labels = new JPanel(new GridLayout(3, 1));
    JPanel adv_controls = new JPanel(new GridLayout(3, 1));

    m_type = new ButtonGroup();
    m_perfect = new JRadioButton("Perfect maze", true);
    m_perfect.setActionCommand("Perfect");
    m_add_cycles = new JRadioButton("Add cycles:");
    m_add_cycles.setActionCommand("Cycles");
    m_add_components = new JRadioButton("Add walls:");
    m_add_components.setActionCommand("Walls");

    m_type.add(m_perfect);
    m_type.add(m_add_cycles);
    m_type.add(m_add_components);

    m_cycles = new JSpinner(new SpinnerNumberModel(0, 0,
        (int) Math.round(Maze.perfectMazeWalls(MIN_MAZE_SIZE, MIN_MAZE_SIZE) * 0.75), 1));
    m_components = new JSpinner(new SpinnerNumberModel(0, 0,
        (int) Math.round(Maze.perfectMazeEdges(MIN_MAZE_SIZE, MIN_MAZE_SIZE) * 0.75), 1));

    m_cycles.setEnabled(false);
    m_components.setEnabled(false);

    adv_labels.add(m_perfect);
    adv_controls.add(Box.createGlue());
    adv_labels.add(m_add_cycles);
    adv_controls.add(m_cycles);
    adv_labels.add(m_add_components);
    adv_controls.add(m_components);

    adv_global.add(adv_labels, BorderLayout.WEST);
    adv_global.add(adv_controls, BorderLayout.CENTER);
    adv_global.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(2, 5, 0, 5),
        BorderFactory.createTitledBorder(
          BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
          "Maze type"
        )
      ));

    m_ok = new JButton("OK");
    m_cancel = new JButton("Cancel");

    JPanel button_panel = new JPanel(new FlowLayout());
    button_panel.add(m_ok);
    button_panel.add(m_cancel);

    JPanel global = new JPanel(new BorderLayout());
    global.add(basic_global, BorderLayout.CENTER);
    global.add(adv_global, BorderLayout.SOUTH);

    add(global, BorderLayout.CENTER);
    add(button_panel, BorderLayout.SOUTH);
    pack();
  }

  private void setupListeners () {
    ChangeListener dim_change_listener = new ChangeListener() {
      @Override
      public void stateChanged (ChangeEvent e) {
        int rows = (Integer) m_rows.getValue();
        int columns = (Integer) m_columns.getValue();

        int max_cycles = (int) Math.round(Maze.perfectMazeWalls(rows, columns) * 0.75);
        int max_components = (int) Math.round(Maze.perfectMazeEdges(rows, columns) * 0.75);

        SpinnerNumberModel cycles_model = (SpinnerNumberModel)m_cycles.getModel();
        SpinnerNumberModel components_model = (SpinnerNumberModel)m_components.getModel();

        cycles_model.setMaximum(max_cycles);
        components_model.setMaximum(max_components);

        cycles_model.setValue(Math.min((Integer) cycles_model.getValue(), max_cycles));
        components_model.setValue(Math.min((Integer) components_model.getValue(), max_components));
      }
    };

    m_rows.addChangeListener(dim_change_listener);
    m_columns.addChangeListener(dim_change_listener);

    ActionListener types_listener = new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        if (e.getSource() == m_perfect) {
          m_cycles.setEnabled(false);
          m_components.setEnabled(false);
        }
        else if (e.getSource() == m_add_cycles) {
          m_cycles.setEnabled(true);
          m_components.setEnabled(false);
        }
        else if (e.getSource() == m_add_components) {
          m_cycles.setEnabled(false);
          m_components.setEnabled(true);
        }
      }
    };

    m_perfect.addActionListener(types_listener);
    m_add_cycles.addActionListener(types_listener);
    m_add_components.addActionListener(types_listener);

    m_ok.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        String alg_name = (String) m_algorithms.getSelectedItem();
        int rows = (Integer) m_rows.getValue();
        int columns = (Integer) m_columns.getValue();

        MazeCreationAlgorithm alg = null;

        switch (alg_name) {
          case "Aldous Broder":
            alg = new AldousBroder(rows, columns);
            break;
          case "Hunt and Kill":
            alg = new HuntAndKill(rows, columns);
            break;
          case "Kruskal":
            alg = new Kruskal(rows, columns);
            break;
          case "Prim":
            alg = new Prim(rows, columns);
            break;
          case "Recursive Division":
            alg = new RecursiveDivision(rows, columns);
            break;
          case "Recursive Backtracking":
            alg = new RecursiveBacktracking(rows, columns);
            break;
          case "Wilson":
            alg = new Wilson(rows, columns);
            break;
        }

        if (alg != null) {
          String command = m_type.getSelection().getActionCommand();
          if (command.equals(m_add_cycles.getActionCommand()))
            alg.setCycles((Integer) m_cycles.getValue());
          else if (command.equals(m_add_components.getActionCommand()))
            alg.setComponents((Integer) m_components.getValue() + 1);

          m_result = new Maze(alg);
        }

        setVisible(false);
        dispose();
      }
    });

    m_cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        setVisible(false);
        dispose();
      }
    });
  }

}
