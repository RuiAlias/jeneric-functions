package ist.meic.pa.GenericFunctions;

import static org.junit.Assert.*;
import org.junit.Test;

public class GFMethodTest {
  @Test
  public void choosingTheMostSpecificMethod1() {
    GFMethod gfm1 = new GFMethod() {
      public Object call(Object o, A a, C c) {
        return null;
      }
    };

    GFMethod gfm2 = new GFMethod() {
      public Object call(Object o, A a1, A a2) {
        return null;
      }
    };

    assertTrue(gfm1.compareTo(gfm2) < 0);
  }

  @Test
  public void choosingTheMostSpecificMethod2() {
    GFMethod gfm1 = new GFMethod() {
      public Object call(Object o, A a, C c) {
        return null;
      }
    };

    GFMethod gfm2 = new GFMethod() {
      public Object call(D d1, D d2, D d3) {
        return null;
      }
    };

    assertTrue(gfm1.compareTo(gfm2) > 0);
  }

  @Test
  public void testEquals() {
    GFMethod gfm1 = new GFMethod() {
        public Object call(Object o, A a, C c) {
          return null;
        }
      };

    GFMethod gfm2 = new GFMethod() {
        public Object call(Object o, A a, C c) {
          return true;
        }
      };

    assertEquals(gfm1, gfm2);
    assertEquals("Hashcode", gfm1.hashCode(), gfm2.hashCode());
    assertNotSame(gfm1, gfm2);
    assertEquals(0, gfm1.compareTo(gfm2));
    assertEquals(0, gfm2.compareTo(gfm1));
  }

  @Test
  public void testDifferent() {
    GFMethod gfm1 = new GFMethod() {
        public Object call(Object o, A a, C c) {
          return null;
        }
      };

    GFMethod gfm2 = new GFMethod() {
        public Object call(Object o, A a, B b) {
          return true;
        }
      };

    assertNotEquals(gfm1, gfm2);
    assertNotEquals(gfm1.hashCode(), gfm2.hashCode());
    assertNotSame(gfm1, gfm2);
    System.out.println("qweqweqwe "+gfm1.compareTo(gfm2));

    assertTrue("compareTo > 0", gfm1.compareTo(gfm2) > 0);
    assertTrue("compareTo < 0", gfm2.compareTo(gfm1) < 0);
  }

  @Test
  public void testDifferent2() {
    GFMethod gfm1 = new GFMethod() {
        public Object call(Integer a, Integer b) {
          return a + b;
        }
      };

    GFMethod gfm2 = new GFMethod() {
        public Object call(Object[] a, Object[] b) {
          return true;
        }
      };

    assertNotEquals(gfm1, gfm2);
    assertNotEquals(gfm2, gfm1);
    assertNotEquals(gfm1.hashCode(), gfm2.hashCode());
    assertNotEquals(gfm2.hashCode(), gfm1.hashCode());
    assertNotSame(gfm1, gfm2);
    assertNotSame(gfm2, gfm1);
    System.out.println("asdasdasd "+gfm1.compareTo(gfm2));
    assertTrue("compareTo < 0", gfm1.compareTo(gfm2) < 0);
    assertTrue("compareTo > 0", gfm2.compareTo(gfm1) > 0);
  }

  @Test
  public void selfCompare() {
    GFMethod gfm1 = new GFMethod() {
        public Object call(Integer a, Integer b) {
          return a + b;
        }
      };

    assertEquals(gfm1, gfm1);
    assertEquals("Hashcode", gfm1.hashCode(), gfm1.hashCode());
    assertSame(gfm1, gfm1);
    assertEquals(0, gfm1.compareTo(gfm1));
  }
}
