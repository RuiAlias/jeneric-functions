package ist.meic.pa.GenericFunctions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

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
    // System.out.println("isApplicable(" + Arrays.deepToString(args) + ")");

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
    // System.out.println("GFMethod::compareTo - this(" + debugParametersToString()
    //                    + ")\tother(" + other.debugParametersToString() + ")");

    Class<?>[] thisParameterTypes = getParameterTypes();
    Class<?>[] otherParameterTypes = other.getParameterTypes();

    assert(thisParameterTypes.length == otherParameterTypes.length);

    int diff = 0;

    for (int i = 0; diff == 0 && i < thisParameterTypes.length; i++) {
      diff = compareTypes(thisParameterTypes[i], otherParameterTypes[i]);
    }

    // if (diff == 0) {
    //   System.out.println("GFMethod::compareTo - Equally specific!!");
    // }

    return diff;
  }

  public static int compareTypes(Class<?> t1, Class<?> t2) {
    boolean moreSpecific = t2.isAssignableFrom(t1);
    boolean lessSpecific = t1.isAssignableFrom(t2);

    // System.out.println("GFMethod::compareTypes - moreSpecific?" + moreSpecific +
    //                    "\tlessSpecific?" + lessSpecific);

    if (moreSpecific && !lessSpecific) {
      // System.out.println("GFMethod::compareTypes - t1 is more specific on #" +
      //                    i);
      return -1;
    } else if (lessSpecific && !moreSpecific) {
      // System.out.println("GFMethod::compareTypes - t1 is less specific on #" +
      //                    i);
      return 1;
    } else {
      // System.out.println("hc "+t1.hashCode()+" "+t2.hashCode());

      return t1.hashCode() - t2.hashCode();
    }
  }

  private String debugParametersToString() {
    return Arrays.stream(getParameterTypes())
        .map(Class::getSimpleName)
        .collect(Collectors.joining(", "));
  }
}
