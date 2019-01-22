
/**
 * we want to build a customized type system which can handle 
 * MyNumber + MyNumber and MyString + MyString, but MyString + 
 * MyNumber and MyNumber is not allowed.
 * If we want to avoid to use a lot of clause like `if (obj.getClass() == MyString.class)`,
 * we'd better use multiple dispatching.
 * Since in Java overriding is dynamic binding but overloading is static binding, Java only support
 * single dispatching by overriding. But we can use overriding twice to implement two-dispatching
 */

class TypeNotCompatibleException extends Exception {}

abstract class MyObject {
    //first dispatching
    abstract MyObject add(MyObject obj) throws TypeNotCompatibleException;

    //second dispatching
    abstract MyObject evaluate(MyNumber number) throws TypeNotCompatibleException;
    abstract MyObject evaluate(MyString string) throws TypeNotCompatibleException;
}

class MyNumber extends MyObject {
    public int num;

    MyNumber(int num) {
        this.num = num;
    }

    @Override
    MyObject add(MyObject obj) throws TypeNotCompatibleException {
        return obj.evaluate(this);
    }

    @Override
    MyObject evaluate(MyNumber number) throws TypeNotCompatibleException {
        //MyNumber + MyNumber
        return new MyNumber(this.num + number.num);
    }

    @Override
    MyObject evaluate(MyString string) throws TypeNotCompatibleException {
        //MyNumber + MyString, not allowed
        throw new TypeNotCompatibleException();
    }

    @Override
    public String toString() {
        return String.valueOf(num);
    }
}

class MyString extends MyObject {
    public String str;

    MyString(String str) {
        this.str = str;
    }

    @Override
    MyObject add(MyObject obj) throws TypeNotCompatibleException {
        return obj.evaluate(this);
    }

    @Override
    MyObject evaluate(MyNumber number) throws TypeNotCompatibleException {
        //MyString + MyNumber, not allowed
        throw new TypeNotCompatibleException();
    }

    @Override
    MyObject evaluate(MyString string) throws TypeNotCompatibleException {
        //MyString + MyString
        return new MyString(this.str + string.str);
    }

    @Override
    public String toString() {
        return str;
    }
}

class Dispatcher {
    static MyObject add(MyObject obj1, MyObject obj2) throws TypeNotCompatibleException {
        return obj1.add(obj2);
    }

    public static void main(String[] args) {
        try {
            MyObject obj1 = add(new MyNumber(1), new MyNumber(2)); // 3
            System.out.println(obj1);
            MyObject obj2 = add(new MyNumber(1), new MyString("str")); // type not compatible
        } catch (TypeNotCompatibleException e) {
            System.out.println("Type not compatible.");
        }
    }
}
