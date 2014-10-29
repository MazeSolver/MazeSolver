/**
 * @file Direction.java
 * @date 21/10/2014
 */
package maze;

import util.Pair;

/**
 * Enum que representa una dirección de movimiento en 2D. Cada posible opción es
 * un flag, por lo que se pueden hacer operaciones de bit para representar
 * varias direcciones en la misma variable simultáneamente.
 */
public enum Direction {
  UP    ((short) 0x01),
  DOWN  ((short) 0x02),
  LEFT  ((short) 0x04),
  RIGHT ((short) 0x08);

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
   * Descompone la dirección en sus componentes x e y, con una magnitud de 1.
   * @param dir Dirección que se quiere traducir.
   * @return Pareja con la descomposición de la dirección (x, y).
   */
  public static Pair<Integer, Integer> decompose (Direction dir) {
    Pair<Integer, Integer> p = new Pair <Integer, Integer>(0, 0);
    switch (dir) {
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

}
