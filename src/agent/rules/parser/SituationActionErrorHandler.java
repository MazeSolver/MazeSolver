/**
 * @file SituationActionErrorHandler.java
 * @date 4/11/2014
 */
package agent.rules.parser;

import java.util.ArrayList;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * Clase que gestiona los errores en el parsing del código escrito en el DSL
 * para la definición de reglas de situación-acción.
 */
public class SituationActionErrorHandler extends BaseErrorListener {
  private ArrayList <String> m_errors;

  /**
   * Constructor. Crea la lista de errores vacía.
   */
  public SituationActionErrorHandler () {
    m_errors = new ArrayList<String>();
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.antlr.v4.runtime.BaseErrorListener#syntaxError(org.antlr.v4.runtime.Recognizer, java.lang.Object, int, int, java.lang.String, org.antlr.v4.runtime.RecognitionException)
   */
  @Override
  public void syntaxError (Recognizer <?, ?> recognizer, Object offendingSymbol, int line,
      int charPositionInLine, String msg, RecognitionException e) {
    m_errors.add("line " + line + ":" + charPositionInLine + " :: " + msg);
  }

  /**
   * Limpia la lista de errores. Se vacía para poder volver a ejecutar la
   * compilación usando este mismo gestor de errores.
   */
  public void resetErrorList () {
    m_errors.clear();
  }

  /**
   * @return Lista de errores.
   */
  public ArrayList <String> getErrors () {
    return m_errors;
  }

  /**
   * @return Si hay errores guardados o no.
   */
  public boolean hasErrors () {
    return !m_errors.isEmpty();
  }

}
