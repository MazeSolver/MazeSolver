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
 * @file EnvironmentFrame.java
 * @date 29/10/2014
 */
package es.ull.mazesolver.gui.environment;

import es.ull.mazesolver.maze.Maze;
import es.ull.mazesolver.maze.MazeCell;
import es.ull.mazesolver.util.Direction;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Clase que permite dibujar el laberinto de un entorno en un panel. Controla
 * también el nivel de zoom, que es el mismo en todos los entornos.
 */
public class EnvironmentPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int CELL_SIZE_PX = 5;
    private static final float HUE_DIFF = 0.3f;
    private static final float SAT_BRI_THRESHOLD = 0.5f;

    private static double s_zoom = 1.0;
    protected Environment m_env;

    /**
     * Crea un nuevo panel de dibujo de entornos del entorno especificado.
     *
     * @param env Entorno que se dibujará en el panel.
     */
    public EnvironmentPanel(Environment env) {
        setEnvironment(env);
    }

    /**
     * Establece el entorno que desea dibujarse en el panel.
     *
     * @param env Nuevo entorno que se dibujará en el panel.
     */
    public void setEnvironment(Environment env) {
        m_env = env;
        updateSize();
    }

    /**
     * Actualiza el tamaño del panel si hay un entorno cargado y el entorno tiene
     * un laberinto válido cargado.
     */
    public void updateSize() {
        if (m_env != null) {
            Maze maze = m_env.getMaze();
            if (maze != null) {
                double cell_sz = getCellSize();
                setSize((int) Math.round((maze.getWidth() + 2) * cell_sz),
                        (int) Math.round((maze.getHeight() + 2) * cell_sz));
            }
        }
    }

    /**
     * Establece el nivel de aumento de visualización de todos los entornos.
     *
     * @param zoom Nivel de escala que se quiere aplicar a la visualización de los
     *             paneles.
     */
    public static void setZoom(double zoom) {
        if (zoom >= 0.0)
            s_zoom = zoom;
    }

    /**
     * @return El tamaño en píxeles de cada celda del laberinto.
     */
    public static double getCellSize() {
        return CELL_SIZE_PX * s_zoom;
    }

    /**
     * Traduce una coordenada de ratón en la pantalla (local al panel) a una
     * dirección de celda dentro de un laberinto.
     *
     * @param coord Coordenadas (x, y) del ratón.
     * @return Posición (x, y) dentro de la matriz del laberinto.
     */
    public static Point screenCoordToGrid(final Point coord) {
        double cell_sz = getCellSize();
        return new Point((int) (coord.x / cell_sz) - 1,
                (int) (coord.y / cell_sz) - 1);
    }

    /**
     * Traduce una dirección de celda dentro de un laberinto a una coordenada de
     * ratón en la pantalla (local al panel).
     *
     * @param coord Posición (x, y) dentro de la matriz del laberinto.
     * @return Coordenadas (x, y) del ratón.
     */
    public static Point gridCoordToScreen(final Point coord) {
        double cell_sz = getCellSize();
        return new Point((int) Math.round((coord.x + 1) * cell_sz),
                (int) Math.round((coord.y + 1) * cell_sz));
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Si no hay entorno asignado o el entorno no tiene laberinto, no dibujamos
        // nada.
        if (m_env == null)
            return;

        Maze maze = m_env.getMaze();
        if (maze == null)
            return;

        // Calculamos una vez el tamaño de las celdas para utilizarlo en todos los
        // cálculos de dimensiones
        double cell_sz = getCellSize();

        // Dibujamos el laberinto.
        g.setColor(Color.BLACK);
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                final MazeCell actual = maze.get(y, x);
                Point pos =
                        new Point((int) Math.round((x + 1) * cell_sz), (int) Math.round((y + 1) * cell_sz));

                if (y == 0 && actual.hasWall(Direction.UP))
                    g.drawLine(pos.x, pos.y, (int) Math.round(pos.x + cell_sz), pos.y);

                if (actual.hasWall(Direction.DOWN))
                    g.drawLine(pos.x, (int) Math.round(pos.y + cell_sz),
                            (int) Math.round(pos.x + cell_sz), (int) Math.round(pos.y + cell_sz));

                if (x == 0 && actual.hasWall(Direction.LEFT))
                    g.drawLine(pos.x, pos.y, pos.x, (int) Math.round(pos.y + cell_sz));

                if (actual.hasWall(Direction.RIGHT))
                    g.drawLine((int) Math.round(pos.x + cell_sz), pos.y,
                            (int) Math.round(pos.x + cell_sz), (int) Math.round(pos.y + cell_sz));
            }
        }
    }

    /**
     * Obtiene un color fácilmente diferenciable de otro color indicado. Cuando
     * se le indican colores muy cercanos al blanco o al negro, los colores que
     * devuelve no se diferencian con claridad.
     *
     * @param color El color del que se quiere buscar otro diferente.
     * @return El color que
     */
    protected static Color differentColor(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

        // Obtenemos un color diferente al cambiar el matiz
        hsb[0] += HUE_DIFF;
        if (hsb[0] > 1.0f)
            hsb[0] = 1.0f - hsb[0];

        // Comprobamos el brillo y la saturación para que los colores grisáceos se
        // puedan diferenciar
        if (hsb[1] < SAT_BRI_THRESHOLD)
            hsb[1] = 1.0f - hsb[1];
        if (hsb[2] < SAT_BRI_THRESHOLD)
            hsb[2] = 1.0f - hsb[2];

        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

}
