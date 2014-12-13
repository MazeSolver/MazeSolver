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

import java.util.List;

import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings ({"all", "warnings", "unchecked", "unused", "cast"})
public class SituationActionParser extends Parser {
  static {
    RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
  }

  protected static final DFA [] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
  public static final int IMPLIES = 1, DOT = 2, AND = 3, OR = 4, LEFTPAR = 5, RIGHTPAR = 6,
      NOT = 7, FREE = 8, WALL = 9, VISITED = 10, AGENT = 11, OFFLIMITS = 12, MOVE = 13, STOP = 14,
      UP = 15, DOWN = 16, LEFT = 17, RIGHT = 18, COMMENT = 19, BLANK = 20;
  public static final String [] tokenNames = {"<INVALID>", "IMPLIES", "'.'", "AND", "OR", "'('",
      "')'", "NOT", "FREE", "WALL", "VISITED", "AGENT", "OFFLIMITS", "MOVE", "STOP", "UP", "DOWN",
      "LEFT", "RIGHT", "COMMENT", "BLANK"};
  public static final int RULE_program = 0, RULE_sa_rule = 1, RULE_situation = 2, RULE_term = 3,
      RULE_action = 4, RULE_direction = 5;
  public static final String [] ruleNames = {"program", "sa_rule", "situation", "term", "action",
      "direction"};

  @Override
  public String getGrammarFileName () {
    return "SituationAction.g4";
  }

  @Override
  public String [] getTokenNames () {
    return tokenNames;
  }

  @Override
  public String [] getRuleNames () {
    return ruleNames;
  }

  @Override
  public String getSerializedATN () {
    return _serializedATN;
  }

  @Override
  public ATN getATN () {
    return _ATN;
  }

  public SituationActionParser (TokenStream input) {
    super(input);
    _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
  }

  public static class ProgramContext extends ParserRuleContext {
    public Sa_ruleContext sa_rule (int i) {
      return getRuleContext(Sa_ruleContext.class, i);
    }

    public TerminalNode EOF () {
      return getToken(SituationActionParser.EOF, 0);
    }

    public List <Sa_ruleContext> sa_rule () {
      return getRuleContexts(Sa_ruleContext.class);
    }

    public ProgramContext (ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex () {
      return RULE_program;
    }

    @Override
    public void enterRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).enterProgram(this);
    }

    @Override
    public void exitRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).exitProgram(this);
    }
  }

  public final ProgramContext program () throws RecognitionException {
    ProgramContext _localctx = new ProgramContext(_ctx, getState());
    enterRule(_localctx, 0, RULE_program);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(13);
        _errHandler.sync(this);
        _la = _input.LA(1);
        do {
          {
            {
              setState(12);
              sa_rule();
            }
          }
          setState(15);
          _errHandler.sync(this);
          _la = _input.LA(1);
        }
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LEFTPAR) | (1L << NOT) | (1L << UP)
            | (1L << DOWN) | (1L << LEFT) | (1L << RIGHT))) != 0));
        setState(17);
        match(EOF);
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static class Sa_ruleContext extends ParserRuleContext {
    public SituationContext situation () {
      return getRuleContext(SituationContext.class, 0);
    }

    public TerminalNode DOT () {
      return getToken(SituationActionParser.DOT, 0);
    }

    public TerminalNode IMPLIES () {
      return getToken(SituationActionParser.IMPLIES, 0);
    }

    public ActionContext action () {
      return getRuleContext(ActionContext.class, 0);
    }

    public Sa_ruleContext (ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex () {
      return RULE_sa_rule;
    }

    @Override
    public void enterRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).enterSa_rule(this);
    }

    @Override
    public void exitRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).exitSa_rule(this);
    }
  }

  public final Sa_ruleContext sa_rule () throws RecognitionException {
    Sa_ruleContext _localctx = new Sa_ruleContext(_ctx, getState());
    enterRule(_localctx, 2, RULE_sa_rule);
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(19);
        situation(0);
        setState(20);
        match(IMPLIES);
        setState(21);
        action();
        setState(22);
        match(DOT);
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static class SituationContext extends ParserRuleContext {
    public SituationContext (ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex () {
      return RULE_situation;
    }

    public SituationContext () {
    }

    public void copyFrom (SituationContext ctx) {
      super.copyFrom(ctx);
    }
  }

  public static class SingleTermContext extends SituationContext {
    public TermContext term () {
      return getRuleContext(TermContext.class, 0);
    }

    public SingleTermContext (SituationContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).enterSingleTerm(this);
    }

    @Override
    public void exitRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).exitSingleTerm(this);
    }
  }

  public static class OrContext extends SituationContext {
    public List <SituationContext> situation () {
      return getRuleContexts(SituationContext.class);
    }

    public SituationContext situation (int i) {
      return getRuleContext(SituationContext.class, i);
    }

    public TerminalNode OR () {
      return getToken(SituationActionParser.OR, 0);
    }

    public OrContext (SituationContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).enterOr(this);
    }

    @Override
    public void exitRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).exitOr(this);
    }
  }

  public static class ParensContext extends SituationContext {
    public SituationContext situation () {
      return getRuleContext(SituationContext.class, 0);
    }

    public TerminalNode NOT () {
      return getToken(SituationActionParser.NOT, 0);
    }

    public TerminalNode RIGHTPAR () {
      return getToken(SituationActionParser.RIGHTPAR, 0);
    }

    public TerminalNode LEFTPAR () {
      return getToken(SituationActionParser.LEFTPAR, 0);
    }

    public ParensContext (SituationContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).enterParens(this);
    }

    @Override
    public void exitRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).exitParens(this);
    }
  }

  public static class AndContext extends SituationContext {
    public List <SituationContext> situation () {
      return getRuleContexts(SituationContext.class);
    }

    public SituationContext situation (int i) {
      return getRuleContext(SituationContext.class, i);
    }

    public TerminalNode AND () {
      return getToken(SituationActionParser.AND, 0);
    }

    public AndContext (SituationContext ctx) {
      copyFrom(ctx);
    }

    @Override
    public void enterRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).enterAnd(this);
    }

    @Override
    public void exitRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).exitAnd(this);
    }
  }

  public final SituationContext situation () throws RecognitionException {
    return situation(0);
  }

  private SituationContext situation (int _p) throws RecognitionException {
    ParserRuleContext _parentctx = _ctx;
    int _parentState = getState();
    SituationContext _localctx = new SituationContext(_ctx, _parentState);
    SituationContext _prevctx = _localctx;
    int _startState = 4;
    enterRecursionRule(_localctx, 4, RULE_situation, _p);
    int _la;
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(33);
        switch (_input.LA(1)) {
          case LEFTPAR:
          case NOT: {
            _localctx = new ParensContext(_localctx);
            _ctx = _localctx;
            _prevctx = _localctx;

            setState(26);
            _la = _input.LA(1);
            if (_la == NOT) {
              {
                setState(25);
                match(NOT);
              }
            }

            setState(28);
            match(LEFTPAR);
            setState(29);
            situation(0);
            setState(30);
            match(RIGHTPAR);
          }
            break;
          case UP:
          case DOWN:
          case LEFT:
          case RIGHT: {
            _localctx = new SingleTermContext(_localctx);
            _ctx = _localctx;
            _prevctx = _localctx;
            setState(32);
            term();
          }
            break;
          default:
            throw new NoViableAltException(this);
        }
        _ctx.stop = _input.LT(-1);
        setState(43);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            if (_parseListeners != null)
              triggerExitRuleEvent();
            _prevctx = _localctx;
            {
              setState(41);
              switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
                case 1: {
                  _localctx = new AndContext(new SituationContext(_parentctx, _parentState));
                  pushNewRecursionContext(_localctx, _startState, RULE_situation);
                  setState(35);
                  if (!(precpred(_ctx, 4)))
                    throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                  setState(36);
                  match(AND);
                  setState(37);
                  situation(5);
                }
                  break;
                case 2: {
                  _localctx = new OrContext(new SituationContext(_parentctx, _parentState));
                  pushNewRecursionContext(_localctx, _startState, RULE_situation);
                  setState(38);
                  if (!(precpred(_ctx, 3)))
                    throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                  setState(39);
                  match(OR);
                  setState(40);
                  situation(4);
                }
                  break;
              }
            }
          }
          setState(45);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 4, _ctx);
        }
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      unrollRecursionContexts(_parentctx);
    }
    return _localctx;
  }

  public static class TermContext extends ParserRuleContext {
    public TerminalNode NOT () {
      return getToken(SituationActionParser.NOT, 0);
    }

    public TerminalNode FREE () {
      return getToken(SituationActionParser.FREE, 0);
    }

    public TerminalNode AGENT () {
      return getToken(SituationActionParser.AGENT, 0);
    }

    public TerminalNode OFFLIMITS () {
      return getToken(SituationActionParser.OFFLIMITS, 0);
    }

    public TerminalNode WALL () {
      return getToken(SituationActionParser.WALL, 0);
    }

    public DirectionContext direction () {
      return getRuleContext(DirectionContext.class, 0);
    }

    public TerminalNode VISITED () {
      return getToken(SituationActionParser.VISITED, 0);
    }

    public TermContext (ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex () {
      return RULE_term;
    }

    @Override
    public void enterRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).enterTerm(this);
    }

    @Override
    public void exitRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).exitTerm(this);
    }
  }

  public final TermContext term () throws RecognitionException {
    TermContext _localctx = new TermContext(_ctx, getState());
    enterRule(_localctx, 6, RULE_term);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(46);
        direction();
        setState(48);
        _la = _input.LA(1);
        if (_la == NOT) {
          {
            setState(47);
            match(NOT);
          }
        }

        setState(50);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FREE) | (1L << WALL)
            | (1L << VISITED) | (1L << AGENT) | (1L << OFFLIMITS))) != 0))) {
          _errHandler.recoverInline(this);
        }
        consume();
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static class ActionContext extends ParserRuleContext {
    public TerminalNode STOP () {
      return getToken(SituationActionParser.STOP, 0);
    }

    public TerminalNode MOVE () {
      return getToken(SituationActionParser.MOVE, 0);
    }

    public DirectionContext direction () {
      return getRuleContext(DirectionContext.class, 0);
    }

    public ActionContext (ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex () {
      return RULE_action;
    }

    @Override
    public void enterRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).enterAction(this);
    }

    @Override
    public void exitRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).exitAction(this);
    }
  }

  public final ActionContext action () throws RecognitionException {
    ActionContext _localctx = new ActionContext(_ctx, getState());
    enterRule(_localctx, 8, RULE_action);
    try {
      setState(55);
      switch (_input.LA(1)) {
        case MOVE:
          enterOuterAlt(_localctx, 1);
          {
            setState(52);
            match(MOVE);
            setState(53);
            direction();
          }
          break;
        case STOP:
          enterOuterAlt(_localctx, 2);
          {
            setState(54);
            match(STOP);
          }
          break;
        default:
          throw new NoViableAltException(this);
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public static class DirectionContext extends ParserRuleContext {
    public TerminalNode LEFT () {
      return getToken(SituationActionParser.LEFT, 0);
    }

    public TerminalNode UP () {
      return getToken(SituationActionParser.UP, 0);
    }

    public TerminalNode RIGHT () {
      return getToken(SituationActionParser.RIGHT, 0);
    }

    public TerminalNode DOWN () {
      return getToken(SituationActionParser.DOWN, 0);
    }

    public DirectionContext (ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex () {
      return RULE_direction;
    }

    @Override
    public void enterRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).enterDirection(this);
    }

    @Override
    public void exitRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).exitDirection(this);
    }
  }

  public final DirectionContext direction () throws RecognitionException {
    DirectionContext _localctx = new DirectionContext(_ctx, getState());
    enterRule(_localctx, 10, RULE_direction);
    int _la;
    try {
      enterOuterAlt(_localctx, 1);
      {
        setState(57);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UP) | (1L << DOWN) | (1L << LEFT) | (1L << RIGHT))) != 0))) {
          _errHandler.recoverInline(this);
        }
        consume();
      }
    }
    catch (RecognitionException re) {
      _localctx.exception = re;
      _errHandler.reportError(this, re);
      _errHandler.recover(this, re);
    }
    finally {
      exitRule();
    }
    return _localctx;
  }

  public boolean sempred (RuleContext _localctx, int ruleIndex, int predIndex) {
    switch (ruleIndex) {
      case 2:
        return situation_sempred((SituationContext) _localctx, predIndex);
    }
    return true;
  }

  private boolean situation_sempred (SituationContext _localctx, int predIndex) {
    switch (predIndex) {
      case 0:
        return precpred(_ctx, 4);
      case 1:
        return precpred(_ctx, 3);
    }
    return true;
  }

  public static final String _serializedATN =
      "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\26>\4\2\t\2\4\3\t"
          + "\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\6\2\20\n\2\r\2\16\2\21\3\2\3\2"
          + "\3\3\3\3\3\3\3\3\3\3\3\4\3\4\5\4\35\n\4\3\4\3\4\3\4\3\4\3\4\5\4$\n\4\3"
          + "\4\3\4\3\4\3\4\3\4\3\4\7\4,\n\4\f\4\16\4/\13\4\3\5\3\5\5\5\63\n\5\3\5"
          + "\3\5\3\6\3\6\3\6\5\6:\n\6\3\7\3\7\3\7\2\3\6\b\2\4\6\b\n\f\2\4\3\2\n\16"
          + "\3\2\21\24>\2\17\3\2\2\2\4\25\3\2\2\2\6#\3\2\2\2\b\60\3\2\2\2\n9\3\2\2"
          + "\2\f;\3\2\2\2\16\20\5\4\3\2\17\16\3\2\2\2\20\21\3\2\2\2\21\17\3\2\2\2"
          + "\21\22\3\2\2\2\22\23\3\2\2\2\23\24\7\2\2\3\24\3\3\2\2\2\25\26\5\6\4\2"
          + "\26\27\7\3\2\2\27\30\5\n\6\2\30\31\7\4\2\2\31\5\3\2\2\2\32\34\b\4\1\2"
          + "\33\35\7\t\2\2\34\33\3\2\2\2\34\35\3\2\2\2\35\36\3\2\2\2\36\37\7\7\2\2"
          + "\37 \5\6\4\2 !\7\b\2\2!$\3\2\2\2\"$\5\b\5\2#\32\3\2\2\2#\"\3\2\2\2$-\3"
          + "\2\2\2%&\f\6\2\2&\'\7\5\2\2\',\5\6\4\7()\f\5\2\2)*\7\6\2\2*,\5\6\4\6+"
          + "%\3\2\2\2+(\3\2\2\2,/\3\2\2\2-+\3\2\2\2-.\3\2\2\2.\7\3\2\2\2/-\3\2\2\2"
          + "\60\62\5\f\7\2\61\63\7\t\2\2\62\61\3\2\2\2\62\63\3\2\2\2\63\64\3\2\2\2"
          + "\64\65\t\2\2\2\65\t\3\2\2\2\66\67\7\17\2\2\67:\5\f\7\28:\7\20\2\29\66"
          + "\3\2\2\298\3\2\2\2:\13\3\2\2\2;<\t\3\2\2<\r\3\2\2\2\t\21\34#+-\629";
  public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA [_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}