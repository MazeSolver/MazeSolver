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
 * @file EnvironmentEditionPanel.java
 * @date 1/10/2015
 */
package es.ull.mazesolver.gui.environment;

import es.ull.mazesolver.maze.Maze;
import es.ull.mazesolver.util.Direction;
import es.ull.mazesolver.util.Pair;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Clase que permite dibujar un laberinto, marcando en otro color la pared bajo
 * la que se encuentra el cursor en todo momento.
 */
public class EnvironmentEditionPanel extends EnvironmentPanel {
    private static final long serialVersionUID = 1L;
    private static final double WALL_THICKNESS = 0.15;
    private static final double WALL_SELECT_THICKNESS = 0.25;

    private Pair<Point, Direction> m_hovered;

    /**
     * Crea un nuevo panel de dibujo de entornos del entorno especificado.
     *
     * @param env Entorno que se dibujará en el panel.
     */
    public EnvironmentEditionPanel(Environment env) {
        super(env);
        m_hovered = null;

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                m_hovered = getWallAt(e.getPoint());

                repaint();
                super.mouseMoved(e);
            }
        });
    }

    /**
     * Devuelve la pared que hay bajo las coordenadas de pantalla (local al panel)
     * indicadas.
     *
     * @param pos Coordenadas de pantalla.
     * @return La celda y dirección en la que se encuentra la pared. Devuelve
     * {@code null} si en esa posición no se está seleccionando ninguna pared.
     */
    public Pair<Point, Direction> getWallAt(final Point pos) {
        Point grid = screenCoordToGrid(pos);
        Point grid_scr = gridCoordToScreen(grid);

        double cell_sz = getCellSize();
        Direction closest_dir = Direction.NONE;

        // Miramos si las coordenadas están suficientemente cerca de uno de los
        // bordes de la celda
        if (pos.y <= grid_scr.y + WALL_SELECT_THICKNESS * cell_sz)
            closest_dir = Direction.UP;
        else if (pos.y >= grid_scr.y + (1.0 - WALL_SELECT_THICKNESS) * cell_sz)
            closest_dir = Direction.DOWN;
        else if (pos.x <= grid_scr.x + WALL_SELECT_THICKNESS * cell_sz)
            closest_dir = Direction.LEFT;
        else if (pos.x >= grid_scr.x + (1.0 - WALL_SELECT_THICKNESS) * cell_sz)
            closest_dir = Direction.RIGHT;

        Pair<Point, Direction> result = null;
        if (closest_dir != Direction.NONE) {
            Maze maze = m_env.getMaze();

            // Si alguna de las celdas adyacentes a la pared está dentro del
            // laberinto, la usamos junto a la dirección para definir la pared
            if (maze.containsPoint(grid))
                result = new Pair<>(grid, closest_dir);
            else {
                Point adj = closest_dir.movePoint(grid);
                if (maze.containsPoint(adj))
                    result = new Pair<>(adj, closest_dir.getOpposite());
            }
        }

        return result;
    }

    /* (non-Javadoc)
     * @see es.ull.mazesolver.gui.environment.EnvironmentPanel#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (m_hovered != null) {
            Point pos = gridCoordToScreen(m_hovered.first);
            Direction dir = m_hovered.second;

            double cell_sz = getCellSize();
            double wall_sz = WALL_THICKNESS * cell_sz;

            g.setColor(Color.RED);
            if (dir.isVertical()) {
                double y = dir == Direction.UP ? pos.y : pos.y + cell_sz;
                g.fillRect((int) Math.round(pos.x - wall_sz / 2),
                        (int) Math.round(y - wall_sz),
                        (int) Math.round(cell_sz + wall_sz),
                        (int) Math.round(2 * wall_sz));
            } else {
                double x = dir == Direction.LEFT ? pos.x : pos.x + cell_sz;
                g.fillRect((int) Math.round(x - wall_sz),
                        (int) Math.round(pos.y - wall_sz / 2),
                        (int) Math.round(2 * wall_sz),
                        (int) Math.round(cell_sz + wall_sz));
            }
        }
    }

}
