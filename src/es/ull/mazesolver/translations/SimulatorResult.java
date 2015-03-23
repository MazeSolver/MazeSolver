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
 * @file SimulatorResult.java
 * @date Mar 22, 2015
 */
package es.ull.mazesolver.translations;

import com.github.rodionmoiseev.c10n.C10NMessages;
import com.github.rodionmoiseev.c10n.annotations.De;
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;

/**
 * Clase contenedora de las traducciones para los resultados de las
 * simulaciones.
 */
@C10NMessages
public interface SimulatorResult {

  @En ("SIMULATION RESULTS")
  @Es ("RESULTADOS DE LA SIMULACIÓN")
  @De ("SIMULATIONSERGEBNISSE")
  String title ();

  @En ("Winner")
  @Es ("Ganador")
  @De ("Gewinner")
  String winner ();

  @En ("Maze")
  @Es ("Laberinto")
  @De ("Labyrinth")
  String maze ();

  @En ("Time taken first")
  @Es ("Tiempo que tardo el primero")
  @De ("Erste Zeit")
  String timeTakenFirst ();

  @En ("Time taken last")
  @Es ("Tiempo que tardo el último")
  @De ("Letzte Zeit")
  String timeTakenLast ();

  @En ("None")
  @Es ("Nada")
  @De ("Nichts")
  String none ();

  @En ("Agents detail")
  @Es ("Agente detallados")
  @De ("Agentdetails")
  String agentsDetail ();

  @En ("NOT FINISHED")
  @Es ("NO ACABO")
  @De ("NICHT BEENDET")
  String notFinished ();

  @En ("FINISHED")
  @Es ("ACABO")
  @De ("BEENDET")
  String finished ();

  @En ("steps")
  @Es ("pasos")
  @De ("Schritte")
  String steps ();

}
