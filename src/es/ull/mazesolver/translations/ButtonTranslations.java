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
 * @file ButtonTranslations.java
 * @date 21/3/2015
 */
package es.ull.mazesolver.translations;

import com.github.rodionmoiseev.c10n.C10NMessages;
import com.github.rodionmoiseev.c10n.annotations.En;
import com.github.rodionmoiseev.c10n.annotations.Es;

/**
 * Agrupa las traducciones de los botones.
 */
@C10NMessages
public interface ButtonTranslations {
  @En ("Run")
  @Es ("Ejecutar")
  String run ();

  @En ("Step")
  @Es ("Siguiente")
  String step ();

  @En ("Pause")
  @Es ("Pausar")
  String pause ();

  @En ("Continue")
  @Es ("Continuar")
  String kontinue();

  @En ("Stop")
  @Es ("Detener")
  String stop ();

  @En ("Zoom")
  @Es ("Aumento")
  String zoom ();

  @En ("Ok")
  @Es ("Aceptar")
  String ok ();

  @En ("Cancel")
  @Es ("Cancelar")
  String cancel ();

  @En ("Configure")
  @Es ("Configurar")
  String configure ();
}
