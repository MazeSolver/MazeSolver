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
 * @file FastSimulationDialog.java
 * @date 29/9/2015
 */
package es.ull.mazesolver.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.alee.extended.layout.VerticalFlowLayout;

import es.ull.mazesolver.translations.Translations;

/**
 * Interfaz gráfica para lanzar simulaciones rápidas con un número de pasos
 * limitado.
 */
public class FastSimulationDialog extends JDialog {
  private static final long serialVersionUID = 1L;
  private static final int DEFAULT_STEPS = 100;
  private static final int MIN_STEPS = 1;
  private static final int MAX_STEPS = 50000;

  private int m_result;
  private JSpinner m_steps;

  /**
   * Crea el diálogo de lanzamiento de simulaciones rápidas.
   *
   * @param parent
   *          Ventana padre del diálogo.
   */
  public FastSimulationDialog (JFrame parent) {
    super(parent, "", true);

    buildInterface();
    setLocationRelativeTo(parent);
    setResizable(false);
  }

  /**
   * Muestra el diálogo por pantalla y devuelve el número de pasos que se debe
   * simular como resultado de la selección del usuario.
   *
   * @return Número de pasos máximo que se debe simular.
   */
  public int showDialog () {
    setVisible(true);
    return m_result;
  }

  /**
   * Construye la interfaz gráfica y configura los listeners.
   */
  private void buildInterface () {
    final Translations tr = MainWindow.getTranslations();

    setTitle(tr.button().fastRun());
    setLayout(new VerticalFlowLayout(0, 5));

    m_steps = new JSpinner(new SpinnerNumberModel(DEFAULT_STEPS, MIN_STEPS,
                                                  MAX_STEPS, 1));

    JPanel global = new JPanel(new BorderLayout(5, 0));
    global.add(new JLabel(tr.other().numberSteps() + ":"), BorderLayout.WEST);
    global.add(m_steps, BorderLayout.CENTER);
    global.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));

    JButton ok = new JButton(tr.button().ok());
    JButton cancel = new JButton(tr.button().cancel());

    JPanel buttons_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
    buttons_panel.add(ok);
    buttons_panel.add(cancel);

    add(global);
    add(buttons_panel);
    pack();

    ok.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        m_result = (Integer) m_steps.getValue();

        setVisible(false);
        dispose();
      }
    });

    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        m_result = -1;

        setVisible(false);
        dispose();
      }
    });
  }

}
