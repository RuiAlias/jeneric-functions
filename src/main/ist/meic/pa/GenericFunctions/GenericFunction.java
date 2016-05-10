package ist.meic.pa.GenericFunctions;

import java.util.Arrays;

public class GenericFunction {
  final private String name;
  private GFMSet primaryMethods;
  private GFMSet beforeMethods;
  private GFMSet afterMethods;
  private static final String NO_APPLICABLE_METHODS =
      "No methods for generic function %s with args %s of classes %s";

  public GenericFunction(String name) {
    this.name = name;
    this.primaryMethods = new GFMSet();
    this.beforeMethods = new GFMSet();
    this.afterMethods = new GFMSet((gfm1, gfm2) -> -gfm1.compareTo(gfm2));
  }

  public void addMethod(GFMethod gfm) {
    this.primaryMethods.add(gfm);
  }

  public void addBeforeMethod(GFMethod gfm) {
    this.beforeMethods.add(gfm);
  }

  public void addAfterMethod(GFMethod gfm) {
    this.afterMethods.add(gfm);
  }

  public Object call(Object... args) {
    callBefores(args);

    Object bestMethodReturn = callPrimary(args);

    callAfters(args);

    return bestMethodReturn;
  }

private Object callPrimary(Object... args) {
	return primaryMethods.stream()
        .filter(gfm -> gfm.isApplicable(args)).findFirst()
        .orElseThrow(() -> generateNoApplicableMethodsException(args))
        .dynamicCall(args);
}

private void callAfters(Object... args) {
	afterMethods.stream()
        .filter(gfm -> gfm.isApplicable(args))
        .forEachOrdered(gfm -> gfm.dynamicCall(args));
}

private void callBefores(Object... args) {
	beforeMethods.stream()
        .filter(gfm -> gfm.isApplicable(args))
        .forEachOrdered(gfm -> gfm.dynamicCall(args));
}

  protected IllegalArgumentException generateNoApplicableMethodsException(
      Object... args) {
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
