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
 * @file SituationActionRule.java
 * @date 31/10/2014
 */
package es.ull.mazesolver.agent.rules;

import es.ull.mazesolver.agent.SARulesAgent;
import es.ull.mazesolver.agent.rules.parser.SituationActionParser.Sa_ruleContext;
import es.ull.mazesolver.gui.MainWindow;

/**
 * Clase que modela una regla de situación-acción.
 */
public class SituationActionRule implements Cloneable {
  private RulePredicate m_predicate;
  private RuleAction m_action;

  /**
   * Construye la regla asociada al nodo de tipo regla de situación-acción
   * dentro del árbol de parseo.
   *
   * @param ctx
   *          Contexto de una regla dentro del árbol de parseo de una entrada.
   * @return Regla creada a partir del árbol.
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
          MainWindow.getTranslations().exception().situationActionInvalid());

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
  public RuleAction getAction (SARulesAgent ag) {
    if (m_predicate.evaluate(ag))
      return m_action;
    return null;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#clone()
   */
  @Override
  public Object clone () {
    return new SituationActionRule((RulePredicate) m_predicate.clone(),
        (RuleAction) m_action.clone());
  }

}
