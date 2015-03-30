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
 * @file MazeSelectorTranslations.java
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
 * Traducciones para la ventana de creación de laberintos.
 */
@C10NMessages
public interface MazeSelectorTranslations {
  @En ("Create a new maze")
  @Es ("Crear un nuevo laberinto")
  @De ("Neues Labyrinth erstellen")
  @Ru ("Создать новый лабиринт")
  @Fr ("Créer un nouveau labyrinthe")
  String createNewMaze ();

  @En ("Algorithm")
  @Es ("Algoritmo")
  @De ("Algorithmus")
  @Ru ("Алгоритм")
  @Fr ("Algorithme")
  String algorithm ();

  @En ("Rows")
  @Es ("Filas")
  @De ("Zeilen")
  @Ru ("Ряды")
  @Fr ("Lignes")
  String rows ();

  @En ("Columns")
  @Es ("Columnas")
  @De ("Spalten")
  @Ru ("Колонки")
  @Fr ("Colonnes")
  String columns ();

  @En ("Basic configuration")
  @Es ("Configuración básica")
  @De ("Basiseinstellung")
  @Ru ("Основные настройки")
  @Fr ("Configuration de base")
  String basicConfiguration ();

  @En ("Perfect maze")
  @Es ("Laberinto perfecto")
  @De ("Perfektes Labyrinth")
  @Ru ("Идеальный лабиринт")
  @Fr ("Labyrinthe parfait")
  String perfectMaze ();

  @En ("Add cycles")
  @Es ("Añadir ciclos")
  @De ("Zyklen hinzufügen")
  @Ru ("Добавить циклы")
  @Fr ("Ajouter des cycles")
  String addCycles ();

  @En ("Add walls")
  @Es ("Añadir paredes")
  @De ("Wände hinzufügen")
  @Ru ("Добавить стены")
  @Fr ("Ajouter des murs")
  String addWalls ();

  @En ("Maze type")
  @Es ("Tipo de laberinto")
  @De ("Labyrinthtyp")
  @Ru ("Вид лабиринта")
  @Fr ("Type de labyrinthe")
  String mazeType ();
}
