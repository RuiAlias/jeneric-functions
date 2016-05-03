package ist.meic.pa.GenericFunctions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    System.out.println("isApplicable("+Arrays.deepToString(args)+")");

    Class<?>[] parameterTypes = getParameterTypes();

    if (parameterTypes.length != args.length) {
      return false;
    }

    for (int i = 0; i < args.length; i++) {
      if (!parameterTypes[i].isAssignableFrom(args[i].getClass())) {
        return false;
      }
    }

    System.out.println("it is applicable");

    return true;
  }

  protected int compareToGivenArgs(GFMethod other, Object... args) {
    System.out.println("compareTo - other:"+other+"\targs:"+Arrays.deepToString(args));

    List<Integer> thisDistances = distanceToArgs(args);
    List<Integer> otherDistances = other.distanceToArgs(args);

    for (int i = 0; i < thisDistances.size(); i++) {
      int diff = thisDistances.get(i) - otherDistances.get(i);
      if (diff != 0) {
        return diff;
      }
    }

    return 0;
  }

  protected List<Integer> distanceToArgs(Object... args) {
    System.out.println("distanceToArgs("+Arrays.deepToString(args)+")");

    Class<?>[] parameterTypes = getParameterTypes();

    List<Integer> distances = new ArrayList<Integer>(args.length);

    for (int i = 0; i < parameterTypes.length; i++) {
      distances.add(typeDistance(args[i].getClass(), parameterTypes[i]));
    }

    return distances;
  }

  private int typeDistance(Class<?> subType, Class<?> superType) {
    System.out.println("typeDistance("+subType+", "+superType+")");

    int distance = 0;

    while (subType != superType) {
      subType = subType.getSuperclass();
      distance++;
    }

    return distance;
  }
}
