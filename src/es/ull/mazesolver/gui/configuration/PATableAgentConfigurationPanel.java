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
 * @file PATableAgentConfigurationPanel.java
 * @date 25/9/2015
 */
package es.ull.mazesolver.gui.configuration;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.ull.mazesolver.agent.PATableAgent;
import es.ull.mazesolver.util.Direction;

/**
 * Panel de configuración de agentes de tipo {@link PATableAgent}.
 */
public class PATableAgentConfigurationPanel extends SimpleAgentConfigurationPanel {
  private static final long serialVersionUID = 1L;

  private Direction[][][][] m_table;
  private PATableWidget m_table_widget;

  /**
   * Crea el panel de configuración para el agente indicado.
   *
   * @param agent
   *          Agente que se quiere configurar.
   */
  public PATableAgentConfigurationPanel (PATableAgent agent) {
    super(agent);
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.SimpleAgentConfigurationPanel#createGUI(javax.swing.JPanel)
   */
  @Override
  protected void createGUI (JPanel root) {
    super.createGUI(root);

    PATableAgent agent = (PATableAgent) m_agent;
    m_table = agent.getPerceptionActionTable();
    m_table_widget = new PATableWidget(m_table);
    m_table_widget.setBorder(BorderFactory.createTitledBorder("Perception-Action"));

    root.add(m_table_widget);
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.SimpleAgentConfigurationPanel#accept()
   */
  @Override
  public boolean accept () {
    if (super.accept()) {
      PATableAgent agent = (PATableAgent) m_agent;
      agent.setPerceptionActionTable(m_table);
      return true;
    }

    return false;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.SimpleAgentConfigurationPanel#translate()
   */
  @Override
  public void translate () {
    super.translate();
    // TODO Traducir m_table_widget
  }
}
