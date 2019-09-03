import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

abstract class Value {
}

class IntValue extends Value {
    public int value;
}

class DoubleValue extends Value {
    public double value;
}

class Operator {

    void apply(IntValue a, IntValue b) {
        System.out.println("apply int, int");
    }

    void apply(DoubleValue a, DoubleValue b) {
        System.out.println("apply double, double");
    }

    void apply(Value a, Value b) throws Exception {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        dynamicDispatch(this.getClass(), currentMethodName, a, b);
    }

    private void dynamicDispatch(Class clazz, String methodName, Object... args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?>[] classes = new Class<?>[args.length];
        for (int i = 0; i < args.length; ++i) {
            classes[i] = args[i].getClass();
        }
        Method method = clazz.getDeclaredMethod(methodName, classes);
        method.invoke(this, args);
    }
}

class Test {
    public static void main(String[] args) throws Exception {
        Value a1 = new IntValue();
        Value b1 = new IntValue();
        Value a2 = new DoubleValue();
        Value b2 = new DoubleValue();
        Operator operator = new Operator();
        operator.apply(a1, b1);
        operator.apply(a2, b2);
    }
}
/**
 * Shorcumming: Cannot handle this situation: IntValue -> NumberValue -> Value
 * 
 * void apply(IntValue, NumberValue) {}
 * 
 * apply(new IntValue(), new IntValue());
 * 
 * Because through getClass(), we can only get the precise class so when faced
 * with parent class, this solution failed.
 * 
 * 
 */