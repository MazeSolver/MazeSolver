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
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;

/**
 * Clase que agrupa las traducciones de los mensajes de la aplicación.
 */
@C10NMessages
public interface MessageTranslations {
  @En ("File save failed")
  @Es ("Error al guardar el fichero")
  String fileSaveFailed ();

  @En ("File open failed")
  @Es ("Error al abrir el fichero")
  String fileOpenFailed ();

  @En ("Copy failed")
  @Es ("Copia fallida")
  String cloningFailed ();

  @En ("Maze change failed")
  @Es ("Error al cambiar de laberinto")
  String mazeChangeFailed ();

  @En ("Agent creation failed")
  @Es ("Error al crear el agente")
  String agentCreationFailed ();

  @En ("Agent configuration failed")
  @Es ("Error al configurar el agente")
  String agentConfigFailed ();

  @En ("Agent removal failed")
  @Es ("Eliminación del agente fallida")
  String agentRemovalFailed ();

  @En ("There are no environments selected")
  @Es ("No hay ningún entorno seleccionado")
  String noEnvironmentSelected ();

  @En ("There are no agents selected")
  @Es ("No hay ningún agente seleccionado")
  String noAgentSelected ();

  @En ("Configure agent")
  @Es ("Configurar agente")
  String configureAgent ();

  @En ("Operation succeeded")
  @Es ("Operación exitosa")
  String operationSucceeded ();

  @En ("Operation failed")
  @Es ("Operación fallida")
  String operationFailed ();

  @En ("Maze files")
  @Es ("Ficheros de laberintos")
  String mazeFiles ();

  @En ("Log files")
  @Es ("Ficheros de registro")
  String logFiles ();

  @En ("Agent files")
  @Es ("Ficheros de agentes")
  String agentFiles ();

  @En ("File already exists")
  @Es ("El fichero ya existe")
  String fileExists ();

  @En ("The selected file already exists, do you want to overwrite it?")
  @Es ("El fichero seleccionado ya existe, ¿desea reemplazarlo?")
  String fileExistsOverwrite ();
}
