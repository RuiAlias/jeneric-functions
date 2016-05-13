package ist.meic.pa.GenericFunctions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Contains the reflection mechanisms to fetch and invoke the method call() at
 * runtime. Implements the comparison between 2 GFMethods. Implements the
 * applicability criteria given 1 or more arguments.
 */
public class GFMethod implements Comparable<GFMethod> {
  public Object dynamicCall(Object... args) {
    try {
      return getMethod().invoke(this, args);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
      return null;
    }
  }

  private Method getMethod() {
    Method m = getClass().getDeclaredMethods()[0];
    m.setAccessible(true);

    return m;
  }

  public Class<?>[] getParameterTypes() {
    return getMethod().getParameterTypes();
  }

  public boolean isApplicable(Object... args) {
    Class<?>[] parameterTypes = getParameterTypes();

    if (parameterTypes.length != args.length) {
      return false;
    }

    for (int i = 0; i < args.length; i++) {
      if (!parameterTypes[i].isAssignableFrom(args[i].getClass())) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int compareTo(GFMethod other) {
    Class<?>[] thisParameterTypes = getParameterTypes();
    Class<?>[] otherParameterTypes = other.getParameterTypes();

    assert (thisParameterTypes.length == otherParameterTypes.length);

    int diff = 0;

    for (int i = 0; diff == 0 && i < thisParameterTypes.length; i++) {
      diff = compareTypes(thisParameterTypes[i], otherParameterTypes[i]);
    }

    return diff;
  }

  private static int compareTypes(Class<?> t1, Class<?> t2) {
    boolean moreSpecific = t2.isAssignableFrom(t1);
    boolean lessSpecific = t1.isAssignableFrom(t2);

    if (moreSpecific && !lessSpecific) {
      return -1;
    } else if (lessSpecific && !moreSpecific) {
      return 1;
    } else {
      return 0;
    }
  }
}
