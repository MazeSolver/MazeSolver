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
 * @file SARulesAgentConfigurationPanel.java
 * @date 25/9/2015
 */
package es.ull.mazesolver.gui.configuration;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultEditorKit;

import es.ull.mazesolver.agent.SARulesAgent;
import es.ull.mazesolver.gui.MainWindow;

/**
 * Panel de configuración de agentes de tipo {@link SARulesAgent}.
 */
public class SARulesAgentConfigurationPanel extends SimpleAgentConfigurationPanel {
  private static final long serialVersionUID = 1L;
  public static final int MINIMUM_WIDTH = 300;
  public static final int MINIMUM_HEIGHT = 100;

  private JTextArea m_text;
  private JLabel m_title;

  /**
   * Crea el panel de configuración para el agente indicado.
   *
   * @param agent
   *          Agente que se quiere configurar.
   */
  public SARulesAgentConfigurationPanel (SARulesAgent agent) {
    super(agent);
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.SimpleAgentConfigurationPanel#createGUI(javax.swing.JPanel)
   */
  @Override
  protected void createGUI (JPanel root) {
    super.createGUI(root);

    SARulesAgent agent = (SARulesAgent) m_agent;
    m_title = new JLabel();
    m_text = new JTextArea(agent.getCode());
    JScrollPane scroll = new JScrollPane(m_text);

    // Añadimos un popup para cortar, copiar, pegar y seleccionar todo
    final JPopupMenu popup = new JPopupMenu();
    ActionMap actions = m_text.getActionMap();
    popup.add(actions.get(DefaultEditorKit.cutAction));
    popup.add(actions.get(DefaultEditorKit.copyAction));
    popup.add(actions.get(DefaultEditorKit.pasteAction));
    popup.addSeparator();
    popup.add(actions.get(DefaultEditorKit.selectAllAction));

    m_text.addMouseListener(new MouseAdapter() {
      public void mousePressed (MouseEvent e) {
        maybeShowPopup(e);
      }

      public void mouseReleased (MouseEvent e) {
        maybeShowPopup(e);
      }

      private void maybeShowPopup (MouseEvent e) {
        if (e.isPopupTrigger()) {
          popup.show(e.getComponent(), e.getX(), e.getY());
        }
      }
    });

    JPanel center_panel = new JPanel(new BorderLayout());
    center_panel.add(m_title, BorderLayout.NORTH);
    center_panel.add(scroll, BorderLayout.CENTER);
    center_panel.setBorder(BorderFactory.createTitledBorder("Situation-Action"));

    center_panel.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
    root.add(center_panel);
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.SimpleAgentConfigurationPanel#accept()
   */
  @Override
  public boolean accept () {
    if (super.accept()) {
      SARulesAgent agent = (SARulesAgent) m_agent;

      // Cargamos el código nuevo en el agente y guardamos una copia del
      // anterior
      String prev_code = agent.getCode();
      agent.setCode(m_text.getText());

      // Intentamos compilar el código, y si no es válido restauramos el
      // anterior y guardamos los errores.
      if (!agent.compileCode()) {
        agent.setCode(prev_code);
        m_errors = agent.getCompilationErrors();
        return false;
      }
      else {
        m_success.add(MainWindow.getTranslations().message().codeCompiled());
        return true;
      }
    }

    return false;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.SimpleAgentConfigurationPanel#translate()
   */
  @Override
  public void translate () {
    super.translate();
    m_title.setText(MainWindow.getTranslations().agent().writeRulesHere() + ":");
  }

}
