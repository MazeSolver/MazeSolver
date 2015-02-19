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
 * @file SimulatedAnnealingAgent.java
 * @date 14/12/2014
 */
package es.ull.mazesolver.agent;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import es.ull.mazesolver.agent.distance.DistanceCalculator;
import es.ull.mazesolver.gui.AgentConfigurationPanel;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.maze.MazeCell;
import es.ull.mazesolver.util.Direction;

/**
 * Agente que implementa el algoritmo meta-heurístico "Recocido simulado".
 */
public class SimulatedAnnealingAgent extends HeuristicAgent {
  private static final long serialVersionUID = 1607730980597645713L;

  private static final double DISTANCE_BOOSTING = 5.0;
  private static final int DEFAULT_TEMPERATURE = 5000;
  private static final double DEFAULT_COOLING_RATE = 0.005;

  private transient int m_actual_temp;
  private int m_initial_temp;
  private double m_cooling_rate;

  /**
   * Crea el agente en el entorno indicado con una configuración por defecto que
   * no está adaptada a ningún laberinto en concreto.
   *
   * @param env
   *          Entorno en el que colocar al agente.
   */
  public SimulatedAnnealingAgent (Environment env) {
    super(env);
    m_actual_temp = m_initial_temp = DEFAULT_TEMPERATURE;
    m_cooling_rate = DEFAULT_COOLING_RATE;
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.agent.Agent#getAlgorithmName()
   */
  @Override
  public String getAlgorithmName () {
    return "Simulated Annealing";
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    // Enfriamos la temperatura si aún está caliente
    if (m_actual_temp > 1)
      m_actual_temp *= 1.0 - m_cooling_rate;

    // Obtenemos los estados vecinos y los permutamos aleatoriamente
    ArrayList <Direction> neighbours = getNeighbours();
    Collections.shuffle(neighbours);

    // Si no hay vecinos no nos movemos a ningún lado
    if (neighbours.isEmpty())
      return Direction.NONE;

    // Seleccionamos aleatoriamente alguna dirección de movimiento de acuerdo
    // a la temperatura actual
    Point exit = m_env.getMaze().getExit();
    for (int i = 0; i < neighbours.size(); i++) {
      Direction dir = neighbours.get(i);
      double diff = m_dist.distance(dir.movePoint(m_pos), exit) - m_dist.distance(m_pos, exit);

      // Si la nueva posición está más cerca de la salida se acepta
      // directamente, si no se acepta con una probabilidad directamente
      // proporcional a la temperatura actual. Aumentamos la distancia real
      // entre las 2 posiciones para que el proceso de convergencia acabe menos
      // drásticamente.
      if (diff < 0.0 || Math.exp((-DISTANCE_BOOSTING * diff) / m_actual_temp) > Math.random())
        return dir;
    }

    return Direction.NONE;
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.agent.Agent#resetMemory()
   */
  @Override
  public void resetMemory () {
    m_actual_temp = m_initial_temp;
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.agent.Agent#getConfigurationPanel()
   */
  @Override
  public AgentConfigurationPanel getConfigurationPanel () {
    return new AgentConfigurationPanel() {
      private static final long serialVersionUID = 1L;

      private static final double COOLING_STEP = 0.001;
      private static final double EPSILON = 0.000000001;
      private static final String COOLING_RATE_FORMAT = "0.000000000";

      private DistanceConfigurationPanel distance;
      private JSpinner initial_temp;
      private JSpinner cooling_rate;

      @Override
      protected void createGUI (JPanel root) {
        distance = new DistanceConfigurationPanel();
        initial_temp =
            new JSpinner(new SpinnerNumberModel(m_initial_temp, 1, Integer.MAX_VALUE, 1));
        cooling_rate =
            new JSpinner(new SpinnerNumberModel(m_cooling_rate, EPSILON, 1 - EPSILON, COOLING_STEP));
        cooling_rate.setEditor(new JSpinner.NumberEditor(cooling_rate, COOLING_RATE_FORMAT));

        JPanel label_panel = new JPanel(new GridLayout(2, 1));
        label_panel.add(new JLabel("Initial temperature:"));
        label_panel.add(new JLabel("Cooling rate factor:"));

        JPanel content_panel = new JPanel(new GridLayout(2, 1));
        content_panel.add(initial_temp);
        content_panel.add(cooling_rate);

        JPanel label_content = new JPanel(new BorderLayout());
        label_content.add(label_panel, BorderLayout.WEST);
        label_content.add(content_panel, BorderLayout.CENTER);

        JPanel global = new JPanel(new BorderLayout());
        global.add(distance, BorderLayout.NORTH);
        global.add(label_content, BorderLayout.SOUTH);

        root.setLayout(new BorderLayout(5, 0));
        root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        root.add(global, BorderLayout.NORTH);
      }

      @Override
      protected void cancel () {
      }

      @Override
      protected boolean accept () {
        m_dist = DistanceCalculator.fromType(distance.getSelectedType());
        m_initial_temp = (Integer) initial_temp.getValue();
        m_cooling_rate = (Double) cooling_rate.getValue();
        return true;
      }
    };
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.agent.Agent#clone()
   */
  @Override
  public Object clone () {
    SimulatedAnnealingAgent ag = new SimulatedAnnealingAgent(m_env);
    ag.setDistanceCalculator(m_dist);
    ag.m_cooling_rate = m_cooling_rate;
    ag.m_initial_temp = m_initial_temp;
    return ag;
  }

  /**
   * Extrae la información del objeto a partir de una forma serializada del
   * mismo.
   *
   * @param input
   *          Flujo de entrada con la información del objeto.
   * @throws ClassNotFoundException
   *           Si se trata de un objeto de otra clase.
   * @throws IOException
   *           Si no se puede leer el flujo de entrada.
   */
  private void readObject (ObjectInputStream input) throws ClassNotFoundException, IOException {
    input.defaultReadObject();
    m_actual_temp = m_initial_temp;
  }

  /**
   * Busca las celdas adyacentes accesibles desde la posición del agente. No
   * tiene en cuenta otros agentes.
   *
   * @return Una lista con las direcciones vecinas (accesibles) del agente.
   */
  public ArrayList <Direction> getNeighbours () {
    ArrayList <Direction> neighbours = new ArrayList <Direction>();

    for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
      Direction dir = Direction.fromIndex(i);
      if (m_env.look(m_pos, dir) != MazeCell.Vision.WALL)
        neighbours.add(dir);
    }

    return neighbours;
  }

}
