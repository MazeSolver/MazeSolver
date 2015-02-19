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
 * @file MessageCommunication.java
 * @date 24/12/2014
 */
package es.ull.mazesolver.agent.util;

/**
 * Interfaz que deben proporcionar los agentes que deseen comunicarse mediante
 * mensajes.
 */
public interface MessageCommunication {

  /**
   * Tipos de mensaje, inspirados en las performativas de KQML.
   */
  public static enum MessageType {
    // Algunas performativas de discurso
    ASK, TELL, DENY, ACCEPT, ACHIEVE,
    // Performativas de intervención y mecánica de la conversación
    ERROR, SORRY, READY, NEXT, DISCARD, REST, STANDBY
  }

  /**
   * Representa un mensaje que se puede transmitir por los agentes.
   */
  public static class Message {
    private MessageType m_type;
    private Object m_content;

    /**
     * Crea un mensaje indicando el tipo y contenido.
     *
     * @param type
     *          Tipo de mensaje / acto del habla.
     * @param content
     *          Contenido del mensaje.
     */
    public Message (MessageType type, Object content) {
      m_type = type;
      m_content = content;
    }

    /**
     * Crea un mensaje sin contenido, cuyo significado viene dado exclusivamente
     * por el estado del receptor y el tipo de mensaje.
     *
     * @param type
     *          Tipo de mensaje / acto del habla.
     */
    public Message (MessageType type) {
      this(type, null);
    }

    /**
     * @return Tipo de mensaje.
     */
    public MessageType getType () {
      return m_type;
    }

    /**
     * @return Contenido del mensaje. Será {@code null} si no tiene contenido.
     */
    public Object getContent () {
      return m_content;
    }
  }

  /**
   * Recibe un mensaje y lo procesa, posiblemente produciendo un cambio en su
   * estado y un mensaje de respuesta.
   *
   * @param sender
   *          Agente que envia el mensaje.
   * @param msg
   *          Mensaje que se está enviando.
   */
  public void receiveMessage (MessageCommunication sender, Message msg);
}
