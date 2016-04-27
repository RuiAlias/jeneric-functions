package ist.meic.pa.GenericFunctions;

import java.util.Arrays;

public class GenericFunction {
  final private String name;
  private GFMethod method;

  public GenericFunction(String name) {
    this.name = name;
  }

  public void addMethod(GFMethod method) {
    this.method = method;
  }

  public Object call(Object... args) {
    System.out.println(
        Arrays.deepToString(this.method.getClass().getDeclaredMethods()));
    throw new IllegalArgumentException();
  }
}
