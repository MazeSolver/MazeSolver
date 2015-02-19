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
 * @file MessageManager.java
 * @date 24/12/2014
 */
package es.ull.mazesolver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import es.ull.mazesolver.agent.util.MessageCommunication;
import es.ull.mazesolver.agent.util.MessageCommunication.Message;

/**
 * Clase que gestiona el paso de mensajes y la creación de grupos de mensajes
 * entre agentes.
 */
public class MessageManager {
  // Para cada grupo se almacena una lista de mensajes pendientes de transmisión
  // junto al emisor y una lista de receptores, o agentes suscritos al canal.
  private HashMap <String, Pair <ArrayList <Pair <MessageCommunication, Message>>, ArrayList <MessageCommunication>>> m_groups;

  /**
   * Construye una nueva instancia de la clase.
   */
  public MessageManager () {
    m_groups = new HashMap <>();
  }

  /**
   * Suscribe el agente al grupo indicado, de manera que recibe los mensajes
   * enviados a ese grupo.
   *
   * @param agent
   *          Agente que se quiere suscribir.
   * @param group
   *          Grupo al que suscribir el agente.
   * @return {@code true} si el agente se añadió al grupo y {@code false} si no
   *         se pudo añadir el agente al grupo.
   */
  public boolean subscribeGroup (MessageCommunication agent, String group) {
    Pair <ArrayList <Pair <MessageCommunication, Message>>, ArrayList <MessageCommunication>> g =
        m_groups.get(group);
    if (g != null) {
      if (!g.second.contains(agent))
        g.second.add(agent);
      return true;
    }
    return false;
  }

  /**
   * Hace que el agente deje de recibir mensajes del grupo especificado.
   *
   * @param agent
   *          Agente que quiere eliminar su suscripción.
   * @param group
   *          Grupo del cual eliminar la suscripción.
   */
  public void unsubscribeGroup (MessageCommunication agent, String group) {
    Pair <ArrayList <Pair <MessageCommunication, Message>>, ArrayList <MessageCommunication>> g =
        m_groups.get(group);
    if (g != null)
      g.second.remove(agent);
  }

  /**
   * Pone un mensaje a la cola de un grupo para ser enviado posteriormente
   * cuando se llame a {@code flushMessageQueues()}. Que la inserción se realice
   * satisfactoriamente no implica que el mensaje sea enviado.
   *
   * @see #flushMessageQueues()
   * @param sender
   *          Agente que envía el mensaje.
   * @param group
   *          Grupo al que se está enviando el mensaje.
   * @param msg
   *          Mensaje que se quiere enviar.
   * @return {@code true} si el mensaje se ha colocado en la cola
   *         satisfactoriamente o {@code false} si no se ha podido.
   */
  public boolean sendMessage (MessageCommunication sender, String group, Message msg) {
    Pair <ArrayList <Pair <MessageCommunication, Message>>, ArrayList <MessageCommunication>> g =
        m_groups.get(group);
    if (g != null) {
      g.first.add(new Pair <>(sender, msg));
      return true;
    }
    return false;
  }

  /**
   * Crea un nuevo grupo y devuelve su nombre.
   *
   * @return Nombre del grupo recién creado.
   */
  public String createGroup () {
    String name = "";
    do {
      name = Long.toUnsignedString(((Double) (Math.random() * Long.MAX_VALUE)).longValue());
    }
    while (m_groups.containsKey(name));

    ArrayList <Pair <MessageCommunication, Message>> msg_queue = new ArrayList <>();
    ArrayList <MessageCommunication> group = new ArrayList <>();
    m_groups.put(name, new Pair <>(msg_queue, group));

    return name;
  }

  /**
   * @param group
   *          Grupo del cual se quiere comprobar su existencia.
   * @return Si el grupo existe o no.
   */
  public boolean groupCreated (String group) {
    return m_groups.containsKey(group);
  }

  /**
   * Comprueba si un agente está suscrito a un grupo de mensajes determinado.
   *
   * @param agent
   *          Agente que se quiere comprobar.
   * @param group
   *          Grupo del que se quiere saber si el agente está suscrito.
   * @return Si el agente está suscrito al grupo o no.
   */
  public boolean isSubscribed (MessageCommunication agent, String group) {
    Pair <ArrayList <Pair <MessageCommunication, Message>>, ArrayList <MessageCommunication>> g =
        m_groups.get(group);

    return g != null && g.second.contains(group);
  }

  /**
   * Envía todos los mensajes en las colas a sus destinatarios, pero evitando
   * enviar varias veces el mismo mensaje al mismo destinatario (es posible que
   * un agente envíe el mismo mensaje a varios grupos y que haya algún agente en
   * varios de ellos).
   */
  public void flushMessageQueues () {
    // Creamos una cola de mensajes a ser enviados a cada agente, para detectar
    // las repeticiones de mensajes
    HashMap <MessageCommunication, ArrayList <Pair <MessageCommunication, Message>>> msgs =
        new HashMap <>();

    // Iteramos sobre los grupos
    for (Entry <String, Pair <ArrayList <Pair <MessageCommunication, Message>>, ArrayList <MessageCommunication>>> entry: m_groups
        .entrySet()) {

      // Sólo nos interesan la cola de mensajes y de suscritos al grupo, no el
      // nombre del mismo
      Pair <ArrayList <Pair <MessageCommunication, Message>>, ArrayList <MessageCommunication>> e_val =
          entry.getValue();

      // Recorremos todos los mensajes del grupo y los añadimos a la cola de
      // envío de todos los agentes del grupo
      for (Pair <MessageCommunication, Message> msg: e_val.first)
        for (MessageCommunication recv: e_val.second)
          addMsgToReceiverQueue(msgs, recv, msg);

      // Vaciamos la lista de mensajes, porque ya están a la cola para ser
      // procesados
      e_val.first.clear();
    }

    // Recorremos las listas de mensajes de cada receptor para enviarle sus
    // mensajes pendientes
    for (Entry <MessageCommunication, ArrayList <Pair <MessageCommunication, Message>>> e: msgs
        .entrySet()) {
      MessageCommunication recv = e.getKey();
      for (Pair <MessageCommunication, Message> msg: e.getValue())
        recv.receiveMessage(msg.first, msg.second);
    }
  }

  /**
   * Añade un mensaje a la cola de envío de un agente, evitando añadir mensajes
   * que ya se encuentren en dicha cola.
   *
   * @param msgs
   *          Estructura de datos con la lista de mensajes para cada receptor.
   * @param recv
   *          Receptor del mensaje.
   * @param msg
   *          Mensaje + Emisor del mensaje que se quiere añadir a la cola.
   */
  private void addMsgToReceiverQueue (
      Map <MessageCommunication, ArrayList <Pair <MessageCommunication, Message>>> msgs,
      MessageCommunication recv, Pair <MessageCommunication, Message> msg) {

    ArrayList <Pair <MessageCommunication, Message>> r_msgs = msgs.get(recv);
    if (r_msgs == null) {
      r_msgs = new ArrayList <Pair <MessageCommunication, Message>>();
      msgs.put(recv, r_msgs);
    }

    for (Pair <MessageCommunication, Message> r_msg: r_msgs) {
      // Comparamos las referencias, porque no queremos desechar mensajes
      // iguales, sino repeticiones del mismo mensaje
      if (r_msg.first == msg.first && r_msg.second == msg.second)
        return;
    }

    // Si el mensaje es nuevo, lo añadimos a la lista de mensajes del receptor
    r_msgs.add(msg);
  }
}
