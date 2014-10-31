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
public class RuleAction {
  private Direction m_direction;

  /**
   * Crea una acción a partir de un nodo de acción del árbol de parseo.
   *
   * @param action
   *          Nodo del árbol de parseo donde se encuentra la acción a realizar.
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

}
