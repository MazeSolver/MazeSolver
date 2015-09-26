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
 * @file HeuristicAgentConfigurationPanel.java
 * @date 25/9/2015
 */
package es.ull.mazesolver.gui.configuration;

import javax.swing.JPanel;

import es.ull.mazesolver.agent.HeuristicAgent;
import es.ull.mazesolver.agent.distance.DistanceCalculator;

/**
 * Panel de configuración de agentes heurísticos, que contiene toda la
 * configuración compartida por agentes heurísticos.
 */
public class HeuristicAgentConfigurationPanel extends SimpleAgentConfigurationPanel {
  private static final long serialVersionUID = 1L;

  private DistanceWidget m_distance;

  /**
   * Crea el panel de configuración para el agente indicado.
   *
   * @param agent
   *          El agente que se configurará a través de este panel de
   *          configuración.
   */
  public HeuristicAgentConfigurationPanel (HeuristicAgent agent) {
    super(agent);
  }

  /* (non-Javadoc)
   * @see
   * es.ull.mazesolver.gui.AgentConfigurationPanel#createGUI(javax.swing.JPanel)
   */
  @Override
  protected void createGUI (JPanel root) {
    super.createGUI(root);

    HeuristicAgent agent = (HeuristicAgent) m_agent;
    m_distance = new DistanceWidget(agent.getDistanceCalculator().getType());

    root.add(m_distance);
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.AgentConfigurationPanel#accept()
   */
  @Override
  protected boolean accept () {
    if (super.accept()) {
      HeuristicAgent agent = (HeuristicAgent) m_agent;
      agent.setDistanceCalculator(DistanceCalculator.fromType(m_distance.getSelectedType()));

      return true;
    }

    return false;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.AgentConfigurationPanel#translate()
   */
  @Override
  public void translate () {
    super.translate();
    m_distance.translate();
  }

}
