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
 * @file BlackboardManager.java
 * @date 24/12/2014
 */
package es.ull.mazesolver.util;

import java.util.HashMap;

/**
 * Clase que gestiona el sistema de pizarras por canales utilizado por los
 * entornos para permitir la compartición de las pizarras sólo a subconjuntos de
 * agentes.
 */
public class BlackboardManager {
  private HashMap <String, Object> m_blackboards;

  /**
   * Inicializa el gestor de pizarras.
   */
  public BlackboardManager () {
    m_blackboards = new HashMap <String, Object>();
  }

  /**
   * Obtiene la pizarra que hay en el canal indicado.
   *
   * @param channel
   *          Canal del que se quiere obtener la pizarra.
   * @return El objeto pizarra del canal.
   */
  public Object getBlackboard (String channel) {
    return m_blackboards.get(channel);
  }

  /**
   * Añade una nueva pizarra al gestor.
   *
   * @param blackboard
   *          Objeto que representa la nueva pizarra. Las modificaciones
   *          realizadas a este objeto son visibles para todos los agentes que
   *          posean una referencia a la misma.
   * @return El nombre del nuevo canal donde se ha colocado la pizarra.
   */
  public String addBlackboard (Object blackboard) {
    String name = "";
    do {
      name = Long.toUnsignedString(((Double) (Math.random() * Long.MAX_VALUE)).longValue());
    }
    while (m_blackboards.containsKey(name));

    m_blackboards.put(name, blackboard);
    return name;
  }

  /**
   * Intenta añadir la pizarra al canal deseado. Esto será posible sólo si el
   * canal no está ya ocupado.
   *
   * @param blackboard
   *          Objeto que representa la pizarra.
   * @param desired_channel
   *          Canal donde se quiere colocar la pizarra.
   * @return Canal donde finalmente se ha colocado la pizarra.
   */
  public String addBlackboard (Object blackboard, String desired_channel) {
    if (!m_blackboards.containsKey(desired_channel)) {
      m_blackboards.put(desired_channel, blackboard);
      return desired_channel;
    }
    else
      return addBlackboard(blackboard);
  }

  /**
   * Cambia el objeto pizarra asociado a un canal ya creado.
   *
   * @param channel
   *          Canal en el que modificar la pizarra.
   * @param blackboard
   *          Nueva pizarra que colocar en el canal.
   * @return {@code true} si se ha realizado el cambio y {@code false} si el
   *         canal indicado no existía.
   */
  public boolean changeBlackboard (String channel, Object blackboard) {
    if (m_blackboards.containsKey(channel)) {
      m_blackboards.put(channel, blackboard);
      return true;
    }
    return false;
  }

  /**
   * Elimina un canal del gestor de pizarras.
   *
   * @param channel
   *          Canal que eliminar.
   * @return {@code true} si se ha realizado la eliminación y {@code false} si
   *         el canal indicado no existía.
   */
  public boolean removeBlackboard (String channel) {
    if (m_blackboards.containsKey(channel)) {
      m_blackboards.remove(channel);
      return true;
    }
    return false;
  }

  /**
   * @param channel
   *          Canal que consultar.
   * @return Si el canal consultado existe para este gestor.
   */
  boolean channelUsed (String channel) {
    return m_blackboards.containsKey(channel);
  }
}
