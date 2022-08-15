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
 * @file EnvironmentSimulationPanel.java
 * @date 1/10/2015
 */
package es.ull.mazesolver.gui.environment;

import es.ull.mazesolver.agent.Agent;
import es.ull.mazesolver.gui.PopupTip;
import es.ull.mazesolver.gui.PopupTip.CloseOperationListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que permite dibujar un entorno de simulación (laberinto y agentes).
 * Muestra los agentes con sus colores personalizados y también permite ver
 * cuál está seleccionado o sobre cuál se encuentra el cursor.
 * <br><br>
 * A través de un popup se puede ver fácilmente el nombre del agente sobre el
 * cual se encuentra el cursor en todo momento.
 */
public class EnvironmentSimulationPanel extends EnvironmentPanel {
    private static final long serialVersionUID = 1L;

    private Agent m_last_hovered;

    /**
     * Crea un nuevo panel de dibujo de entornos del entorno especificado.
     *
     * @param env Entorno que se dibujará en el panel.
     */
    public EnvironmentSimulationPanel(Environment env) {
        super(env);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                resetPopup();
                super.mouseExited(e);
            }
        });
    }

    /* (non-Javadoc)
     * @see es.ull.mazesolver.gui.environment.EnvironmentPanel#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double cell_sz = getCellSize();

        // Dibujamos los agentes.
        for (int i = 0; i < m_env.getAgentCount(); i++) {
            Agent agent = m_env.getAgent(i);
            drawAgent(agent, agent.getAgentColor(), g);
        }

        // Dibujamos en el agente seleccionado un marcador para resaltarlo.
        Agent selected = m_env.getSelectedAgent();
        if (selected != null) {
            g.setColor(differentColor(selected.getAgentColor()));
            g.fillOval((int) Math.round(((selected.getX() + 1) * cell_sz) + cell_sz / 4),
                    (int) Math.round(((selected.getY() + 1) * cell_sz) + cell_sz / 4),
                    (int) Math.round((cell_sz / 2) - 1), (int) Math.round((cell_sz / 2) - 1));
        }

        // Dibujamos un marcador al agente sobre el que se encuentra el ratón y un
        // popup con su nombre.
        Agent hovered = m_env.getHoveredAgent();
        if (hovered != null) {
            g.setColor(differentColor(hovered.getAgentColor()));
            g.drawOval((int) Math.round((hovered.getX() + 1) * cell_sz),
                    (int) Math.round((hovered.getY() + 1) * cell_sz),
                    (int) Math.round(cell_sz - 1),
                    (int) Math.round(cell_sz - 1));

            if (m_last_hovered != hovered) {
                String name = hovered.getAgentName();
                Point p = MouseInfo.getPointerInfo().getLocation();

                PopupTip.hide();
                // Hacemos esto para que después de que el popup se cierre, se vuelva
                // a abrir porque todavía el usuario quiere verlo
                PopupTip.setNextCloseOperationListener(new CloseOperationListener() {
                    @Override
                    public void onPopupClose() {
                        m_last_hovered = null;
                    }
                });
                PopupTip.show(this, name, p.x + 15, p.y);

                m_last_hovered = hovered;
            }
        } else
            resetPopup();
    }

    /**
     * Dibuja un agente en el panel.
     *
     * @param ag        Agente que dibujar.
     * @param col       Color en el que dibujar el agente.
     * @param g         "Pincel" con el que hacer el dibujado.
     * @param cell_size Tamaño de las celdas en el panel.
     */
    private static void drawAgent(Agent ag, Color col, Graphics g) {
        double cell_sz = getCellSize();
        g.setColor(col);
        g.fillOval((int) Math.round((ag.getX() + 1) * cell_sz),
                (int) Math.round((ag.getY() + 1) * cell_sz),
                (int) Math.round(cell_sz - 1),
                (int) Math.round(cell_sz - 1));
    }

    /**
     * Cierra el popup si está abierto y deja todo listo para detectar de nuevo la
     * selección de los agentes.
     */
    private synchronized void resetPopup() {
        PopupTip.hide();
        m_last_hovered = null;
    }

}
