// Generated from SituationAction.g4 by ANTLR 4.4
package parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings ({"all", "warnings", "unchecked", "unused", "cast"})
public class SituationActionLexer extends Lexer {
  static {
    RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION);
  }

  protected static final DFA [] _decisionToDFA;
  protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
  public static final int IMPLIES = 1, DOT = 2, AND = 3, OR = 4, LEFTPAR = 5, RIGHTPAR = 6,
      FREE = 7, WALL = 8, VISITED = 9, MOVE = 10, STOP = 11, UP = 12, DOWN = 13, LEFT = 14,
      RIGHT = 15, BLANK = 16;
  public static String [] modeNames = {"DEFAULT_MODE"};

  public static final String [] tokenNames = {"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'",
      "'\\u0004'", "'\\u0005'", "'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'",
      "'\f'", "'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'"};
  public static final String [] ruleNames = {"IMPLIES", "DOT", "AND", "OR", "LEFTPAR", "RIGHTPAR",
      "FREE", "WALL", "VISITED", "MOVE", "STOP", "UP", "DOWN", "LEFT", "RIGHT", "BLANK", "A", "B",
      "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
      "U", "V", "W", "X", "Y", "Z"};

  public SituationActionLexer (CharStream input) {
    super(input);
    _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
  }

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
  public String [] getModeNames () {
    return modeNames;
  }

  @Override
  public ATN getATN () {
    return _ATN;
  }

  public static final String _serializedATN =
      "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\22\u00d1\b\1\4\2"
          + "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"
          + "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"
          + "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"
          + "\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"
          + " \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"
          + "+\3\2\3\2\3\2\3\2\5\2\\\n\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3"
          + "\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"
          + "\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3"
          + "\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3"
          + "\21\6\21\u0098\n\21\r\21\16\21\u0099\3\21\3\21\3\22\3\22\3\23\3\23\3\24"
          + "\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33"
          + "\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3"
          + "#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\2\2,\3\3\5\4\7\5\t"
          + "\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\2%"
          + "\2\'\2)\2+\2-\2/\2\61\2\63\2\65\2\67\29\2;\2=\2?\2A\2C\2E\2G\2I\2K\2M"
          + "\2O\2Q\2S\2U\2\3\2\35\5\2\13\f\17\17\"\"\4\2CCcc\4\2DDdd\4\2EEee\4\2F"
          + "Fff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4"
          + "\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWw"
          + "w\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2\\\\||\u00b8\2\3\3\2\2\2\2\5\3\2"
          + "\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"
          + "\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2"
          + "\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\3[\3\2\2\2\5]\3\2\2\2\7_\3\2"
          + "\2\2\ta\3\2\2\2\13c\3\2\2\2\re\3\2\2\2\17g\3\2\2\2\21l\3\2\2\2\23q\3\2"
          + "\2\2\25y\3\2\2\2\27~\3\2\2\2\31\u0083\3\2\2\2\33\u0086\3\2\2\2\35\u008b"
          + "\3\2\2\2\37\u0090\3\2\2\2!\u0097\3\2\2\2#\u009d\3\2\2\2%\u009f\3\2\2\2"
          + "\'\u00a1\3\2\2\2)\u00a3\3\2\2\2+\u00a5\3\2\2\2-\u00a7\3\2\2\2/\u00a9\3"
          + "\2\2\2\61\u00ab\3\2\2\2\63\u00ad\3\2\2\2\65\u00af\3\2\2\2\67\u00b1\3\2"
          + "\2\29\u00b3\3\2\2\2;\u00b5\3\2\2\2=\u00b7\3\2\2\2?\u00b9\3\2\2\2A\u00bb"
          + "\3\2\2\2C\u00bd\3\2\2\2E\u00bf\3\2\2\2G\u00c1\3\2\2\2I\u00c3\3\2\2\2K"
          + "\u00c5\3\2\2\2M\u00c7\3\2\2\2O\u00c9\3\2\2\2Q\u00cb\3\2\2\2S\u00cd\3\2"
          + "\2\2U\u00cf\3\2\2\2WX\7?\2\2X\\\7@\2\2YZ\7/\2\2Z\\\7@\2\2[W\3\2\2\2[Y"
          + "\3\2\2\2\\\4\3\2\2\2]^\7\60\2\2^\6\3\2\2\2_`\7(\2\2`\b\3\2\2\2ab\7~\2"
          + "\2b\n\3\2\2\2cd\7*\2\2d\f\3\2\2\2ef\7+\2\2f\16\3\2\2\2gh\5-\27\2hi\5E"
          + "#\2ij\5+\26\2jk\5+\26\2k\20\3\2\2\2lm\5O(\2mn\5#\22\2no\59\35\2op\59\35"
          + "\2p\22\3\2\2\2qr\5M\'\2rs\5\63\32\2st\5G$\2tu\5\63\32\2uv\5I%\2vw\5+\26"
          + "\2wx\5)\25\2x\24\3\2\2\2yz\5;\36\2z{\5? \2{|\5M\'\2|}\5+\26\2}\26\3\2"
          + "\2\2~\177\5G$\2\177\u0080\5I%\2\u0080\u0081\5? \2\u0081\u0082\5A!\2\u0082"
          + "\30\3\2\2\2\u0083\u0084\5K&\2\u0084\u0085\5A!\2\u0085\32\3\2\2\2\u0086"
          + "\u0087\5)\25\2\u0087\u0088\5? \2\u0088\u0089\5O(\2\u0089\u008a\5=\37\2"
          + "\u008a\34\3\2\2\2\u008b\u008c\59\35\2\u008c\u008d\5+\26\2\u008d\u008e"
          + "\5-\27\2\u008e\u008f\5I%\2\u008f\36\3\2\2\2\u0090\u0091\5E#\2\u0091\u0092"
          + "\5\63\32\2\u0092\u0093\5/\30\2\u0093\u0094\5\61\31\2\u0094\u0095\5I%\2"
          + "\u0095 \3\2\2\2\u0096\u0098\t\2\2\2\u0097\u0096\3\2\2\2\u0098\u0099\3"
          + "\2\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009b\3\2\2\2\u009b"
          + "\u009c\b\21\2\2\u009c\"\3\2\2\2\u009d\u009e\t\3\2\2\u009e$\3\2\2\2\u009f"
          + "\u00a0\t\4\2\2\u00a0&\3\2\2\2\u00a1\u00a2\t\5\2\2\u00a2(\3\2\2\2\u00a3"
          + "\u00a4\t\6\2\2\u00a4*\3\2\2\2\u00a5\u00a6\t\7\2\2\u00a6,\3\2\2\2\u00a7"
          + "\u00a8\t\b\2\2\u00a8.\3\2\2\2\u00a9\u00aa\t\t\2\2\u00aa\60\3\2\2\2\u00ab"
          + "\u00ac\t\n\2\2\u00ac\62\3\2\2\2\u00ad\u00ae\t\13\2\2\u00ae\64\3\2\2\2"
          + "\u00af\u00b0\t\f\2\2\u00b0\66\3\2\2\2\u00b1\u00b2\t\r\2\2\u00b28\3\2\2"
          + "\2\u00b3\u00b4\t\16\2\2\u00b4:\3\2\2\2\u00b5\u00b6\t\17\2\2\u00b6<\3\2"
          + "\2\2\u00b7\u00b8\t\20\2\2\u00b8>\3\2\2\2\u00b9\u00ba\t\21\2\2\u00ba@\3"
          + "\2\2\2\u00bb\u00bc\t\22\2\2\u00bcB\3\2\2\2\u00bd\u00be\t\23\2\2\u00be"
          + "D\3\2\2\2\u00bf\u00c0\t\24\2\2\u00c0F\3\2\2\2\u00c1\u00c2\t\25\2\2\u00c2"
          + "H\3\2\2\2\u00c3\u00c4\t\26\2\2\u00c4J\3\2\2\2\u00c5\u00c6\t\27\2\2\u00c6"
          + "L\3\2\2\2\u00c7\u00c8\t\30\2\2\u00c8N\3\2\2\2\u00c9\u00ca\t\31\2\2\u00ca"
          + "P\3\2\2\2\u00cb\u00cc\t\32\2\2\u00ccR\3\2\2\2\u00cd\u00ce\t\33\2\2\u00ce"
          + "T\3\2\2\2\u00cf\u00d0\t\34\2\2\u00d0V\3\2\2\2\5\2[\u0099\3\b\2\2";
  public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA [_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }

}