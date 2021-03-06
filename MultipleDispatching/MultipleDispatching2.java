import java.lang.reflect.Method;

abstract class Value {}

class IntValue extends Value {
    public int value;
}

class DoubleValue extends Value {
    public double value;
}

class Operator {

    void apply(IntValue a, IntValue b) { System.out.println("apply int, int"); }

    void apply(DoubleValue a, DoubleValue b) { System.out.println("apply double, double"); }

    void apply(Value a, Value b) throws Exception {
        String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        Method method = this.getClass().getDeclaredMethod(currentMethodName, a.getClass(), b.getClass());
        method.invoke(this, a, b);
    }
    
    void dynamicDispatch()
}

class Test {
    public static void main(String[] args) throws Exception {
        Value    a1       = new IntValue();
        Value    b1       = new IntValue();
        Value    a2       = new DoubleValue();
        Value    b2       = new DoubleValue();
        Operator operator = new Operator();
        operator.apply(a1, b1);
        operator.apply(a2, b2);
    }
}
