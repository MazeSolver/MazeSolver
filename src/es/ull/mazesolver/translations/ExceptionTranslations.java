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
 * @file ExceptionTranslations.java
 * @date 21/3/2015
 */
package es.ull.mazesolver.translations;

import com.github.rodionmoiseev.c10n.C10NMessages;
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;

/**
 * Clase que contiene las traducciones de los mensajes de las excepciones.
 */
@C10NMessages
public interface ExceptionTranslations {
  @En ("The specified environment is invalid")
  @Es ("El entorno especificado es inválido")
  String invalidEnvironment ();

  @En ("The specified maze is invalid")
  @Es ("El laberinto especificado es inválido")
  String invalidMaze ();

  @En ("The specified agent is invalid")
  @Es ("El agente especificado es inválido")
  String invalidAgent ();

  @En ("The maze creation algorithm specified is invalid")
  @Es ("El algoritmo de creación de laberintos especificado es inválido")
  String invalidMazeCreationAlgorithm ();

  @En ("The specified distance calculator is invalid")
  @Es ("El medidor de distancias especificado es inválido")
  String invalidDistanceCalculator ();

  @En ("The agent is not in the environment")
  @Es ("El agente no se encuentra en el entorno")
  String agentNotInEnvironment ();

  @En ("The environment is not in the current environment set")
  @Es ("El entorno no está en el conjunto de entornos actual")
  String envNotInEnvSet ();

  @En ("Environments exchange is not possible")
  @Es ("Entornos no intercambiables")
  String environmentsNotExchangeable ();

  @En ("Index out of range")
  @Es ("Índice fuera de rango")
  String indexOutOfRange ();

  @En ("The number of rows or columns is too small")
  @Es ("El número de filas o columnas es demasiado pequeño")
  String tooSmallRowsCols ();

  @En ("Too many walls specified")
  @Es ("El número de paredes es superior al posible")
  String tooManyWalls ();

  @En ("The situation or action specified was invalid: Rule cannot be created")
  @Es ("La situación o la acción eran inválidas: No se puede crear la regla")
  String situationActionInvalid ();
}
