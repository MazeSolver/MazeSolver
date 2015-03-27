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
import com.github.rodionmoiseev.c10n.annotations.De;
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;
import com.github.rodionmoiseev.c10n.annotations.Ru;

/**
 * Clase que agrupa las traducciones de la ventana de selección de agentes.
 */
@C10NMessages
public interface AgentSelectorTranslations {
  @En ("Create new agent")
  @Es ("Crear nuevo agente")
  @De ("Neuen Agent erstellen")
  @Ru ("Создать нового агента")
  String createNewAgent ();

  @En ("Algorithm")
  @Es ("Algoritmo")
  @De ("Algorithmus")
  @Ru ("Алгоритм")
  String algorithm ();

  @En ("Amount")
  @Es ("Cantidad")
  @De ("Anzahl")
  @Ru ("Количество")
  String amount ();

  @En ("Distance measure")
  @Es ("Medición de distancias")
  @De ("Distanzmaß")
  @Ru ("Измерение дистанции")
  String distanceCalculator ();

  @En ("This agent has no configuration options available")
  @Es ("Este agente no tiene opciones de configuración disponibles")
  @De ("Dieser Agent hat keine Konfigurationsoptionen verfügbar")
  @Ru ("У данного агента нет настроек")
  String noAgentConfigurationAvailable ();

  @En ("Write your rules here")
  @Es ("Escriba aquí sus reglas")
  @De ("Schreiben Sie ihre Regeln hier")
  @Ru ("Правила записываются здесь")
  String writeRulesHere ();

  @En ("Initial temperature")
  @Es ("Temperatura inicial")
  @De ("Anfangstemperatur")
  @Ru ("Начальная температура")
  String initialTemp ();

  @En ("Cooling rate factor")
  @Es ("Factor de enfriado")
  @De ("Kühlrate")
  @Ru ("Фактор охлаждаемости")
  String coolingRateFactor ();

  @En ("Wall to follow")
  @Es ("Pared que seguir")
  @De ("Wand zum folgen")
  @Ru ("Следовать стене")
  String wallToFollow ();
}
