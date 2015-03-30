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
import com.github.rodionmoiseev.c10n.annotations.De;
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;
import com.github.rodionmoiseev.c10n.annotations.Fr;
import com.github.rodionmoiseev.c10n.annotations.Ru;

/**
 * Clase contenedora de las traducciones para los menús.
 */
@C10NMessages
public interface MenuTranslations {
  @En ("File")
  @Es ("Archivo")
  @De ("Datei")
  @Ru ("Файл")
  @Fr ("Fichier")
  String file ();

  @En ("New maze")
  @Es ("Nuevo laberinto")
  @De ("Neues Labyrinth")
  @Ru ("Новый лабиринт")
  @Fr ("Nouveau labyrinthe")
  String newMaze ();

  @En ("Open maze")
  @Es ("Abrir laberinto")
  @De ("Labyrinth öffnen")
  @Ru ("Открыть лабиринт")
  @Fr ("Ouvrir un labyrinthe")
  String openMaze ();

  @En ("Save maze")
  @Es ("Guardar laberinto")
  @De ("Labyrinth speichern")
  @Ru ("Сохранить лабиринт")
  @Fr ("Enregistrer le labyrinthe")
  String saveMaze ();

  @En ("Exit")
  @Es ("Salir")
  @De ("Schließen")
  @Ru ("Выход")
  @Fr ("Quitter")
  String exit ();

  @En ("Maze")
  @Es ("Laberinto")
  @De ("Labyrinth")
  @Ru ("Лабиринт")
  @Fr ("Labyrinthe")
  String maze ();

  @En ("Copy maze")
  @Es ("Copiar laberinto")
  @De ("Labyrinth kopieren")
  @Ru ("Копировать лабиринт")
  @Fr ("Copier le labyrinthe")
  String copyMaze ();

  @En ("Change maze")
  @Es ("Cambiar de laberinto")
  @De ("Labyrinth ändern")
  @Ru ("Изменить лабиринт")
  @Fr ("Changer de labyrinthe")
  String changeMaze ();

  @En ("Close maze")
  @Es ("Cerrar laberinto")
  @De ("Labyrinth schließen")
  @Ru ("Закрыть лабиринт")
  @Fr ("Fermer le labyrinthe")
  String closeMaze ();

  @En ("Agent")
  @Es ("Agente")
  @De ("Agent")
  @Ru ("Агент")
  @Fr ("Agent")
  String agent ();

  @En ("New agent")
  @Es ("Nuevo agente")
  @De ("Neuer Agent")
  @Ru ("Новый агент")
  @Fr ("Nouvel agent")
  String newAgent ();

  @En ("Clone agent")
  @Es ("Clonar agente")
  @De ("Agent klonen")
  @Ru ("Клонировать агента")
  @Fr ("Cloner l'agent")
  String cloneAgent ();

  @En ("Configure agent")
  @Es ("Configurar agente")
  @De ("Agent konfigurieren")
  @Ru ("Настроить агента")
  @Fr ("Configurer l'agent")
  String configureAgent ();

  @En ("Load agent")
  @Es ("Cargar agente")
  @De ("Agent laden")
  @Ru ("Открыть агента")
  @Fr ("Charger un agent")
  String loadAgent ();

  @En ("Save agent")
  @Es ("Guardar agente")
  @De ("Agent speichern")
  @Ru ("Сохранить агента")
  @Fr ("Enregistrer l'agent")
  String saveAgent ();

  @En ("Remove agent")
  @Es ("Eliminar agente")
  @De ("Agent entfernen")
  @Ru ("Удалить агента")
  @Fr ("Supprimer l'agent")
  String removeAgent ();

  @En ("Help")
  @Es ("Ayuda")
  @De ("Hilfe")
  @Ru ("Помощь")
  @Fr ("Aide")
  String help ();

  @En ("About")
  @Es ("Acerca de")
  @De ("Über")
  @Ru ("О программе")
  @Fr ("À propos")
  String about ();

  @En ("Language")
  @Es ("Idioma")
  @De ("Sprache")
  @Ru ("Язык")
  @Fr ("Langue")
  String language ();
}
