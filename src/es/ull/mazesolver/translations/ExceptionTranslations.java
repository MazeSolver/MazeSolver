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
import com.github.rodionmoiseev.c10n.annotations.De;
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;
import com.github.rodionmoiseev.c10n.annotations.Fr;
import com.github.rodionmoiseev.c10n.annotations.Ru;

/**
 * Clase que contiene las traducciones de los mensajes de las excepciones.
 */
@C10NMessages
public interface ExceptionTranslations {
  @En ("The specified environment is invalid")
  @Es ("El entorno especificado es inválido")
  @De ("Die Umgebung ist ungültig")
  @Ru ("Указанная среда неверна")
  @Fr ("L'environnement spécifié est invalide")
  String invalidEnvironment ();

  @En ("The specified maze is invalid")
  @Es ("El laberinto especificado es inválido")
  @De ("Das Labyrinth ist ungültig")
  @Ru ("Указанный лабиринт неверен")
  @Fr ("Le labyrinthe spécifié est invalide")
  String invalidMaze ();

  @En ("The specified agent is invalid")
  @Es ("El agente especificado es inválido")
  @De ("Der Agent ist ungültig")
  @Ru ("Указанный агент неверен")
  @Fr ("L'agent spécifié est invalide")
  String invalidAgent ();

  @En ("The maze creation algorithm specified is invalid")
  @Es ("El algoritmo de creación de laberintos especificado es inválido")
  @De ("Der Labyrinth-Erzeugungsalgorithmus ist ungültig")
  @Ru ("Указанный алгоритм создания лабиринта неверен")
  @Fr ("L'algorithme de création de labyrinthe spécifié est invalide")
  String invalidMazeCreationAlgorithm ();

  @En ("The specified distance calculator is invalid")
  @Es ("El medidor de distancias especificado es inválido")
  @De ("Der Distanzrechner ist ungültig")
  @Ru ("Указанный калькулятор дистанции неверен")
  @Fr ("Le calculateur de distances spécifié est invalide")
  String invalidDistanceCalculator ();

  @En ("The agent is not in the environment")
  @Es ("El agente no se encuentra en el entorno")
  @De ("Der Agent ist nicht in der Umgebung")
  @Ru ("Агенту не присвоена среда")
  @Fr ("L'agent n'est pas dans l'environnement")
  String agentNotInEnvironment ();

  @En ("The environment is not in the current environment set")
  @Es ("El entorno no está en el conjunto de entornos actual")
  @De ("Die Umgebung ist nicht im aktuellen Umgebungsset")
  @Ru ("Данная среде не присвоена группа сред")
  @Fr ("L'environnement n'est pas dans l'ensemble d'environnements courant")
  String envNotInEnvSet ();

  @En ("Environments exchange is not possible")
  @Es ("Entornos no intercambiables")
  @De ("Umgebungstausch nicht möglich")
  @Ru ("Обмен сред невозможен")
  @Fr ("Environnements non échangeables")
  String environmentsNotExchangeable ();

  @En ("Index out of range")
  @Es ("Índice fuera de rango")
  @De ("Index außerhalb des Gültigkeitsbereichs")
  @Ru ("Индекс вне пределов")
  @Fr ("Indice hors d'atteinte")
  String indexOutOfRange ();

  @En ("The number of rows or columns is too small")
  @Es ("El número de filas o columnas es demasiado pequeño")
  @De ("Die Zeilen- oder Spaltenanzahl ist zu niedrig")
  @Ru ("Количество рядов или колонок недостаточно")
  @Fr ("Le nombre de lignes ou de colonnes est trop petit")
  String tooSmallRowsCols ();

  @En ("Too many walls specified")
  @Es ("El número de paredes es superior al posible")
  @De ("Zu viele Wände spezifiziert")
  @Ru ("Указанно слишком много стен")
  @Fr ("Trop de murs spécifiés")
  String tooManyWalls ();

  @En ("The situation or action specified was invalid: Rule cannot be created")
  @Es ("La situación o la acción eran inválidas: No se puede crear la regla")
  @De ("Die Situation oder Aktion ist ungültig: Regel konnte nicht erzeugt werden")
  @Ru ("Указанная ситуация или действие неверно: Создание правила невозможно")
  @Fr ("La situation ou l'action spécifiée était invalide: La règle ne peut être créée")
  String situationActionInvalid ();
}
