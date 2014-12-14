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

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

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
   * @param env Entorno en el que colocar al agente.
   */
  public SimulatedAnnealingAgent (Environment env) {
    super(env);
    m_actual_temp = m_initial_temp = DEFAULT_TEMPERATURE;
    m_cooling_rate = DEFAULT_COOLING_RATE;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.agent.Agent#getAlgorithmName()
   */
  @Override
  public String getAlgorithmName () {
    return "Simulated Annealing";
  }

  /* (non-Javadoc)
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
      double diff = m_dist.distance(dir.movePoint(m_pos), exit) -
                    m_dist.distance(m_pos, exit);

      // Si la nueva posición está más cerca de la salida se acepta
      // directamente, si no se acepta con una probabilidad directamente
      // proporcional a la temperatura actual. Aumentamos la distancia real
      // entre las 2 posiciones para que el proceso de convergencia acabe menos
      // drásticamente.
      if (diff < 0.0 ||
          Math.exp((-DISTANCE_BOOSTING * diff) / m_actual_temp) > Math.random())
        return dir;
    }

    return Direction.NONE;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.agent.Agent#resetMemory()
   */
  @Override
  public void resetMemory () {
    m_actual_temp = m_initial_temp;
  }

  /* (non-Javadoc)
   * @see es.ull.mazesolver.agent.Agent#getConfigurationPanel()
   */
  @Override
  public AgentConfigurationPanel getConfigurationPanel () {
    return new AgentConfigurationPanel() {
      private static final long serialVersionUID = 1L;

      private DistanceConfigurationPanel m_dist;

      @Override
      protected void createGUI (JPanel root) {
        // TODO Añadir controles para modificar la temperatura inicial y el
        // ratio de enfriamiento
        m_dist = new DistanceConfigurationPanel();
        root.add(m_dist);
      }

      @Override
      protected void cancel () {}

      @Override
      protected boolean accept () {
        // TODO Copiar los valores seleccionados por el usuario a los miembros
        // de la clase
        return true;
      }
    };
  }

  /* (non-Javadoc)
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
   * Busca las celdas adyacentes accesibles desde la posición del agente.
   * No tiene en cuenta otros agentes.
   * @return Una lista con las direcciones vecinas (accesibles) del agente.
   */
  public ArrayList<Direction> getNeighbours () {
    ArrayList <Direction> neighbours = new ArrayList<Direction>();

    for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
      Direction dir = Direction.fromIndex(i);
      if (m_env.look(m_pos, dir) != MazeCell.Vision.WALL)
        neighbours.add(dir);
    }

    return neighbours;
  }

}
