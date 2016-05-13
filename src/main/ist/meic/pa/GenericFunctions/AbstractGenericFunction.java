package ist.meic.pa.GenericFunctions;

import java.util.Arrays;

/**
 * Stores the genericFunction's name and generates the IllegalArgumentException.
 */
public abstract class AbstractGenericFunction {
  private final String name;
  private static final String NO_APPLICABLE_METHODS =
      "No methods for generic function %s with args %s of classes %s";

  public AbstractGenericFunction(String name) {
    this.name = name;
  }

  protected IllegalArgumentException generateNoApplicableMethodsException(
      Object[] args) {
    final String arguments = Arrays.deepToString(args);
    final String types = Arrays.deepToString(argsTypes(args));
    final String message =
        String.format(NO_APPLICABLE_METHODS, this.name, arguments, types);

    return new IllegalArgumentException(message);
  }

  protected static Class<?>[] argsTypes(Object[] args) {
    Class<?>[] types = new Class<?>[args.length];

    for (int i = 0; i < args.length; i++) {
      types[i] = args[i].getClass();
    }

    return types;
  }
}
