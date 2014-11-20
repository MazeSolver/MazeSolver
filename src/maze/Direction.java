/**
 * @file Direction.java
 * @date 21/10/2014
 */
package maze;

import java.awt.Point;

import util.Pair;

/**
 * Enum que representa una dirección de movimiento en 2D. Cada posible opción es
 * un flag, por lo que se pueden hacer operaciones de bit para representar
 * varias direcciones en la misma variable simultáneamente.
 */
public enum Direction {
  NONE  ((short) 0x00),
  UP    ((short) 0x01),
  DOWN  ((short) 0x02),
  LEFT  ((short) 0x04),
  RIGHT ((short) 0x08);

  public static int MAX_DIRECTIONS = 5;

  public short val;
  private static Direction[] values = Direction.values();

  private Direction (short val) {
    this.val = val;
  }

  /**
   * Transforma un short en dirección, comparando sus valores directamente.
   * @param value Valor (dentro de los valores posibles de dirección).
   * @return Dirección asociada a ese valor.
   */
  public static Direction fromValue (short value) {
    for (Direction i: values)
      if (i.val == value)
        return i;

    return null;
  }

  /**
   * Devuelve la dirección asociada a un índice.
   * @param index Índice de la dirección. El orden es el siguiente:
   *              <ol start="0">
   *                <li>NONE</li>
   *                <li>UP</li>
   *                <li>DOWN</li>
   *                <li>LEFT</li>
   *                <li>RIGHT</li>
   *              </ol>
   * @return Dirección asociada al índice.
   */
  public static Direction fromIndex (int index) {
    return values[index];
  }

  /**
   * Crea una dirección de forma aleatoria.
   * @return Una dirección aleatoria. No va a ser Direction.NONE.
   */
  public static Direction random () {
    return values[1 + (int)(Math.random() * 4.0)];
  }

  /**
   * Descompone la dirección en sus componentes x e y, con una magnitud de 1.
   * @return Pareja con la descomposición de la dirección (x, y).
   */
  public Pair<Integer, Integer> decompose () {
    Pair<Integer, Integer> p = new Pair <Integer, Integer>(0, 0);
    switch (this) {
      case NONE:
        break;
      case UP:
        p.second = -1;
        break;
      case DOWN:
        p.second = 1;
        break;
      case LEFT:
        p.first = -1;
        break;
      case RIGHT:
        p.first = 1;
        break;
    }
    return p;
  }

  /**
   * Invierte la posición actual.
   * @return Dirección contraria de la actual.
   */
  public Direction getOpposite () {
    switch (this) {
      case UP:
        return DOWN;
      case DOWN:
        return UP;
      case LEFT:
        return RIGHT;
      case RIGHT:
        return LEFT;
      default:
        return NONE;
    }
  }

  /**
   * Desplaza un punto en la dirección y lo guarda en un punto nuevo.
   * @param p Punto que se desea mover en esta dirección.
   * @return Nuevo punto equivalente al indicado desplazado en esta dirección.
   */
  public Point movePoint(final Point p) {
    Pair<Integer, Integer> desp = decompose();
    return new Point(p.x + desp.first, p.y + desp.second);
  }

}
