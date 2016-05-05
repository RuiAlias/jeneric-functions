package ist.meic.pa.GenericFunctions;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class GenericFunctionTest {
  // @Test
  // public void genericFunctionHasName() {
  //   new GenericFunction("add");
  // }

  // @Test
  // public void addOneGFMethod() {
  //   final GenericFunction add = new GenericFunction("add");

  //   add.addMethod(new GFMethod() {
  //     Object call(Integer a, Integer b) {
  //       return a + b;
  //     }
  //   });

  //   // 10 + (-5) = 5
  //   assertEquals(5, add.call(10, -5));
  // }

  @Test
  public void addTwoGFMethod() {
    final GenericFunction add = new GenericFunction("add");

    add.addMethod(new GFMethod() {
      Object call(Integer a, Integer b) {
        return a + b;
      }
    });

    System.out.println("Primeiro método adicionado.");

    add.addMethod(new GFMethod() {
      Object call(Object[] a, Object[] b) {
        Object[] r = new Object[a.length];
        for (int i = 0; i < a.length; i++) {
          r[i] = add.call(a[i], b[i]);
        }
        return r;
      }
    });

    System.out.println("Segundo método adicionado.");

    // 1 + 3 = 4
    assertEquals(4, add.call(1, 3));

    // [1, 2, 3] + [4, 5, 6] = [5, 7, 9]
    assertArrayEquals(new Object[] {5, 7, 9},
        (Object[]) add.call(new Object[] {1, 2, 3}, new Object[] {4, 5, 6}));

    // [[1, 2], 3] + [[3, 4], 5] = [[4, 6], 8]
    assertArrayEquals(new Object[] {new Object[] {4, 6}, 8},
        (Object[]) add.call(new Object[] {new Object[] {1, 2}, 3},
            new Object[] {new Object[] {3, 4}, 5}));
  }

  // @Test
  // public void shouldCallTheOnlyMethod() {
  //   final GenericFunction add = new GenericFunction("add");

  //   add.addMethod(new GFMethod() {
  //     Object call(Integer a, Integer b) {
  //       return a + b;
  //     }
  //   });

  //   // 1 + 3 = 4
  //   assertEquals(4, add.call(1, 3));
  // }

  // @Test
  // public void shouldUpdateMethod() {
  //   final GenericFunction gf = new GenericFunction("test");

  //   gf.addMethod(new GFMethod() {
  //       Object call(Integer a, Integer b) {
  //         return a + b;
  //       }
  //     });

  //   gf.addMethod(new GFMethod() {
  //       Object call(Integer a, Integer b) {
  //         return a * b;
  //       }
  //     });

  //   assertEquals(9, gf.call(3,3));
  // }

  // @Test
  // public void shouldIllegalArgumentExceptionWhenZeroMethods() {
  //   final GenericFunction empty = new GenericFunction("empty");

  //   try {
  //     empty.call(1, "a");
  //     fail("Should have thrown IllegalArgumentException because the generic "
  //         + "function has no methods.");
  //   } catch (IllegalArgumentException e) {
  //   }
  // }

  // @Test
  // public void shouldIllegalArgumentExceptionWhenNoApplicableMethods() {
  //   final GenericFunction add = new GenericFunction("add");

  //   add.addMethod(new GFMethod() {
  //     Object call(Integer a, Integer b) {
  //       return a + b;
  //     }
  //   });

  //   try {
  //     add.call(new Object[] {1, 2}, 3);
  //     fail("Should have thrown IllegalArgumentException because there is no "
  //         + "aplicable method.");
  //   } catch (IllegalArgumentException e) {
  //   }
  // }

  // @Test
  // public void illegalArgumentExceptionMessage() {
  //   final GenericFunction add = new GenericFunction("add");

  //   try {
  //     add.call(new Object[] {1, 2}, 3);
  //     fail("It didn't even throw the exception.");
  //   } catch (IllegalArgumentException e) {
  //     final String expectedMessage = "No methods for generic function add with "
  //         + "args [[1, 2], 3] of classes [class [Ljava.lang.Object;, class "
  //         + "java.lang.Integer]";
  //     assertEquals(expectedMessage, e.getMessage());
  //   }
  // }

  // @Test
  // public void shouldCallTheMostSpecificMethod1() {
  //   final GenericFunction gf = new GenericFunction("gf");

  //   gf.addMethod(new GFMethod() {
  //     Object call(A a1, A a2) {
  //       return "Método 1";
  //     }
  //   });

  //   gf.addMethod(new GFMethod() {
  //     Object call(B b1, B b2) {
  //       return "Método 2";
  //     }
  //   });

  //   assertEquals("Método 2", gf.call(new B(), new B()));
  // }

  // @Test
  // public void shouldCallTheMostSpecificMethod2() {
  //   final GenericFunction gf = new GenericFunction("gf");

  //   gf.addMethod(new GFMethod() {
  //     Object call(A a1, A a2) {
  //       return "Método 1";
  //     }
  //   });

  //   gf.addMethod(new GFMethod() {
  //     Object call(B b1, A a2) {
  //       return "Método 2";
  //     }
  //   });

  //   assertEquals("Método 1", gf.call(new A(), new B()));
  // }

  // @Test
  // public void testOrder() {
  //   final GenericFunction gf = new GenericFunction("gf");

  //   gf.addMethod(new GFMethod() {
  //     Object call(A a1, A a2) {
  //       System.out.println("Primary");
  //       return "Primary";
  //     }
  //   });

  //   gf.addBeforeMethod(new GFMethod() {
  //     void call(A a1, A a2) {
  //       System.out.println("Before AA");
  //     }
  //   });

  //   gf.addBeforeMethod(new GFMethod() {
  //     void call(B b1, B b2) {
  //       System.out.println("Before BB");
  //     }
  //   });

  //   gf.addAfterMethod(new GFMethod() {
  //     void call(A a1, A a2) {
  //       System.out.println("After AA");
  //     }
  //   });

  //   gf.addAfterMethod(new GFMethod() {
  //     void call(B b1, B a2) {
  //       System.out.println("After BB");
  //     }
  //   });

  //   assertEquals("Primary", gf.call(new B(), new B()));
  // }

  // @Test
  // public void testAdd() {
  //   final GenericFunction add = new GenericFunction("add");

  //   add.addMethod(new GFMethod() {
  //     Object call(Integer a, Integer b) {
  //       return a + b;
  //     }
  //   });

  //   add.addMethod(new GFMethod() {
  //     Object call(Object[] a, Object[] b) {
  //       Object[] r = new Object[a.length];
  //       for (int i = 0; i < a.length; i++) {
  //         r[i] = add.call(a[i], b[i]);
  //       }
  //       return r;
  //     }
  //   });

  //   add.addMethod(new GFMethod() {
  //     Object call(Object[] a, Object b) {
  //       Object[] ba = new Object[a.length];
  //       Arrays.fill(ba, b);
  //       return add.call(a, ba);
  //     }
  //   });

  //   add.addMethod(new GFMethod() {
  //     Object call(Object a, Object b[]) {
  //       Object[] aa = new Object[b.length];
  //       Arrays.fill(aa, a);
  //       return add.call(aa, b);
  //     }
  //   });

  //   add.addMethod(new GFMethod() {
  //     Object call(String a, Object b) {
  //       return add.call(Integer.decode(a), b);
  //     }
  //   });

  //   add.addMethod(new GFMethod() {
  //     Object call(Object a, String b) {
  //       return add.call(a, Integer.decode(b));
  //     }
  //   });

  //   add.addMethod(new GFMethod() {
  //     Object call(Object[] a, List b) {
  //       return add.call(a, b.toArray());
  //     }
  //   });

  //   println(add.call(new Object[] {1, 2}, 3));
  //   println(add.call(1, new Object[][] {{1, 2}, {3, 4}}));
  //   println(add.call("12", "34"));
  //   println(add.call(new Object[] {"123", "4"}, 5));
  //   println(add.call(new Object[] {1, 2, 3}, Arrays.asList(4, 5, 6)));
  // }

  // @Test
  // public void testExplain() {
  //   final GenericFunction explain = new GenericFunction("explain");
  //   explain.addMethod(new GFMethod() {
  //     Object call(Integer entity) {
  //       System.out.printf("%s is a integer", entity);
  //       return "";
  //     }
  //   });

  //   explain.addMethod(new GFMethod() {
  //     Object call(Number entity) {
  //       System.out.printf("%s is a number", entity);
  //       return "";
  //     }
  //   });

  //   explain.addMethod(new GFMethod() {
  //     Object call(String entity) {
  //       System.out.printf("%s is a string", entity);
  //       return "";
  //     }
  //   });

  //   explain.addAfterMethod(new GFMethod() {
  //     void call(Integer entity) {
  //       System.out.printf(" (in hexadecimal, is %x)", entity);
  //     }
  //   });

  //   explain.addBeforeMethod(new GFMethod() {
  //     void call(Number entity) {
  //       System.out.printf("The number ", entity);
  //     }
  //   });

  //   println(explain.call(123));
  //   println(explain.call("Hi"));
  //   println(explain.call(3.14159));
  // }

  // private static void println(Object obj) {
  //   if (obj instanceof Object[]) {
  //     System.out.println(Arrays.deepToString((Object[]) obj));
  //   } else {
  //     System.out.println(obj);
  //   }
  // }
}
