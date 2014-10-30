// Generated from SituationAction.g4 by ANTLR 4.4
package parser;

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
      FREE = 7, WALL = 8, VISITED = 9, MOVE = 10, STOP = 11, UP = 12, DOWN = 13, LEFT = 14,
      RIGHT = 15, BLANK = 16;
  public static final String [] tokenNames = {"<INVALID>", "IMPLIES", "'.'", "'&'", "'|'", "'('",
      "')'", "FREE", "WALL", "VISITED", "MOVE", "STOP", "UP", "DOWN", "LEFT", "RIGHT", "BLANK"};
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
        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LEFTPAR) | (1L << UP) | (1L << DOWN)
            | (1L << LEFT) | (1L << RIGHT))) != 0));
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
    public List <SituationContext> situation () {
      return getRuleContexts(SituationContext.class);
    }

    public TermContext term () {
      return getRuleContext(TermContext.class, 0);
    }

    public SituationContext situation (int i) {
      return getRuleContext(SituationContext.class, i);
    }

    public TerminalNode AND () {
      return getToken(SituationActionParser.AND, 0);
    }

    public TerminalNode OR () {
      return getToken(SituationActionParser.OR, 0);
    }

    public TerminalNode RIGHTPAR () {
      return getToken(SituationActionParser.RIGHTPAR, 0);
    }

    public TerminalNode LEFTPAR () {
      return getToken(SituationActionParser.LEFTPAR, 0);
    }

    public SituationContext (ParserRuleContext parent, int invokingState) {
      super(parent, invokingState);
    }

    @Override
    public int getRuleIndex () {
      return RULE_situation;
    }

    @Override
    public void enterRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).enterSituation(this);
    }

    @Override
    public void exitRule (ParseTreeListener listener) {
      if (listener instanceof SituationActionListener)
        ((SituationActionListener) listener).exitSituation(this);
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
    try {
      int _alt;
      enterOuterAlt(_localctx, 1);
      {
        setState(30);
        switch (_input.LA(1)) {
          case LEFTPAR: {
            setState(25);
            match(LEFTPAR);
            setState(26);
            situation(0);
            setState(27);
            match(RIGHTPAR);
          }
            break;
          case UP:
          case DOWN:
          case LEFT:
          case RIGHT: {
            setState(29);
            term();
          }
            break;
          default:
            throw new NoViableAltException(this);
        }
        _ctx.stop = _input.LT(-1);
        setState(40);
        _errHandler.sync(this);
        _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
          if (_alt == 1) {
            if (_parseListeners != null)
              triggerExitRuleEvent();
            _prevctx = _localctx;
            {
              setState(38);
              switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
                case 1: {
                  _localctx = new SituationContext(_parentctx, _parentState);
                  pushNewRecursionContext(_localctx, _startState, RULE_situation);
                  setState(32);
                  if (!(precpred(_ctx, 4)))
                    throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                  setState(33);
                  match(AND);
                  setState(34);
                  situation(5);
                }
                  break;
                case 2: {
                  _localctx = new SituationContext(_parentctx, _parentState);
                  pushNewRecursionContext(_localctx, _startState, RULE_situation);
                  setState(35);
                  if (!(precpred(_ctx, 3)))
                    throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                  setState(36);
                  match(OR);
                  setState(37);
                  situation(4);
                }
                  break;
              }
            }
          }
          setState(42);
          _errHandler.sync(this);
          _alt = getInterpreter().adaptivePredict(_input, 3, _ctx);
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
    public TerminalNode FREE () {
      return getToken(SituationActionParser.FREE, 0);
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
        setState(43);
        direction();
        setState(44);
        _la = _input.LA(1);
        if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FREE) | (1L << WALL) | (1L << VISITED))) != 0))) {
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
      setState(49);
      switch (_input.LA(1)) {
        case MOVE:
          enterOuterAlt(_localctx, 1);
          {
            setState(46);
            match(MOVE);
            setState(47);
            direction();
          }
          break;
        case STOP:
          enterOuterAlt(_localctx, 2);
          {
            setState(48);
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
        setState(51);
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
      "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\228\4\2\t\2\4\3\t"
          + "\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\6\2\20\n\2\r\2\16\2\21\3\2\3\2"
          + "\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4!\n\4\3\4\3\4\3\4\3\4"
          + "\3\4\3\4\7\4)\n\4\f\4\16\4,\13\4\3\5\3\5\3\5\3\6\3\6\3\6\5\6\64\n\6\3"
          + "\7\3\7\3\7\2\3\6\b\2\4\6\b\n\f\2\4\3\2\t\13\3\2\16\21\66\2\17\3\2\2\2"
          + "\4\25\3\2\2\2\6 \3\2\2\2\b-\3\2\2\2\n\63\3\2\2\2\f\65\3\2\2\2\16\20\5"
          + "\4\3\2\17\16\3\2\2\2\20\21\3\2\2\2\21\17\3\2\2\2\21\22\3\2\2\2\22\23\3"
          + "\2\2\2\23\24\7\2\2\3\24\3\3\2\2\2\25\26\5\6\4\2\26\27\7\3\2\2\27\30\5"
          + "\n\6\2\30\31\7\4\2\2\31\5\3\2\2\2\32\33\b\4\1\2\33\34\7\7\2\2\34\35\5"
          + "\6\4\2\35\36\7\b\2\2\36!\3\2\2\2\37!\5\b\5\2 \32\3\2\2\2 \37\3\2\2\2!"
          + "*\3\2\2\2\"#\f\6\2\2#$\7\5\2\2$)\5\6\4\7%&\f\5\2\2&\'\7\6\2\2\')\5\6\4"
          + "\6(\"\3\2\2\2(%\3\2\2\2),\3\2\2\2*(\3\2\2\2*+\3\2\2\2+\7\3\2\2\2,*\3\2"
          + "\2\2-.\5\f\7\2./\t\2\2\2/\t\3\2\2\2\60\61\7\f\2\2\61\64\5\f\7\2\62\64"
          + "\7\r\2\2\63\60\3\2\2\2\63\62\3\2\2\2\64\13\3\2\2\2\65\66\t\3\2\2\66\r"
          + "\3\2\2\2\7\21 (*\63";
  public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA [_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }

}