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
 * @file ConsoleTranslations.java
 * @date 19/3/2015
 */
package es.ull.mazesolver.translations;

import com.github.rodionmoiseev.c10n.C10NMessages;
import com.github.rodionmoiseev.c10n.annotations.De;
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;
import com.github.rodionmoiseev.c10n.annotations.Fr;
import com.github.rodionmoiseev.c10n.annotations.Ru;

/**
 * Contiene las traducciones de las cadenas en la consola.
 */
@C10NMessages
public interface ConsoleTranslations {
  @En("Log")
  @Es("Registro")
  @De ("Log")
  @Ru ("Журнал")
  @Fr ("Journal")
  String log ();

  @En("Clear")
  @Es("Limpiar")
  @De ("Löschen")
  @Ru ("Очистить")
  @Fr ("Effacer")
  String clear ();

  @En("Save to file")
  @Es("Guardar en un fichero")
  @De ("Als Datei speichern")
  @Ru ("Сохранить в файл")
  @Fr ("Enregistrer dans un fichier")
  String saveToFile ();

  @En("Log save failed")
  @Es("Error al guardar el registro")
  @De ("Fehler beim Speichern des Logs")
  @Ru ("Ошибка при сохранении журнала")
  @Fr ("Échec de la sauvegarde du journal")
  String logSaveError ();
}
