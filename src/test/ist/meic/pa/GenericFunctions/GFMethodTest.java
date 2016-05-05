package ist.meic.pa.GenericFunctions;

import static org.junit.Assert.assertTrue;
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
}
