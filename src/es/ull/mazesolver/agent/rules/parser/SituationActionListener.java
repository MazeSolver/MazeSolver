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

// Generated from SituationAction.g4 by ANTLR 4.4
package es.ull.mazesolver.agent.rules.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SituationActionParser}.
 */
public interface SituationActionListener extends ParseTreeListener {
  /**
   * Enter a parse tree produced by the {@code SingleTerm} labeled alternative
   * in {@link SituationActionParser#situation}.
   *
   * @param ctx
   *          the parse tree
   */
  void enterSingleTerm (@NotNull SituationActionParser.SingleTermContext ctx);

  /**
   * Exit a parse tree produced by the {@code SingleTerm} labeled alternative in
   * {@link SituationActionParser#situation}.
   *
   * @param ctx
   *          the parse tree
   */
  void exitSingleTerm (@NotNull SituationActionParser.SingleTermContext ctx);

  /**
   * Enter a parse tree produced by the {@code Or} labeled alternative in
   * {@link SituationActionParser#situation}.
   *
   * @param ctx
   *          the parse tree
   */
  void enterOr (@NotNull SituationActionParser.OrContext ctx);

  /**
   * Exit a parse tree produced by the {@code Or} labeled alternative in
   * {@link SituationActionParser#situation}.
   *
   * @param ctx
   *          the parse tree
   */
  void exitOr (@NotNull SituationActionParser.OrContext ctx);

  /**
   * Enter a parse tree produced by the {@code Parens} labeled alternative in
   * {@link SituationActionParser#situation}.
   *
   * @param ctx
   *          the parse tree
   */
  void enterParens (@NotNull SituationActionParser.ParensContext ctx);

  /**
   * Exit a parse tree produced by the {@code Parens} labeled alternative in
   * {@link SituationActionParser#situation}.
   *
   * @param ctx
   *          the parse tree
   */
  void exitParens (@NotNull SituationActionParser.ParensContext ctx);

  /**
   * Enter a parse tree produced by the {@code And} labeled alternative in
   * {@link SituationActionParser#situation}.
   *
   * @param ctx
   *          the parse tree
   */
  void enterAnd (@NotNull SituationActionParser.AndContext ctx);

  /**
   * Exit a parse tree produced by the {@code And} labeled alternative in
   * {@link SituationActionParser#situation}.
   *
   * @param ctx
   *          the parse tree
   */
  void exitAnd (@NotNull SituationActionParser.AndContext ctx);

  /**
   * Enter a parse tree produced by {@link SituationActionParser#action}.
   *
   * @param ctx
   *          the parse tree
   */
  void enterAction (@NotNull SituationActionParser.ActionContext ctx);

  /**
   * Exit a parse tree produced by {@link SituationActionParser#action}.
   *
   * @param ctx
   *          the parse tree
   */
  void exitAction (@NotNull SituationActionParser.ActionContext ctx);

  /**
   * Enter a parse tree produced by {@link SituationActionParser#term}.
   *
   * @param ctx
   *          the parse tree
   */
  void enterTerm (@NotNull SituationActionParser.TermContext ctx);

  /**
   * Exit a parse tree produced by {@link SituationActionParser#term}.
   *
   * @param ctx
   *          the parse tree
   */
  void exitTerm (@NotNull SituationActionParser.TermContext ctx);

  /**
   * Enter a parse tree produced by {@link SituationActionParser#program}.
   *
   * @param ctx
   *          the parse tree
   */
  void enterProgram (@NotNull SituationActionParser.ProgramContext ctx);

  /**
   * Exit a parse tree produced by {@link SituationActionParser#program}.
   *
   * @param ctx
   *          the parse tree
   */
  void exitProgram (@NotNull SituationActionParser.ProgramContext ctx);

  /**
   * Enter a parse tree produced by {@link SituationActionParser#sa_rule}.
   *
   * @param ctx
   *          the parse tree
   */
  void enterSa_rule (@NotNull SituationActionParser.Sa_ruleContext ctx);

  /**
   * Exit a parse tree produced by {@link SituationActionParser#sa_rule}.
   *
   * @param ctx
   *          the parse tree
   */
  void exitSa_rule (@NotNull SituationActionParser.Sa_ruleContext ctx);

  /**
   * Enter a parse tree produced by {@link SituationActionParser#direction}.
   *
   * @param ctx
   *          the parse tree
   */
  void enterDirection (@NotNull SituationActionParser.DirectionContext ctx);

  /**
   * Exit a parse tree produced by {@link SituationActionParser#direction}.
   *
   * @param ctx
   *          the parse tree
   */
  void exitDirection (@NotNull SituationActionParser.DirectionContext ctx);
}