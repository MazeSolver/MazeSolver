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
 * @file Agent.java
 * @date 21/10/2014
 */
package es.ull.mazesolver.agent;

import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.gui.configuration.AgentConfigurationPanel;
import es.ull.mazesolver.gui.environment.Environment;
import es.ull.mazesolver.maze.MazeCell;
import es.ull.mazesolver.util.Direction;

import java.awt.Color;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Clase que representa un agente abstracto que se encuentra en algún laberinto.
 * Sus subclases implementan los distintos algoritmos para resolver laberintos.
 * <br><br>
 * Cada agente debe implementar los métodos de serialización para poder
 * guardarse y cargarse correctamente, en caso de que requiriesen configuración
 * adicional a la genérica.
 */
public abstract class Agent implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static int s_agent_count = 0;

    private transient int m_agent_id;
    private transient String m_name;
    private Color m_color;

    /**
     * Entorno en el que reside el agente.
     */
    protected transient Environment m_env;

    /**
     * Posición en la que se encuentra el agente.
     */
    protected transient Point m_pos;

    /**
     * Crea un nuevo agente en el entorno. Le asigna un ID único y lo coloca en el
     * punto (0,0).
     *
     * @param env Entorno al que va a ser asignado dicho agente.
     */
    protected Agent(Environment env) {
        m_agent_id = s_agent_count++;
        m_name = getAlgorithmName() + " " + String.valueOf(m_agent_id);
        m_color = getAlgorithmColor();
        m_pos = new Point();
        setEnvironment(env);
    }

    /**
     * Establece la posición del agente.
     * <br><br>
     * Este método se puede sobrecargar en las clases derivadas para mantener
     * coherente la memoria de la que disponga el mismo.
     *
     * @param pos Nueva posición del agente.
     */
    public void setPosition(Point pos) {
        m_pos.x = pos.x;
        m_pos.y = pos.y;
    }

    /**
     * Obtiene la posición en el eje X del agente.
     *
     * @return Posición en el eje X del agente.
     */
    public int getX() {
        return m_pos.x;
    }

    /**
     * Obtiene la posición en el eje Y del agente.
     *
     * @return Posición en el eje Y de agente.
     */
    public int getY() {
        return m_pos.y;
    }

    /**
     * Obtiene la posición del agente.
     *
     * @return Posición del agente.
     */
    public Point getPos() {
        return new Point(m_pos);
    }

    /**
     * Cambia el entorno en el que se sitúa el agente.
     * <br><br>
     * Este método debería sobrecargarse en las clases derivadas que contengan
     * información acerca del camino a seguir por el agente (un plan) de forma que
     * éste siga siendo coherente tras el cambio de laberinto.
     *
     * @param env Entorno donde colocar el agente.
     */
    public void setEnvironment(Environment env) {
        if (env != null)
            m_env = env;
        else
            throw new IllegalArgumentException(
                    MainWindow.getTranslations().exception().invalidEnvironment());
    }

    /**
     * Obtiene el entorno en el que se encuentra el agente.
     *
     * @return Entorno en el que se encuentra el agente.
     */
    public Environment getEnvironment() {
        return m_env;
    }

    /**
     * Obtiene la visión que tiene el agente al mirar en la dirección indicada.
     *
     * @param dir Dirección hacia la que mirar.
     * @return Lo que vería el agente si mira en la dirección especificada.
     */
    public MazeCell.Vision look(Direction dir) {
        return m_env.look(m_pos, dir);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return m_env.hashCode();
    }

    /**
     * Hace que el agente realice el movimiento especificado, sin comprobar que se
     * trata de un movimiento válido.
     * <br><br>
     * La clase base sólo cambia la posición del agente, si se desea más
     * funcionalidad, se debe sobrecargar en las clases derivadas.
     *
     * @param dir Dirección hacia la que mover el agente.
     */
    public void doMovement(Direction dir) {
        m_pos = dir.movePoint(m_pos);
    }

    /**
     * Cambia el nombre del agente.
     *
     * @param name Nombre del agente.
     */
    public void setAgentName(String name) {
        if (name != null && !name.isEmpty())
            m_name = name;
    }

    /**
     * Pregunta al agente el nombre que le identifica.
     *
     * @return Nombre identificador del agente. Por defecto incluye el nombre del
     * algoritmo que implementa.
     */
    public String getAgentName() {
        return m_name;
    }

    /**
     * Cambia el color del agente.
     *
     * @param color Nuevo color del agente.
     */
    public void setAgentColor(Color color) {
        if (color != null)
            m_color = color;
    }

    /**
     * Pregunta al agente su color.
     *
     * @return El color del agente.
     */
    public Color getAgentColor() {
        return m_color;
    }

    /**
     * Carga la descripción serializada del agente de un fichero, lo crea y lo
     * devuelve.
     *
     * @param filename Nombre del fichero de entrada.
     * @param env      Entorno en el que cargar el agente.
     * @return El agente creado.
     * @throws IOException Si no es posible leer el fichero.
     */
    public static Agent loadFile(String filename, Environment env) throws IOException {
        try {
            Agent ag;
            FileInputStream file_in = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file_in);
            ag = (Agent) in.readObject();
            in.close();
            file_in.close();

            ag.m_agent_id = s_agent_count++;
            ag.m_name = ag.getAlgorithmName() + " " + String.valueOf(ag.m_agent_id);
            ag.m_pos = new Point();
            ag.setEnvironment(env);

            return ag;
        } catch (ClassNotFoundException c) {
            throw new IOException(c);
        }
    }

    /**
     * Guarda la instancia del agente en un fichero utilizando su serialización.
     *
     * @param filename Nombre del fichero de salida.
     * @throws IOException Si no es posible guardar el fichero.
     */
    public void saveFile(String filename) throws IOException {
        FileOutputStream file_out = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file_out);
        out.writeObject(this);
        out.close();
        file_out.close();
    }

    /**
     * Pregunta al agente el nombre del algoritmo que implementa.
     *
     * @return Nombre del algoritmo que implementa.
     */
    public abstract String getAlgorithmName();


    /**
     * Pregunta al agente el color por defecto de los agentes de su tipo.
     *
     * @return Color por defecto de los agentes que implementan ese algoritmo.
     */
    public abstract Color getAlgorithmColor();

    /**
     * Obtiene el siguiente movimiento dado el estado actual del agente.
     *
     * @return La dirección en la que el agente quiere realizar el siguiente
     * movimiento.
     */
    public abstract Direction getNextMovement();

    /**
     * Elimina la memoria que el agente tenga sobre el entorno. No elimina su
     * configuración, sino que lo deja en el estado inicial.
     */
    public abstract void resetMemory();

    /**
     * Obtiene el panel de configuración asociado al agente.
     *
     * @return Un panel de configuración para el agente.
     */
    public abstract AgentConfigurationPanel getConfigurationPanel();

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public abstract Object clone();

}
