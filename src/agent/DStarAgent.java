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
 * @file DStarAgent.java
 * @date 10/12/2014
 */
package agent;

import gui.AgentConfigurationPanel;
import gui.environment.Environment;

import java.awt.Point;

import maze.Direction;
import maze.Maze;
import maze.algorithm.EmptyMaze;

/**
 * Agente que implementa el algoritmo D* para calcular la ruta más corta hasta
 * la salida teniendo tan sólo conocimiento local del entorno.
 * @see <a href="http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.15.3683">
 *   Optimal and Efficient Path Planning for Unknown and Dynamic Environments
 * </a>
 */
public class DStarAgent extends HeuristicAgent {
  private static final long serialVersionUID = 1342168437798267323L;

  /**
   * No se trata del laberinto en el que el agente se mueve, sino la
   * representación de lo que el agente conoce sobre el laberinto. Todas
   * aquellas zonas que el agente no ha visitado supone que no contienen
   * paredes.
   */
  private transient Maze m_maze;
  private transient Point m_exit;

  /**
   * @param env Entorno en el que se va a colocar al agente.
   */
  public DStarAgent (Environment env) {
    super(env);

    Maze real_maze = env.getMaze();
    m_exit = real_maze.getExit();
    m_maze = new Maze(new EmptyMaze(real_maze.getHeight(), real_maze.getWidth()));
  }

  /* (non-Javadoc)
   * @see agent.Agent#getAlgorithmName()
   */
  @Override
  public String getAlgorithmName () {
    return "D*";
  }

  /* (non-Javadoc)
   * @see agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see agent.Agent#resetMemory()
   */
  @Override
  public void resetMemory () {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see agent.Agent#getConfigurationPanel()
   */
  @Override
  public AgentConfigurationPanel getConfigurationPanel () {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see agent.Agent#clone()
   */
  @Override
  public Object clone () {
    // TODO Auto-generated method stub
    return null;
  }

}
