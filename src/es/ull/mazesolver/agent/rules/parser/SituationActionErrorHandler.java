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
 * @file SituationActionErrorHandler.java
 * @date 4/11/2014
 */
package es.ull.mazesolver.agent.rules.parser;

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
    m_errors = new ArrayList <String>();
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * org.antlr.v4.runtime.BaseErrorListener#syntaxError(org.antlr.v4.runtime
   * .Recognizer, java.lang.Object, int, int, java.lang.String,
   * org.antlr.v4.runtime.RecognitionException)
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
