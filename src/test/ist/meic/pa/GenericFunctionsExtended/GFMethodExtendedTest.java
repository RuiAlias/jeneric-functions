package ist.meic.pa.GenericFunctionsExtended;

import static org.junit.Assert.*;
import org.junit.Test;

public class GFMethodExtendedTest {
  @Test
  public void choosingTheMostSpecificMethod1() {
    GFMethodExtended gfm1 = new GFMethodExtended() {
      public Object call(Object o, A a, C c) {
        return null;
      }
    };

    GFMethodExtended gfm2 = new GFMethodExtended() {
      public Object call(Object o, A a1, A a2) {
        return null;
      }
    };

    assertTrue(gfm1.compareToRightLeft(gfm2) < 0);
  }

  @Test
  public void choosingTheMostSpecificMethod2() {
    GFMethodExtended gfm1 = new GFMethodExtended() {
      public Object call(Object o, A a, C c) {
        return null;
      }
    };

    GFMethodExtended gfm2 = new GFMethodExtended() {
      public Object call(D d1, D d2, D d3) {
        return null;
      }
    };

    assertTrue(gfm1.compareToRightLeft(gfm2) > 0);
  }

  @Test
  public void shouldReturn0WhenComparingEqualMethods() {
    GFMethodExtended gfm1 = new GFMethodExtended() {
        public Object call(Object o, A a, C c) {
          return null;
        }
      };

    GFMethodExtended gfm2 = new GFMethodExtended() {
        public Object call(Object o, A a, C c) {
          return true;
        }
      };

    assertEquals(0, gfm1.compareToRightLeft(gfm2));
    assertEquals(0, gfm2.compareToRightLeft(gfm1));
  }

  @Test
  public void shouldReturnDifferentWhenMethodsAreUnrelated1() {
    GFMethodExtended gfm1 = new GFMethodExtended() {
        public Object call(Object o, A a, C c) {
          return null;
        }
      };

    GFMethodExtended gfm2 = new GFMethodExtended() {
        public Object call(Object o, A a, B b) {
          return true;
        }
      };

    testUnrelatedMethods(gfm1, gfm2);
  }

  @Test
  public void shouldReturnDifferentWhenMethodsAreUnrelated2() {
    GFMethodExtended gfm1 = new GFMethodExtended() {
        public Object call(Integer a, Integer b) {
          return a + b;
        }
      };

    GFMethodExtended gfm2 = new GFMethodExtended() {
        public Object call(Object[] a, Object[] b) {
          return true;
        }
      };

    testUnrelatedMethods(gfm1, gfm2);
  }

  @Test
  public void selfCompare() {
    GFMethodExtended gfm1 = new GFMethodExtended() {
        public Object call(Integer a, Integer b) {
          return a + b;
        }
      };

    assertEquals(0, gfm1.compareToRightLeft(gfm1));
  }


  private void testUnrelatedMethods(GFMethodExtended gfm1, GFMethodExtended gfm2) {
    int diff1 = gfm1.compareToRightLeft(gfm2);
    int diff2 = gfm2.compareToRightLeft(gfm1);

    assertNotEquals(0, diff1);
    assertNotEquals(0, diff2);

    if (diff1 > 0) {
      assertTrue(diff2 < 0);
    } else {
      assertTrue(diff2 > 0);
    }
  }
}
