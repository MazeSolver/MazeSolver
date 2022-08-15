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
 * @file EnvironmentSet.java
 * @date 25/10/2014
 */
package es.ull.mazesolver.gui.environment;

import com.tomtessier.scrollabledesktop.JScrollableDesktopPane;
import es.ull.mazesolver.agent.Agent;
import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.util.InteractionMode;

import java.util.ArrayList;

/**
 * Panel principal en el que se encuentran todos los laberintos cargados.
 * Proporciona la gestión de los laberintos.
 */
public class EnvironmentSet extends JScrollableDesktopPane {
    private static final long serialVersionUID = 1L;

    private ArrayList<Environment> m_envs;
    private InteractionMode m_mode;

    /**
     * Constructor. Inicializa la instancia.
     */
    public EnvironmentSet() {
        setVisible(true);
        setOpaque(true);

        m_envs = new ArrayList<Environment>();
        m_mode = InteractionMode.SIMULATION;
    }

    /**
     * Obtiene el entorno seleccionado actualmente.
     *
     * @return Entorno seleccionado actualmente o null si no hay ninguno.
     */
    public Environment getSelectedEnvironment() {
        return (Environment) getSelectedFrame();
    }

    /**
     * No se deben eliminar los entornos de la lista, o tendrá un comportamiento
     * erróneo posteriormente.
     *
     * @return Lista de entornos cargados actualmente.
     */
    public final ArrayList<Environment> getEnvironmentList() {
        return m_envs;
    }

    /**
     * Obtiene el número de entornos que contiene el conjunto de entornos.
     *
     * @return Número de entornos en el conjunto.
     */
    public int getEnvironmentCount() {
        return m_envs.size();
    }

    /**
     * Añade un nuevo entorno.
     *
     * @param env Añade un entorno a la lista.
     */
    public void addEnvironment(Environment env) {
        if (env != null) {
            env.setInteractionMode(m_mode);

            m_envs.add(env);
            add(env);
            repaint();
        }
    }

    /**
     * Elimina el entorno seleccionado actualmente si hay alguno seleccionado.
     */
    public void removeSelectedEnvironment() {
        Environment env = getSelectedEnvironment();
        if (env != null) {
            m_envs.remove(env);
            remove(env);
            repaint();
        }
    }

    /**
     * Añade un agente al entorno seleccionado y actualiza la referencia al
     * entorno por si se trataba de un entorno simple y tras la adición de un
     * agente pasa a ser un entorno múltiple.
     *
     * @param ag Agente que se quiere añadir al entorno actual.
     */
    public void addAgentToSelectedEnvironment(Agent ag) {
        Environment env = getSelectedEnvironment();
        if (env != null)
            env.addAgent(ag);
        else
            throw new IllegalStateException(
                    MainWindow.getTranslations().message().noEnvironmentSelected());
    }

    /**
     * Elimina un agente de un entorno y actualiza la referencia al entorno, por
     * si era un entorno múltiple y tras la eliminación tan sólo queda un agente.
     *
     * @param ag  Referencia al agente que se quiere eliminar del entorno.
     * @param env Entorno del cual se quiere eliminar el agente.
     */
    public void removeAgentFromEnvironment(Agent ag, Environment env) {
        if (m_envs.contains(env))
            env.removeAgent(ag);
        else
            throw new IllegalArgumentException(
                    MainWindow.getTranslations().exception().envNotInEnvSet());
    }

    /**
     * Actualiza el zoom de los entornos y sus tamaños.
     *
     * @param zoom Nivel de escala a aplicar a la visualización de los entornos.
     */
    public void setZoom(double zoom) {
        EnvironmentPanel.setZoom(zoom);
        for (Environment i : m_envs)
            i.updateSize();
    }

    /**
     * Intercambia un entorno dentro del conjunto de entornos por otro que no está
     * en dicho conjunto.
     *
     * @param e1 Entorno que se quiere eliminar del conjunto.
     * @param e2 Entorno que se quiere introducir en su lugar.
     */
    public void exchangeEnvironments(Environment e1, Environment e2) {
        // Evitamos intercambiar un entorno por sí mismo
        if (e1 != e2) {
            if (!m_envs.contains(e1) || m_envs.contains(e2))
                throw new IllegalArgumentException(
                        MainWindow.getTranslations().exception().environmentsNotExchangeable());

            // Quitamos el primer entorno
            m_envs.remove(e1);
            remove(e1);
            // Ponemos el segundo entorno
            m_envs.add(e2);
            add(e2);
            // Lo seleccionamos como entorno activo
            e2.setLocation(e1.getLocation());
            setSelectedFrame(e2);
            // Le asignamos una clase de renderizado acorde con el modo de interacción
            // actual
            e2.setInteractionMode(m_mode);
        }
        repaint();
    }

    /**
     * Modifica todos los entornos para que funcionen en el modo indicado.
     *
     * @param mode Nuevo modo de interacción.
     */
    public void setInteractionMode(InteractionMode mode) {
        if (mode != m_mode) {
            m_mode = mode;
            for (Environment env : m_envs)
                env.setInteractionMode(mode);
        }
    }

    /**
     * @return El modo de interacción actual de los entornos.
     */
    public InteractionMode getInteractionMode() {
        return m_mode;
    }

}
