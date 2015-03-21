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
 * @file AgentSelectorTranslations.java
 * @date 21/3/2015
 */
package es.ull.mazesolver.translations;

import com.github.rodionmoiseev.c10n.C10NMessages;
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;

/**
 * Clase que agrupa las traducciones de la ventana de selección de agentes.
 */
@C10NMessages
public interface AgentSelectorTranslations {
  @En ("Create new agent")
  @Es ("Crear nuevo agente")
  String createNewAgent ();

  @En ("Algorithm")
  @Es ("Algoritmo")
  String algorithm ();

  @En ("Amount")
  @Es ("Cantidad")
  String amount ();

  @En ("Distance measure")
  @Es ("Medición de distancias")
  String distanceCalculator ();

  @En ("This agent has no configuration options available")
  @Es ("Este agente no tiene opciones de configuración disponibles")
  String noAgentConfigurationAvailable ();

  @En ("Write your rules here")
  @Es ("Escriba aquí sus reglas")
  String writeRulesHere ();

  @En ("Initial temperature")
  @Es ("Temperatura inicial")
  String initialTemp ();

  @En ("Cooling rate factor")
  @Es ("Factor de enfriado")
  String coolingRateFactor ();

  @En ("Wall to follow")
  @Es ("Pared que seguir")
  String wallToFollow ();
}
