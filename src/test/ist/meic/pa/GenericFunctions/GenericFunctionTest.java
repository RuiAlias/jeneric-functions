package ist.meic.pa.GenericFunctions;

import static org.junit.Assert.*;
import org.junit.Test;

public class GenericFunctionTest {
  private static final Object[] a1to3 = new Object[] {1, 2, 3};
  private static final Object[] a4to6 = new Object[] {4, 5, 6};
  private static final Object[] a579 = new Object[] {5, 7, 9};

  @Test
  public void genericFunctionHasName() {
    new GenericFunction("add");
  }

  @Test
  public void addOneGFMethod() {
    final GenericFunction add = new GenericFunction("add");

    add.addMethod(new GFMethod() {
      Object call(Integer a, Integer b) {
        return a + b;
      }
    });

    // 10 + (-5) = 5
    assertEquals(5, add.call(10, -5));
  }

  @Test
  public void addTwoGFMethod() {
    final GenericFunction add = new GenericFunction("add");

    add.addMethod(new GFMethod() {
      Object call(Integer a, Integer b) {
        return a + b;
      }
    });

    add.addMethod(new GFMethod() {
      Object call(Object[] a, Object[] b) {
        Object[] r = new Object[a.length];
        for (int i = 0; i < a.length; i++) {
          r[i] = add.call(a[i], b[i]);
        }
        return r;
      }
    });

    // 1 + 3 = 4
    assertEquals(4, add.call(1, 3));

    // [1, 2, 3] + [4, 5, 6] = [5, 7, 9]
    assertArrayEquals(a579, (Object[]) add.call(a1to3, a4to6));

    // [[1, 2], 3] + [[3, 4], 5] = [[4, 6], 8]
    assertArrayEquals(new Object[] {new Object[] {4, 6}, 8},
        (Object[]) add.call(new Object[] {new Object[] {1, 2}, 3},
                            new Object[] {new Object[] {3, 4}, 5}));
  }

  @Test
  public void shouldCallTheOnlyMethod() {
    final GenericFunction add = new GenericFunction("add");

    add.addMethod(new GFMethod() {
      Object call(Integer a, Integer b) {
        return a + b;
      }
    });

    // 1 + 3 = 4
    assertEquals(4, add.call(1, 3));
  }

  @Test
  public void shouldIllegalArgsWhenZeroMethods() {
    final GenericFunction empty = new GenericFunction("empty");

    try {
      empty.call(1, "a");
      fail("Should have thrown IllegalArgumentException because the generic " +
           "function has no methods.");
    } catch (IllegalArgumentException e) {
    }
  }

  @Test
  public void shouldIllegalArgsWhenNoApplicableMethods() {
    final GenericFunction add = new GenericFunction("add");

    add.addMethod(new GFMethod() {
      Object call(Integer a, Integer b) {
        return a + b;
      }
    });

    try {
      add.call(new Object[] {1, 2 }, 3);
      fail("Should have thrown IllegalArgumentException because there is no " +
           "aplicable method.");
    } catch (IllegalArgumentException e) {
    }
  }
}
