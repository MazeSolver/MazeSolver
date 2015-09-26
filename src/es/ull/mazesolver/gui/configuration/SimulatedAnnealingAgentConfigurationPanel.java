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
 * @file SimulatedAnnealingAgentConfigurationPanel.java
 * @date 25/9/2015
 */
package es.ull.mazesolver.gui.configuration;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import es.ull.mazesolver.agent.SimulatedAnnealingAgent;
import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.translations.AgentSelectorTranslations;

/**
 * Panel de configuración para agentes de tipo {@link SimulatedAnnealingAgent}.
 */
public class SimulatedAnnealingAgentConfigurationPanel extends HeuristicAgentConfigurationPanel {
  private static final long serialVersionUID = 1L;

  private static final double COOLING_STEP = 0.001;
  private static final double EPSILON = 0.000000001;
  private static final String COOLING_RATE_FORMAT = "0.000000000";

  private JSpinner initial_temp, cooling_rate;
  private JLabel initial_temp_text, cooling_rate_text;

  /**
   * Crea el panel de configuración para el agente indicado.
   *
   * @param agent
   *          Agente que se quiere configurar.
   */
  public SimulatedAnnealingAgentConfigurationPanel (SimulatedAnnealingAgent agent) {
    super(agent);
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.HeuristicAgentConfigurationPanel#createGUI(javax.swing.JPanel)
   */
  @Override
  protected void createGUI (JPanel root) {
    super.createGUI(root);
    SimulatedAnnealingAgent agent = (SimulatedAnnealingAgent) m_agent;

    initial_temp = new JSpinner(
        new SpinnerNumberModel(agent.getInitialTemperature(), 1, Integer.MAX_VALUE, 1));
    cooling_rate = new JSpinner(
        new SpinnerNumberModel(agent.getCoolingRate(), EPSILON, 1 - EPSILON, COOLING_STEP));
    cooling_rate.setEditor(new JSpinner.NumberEditor(cooling_rate, COOLING_RATE_FORMAT));

    initial_temp_text = new JLabel();
    cooling_rate_text = new JLabel();

    JPanel label_panel = new JPanel(new GridLayout(2, 1));
    label_panel.add(initial_temp_text);
    label_panel.add(cooling_rate_text);

    JPanel content_panel = new JPanel(new GridLayout(2, 1));
    content_panel.add(initial_temp);
    content_panel.add(cooling_rate);

    JPanel label_content = new JPanel(new BorderLayout());
    label_content.add(label_panel, BorderLayout.WEST);
    label_content.add(content_panel, BorderLayout.CENTER);

    root.add(label_content);
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.HeuristicAgentConfigurationPanel#accept()
   */
  @Override
  protected boolean accept () {
    if (super.accept()) {
      SimulatedAnnealingAgent agent = (SimulatedAnnealingAgent) m_agent;

      agent.setInitialTemperature((Integer) initial_temp.getValue());
      agent.setCoolingRate((Double) cooling_rate.getValue());
      return true;
    }

    return false;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.gui.configuration.HeuristicAgentConfigurationPanel#translate()
   */
  @Override
  public void translate () {
    super.translate();

    AgentSelectorTranslations tr = MainWindow.getTranslations().agent();
    initial_temp_text.setText(tr.initialTemp() + ":");
    cooling_rate_text.setText(tr.coolingRateFactor() + ":");
  }

}
