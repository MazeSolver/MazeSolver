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
 * @file SimpleAgentConfigurationPanel.java
 * @date 25/9/2015
 */
package es.ull.mazesolver.gui.configuration;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.alee.extended.layout.VerticalFlowLayout;

import es.ull.mazesolver.agent.Agent;
import es.ull.mazesolver.gui.MainWindow;

/**
 * Panel de configuración de agentes compuesto por los widgets comunes para
 * todos los agentes.
 */
public class SimpleAgentConfigurationPanel extends AgentConfigurationPanel {
  private static final long serialVersionUID = 1L;

  private GeneralConfigurationWidget m_general;
  private TitledBorder m_title;

  /**
   * Crea el panel de configuración genérico para el agente indicado.
   *
   * @param agent
   *          Agente que se quiere configurar.
   */
  public SimpleAgentConfigurationPanel (Agent agent) {
    super(agent);
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * es.ull.mazesolver.gui.configuration.AgentConfigurationPanel#createGUI(javax
   * .swing.JPanel)
   */
  @Override
  protected void createGUI (JPanel root) {
    m_general = new GeneralConfigurationWidget();
    m_general.setAgentName(m_agent.getAgentName());
    m_general.setAgentColor(m_agent.getAgentColor());

    m_title = BorderFactory.createTitledBorder("");
    m_general.setBorder(m_title);

    root.setLayout(new VerticalFlowLayout());
    root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    root.add(m_general);
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.gui.configuration.AgentConfigurationPanel#accept()
   */
  @Override
  protected boolean accept () {
    if (!m_general.getAgentName().isEmpty()) {
      m_agent.setAgentName(m_general.getAgentName());
      m_agent.setAgentColor(m_general.getAgentColor());
      return true;
    }

    m_errors.clear();
    m_errors.add(MainWindow.getTranslations().exception().unnamedAgent());
    return false;
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.gui.configuration.AgentConfigurationPanel#cancel()
   */
  @Override
  protected void cancel () {
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.gui.AgentConfigurationPanel#translate()
   */
  @Override
  public void translate () {
    super.translate();
    m_general.translate();
    m_title.setTitle(MainWindow.getTranslations().agent().generalConfig());
  }

}
