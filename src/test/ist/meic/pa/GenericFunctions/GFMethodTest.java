package ist.meic.pa.GenericFunctions;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class GFMethodTest {
  @Test
  public void test1() {
    GFMethod gfm = new GFMethod() {
      public Object call(A a) {
        return null;
      }
    };

    assertEquals(Arrays.asList(2), gfm.distanceToArgs(new D()));
  }

  @Test
  public void test2() {
    GFMethod gfm = new GFMethod() {
        public Object call(Object o1, A a2, A a3, A a4, Object o2) {
          return null;
        }
      };

    assertEquals(Arrays.asList(1, 0, 1, 1, 3), gfm.distanceToArgs(new A(), new A(), new B(), new C(), new D()));
  }

  @Test
  public void testCompareToGivenArgs() {
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

    assertTrue(gfm1.compareToGivenArgs(gfm2, new D(), new D(), new D()) < 0);
  }

  @Test
  public void testCompareToGivenArgs2() {
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

    assertTrue(gfm1.compareToGivenArgs(gfm2, new D(), new D(), new D()) > 0);
  }
}
