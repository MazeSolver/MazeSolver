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
 * @file AgentConfigurationPanel.java
 * @date 22/11/2014
 */
package es.ull.mazesolver.gui.configuration;

import es.ull.mazesolver.agent.Agent;
import es.ull.mazesolver.gui.MainWindow;
import es.ull.mazesolver.translations.ButtonTranslations;
import es.ull.mazesolver.translations.Translatable;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Se trata de un panel de configuración de agentes, que permite al usuario
 * configurar un agente dependiendo del tipo que sea.
 * <p>
 * Los controles para aceptar o cancelar la configuración deben ser
 * implementados desde el exterior y utilizarse para llamar a los métodos
 * "accept()" o "cancel", respectivamente.
 */
public abstract class AgentConfigurationPanel extends JPanel implements Translatable {
    private static final long serialVersionUID = 1L;

    private JPanel m_root;
    private ArrayList<EventListener> m_listeners;
    private JButton m_accept, m_cancel;

    /**
     * Agente que se quiere configurar.
     */
    protected Agent m_agent;

    /**
     * Lista de mensajes de error obtenidos al intentar guardar la configuración
     * de un agente.
     */
    protected ArrayList<String> m_errors;

    /**
     * Lista de mensajes de éxito obtenidos tras guardar la configuración de un
     * agente.
     */
    protected ArrayList<String> m_success;

    /**
     * Interfaz de escucha de eventos.
     */
    public static interface EventListener {
        /**
         * LLamado cuando ocurre el evento de tipo "Exitoso". Estos eventos son
         * notificados cuando se guarda el agente y el cambio es aceptado.
         *
         * @param msgs Lista de mensajes que se quieren mostrar al usuario.
         */
        public void onSuccess(ArrayList<String> msgs);

        /**
         * LLamado cuando ocurre el evento de tipo "Cancelar". Estos eventos son
         * notificados cuando se cancela la edición de las propiedades.
         */
        public void onCancel();

        /**
         * LLamado cuando ocurre el evento de tipo "Error". Estos eventos son
         * notificados cuando se intenta guardar y no se aceptan las modificaciones.
         *
         * @param errors Lista de mensajes de error a mostrar al usuario.
         */
        public void onError(ArrayList<String> errors);
    }

    /**
     * Construye la interfaz del panel de configuración de agentes.
     *
     * @param agent Agente que se quiere configurar.
     */
    public AgentConfigurationPanel(Agent agent) {
        m_root = new JPanel();
        m_listeners = new ArrayList<EventListener>();
        m_agent = agent;
        m_errors = new ArrayList<String>();
        m_success = new ArrayList<String>();

        createGUI(m_root);
        createControls();
        translate();
    }

    /**
     * Añade un oyente de eventos.
     *
     * @param listener Clase oyente que se quiere añadir.
     */
    public final void addEventListener(EventListener listener) {
        if (!m_listeners.contains(listener))
            m_listeners.add(listener);
    }

    /**
     * Elimina un oyente de eventos. Si no es un oyente, la lista de oyentes
     * permanece intacta.
     *
     * @param listener Clase oyente que se quiere añadir.
     */
    public final void removeEventListener(EventListener listener) {
        m_listeners.remove(listener);
    }

    /* (non-Javadoc)
     * @see es.ull.mazesolver.translations.Translatable#translate()
     */
    @Override
    public void translate() {
        ButtonTranslations tr = MainWindow.getTranslations().button();

        m_accept.setText(tr.ok());
        m_cancel.setText(tr.cancel());
    }

    /**
     * Provoca que la configuración actualmente almacenada en el panel de
     * configuración se guarde en el agente, modificando su comportamiento.
     * <p>
     * Este método debe ser implementado por cada agente.
     *
     * @return <ul>
     * <li><b>true</b> si se pudo guardar el resultado.</li>
     * <li><b>false</b> si la configuración indicada no es válida.</li>
     * </ul>
     */
    protected abstract boolean accept();

    /**
     * Cancela la operación de configuración, dejando al agente en su estado de
     * partida.
     * <p>
     * Este método debe ser implementado por cada agente.
     */
    protected abstract void cancel();

    /**
     * Crea la interfaz gráfica de usuario, que es la que se mostrará al mismo.
     * Estará personalizada para el agente específico, pero no incluirá los
     * botones de "Aceptar" y "Cancelar", que se proporcionan por defecto.
     *
     * @param root Panel padre de todos los elementos que se creen. Si se intenta
     *             utilizar el panel padre de la clase en lugar de éste, el panel de
     *             configuración no se mostrará correctamente.
     */
    protected abstract void createGUI(JPanel root);

    /**
     * Se crean los controles de "Aceptar" y "Cancelar" y los coloca en el panel
     * junto a los controles personalizados del agente, que ya deben haber sido
     * creados.
     */
    private void createControls() {
        setLayout(new BorderLayout());

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        m_accept = new JButton();
        m_cancel = new JButton();

        m_accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accept())
                    onSuccess();
                else
                    onError();
            }
        });

        m_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
                onCancel();
            }
        });

        controls.add(m_accept);
        controls.add(m_cancel);

        add(m_root, BorderLayout.CENTER);
        add(controls, BorderLayout.SOUTH);
    }

    /**
     * Método llamado cuando el usuario aplica los cambios y éstos son guardados
     * correctamente. Notifica a los {@link EventListener#onSuccess}.
     */
    private void onSuccess() {
        for (EventListener listener : m_listeners)
            listener.onSuccess(m_success);
    }

    /**
     * Método llamado cuando el usuario cancela la operación y el agente ha
     * quedado como al principio. Notifica a los {@link EventListener#onCancel}.
     */
    private void onCancel() {
        for (EventListener listener : m_listeners)
            listener.onCancel();
    }

    /**
     * Método llamado cuando el usuario intenta aplicar los cambios y no es
     * posible porque la configuración no es válida. Notifica a los {@link
     * EventListener#onError}.
     */
    private void onError() {
        for (EventListener listener : m_listeners)
            listener.onError(m_errors);
    }
}
