package ist.meic.pa.GenericFunctions;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class GenericFunction {
  final private String name;
  private Collection<GFMethod> primaryMethods;
  private Collection<GFMethod> beforeMethods;
  private Collection<GFMethod> afterMethods;
  private static final String NO_APPLICABLE_METHODS =
      "No methods for generic function %s with args %s of classes %s";

  public GenericFunction(String name) {
    this.name = name;
    this.primaryMethods = new HashSet<GFMethod>();
    this.beforeMethods = new HashSet<GFMethod>();
    this.afterMethods = new HashSet<GFMethod>();
  }

  public void addMethod(GFMethod gfMethod) {
    this.primaryMethods.add(gfMethod);
  }

  public void addBeforeMethod(GFMethod gfMethod) {
    this.beforeMethods.add(gfMethod);
  }

  public void addAfterMethod(GFMethod gfMethod) {
    this.afterMethods.add(gfMethod);
  }

  public Object call(Object... args) {
    beforeMethods.stream()
      .filter(gfm -> gfm.isApplicable(args))
      .sorted((gfm1, gfm2) -> gfm1.compareTo(gfm2))
      .forEachOrdered((gfm) -> gfm.dynamicCall(args));

    Object bestMethodReturn = primaryMethods.stream()
      .filter(gfm -> gfm.isApplicable(args))
      .min((gfm1, gfm2) -> gfm1.compareTo(gfm2))
      .orElseThrow(() -> generateNoApplicableMethodsException(args))
      .dynamicCall(args);

    afterMethods.stream()
      .filter(gfm -> gfm.isApplicable(args))
      .sorted((gfm1, gfm2) -> -gfm1.compareTo(gfm2))
      .forEachOrdered((gfm) -> gfm.dynamicCall(args));

    return bestMethodReturn;
  }

  private IllegalArgumentException generateNoApplicableMethodsException(
      Object... args) {
    final String arguments = Arrays.deepToString(args);
    final String types = Arrays.deepToString(argsTypes(args));
    final String message =
        String.format(NO_APPLICABLE_METHODS, this.name, arguments, types);

    // System.out.println("Exception message: " + message);

    return new IllegalArgumentException(message);
  }

  private static Class<?>[] argsTypes(Object[] args) {
    Class<?>[] types = new Class<?>[args.length];

    for (int i = 0; i < args.length; i++) {
      types[i] = args[i].getClass();
    }

    return types;
  }
}
