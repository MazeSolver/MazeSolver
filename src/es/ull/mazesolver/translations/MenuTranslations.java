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
 * @file MenuTranslations.java
 * @date 21/3/2015
 */
package es.ull.mazesolver.translations;

import com.github.rodionmoiseev.c10n.C10NMessages;
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;

/**
 * Clase contenedora de las traducciones para los menús.
 */
@C10NMessages
public interface MenuTranslations {
  @En ("File")
  @Es ("Archivo")
  String file ();

  @En ("New maze")
  @Es ("Nuevo laberinto")
  String newMaze ();

  @En ("Open maze")
  @Es ("Abrir laberinto")
  String openMaze ();

  @En ("Save maze")
  @Es ("Guardar laberinto")
  String saveMaze ();

  @En ("Exit")
  @Es ("Salir")
  String exit ();

  @En ("Maze")
  @Es ("Laberinto")
  String maze ();

  @En ("Copy maze")
  @Es ("Copiar laberinto")
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

  @En ("New agent")
  @Es ("Nuevo agente")
  String newAgent ();

  @En ("Clone agent")
  @Es ("Clonar agente")
  String cloneAgent ();

  @En ("Configure agent")
  @Es ("Configurar agente")
  String configureAgent ();

  @En ("Load agent")
  @Es ("Cargar agente")
  String loadAgent ();

  @En ("Save agent")
  @Es ("Guardar agente")
  String saveAgent ();

  @En ("Remove agent")
  @Es ("Eliminar agente")
  String removeAgent ();

  @En ("Help")
  @Es ("Ayuda")
  String help ();

  @En ("About")
  @Es ("Acerca de")
  String about ();
}
