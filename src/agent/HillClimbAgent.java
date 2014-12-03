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
 * @file HillClimbAgent.java
 * @date 3/12/2014
 */
package agent;

import gui.AgentConfigurationPanel;
import gui.environment.Environment;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.TreeMap;

import maze.Direction;
import maze.Maze;
import maze.MazeCell;
import agent.distance.DistanceCalculator;
import agent.distance.ManhattanDistance;

/**
 * Agente que implementa el comportamiento del algoritmo de escalada. Siempre
 * intenta moverse al lugar que no ha visitado y que le acerque más al objetivo.
 * En el peor de los casos hace un recorrido por todo el laberinto.
 */
public class HillClimbAgent extends Agent {
  private static final long serialVersionUID = 6553751391274900253L;

  private DistanceCalculator m_dist;
  private transient boolean m_backtracking;
  private transient Stack <Direction> m_stack;
  private transient boolean[][] m_visited;

  /**
   * Crea el agente en el entorno indicado y con la distancia de Manhattan por
   * defecto.
   * @param env Entorno en el que se quiere colocar.
   */
  public HillClimbAgent (Environment env) {
    super(env);
    m_dist = new ManhattanDistance();
    m_stack = new Stack<Direction>();
  }

  /* (non-Javadoc)
   * @see agent.Agent#setEnvironment(gui.environment.Environment)
   */
  @Override
  public void setEnvironment (Environment env) {
    super.setEnvironment(env);

    Maze maze = env.getMaze();
    m_visited = new boolean[maze.getHeight()][maze.getWidth()];
  }

  /**
   * Cambia el algoritmo de cálculo de distancias.
   * @param dist Algoritmo de cálculo de distancias entre puntos.
   */
  public void setDistanceCalculator (DistanceCalculator dist) {
    if (dist == null)
      throw new IllegalArgumentException("El medidor de distancias indicado no es válido");

    m_dist = (DistanceCalculator) dist.clone();
  }

  /* (non-Javadoc)
   * @see agent.Agent#getAlgorithmName()
   */
  @Override
  public String getAlgorithmName () {
    return "Hill Climbing";
  }

  /* (non-Javadoc)
   * @see agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    Direction dir = selectDirection();
    if (dir == Direction.NONE && !m_stack.empty()) {
      m_backtracking = true;
      dir = m_stack.pop().getOpposite();
    }

    return dir;
  }

  /* (non-Javadoc)
   * @see agent.Agent#doMovement(maze.Direction)
   */
  @Override
  public void doMovement (Direction dir) {
    if (!m_backtracking)
      m_stack.push(dir);
    else
      m_backtracking = false;

    m_visited[m_pos.y][m_pos.x] = true;
    super.doMovement(dir);
  }

  /* (non-Javadoc)
   * @see agent.Agent#setPosition(java.awt.Point)
   */
  @Override
  public void setPosition (Point pos) {
    resetMemory();
    super.setPosition(pos);
  };

  /* (non-Javadoc)
   * @see agent.Agent#resetMemory()
   */
  @Override
  public void resetMemory () {
    m_stack.clear();
    for (int i = 0; i < m_visited.length; i++) {
      for (int j = 0; j < m_visited[i].length; j++)
        m_visited[i][j] = false;
    }
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
    HillClimbAgent ag = new HillClimbAgent(m_env);
    ag.setDistanceCalculator(m_dist);
    return ag;
  }

  /**
   * Elige la dirección que le lleva a la celda más cercana no visitada que no
   * tiene una pared delante.
   * @return La dirección seleccionada. Devuelve {@code Direction.NONE} si no
   *         hay ninguna celda adyacente accesible no visitada.
   */
  private Direction selectDirection () {
    Point exit = m_env.getMaze().getExit();
    TreeMap <Direction, Double> costs = new TreeMap <Direction, Double>();

    for (int i = 0; i < Direction.MAX_DIRECTIONS; i++) {
      Direction dir = Direction.fromIndex(i);
      Point pos = dir.movePoint(m_pos);
      if (pos.equals(exit))
        return dir;

      if (look(dir) == MazeCell.Vision.EMPTY && !m_visited[pos.y][pos.x])
        costs.put(dir, m_dist.distance(pos, exit));
    }

    Direction closest = Direction.NONE;
    double closest_val = Double.MAX_VALUE;
    for (Entry<Direction, Double> entry: costs.entrySet()) {
      if (entry.getValue() < closest_val) {
        closest_val = entry.getValue();
        closest = entry.getKey();
      }
    }

    return closest;
  }

  /**
   * Extrae la información del objeto a partir de una forma serializada del
   * mismo.
   * @param input Flujo de entrada con la información del objeto.
   * @throws ClassNotFoundException Si se trata de un objeto de otra clase.
   * @throws IOException Si no se puede leer el flujo de entrada.
   */
  private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
    input.defaultReadObject();
    m_stack = new Stack <Direction>();
  }
}
