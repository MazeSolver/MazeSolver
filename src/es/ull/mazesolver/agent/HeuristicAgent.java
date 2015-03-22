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
 * @file HeuristicAgent.java
 * @date 5/12/2014
 */
package es.ull.mazesolver.agent;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.ull.mazesolver.agent.distance.DistanceCalculator;
import es.ull.mazesolver.agent.distance.DistanceCalculator.DistanceType;
import es.ull.mazesolver.agent.distance.ManhattanDistance;
import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.translations.Translatable;

/**
 * Representa las características comunes a todos los agentes heurísticos, que
 * son la estrategia de medida de distancias y la parte de la interfaz que
 * permite seleccionarla.
 */
public abstract class HeuristicAgent extends Agent {
  private static final long serialVersionUID = 1L;

  /**
   * Algoritmo de medición de distancias que utiliza el agente.
   */
  protected DistanceCalculator m_dist;

  /**
   * Crea el agente en el entorno indicado y con la distancia de Manhattan por
   * defecto.
   *
   * @param env
   *          Entorno en el que colocar el agente.
   */
  public HeuristicAgent (Environment env) {
    super(env);
    m_dist = new ManhattanDistance();
  }

  /**
   * Cambia el algoritmo de cálculo de distancias.
   *
   * @param dist
   *          Algoritmo de cálculo de distancias entre puntos.
   */
  public void setDistanceCalculator (DistanceCalculator dist) {
    if (dist == null)
      throw new IllegalArgumentException(
          MainWindow.getTranslations().exception().invalidDistanceCalculator());

    m_dist = (DistanceCalculator) dist.clone();
  }

  /**
   * Panel de configuración que dispone tan sólo de la selección de un tipo de
   * algoritmo de cálculo de distancias.
   */
  protected class DistanceConfigurationPanel extends JPanel implements Translatable {
    private static final long serialVersionUID = 1L;
    private JComboBox <DistanceType> combo;
    private JLabel m_dist_calc_text;

    /**
     * Crea el panel de configuración del agente.
     */
    public DistanceConfigurationPanel () {
      setLayout(new BorderLayout(5, 0));
      m_dist_calc_text = new JLabel();
      add(m_dist_calc_text, BorderLayout.WEST);

      combo = new JComboBox <DistanceType>(DistanceType.values());
      combo.setSelectedItem(m_dist.getType());
      add(combo, BorderLayout.CENTER);
    }

    /**
     * @return El tipo de distancia que ha seleccionado el usuario.
     */
    public DistanceType getSelectedType () {
      return (DistanceType) combo.getSelectedItem();
    }

    @Override
    public void translate () {
      m_dist_calc_text.setText(
          MainWindow.getTranslations().agent().distanceCalculator() + ":");
    }
  }
}
