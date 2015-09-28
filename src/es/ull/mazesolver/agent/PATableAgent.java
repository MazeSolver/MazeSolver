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
 * @file PATableAgent.java
 * @date 20/11/2014
 */
package es.ull.mazesolver.agent;

import java.awt.Color;

import es.ull.mazesolver.gui.configuration.AgentConfigurationPanel;
import es.ull.mazesolver.gui.configuration.PATableAgentConfigurationPanel;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.maze.MazeCell.Vision;
import es.ull.mazesolver.util.Direction;

/**
 * Clase que representa a un agente basado en una tabla de percepción-acción.
 */
public class PATableAgent extends Agent {
  private static final long serialVersionUID = -4993197615980679396L;

  private Direction [][][][] m_table;

  /**
   * Crea el agente a partir de un entorno, con la configuración por defecto.
   * @param env Entorno en el que crear el agente.
   */
  public PATableAgent (Environment env) {
    super(env);

    m_table = new Direction[][][][]{
      {
        {
          {Direction.DOWN, Direction.LEFT}, // 0,0,0,0 ; 0,0,0,1
          {Direction.RIGHT, Direction.DOWN} // 0,0,1,0 ; 0,0,1,1
        },
        {
          {Direction.UP, Direction.LEFT},   // 0,1,0,0 ; 0,1,0,1
          {Direction.RIGHT, Direction.UP}   // 0,1,1,0 ; 0,1,1,1
        }
      },
      {
        {
          {Direction.DOWN, Direction.LEFT}, // 1,0,0,0 ; 1,0,0,1
          {Direction.RIGHT, Direction.DOWN} // 1,0,1,0 ; 1,0,1,1
        },
        {
          {Direction.RIGHT, Direction.LEFT},  // 1,1,0,0 ; 1,1,0,1
          {Direction.RIGHT, Direction.NONE}   // 1,1,1,0 ; 1,1,1,1
        }
      }
    };
  }

  /**
   * Cambia la tabla de percepción-acción del agente por otra. Cada dimensión
   * del array debe tener 2 posiciones (EMPTY y WALL) y tiene una dimensión por
   * cada dirección alrededor del agente (UP, DOWN, LEFT y RIGHT). El valor de
   * cada celda de la tabla puede ser cualquier {@link Direction}.
   *
   * @param pa_table La nueva tabla de percepción-acción. Se almacenará una
   * copia de la misma.
   */
  public void setPerceptionActionTable (Direction[][][][] pa_table) {
    m_table = deepDataCopy(pa_table);
  }

  /**
   * @return Una copia de la tabla de percepción-acción del agente.
   */
  public Direction[][][][] getPerceptionActionTable () {
    return deepDataCopy(m_table);
  }

  /* (non-Javadoc)
   * @see agent.Agent#getAlgorithmName()
   */
  @Override
  public String getAlgorithmName () {
    return "Perception-Action Table";
  }

  /*
   * (non-Javadoc)
   *
   * @see es.ull.mazesolver.agent.Agent#getAlgorithmColor()
   */
  @Override
  public Color getAlgorithmColor () {
    return Color.GREEN;
  }

  /* (non-Javadoc)
   * @see agent.Agent#getNextMovement()
   */
  @Override
  public Direction getNextMovement () {
    // Cualquier lugar al que no nos podamos mover se considerará una "pared"
    Vision up = m_env.movementAllowed(m_pos, Direction.UP)? Vision.EMPTY : Vision.WALL;
    Vision down = m_env.movementAllowed(m_pos, Direction.DOWN)? Vision.EMPTY : Vision.WALL;
    Vision left = m_env.movementAllowed(m_pos, Direction.LEFT)? Vision.EMPTY : Vision.WALL;
    Vision right = m_env.movementAllowed(m_pos, Direction.RIGHT)? Vision.EMPTY : Vision.WALL;

    return m_table[visionToIndex(up)][visionToIndex(down)]
                  [visionToIndex(left)][visionToIndex(right)];
  }

  /* (non-Javadoc)
   * @see agent.Agent#resetMemory()
   */
  @Override
  public void resetMemory () {
    // No tiene memoria, así que no hacemos nada
  }

  /* (non-Javadoc)
   * @see agent.Agent#getConfigurationPanel()
   */
  @Override
  public AgentConfigurationPanel getConfigurationPanel () {
    return new PATableAgentConfigurationPanel(this);
  }

  /* (non-Javadoc)
   * @see agent.Agent#clone()
   */
  @Override
  public Object clone () {
    PATableAgent ag = new PATableAgent(m_env);
    ag.setAgentColor(getAgentColor());
    ag.setPerceptionActionTable(m_table);

    return ag;
  }

  /**
   * Traduce una visión a índice entre 0 y 1.
   *
   * @param vision Visión a traducir.
   * @return Índice asociado a la visión.
   */
  private static int visionToIndex (Vision vision) {
    switch (vision) {
      case EMPTY:
      case OFFLIMITS:
        return 0;
      case WALL:
      case AGENT:
        return 1;
      default:
        return -1;
    }
  }

  /**
   * Hace una copia profunda de la configuración de un agente.
   *
   * @param data Array tetra-dimensional con las direcciones asociadas a cada
   *        percepción del agente.
   * @return Copia profunda del array de datos de entrada.
   */
  private static Direction[][][][] deepDataCopy (Direction[][][][] data) {
    Direction [][][][] result = new Direction[2][2][2][2];
    for (int i = 0; i < 2; i++)
      for (int j = 0; j < 2; j++)
        for (int k = 0; k < 2; k++)
          for (int l = 0; l < 2; l++)
            result[i][j][k][l] = data[i][j][k][l];

    return result;
  }

}
