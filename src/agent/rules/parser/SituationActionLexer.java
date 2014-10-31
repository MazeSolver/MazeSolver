// Generated from SituationAction.g4 by ANTLR 4.4
package agent.rules.parser;

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
      NOT = 7, FREE = 8, WALL = 9, VISITED = 10, AGENT = 11, OFFLIMITS = 12, MOVE = 13, STOP = 14,
      UP = 15, DOWN = 16, LEFT = 17, RIGHT = 18, BLANK = 19;
  public static String [] modeNames = {"DEFAULT_MODE"};

  public static final String [] tokenNames = {"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'",
      "'\\u0004'", "'\\u0005'", "'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'",
      "'\f'", "'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", "'\\u0013'"};
  public static final String [] ruleNames = {"IMPLIES", "DOT", "AND", "OR", "LEFTPAR", "RIGHTPAR",
      "NOT", "FREE", "WALL", "VISITED", "AGENT", "OFFLIMITS", "MOVE", "STOP", "UP", "DOWN", "LEFT",
      "RIGHT", "BLANK", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
      "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

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
      "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\25\u00f3\b\1\4\2"
          + "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"
          + "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"
          + "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"
          + "\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"
          + " \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"
          + "+\4,\t,\4-\t-\4.\t.\3\2\3\2\3\2\3\2\5\2b\n\2\3\3\3\3\3\4\3\4\3\5\3\5\3"
          + "\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\5\bs\n\b\3\t\3\t\3\t\3\t\3\t\3\n\3"
          + "\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f"
          + "\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3"
          + "\16\3\16\3\16\3\16\5\16\u009f\n\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20"
          + "\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23"
          + "\3\23\3\23\3\23\3\24\6\24\u00ba\n\24\r\24\16\24\u00bb\3\24\3\24\3\25\3"
          + "\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3"
          + "\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3"
          + "%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3+\3+\3,\3,\3-\3-\3.\3.\2\2/\3\3\5\4"
          + "\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"
          + "#\23%\24\'\25)\2+\2-\2/\2\61\2\63\2\65\2\67\29\2;\2=\2?\2A\2C\2E\2G\2"
          + "I\2K\2M\2O\2Q\2S\2U\2W\2Y\2[\2\3\2\36\4\2##\u0080\u0080\5\2\13\f\17\17"
          + "\"\"\4\2CCcc\4\2DDdd\4\2EEee\4\2FFff\4\2GGgg\4\2HHhh\4\2IIii\4\2JJjj\4"
          + "\2KKkk\4\2LLll\4\2MMmm\4\2NNnn\4\2OOoo\4\2PPpp\4\2QQqq\4\2RRrr\4\2SSs"
          + "s\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\4\2ZZzz\4\2[[{{\4\2"
          + "\\\\||\u00dc\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"
          + "\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"
          + "\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"
          + "\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\3a\3\2\2\2\5c\3\2\2\2\7e\3\2\2"
          + "\2\tg\3\2\2\2\13i\3\2\2\2\rk\3\2\2\2\17r\3\2\2\2\21t\3\2\2\2\23y\3\2\2"
          + "\2\25~\3\2\2\2\27\u0086\3\2\2\2\31\u008c\3\2\2\2\33\u009e\3\2\2\2\35\u00a0"
          + "\3\2\2\2\37\u00a5\3\2\2\2!\u00a8\3\2\2\2#\u00ad\3\2\2\2%\u00b2\3\2\2\2"
          + "\'\u00b9\3\2\2\2)\u00bf\3\2\2\2+\u00c1\3\2\2\2-\u00c3\3\2\2\2/\u00c5\3"
          + "\2\2\2\61\u00c7\3\2\2\2\63\u00c9\3\2\2\2\65\u00cb\3\2\2\2\67\u00cd\3\2"
          + "\2\29\u00cf\3\2\2\2;\u00d1\3\2\2\2=\u00d3\3\2\2\2?\u00d5\3\2\2\2A\u00d7"
          + "\3\2\2\2C\u00d9\3\2\2\2E\u00db\3\2\2\2G\u00dd\3\2\2\2I\u00df\3\2\2\2K"
          + "\u00e1\3\2\2\2M\u00e3\3\2\2\2O\u00e5\3\2\2\2Q\u00e7\3\2\2\2S\u00e9\3\2"
          + "\2\2U\u00eb\3\2\2\2W\u00ed\3\2\2\2Y\u00ef\3\2\2\2[\u00f1\3\2\2\2]^\7?"
          + "\2\2^b\7@\2\2_`\7/\2\2`b\7@\2\2a]\3\2\2\2a_\3\2\2\2b\4\3\2\2\2cd\7\60"
          + "\2\2d\6\3\2\2\2ef\7(\2\2f\b\3\2\2\2gh\7~\2\2h\n\3\2\2\2ij\7*\2\2j\f\3"
          + "\2\2\2kl\7+\2\2l\16\3\2\2\2mn\5C\"\2no\5E#\2op\5O(\2ps\3\2\2\2qs\t\2\2"
          + "\2rm\3\2\2\2rq\3\2\2\2s\20\3\2\2\2tu\5\63\32\2uv\5K&\2vw\5\61\31\2wx\5"
          + "\61\31\2x\22\3\2\2\2yz\5U+\2z{\5)\25\2{|\5? \2|}\5? \2}\24\3\2\2\2~\177"
          + "\5S*\2\177\u0080\59\35\2\u0080\u0081\5M\'\2\u0081\u0082\59\35\2\u0082"
          + "\u0083\5O(\2\u0083\u0084\5\61\31\2\u0084\u0085\5/\30\2\u0085\26\3\2\2"
          + "\2\u0086\u0087\5)\25\2\u0087\u0088\5\65\33\2\u0088\u0089\5\61\31\2\u0089"
          + "\u008a\5C\"\2\u008a\u008b\5O(\2\u008b\30\3\2\2\2\u008c\u008d\5E#\2\u008d"
          + "\u008e\5\63\32\2\u008e\u008f\5\63\32\2\u008f\u0090\5? \2\u0090\u0091\5"
          + "9\35\2\u0091\u0092\5A!\2\u0092\u0093\59\35\2\u0093\u0094\5O(\2\u0094\u0095"
          + "\5M\'\2\u0095\32\3\2\2\2\u0096\u0097\5A!\2\u0097\u0098\5E#\2\u0098\u0099"
          + "\5S*\2\u0099\u009a\5\61\31\2\u009a\u009f\3\2\2\2\u009b\u009c\5\65\33\2"
          + "\u009c\u009d\5E#\2\u009d\u009f\3\2\2\2\u009e\u0096\3\2\2\2\u009e\u009b"
          + "\3\2\2\2\u009f\34\3\2\2\2\u00a0\u00a1\5M\'\2\u00a1\u00a2\5O(\2\u00a2\u00a3"
          + "\5E#\2\u00a3\u00a4\5G$\2\u00a4\36\3\2\2\2\u00a5\u00a6\5Q)\2\u00a6\u00a7"
          + "\5G$\2\u00a7 \3\2\2\2\u00a8\u00a9\5/\30\2\u00a9\u00aa\5E#\2\u00aa\u00ab"
          + "\5U+\2\u00ab\u00ac\5C\"\2\u00ac\"\3\2\2\2\u00ad\u00ae\5? \2\u00ae\u00af"
          + "\5\61\31\2\u00af\u00b0\5\63\32\2\u00b0\u00b1\5O(\2\u00b1$\3\2\2\2\u00b2"
          + "\u00b3\5K&\2\u00b3\u00b4\59\35\2\u00b4\u00b5\5\65\33\2\u00b5\u00b6\5\67"
          + "\34\2\u00b6\u00b7\5O(\2\u00b7&\3\2\2\2\u00b8\u00ba\t\3\2\2\u00b9\u00b8"
          + "\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bb\u00bc\3\2\2\2\u00bc"
          + "\u00bd\3\2\2\2\u00bd\u00be\b\24\2\2\u00be(\3\2\2\2\u00bf\u00c0\t\4\2\2"
          + "\u00c0*\3\2\2\2\u00c1\u00c2\t\5\2\2\u00c2,\3\2\2\2\u00c3\u00c4\t\6\2\2"
          + "\u00c4.\3\2\2\2\u00c5\u00c6\t\7\2\2\u00c6\60\3\2\2\2\u00c7\u00c8\t\b\2"
          + "\2\u00c8\62\3\2\2\2\u00c9\u00ca\t\t\2\2\u00ca\64\3\2\2\2\u00cb\u00cc\t"
          + "\n\2\2\u00cc\66\3\2\2\2\u00cd\u00ce\t\13\2\2\u00ce8\3\2\2\2\u00cf\u00d0"
          + "\t\f\2\2\u00d0:\3\2\2\2\u00d1\u00d2\t\r\2\2\u00d2<\3\2\2\2\u00d3\u00d4"
          + "\t\16\2\2\u00d4>\3\2\2\2\u00d5\u00d6\t\17\2\2\u00d6@\3\2\2\2\u00d7\u00d8"
          + "\t\20\2\2\u00d8B\3\2\2\2\u00d9\u00da\t\21\2\2\u00daD\3\2\2\2\u00db\u00dc"
          + "\t\22\2\2\u00dcF\3\2\2\2\u00dd\u00de\t\23\2\2\u00deH\3\2\2\2\u00df\u00e0"
          + "\t\24\2\2\u00e0J\3\2\2\2\u00e1\u00e2\t\25\2\2\u00e2L\3\2\2\2\u00e3\u00e4"
          + "\t\26\2\2\u00e4N\3\2\2\2\u00e5\u00e6\t\27\2\2\u00e6P\3\2\2\2\u00e7\u00e8"
          + "\t\30\2\2\u00e8R\3\2\2\2\u00e9\u00ea\t\31\2\2\u00eaT\3\2\2\2\u00eb\u00ec"
          + "\t\32\2\2\u00ecV\3\2\2\2\u00ed\u00ee\t\33\2\2\u00eeX\3\2\2\2\u00ef\u00f0"
          + "\t\34\2\2\u00f0Z\3\2\2\2\u00f1\u00f2\t\35\2\2\u00f2\\\3\2\2\2\7\2ar\u009e"
          + "\u00bb\3\b\2\2";
  public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
  static {
    _decisionToDFA = new DFA [_ATN.getNumberOfDecisions()];
    for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
      _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
    }
  }
}