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
 * @file RuleAction.java
 * @date 31/10/2014
 */
package agent.rules;

import maze.Direction;
import agent.rules.parser.SituationActionParser.ActionContext;
import agent.rules.parser.SituationActionParser.DirectionContext;

/**
 * Clase que representa a la acción asociada a una regla.
 */
public class RuleAction implements Cloneable {
  private Direction m_direction;

  /**
   * Crea una acción a partir de un nodo de acción del árbol de parseo.
   *
   * @param action_ctx
   *          Nodo del árbol de parseo donde se encuentra la acción a realizar.
   * @return Acción especificada en el subárbol indicado.
   */
  public static RuleAction createFromTree (ActionContext action_ctx) {
    DirectionContext dir_ctx = action_ctx.direction();
    Direction dir;

    // Si tiene dirección especificada, la acción es un movimiento.
    if (dir_ctx != null) {
      if (dir_ctx.UP() != null)
        dir = Direction.UP;
      else if (dir_ctx.DOWN() != null)
        dir = Direction.DOWN;
      else if (dir_ctx.LEFT() != null)
        dir = Direction.LEFT;
      else // RIGHT
        dir = Direction.RIGHT;
    }
    // Si no la tiene, es el movimiento "STOP"
    else
      dir = Direction.NONE;

    return new RuleAction(dir);
  }

  /**
   * Crea la acción de mover en la dirección especificada.
   *
   * @param dir
   *          Dirección hacia la que realizar el movimiento.
   */
  public RuleAction (Direction dir) {
    m_direction = dir;
  }

  /**
   * @return La dirección hacia la que es el movimiento o la dirección nula si
   *         la regla es de "STOP".
   */
  public Direction getDirection () {
    return m_direction;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  protected Object clone () {
    return new RuleAction(m_direction);
  }

}
