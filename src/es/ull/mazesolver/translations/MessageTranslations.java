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
import com.github.rodionmoiseev.c10n.annotations.De;
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;
import com.github.rodionmoiseev.c10n.annotations.Fr;
import com.github.rodionmoiseev.c10n.annotations.Ru;

/**
 * Clase que agrupa las traducciones de los mensajes de la aplicación.
 */
@C10NMessages
public interface MessageTranslations {
  @En ("File save failed")
  @Es ("Error al guardar el fichero")
  @De ("Fehler beim Speichern der Datei")
  @Ru ("Не удалось сохранить файл")
  @Fr ("Échec de la sauvegarde du fichier")
  String fileSaveFailed ();

  @En ("File open failed")
  @Es ("Error al abrir el fichero")
  @De ("Fehler beim Öffnen der Datei")
  @Ru ("Не удалось открыть файл")
  @Fr ("Échec de l'ouverture du fichier")
  String fileOpenFailed ();

  @En ("Copy failed")
  @Es ("Copia fallida")
  @De ("Fehler beim Kopieren")
  @Ru ("Не удалось копировать файл")
  @Fr ("Échec de la copie du fichier")
  String cloningFailed ();

  @En ("Maze change failed")
  @Es ("Error al cambiar de laberinto")
  @De ("Fehler beim Ändern des Labyrinths")
  @Ru ("Не удалось изменить лабиринт")
  @Fr ("Échec du changement de labyrinthe")
  String mazeChangeFailed ();

  @En ("Agent creation failed")
  @Es ("Error al crear el agente")
  @De ("Fehler beim Erzeugen des Agenten")
  @Ru ("Не удалось создать агента")
  @Fr ("Échec de la création de l'agent")
  String agentCreationFailed ();

  @En ("Agent configuration failed")
  @Es ("Error al configurar el agente")
  @De ("Fehler beim Konfigurieren des Agenten")
  @Ru ("Не удалось настроить агента")
  @Fr ("Échec de la configuration de l'agent")
  String agentConfigFailed ();

  @En ("Agent removal failed")
  @Es ("Eliminación del agente fallida")
  @De ("Fehler beim Entfernen des Agenten")
  @Ru ("Не удалось удалить агента")
  @Fr ("Échec de la suppression de l'agent")
  String agentRemovalFailed ();

  @En ("There are no environments selected")
  @Es ("No hay ningún entorno seleccionado")
  @De ("Es ist keine Umgebung ausgewählt")
  @Ru ("Не выбрано ни одной среды")
  @Fr ("Aucun environnement sélectionné")
  String noEnvironmentSelected ();

  @En ("There are no agents selected")
  @Es ("No hay ningún agente seleccionado")
  @De ("Es ist kein Agent ausgewählt")
  @Ru ("Не выбрано ни одного агента")
  @Fr ("Aucun agent sélectionné")
  String noAgentSelected ();

  @En ("Configure agent")
  @Es ("Configurar agente")
  @De ("Agent konfigurieren")
  @Ru ("Настроить агента")
  @Fr ("Configurer l'agent")
  String configureAgent ();

  @En ("Operation succeeded")
  @Es ("Operación exitosa")
  @De ("Operation erfolgreich")
  @Ru ("Операция успешна")
  @Fr ("Opération réussie")
  String operationSucceeded ();

  @En ("Operation failed")
  @Es ("Operación fallida")
  @De ("Operation fehlgeschlagen")
  @Ru ("Операция неудачна")
  @Fr ("Opération échouée")
  String operationFailed ();

  @En ("Maze files")
  @Es ("Ficheros de laberintos")
  @De ("Labyrinthdateien")
  @Ru ("Файлы лабиринтов")
  @Fr ("Fichiers de labyrinthes")
  String mazeFiles ();

  @En ("Log files")
  @Es ("Ficheros de registro")
  @De ("Logdateien")
  @Ru ("Файлы журналов")
  @Fr ("Fichiers de journal")
  String logFiles ();

  @En ("Agent files")
  @Es ("Ficheros de agentes")
  @De ("Agentdateien")
  @Ru ("Файлы агентов")
  @Fr ("Fichiers d'agents")
  String agentFiles ();

  @En ("File already exists")
  @Es ("El fichero ya existe")
  @De ("Die Datei existiert bereits")
  @Ru ("Файл уже существует")
  @Fr ("Fichier déjà existant")
  String fileExists ();

  @En ("The selected file already exists, do you want to overwrite it?")
  @Es ("El fichero seleccionado ya existe, ¿desea reemplazarlo?")
  @De ("Die angegebene Datei besteht bereits. Wollen Sie die Datei überschreiben?")
  @Ru ("Выбранный файл уже существует. Заменить его?")
  @Fr ("Le fichier sélectionné existe déjà, souhaitez-vous l'écraser?")
  String fileExistsOverwrite ();
}
