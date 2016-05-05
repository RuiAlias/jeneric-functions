package ist.meic.pa.GenericFunctions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GFMethod {
  public Object dynamicCall(Object... args) {
    try {
      return getMethod().invoke(this, args);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
      return null;
    }
  }

  private Method getMethod() {
    return getClass().getDeclaredMethods()[0];
  }

  protected Class<?>[] getParameterTypes() {
    return getMethod().getParameterTypes();
  }

  protected boolean isApplicable(Object... args) {
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

  protected int compareTo(GFMethod other) {
    // System.out.println("compareTo: this(" + parametersToString() + ")\tother("
    //     + other.parametersToString() + ")");

    Class<?>[] thisParameterTypes = getParameterTypes();
    Class<?>[] otherParameterTypes = other.getParameterTypes();

    assert (thisParameterTypes.length == otherParameterTypes.length);

    for (int i = 0; i < thisParameterTypes.length; i++) {
      boolean moreSpecific =
          otherParameterTypes[i].isAssignableFrom(thisParameterTypes[i]);
      boolean lessSpecific =
          thisParameterTypes[i].isAssignableFrom(otherParameterTypes[i]);

      if (moreSpecific && lessSpecific) {
        continue;
      } else if (moreSpecific) {
        // System.out.println("this is more specific on #" + i);
        return -1;
      } else if (lessSpecific) {
        // System.out.println("this is less specific on #" + i);
        return 1;
      } else {
        assert (false);
      }
    }

    System.out.println("Equally specific!!");

    return 0;
  }

  private String parametersToString() {
    return Arrays.stream(getParameterTypes()).map(Class::getSimpleName)
        .collect(Collectors.joining(", "));
  }
}
