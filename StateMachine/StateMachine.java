class Input {}

abstract class State {
    abstract public State input1(Input input);
    
    abstract public State input2(Input input);
}

class State1 extends State {
    @Override
    public State input1(Input input) {
        System.out.println("Get input1 in State1, and the state keep in State1");
        return new State1();
    }

    @Override
    public State input2(Input input) {
        System.out.println("Get input2 in State1, and the state turn to State2");
        return new State2();
    }
}

class State2 extends State {
    @Override
    public State input1(Input input) {
        System.out.println("Get input1 in State2, and the state turn to State1");
        return new State1();
    }

    @Override
    public State input2(Input input) {
        System.out.println("Get input2 in State2, and the state keep in State2");
        return new State2();
    }
}

class Test {
    public static void main(String[] arg) {
        State initialState = new State1();
        State newState = initialState.input1(new Input());
    }
}