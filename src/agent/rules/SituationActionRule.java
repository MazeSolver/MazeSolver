/**
 * @file SituationActionRule.java
 * @date 31/10/2014
 */
package agent.rules;

import agent.Agent;
import agent.rules.parser.SituationActionParser.Sa_ruleContext;

/**
 * Clase que modela una regla de situación-acción.
 */
public class SituationActionRule {
  private RulePredicate m_predicate;
  private RuleAction m_action;

  /**
   * Construye la regla asociada al nodo de tipo regla de situación-acción
   * dentro del árbol de parseo.
   *
   * @param ctx
   *          Contexto de una regla dentro del árbol de parseo de una entrada.
   */
  public static SituationActionRule createFromTree (Sa_ruleContext ctx) {
    return new SituationActionRule(RulePredicate.createFromTree(ctx.situation()),
        RuleAction.createFromTree(ctx.action()));
  }

  /**
   * Crea una regla de situación-acción a partir de los objetos de situación y
   * acción ya creados.
   *
   * @param pred
   *          Predicado o situación.
   * @param act
   *          Acción a realizar si se cumple la situación.
   */
  public SituationActionRule (RulePredicate pred, RuleAction act) {
    if (pred == null || act == null)
      throw new IllegalArgumentException(
          "La situación o la acción eran nulas: No se puede crear la regla.");

    m_predicate = pred;
    m_action = act;
  }

  /**
   * Aplica la regla al agente analizando su entorno para decidir cuál debería
   * ser su siguiente acción a tomar.
   *
   * @param ag
   *          Agente que se quiere mover.
   * @return Acción que la regla indica que se debería tomar. Si la regla no
   *         aplica dado el estado actual, devuelve null.
   */
  public RuleAction getAction (Agent ag) {
    if (m_predicate.evaluate(ag))
      return m_action;
    return null;
  }
}
