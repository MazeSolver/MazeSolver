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
 * @file RecursiveAgent.java
 * @date Jan 4, 2015
 */
package es.ull.mazesolver.agent;

import java.awt.BorderLayout;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.ull.mazesolver.gui.AgentConfigurationPanel;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.maze.Maze;
import es.ull.mazesolver.maze.MazeCell;
import es.ull.mazesolver.util.Direction;

/**
 * Agente que implementa el comportamiento del algoritmo recursivo con
 * backtracking. Siempre intenta moverse al lugar que no ha visitado. En el peor
 * de los casos hace un recorrido por todo el laberinto.
 */
public class RecursiveAgent extends Agent {

  private static final long serialVersionUID = 1L;

  private transient boolean m_backtracking;
  private transient Stack <Direction> m_stack;
  private transient boolean [][] m_visited;

  /**
   * Crea el agente en el entorno indicado.
   *
   * @param env
   *          Entorno en el que se quiere colocar.
   */
  public RecursiveAgent (Environment env) {
    super(env);
    m_stack = new Stack <Direction>();
  }

  /*
   * (non-Javadoc)
   *
   * @see agent.Agent#setEnvironment(gui.environment.Environment)
   */
  @Override
  public void setEnvironment (Environment env) {
    super.setEnvironment(env);

    Maze maze = env.getMaze();
    m_visited = new boolean [maze.getHeight()] [maze.getWidth()];
  }

  /*
   * (non-Javadoc)
   *
   * @see agent.Agent#getAlgorithmName()
   */
  @Override
  public String getAlgorithmName () {
    return "Recursive Backtracking";
  }

  /*
   * (non-Javadoc)
   *
   * @see agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    Direction dir = selectDirection();
    if (dir == Direction.NONE && !m_stack.empty()) {
      Direction head = m_stack.peek().getOpposite();
      if (m_env.movementAllowed(m_pos, head)) {
        m_backtracking = true;
        dir = m_stack.pop().getOpposite();
      }
    }
    return dir;
  }

  /*
   * (non-Javadoc)
   *
   * @see agent.Agent#doMovement(maze.Direction)
   */
  @Override
  public void doMovement (Direction dir) {
    Point prev = m_pos;
    m_visited[m_pos.y][m_pos.x] = true;
    super.doMovement(dir);

    if (!prev.equals(m_pos)) {
      if (!m_backtracking)
        m_stack.push(dir);
      else
        m_backtracking = false;
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see agent.Agent#setPosition(java.awt.Point)
   */
  @Override
  public void setPosition (Point pos) {
    resetMemory();
    super.setPosition(pos);
  };

  /*
   * (non-Javadoc)
   *
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

  /*
   * (non-Javadoc)
   *
   * @see agent.Agent#getConfigurationPanel()
   */
  @Override
  public AgentConfigurationPanel getConfigurationPanel () {
    return new AgentConfigurationPanel() {
      private static final long serialVersionUID = 1L;

      @Override
      protected void createGUI (JPanel root) {
        root.setLayout(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        root.add(new JLabel("This agent has no configuration options available"));
      }

      @Override
      protected void cancel () {
      }

      @Override
      protected boolean accept () {
        return false;
      }
    };
  }

  /*
   * (non-Javadoc)
   *
   * @see agent.Agent#clone()
   */
  @Override
  public Object clone () {
    RecursiveAgent ag = new RecursiveAgent(m_env);
    return ag;
  }

  /**
   * Elige la dirección que le lleva a una no visitada que no tiene una pared
   * delante.
   *
   * @return La dirección seleccionada. Devuelve {@code Direction.NONE} si no
   *         hay ninguna celda adyacente accesible no visitada.
   */
  private Direction selectDirection () {
    Point exit = m_env.getMaze().getExit();
    for (int i = 1; i < Direction.MAX_DIRECTIONS; i++) {
      Direction dir = Direction.fromIndex(i);
      Point next = dir.movePoint(m_pos);
      if (next.equals(exit))
        return dir;
      else if (look(dir) == MazeCell.Vision.EMPTY && !m_visited[next.y][next.x]) {
        return dir;
      }
    }
    return Direction.NONE;
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
    m_stack = new Stack <Direction>();
  }
}
