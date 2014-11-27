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
 * @file PopupTip.java
 * @date 26/11/2014
 */
package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;

/**
 * Una clase que implementa la funcionalidad de JToolTip pero utilizando como
 * base la clase Popup, de manera que se puede mostrar en cualquier lugar de la
 * interfaz.
 */
public class PopupTip {
  private static int DEFAULT_POPUP_DURATION_MS = 2000;

  private static Timer s_timer = new Timer(0, null);

  private static PopupFactory s_factory = PopupFactory.getSharedInstance();
  private static JTextArea s_text_label;
  private static Popup s_popup;
  private static CloseOperationListener s_listener;

  static {
    s_timer.setRepeats(false);
    s_timer.setCoalesce(true);
    // Configuramos un hilo que cierre el popup una vez haya transcurrido su
    // tiempo de visualización
    s_timer.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) { hide(); }
    });

    s_text_label = new JTextArea();
    s_text_label.setForeground(Color.WHITE);
    s_text_label.setBackground(new Color(64, 64, 64));
    s_text_label.setEditable(false);
    s_text_label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
  }

  /**
   * Clase que representa un evento de cierre de Popup.
   */
  public static interface CloseOperationListener {
    /**
     * Método que se llamará cuando se produzca el cierre del popup.
     */
    public void onPopupClose ();
  }

  /**
   * Asigna un listener para la siguiente operación de cerrado del popup que se
   * lleve a cabo.
   * @param l Clase oyente a la cual le llegará la próxima notificación.
   */
  public synchronized static void setNextCloseOperationListener (CloseOperationListener l) {
    s_listener = l;
  }

  /**
   * Muestra el popup en la pantalla, con el mensaje, posición y duración
   * indicados.
   * @param owner Componente que posee el popup.
   * @param msg Mensaje que se va a mostrar en el popup.
   * @param x Posición absoluta en X.
   * @param y Posición absoluta en Y.
   * @param time_ms Tiempo en milisegundos que el popup va a estar mostrándose.
   */
  public static void show (Component owner, String msg, int x, int y, int time_ms) {
    if (s_popup != null) {
      s_popup.hide();
      s_timer.stop();
    }

    s_text_label.setText(msg);
    s_popup = s_factory.getPopup(owner, s_text_label, x, y);
    s_popup.show();

    // Lanza el temporizador que se encarga de cerrar el popup tras un cierto
    // tiempo
    s_timer.setInitialDelay(time_ms);
    s_timer.start();
  }

  /**
   * Función de conveniencia que permite mostrar el popup con una duración
   * predeterminada.
   * @param owner Componente que posee el popup.
   * @param msg Mensaje que se va a mostrar en el popup.
   * @param x Posición absoluta en X.
   * @param y Posición absoluta en Y.
   */
  public static void show (Component owner, String msg, int x, int y) {
    show(owner, msg, x, y, DEFAULT_POPUP_DURATION_MS);
  }

  /**
   * Deja de mostrar el popup en pantalla y lo elimina. También se puede dejar
   * que se elimine automáticamente una vez haya pasado el tiempo que se le
   * indicó.
   */
  public static void hide () {
    s_timer.stop();

    if (s_popup != null) {
      s_popup.hide();
      s_popup = null;
    }
    if (s_listener != null) {
      s_listener.onPopupClose();
      s_listener = null;
    }
  }
}
