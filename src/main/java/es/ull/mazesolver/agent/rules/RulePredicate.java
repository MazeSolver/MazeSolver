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
 * @file RulePredicate.java
 * @date 31/10/2014
 */
package es.ull.mazesolver.agent.rules;

import es.ull.mazesolver.agent.SARulesAgent;
import es.ull.mazesolver.agent.rules.parser.SituationActionParser.AndContext;
import es.ull.mazesolver.agent.rules.parser.SituationActionParser.DirectionContext;
import es.ull.mazesolver.agent.rules.parser.SituationActionParser.OrContext;
import es.ull.mazesolver.agent.rules.parser.SituationActionParser.ParensContext;
import es.ull.mazesolver.agent.rules.parser.SituationActionParser.SingleTermContext;
import es.ull.mazesolver.agent.rules.parser.SituationActionParser.SituationContext;
import es.ull.mazesolver.agent.rules.parser.SituationActionParser.TermContext;
import es.ull.mazesolver.maze.MazeCell;
import es.ull.mazesolver.util.Direction;

/**
 * Representa una situación o predicado dentro de una regla de situación-acción.
 */
public abstract class RulePredicate implements Cloneable {
    protected boolean m_negated = false;

    /**
     * Distintos conectores de reglas que soporta el lenguaje.
     */
    private static enum RuleConnector {
        OR, AND;
    }

    /**
     * Construye un predicado a partir de una "situación" del árbol de parseo.
     *
     * @param ctx Nodo del árbol de parseo con un predicado o situación que se
     *            quiere procesar.
     * @return Predicado creado a partir del árbol.
     */
    public static RulePredicate createFromTree(SituationContext ctx) {
        // Lo que hay... (v_v)
        if (ctx instanceof OrContext)
            return createFromTree((OrContext) ctx);
        if (ctx instanceof AndContext)
            return createFromTree((AndContext) ctx);
        if (ctx instanceof ParensContext)
            return createFromTree((ParensContext) ctx);
        if (ctx instanceof SingleTermContext)
            return createFromTree((SingleTermContext) ctx);

        return null;
    }

    private static RulePredicate createFromTree(OrContext ctx) {
        return new ComplexRulePredicate(createFromTree(ctx.situation(0)),
                createFromTree(ctx.situation(1)), RuleConnector.OR);
    }

    private static RulePredicate createFromTree(AndContext ctx) {
        return new ComplexRulePredicate(createFromTree(ctx.situation(0)),
                createFromTree(ctx.situation(1)), RuleConnector.AND);
    }

    private static RulePredicate createFromTree(ParensContext ctx) {
        RulePredicate pred = createFromTree(ctx.situation());
        if (ctx.NOT() != null)
            pred.negate();
        return pred;
    }

    private static RulePredicate createFromTree(SingleTermContext ctx) {
        return SimpleRulePredicate.createFromTree(ctx.term());
    }

    /**
     * Invierte el resultado de la regla.
     */
    public void negate() {
        m_negated = !m_negated;
    }

    /**
     * Evalúa la regla para el agente indicado e indica si se cumple o no.
     *
     * @param ag Agente sobre el que se quiere evaluar el predicado.
     * @return Si la situación representada se da para el agente indicado.
     */
    public abstract boolean evaluate(SARulesAgent ag);

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public abstract Object clone();

    /**
     * Subclase que gestiona una regla sin conectores. Es decir, un término de la
     * gramática: "term".
     */
    private static class SimpleRulePredicate extends RulePredicate {
        private Direction m_direction;
        private MazeCell.Vision m_vision;
        private boolean m_visited_status;

        /**
         * Construye un término a partir del árbol de parseo generado.
         *
         * @param term_ctx Contexto que hace referencia al término que se quiere convertir
         *                 a regla simple.
         * @return Regla simple que representa el término.
         */
        public static SimpleRulePredicate createFromTree(TermContext term_ctx) {
            DirectionContext ctx = term_ctx.direction();

            // Extraemos la dirección
            Direction dir;
            if (ctx.UP() != null)
                dir = Direction.UP;
            else if (ctx.DOWN() != null)
                dir = Direction.DOWN;
            else if (ctx.LEFT() != null)
                dir = Direction.LEFT;
            else
                // RIGHT
                dir = Direction.RIGHT;

            // Extraemos el estado de la celda
            MazeCell.Vision vision = null;
            if (term_ctx.FREE() != null)
                vision = MazeCell.Vision.EMPTY;
            else if (term_ctx.WALL() != null)
                vision = MazeCell.Vision.WALL;
            else if (term_ctx.AGENT() != null)
                vision = MazeCell.Vision.AGENT;
            else if (term_ctx.OFFLIMITS() != null)
                vision = MazeCell.Vision.OFFLIMITS;

            // Creamos la regla y la negamos si es su caso
            SimpleRulePredicate pred;
            if (vision == null)
                pred = new SimpleRulePredicate(dir);
            else
                pred = new SimpleRulePredicate(dir, vision);

            if (term_ctx.NOT() != null)
                pred.negate();

            return pred;
        }

        /**
         * Crea un predicado que hace referencia a que en la dirección indicada se
         * tiene una visión específica.
         *
         * @param dir Dirección a la que hace referencia el término.
         * @param st  Estado de la celda.
         */
        public SimpleRulePredicate(Direction dir, MazeCell.Vision vision) {
            m_direction = dir;
            m_vision = vision;
        }

        /**
         * Crea un predicado que hace referencia a que la dirección indicada ha sido
         * visitada.
         *
         * @param dir Dirección a la que hace referencia el término.
         */
        public SimpleRulePredicate(Direction dir) {
            m_direction = dir;
            m_visited_status = true;
        }

        /*
         * (non-Javadoc)
         *
         * @see agent.rules.RulePredicate#evaluate(agent.Agent)
         */
        @Override
        public boolean evaluate(SARulesAgent ag) {
            boolean result = false;

            if (m_visited_status)
                result = ag.hasVisited(m_direction);
            else {
                MazeCell.Vision vision = ag.look(m_direction);
                result = vision == m_vision;
            }

            return result ^ m_negated;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#clone()
         */
        @Override
        public Object clone() {
            SimpleRulePredicate pred = new SimpleRulePredicate(m_direction, m_vision);
            pred.m_negated = m_negated;
            pred.m_visited_status = m_visited_status;
            return pred;
        }

    }

    /**
     * Regla que gestiona la unión de 2 reglas usando los operadores definidos.
     */
    private static class ComplexRulePredicate extends RulePredicate {
        private RulePredicate m_p1, m_p2;
        private RuleConnector m_connector;

        /**
         * Crea un nuevo predicado compuesto.
         *
         * @param p1  Regla izquierda.
         * @param p2  Regla derecha.
         * @param con Conector entre las reglas.
         */
        public ComplexRulePredicate(RulePredicate p1, RulePredicate p2, RuleConnector con) {
            m_p1 = p1;
            m_p2 = p2;
            m_connector = con;
        }

        /*
         * (non-Javadoc)
         *
         * @see agent.rules.RulePredicate#evaluate(agent.Agent)
         */
        @Override
        public boolean evaluate(SARulesAgent ag) {
            boolean result = false;
            switch (m_connector) {
                case OR:
                    result = m_p1.evaluate(ag) || m_p2.evaluate(ag);
                    break;
                case AND:
                    result = m_p1.evaluate(ag) && m_p2.evaluate(ag);
                    break;
            }
            return result ^ m_negated;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Object#clone()
         */
        @Override
        public Object clone() {
            ComplexRulePredicate pred =
                    new ComplexRulePredicate((RulePredicate) m_p1.clone(), (RulePredicate) m_p2.clone(),
                            m_connector);
            pred.m_negated = m_negated;
            return pred;
        }

    }

}
