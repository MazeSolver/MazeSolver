/**
 * @file Direction.java
 * @date 21/10/2014
 */
package maze;

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

  public static Direction fromValue (short value) {
    for (Direction i: values)
      if (i.val == value)
        return i;

    return null;
  }

}
