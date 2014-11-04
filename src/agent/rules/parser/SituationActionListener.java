// Generated from SituationAction.g4 by ANTLR 4.4
package agent.rules.parser;

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