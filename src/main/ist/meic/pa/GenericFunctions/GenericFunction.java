package ist.meic.pa.GenericFunctions;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class GenericFunction {
  final private String name;
  private Map<List<Class<?>>,GFMethod> primaryMethods;
  private Map<List<Class<?>>,GFMethod> beforeMethods;
  private Map<List<Class<?>>,GFMethod> afterMethods;
  private static final String NO_APPLICABLE_METHODS =
      "No methods for generic function %s with args %s of classes %s";

  public GenericFunction(String name) {
    this.name = name;
    this.primaryMethods = new HashMap<List<Class<?>>,GFMethod>();
    this.beforeMethods = new HashMap<List<Class<?>>,GFMethod>();
    this.afterMethods = new HashMap<List<Class<?>>,GFMethod>();
  }

  public void addMethod(GFMethod gfm) {
    List<Class<?>> params = Arrays.asList(gfm.getParameterTypes());
    this.primaryMethods.put(params, gfm);
  }

  public void addBeforeMethod(GFMethod gfm) {
    List<Class<?>> params = Arrays.asList(gfm.getParameterTypes());
    this.beforeMethods.put(params, gfm);
  }

  public void addAfterMethod(GFMethod gfm) {
    List<Class<?>> params = Arrays.asList(gfm.getParameterTypes());
    this.afterMethods.put(params, gfm);
  }

  public Object call(Object... args) {
    callBefores(args);
    Object bestMethodReturn = callPrimary(args);
    callAfters(args);

    return bestMethodReturn;
  }

  private Object callPrimary(Object... args) {
    return primaryMethods.values().stream()
        .filter(gfm -> gfm.isApplicable(args))
        .sorted()
        .findFirst()
        .orElseThrow(() -> generateNoApplicableMethodsException(args))
        .dynamicCall(args);
  }

  private void callBefores(Object... args) {
    beforeMethods.values().stream()
      .filter(gfm -> gfm.isApplicable(args))
      .sorted()
      .forEachOrdered(gfm -> gfm.dynamicCall(args));
  }

  private void callAfters(Object... args) {
    afterMethods.values().stream()
        .filter(gfm -> gfm.isApplicable(args))
        .sorted((gfm1, gfm2) -> -gfm1.compareTo(gfm2))
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
