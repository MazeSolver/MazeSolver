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
 * @file MessageTranslations.java
 * @date 21/3/2015
 */
package es.ull.mazesolver.translations;

import com.github.rodionmoiseev.c10n.C10NMessages;

/**
 * Clase que agrupa las traducciones de los mensajes de la aplicación.
 */
@C10NMessages
public interface MessageTranslations {

  String fileSaveFailed ();

  String fileOpenFailed ();

  String cloningFailed ();

  String mazeChangeFailed ();

  String agentCreationFailed ();

  String agentConfigFailed ();

  String agentRemovalFailed ();

  String noEnvironmentSelected ();

  String noAgentSelected ();

  String configureAgent ();

  String operationSucceeded ();

  String operationFailed ();

  String mazeFiles ();

  String logFiles ();

  String agentFiles ();

  String fileExists ();

  String fileExistsOverwrite ();
}
