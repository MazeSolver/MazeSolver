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
 * @file trGUI.java
 * @date Mar 13, 2015
 */
package es.ull.mazesolver.util.c10n;

import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;

/**
 * Interfaz que dota de los elementos de traduccion de la GUI
 */
public interface trGUI {

  @En ("File")
  @Es ("Archivo")
  String File ();

  @En ("New maze")
  @Es ("Nuevo laberinto")
  String newMaze ();

  @En ("Open maze")
  @Es ("Abrir laberinto")
  String openMaze ();

  @En ("Guardar maze")
  @Es ("Guardar laberinto")
  String saveMaze ();

  @En ("Exit")
  @Es ("Salir")
  String Exit ();

  @En ("Maze")
  @Es ("Laberinto")
  String Maze ();

  @En ("New maze")
  @Es ("Nuevo laberinto")
  String copyMaze ();

  @En ("Change maze")
  @Es ("Cambiar de laberinto")
  String changeMaze ();

  @En ("Close maze")
  @Es ("Cerrar laberinto")
  String closeMaze ();

  @En ("Agent")
  @Es ("Agente")
  String agent ();

  @En ("New Agent")
  @Es ("Nuevo Agente")
  String newAgent ();

  @En ("Clone Agent")
  @Es ("Clonar Agente")
  String cloneAgent ();

  @En ("Configure Agent")
  @Es ("Configurar Agente")
  String configureAgent ();

  @En ("Load Agent")
  @Es ("Cargar Agente")
  String loadAgent ();

  @En ("Save Agent")
  @Es ("Guardar Agente")
  String saveAgent ();

  @En ("Remove Agent")
  @Es ("Eliminar Agente")
  String removeAgent ();

  @En ("Help")
  @Es ("Ayuda")
  String Help ();

  @En ("About")
  @Es ("Acerca de")
  String About ();

  @En ("Run")
  @Es ("Ejecutar")
  String buttonRun ();

  @En ("Step")
  @Es ("Siguiente")
  String buttonStep ();

  @En ("Pause")
  @Es ("Pausar")
  String buttonPause ();

  @En ("Stop")
  @Es ("Detener")
  String buttonStop ();

  @En ("Zoom")
  @Es ("Zoom")
  String zoom ();

}
