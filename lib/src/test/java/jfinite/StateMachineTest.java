package jfinite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StateMachineTest {

    Boolean hasTransitioned;

    @Test
    void stateMachineTransitions() {
        StateMachine<State> stateMachine = new StateMachine<>(State.ENTRY);

        stateMachine.setTransitionCondition(State.ENTRY, State.EXIT, () -> false);

        stateMachine.update();
        assertEquals(stateMachine.getState(), State.ENTRY);

        stateMachine.setTransitionCondition(State.ENTRY, State.EXIT, () -> true);

        stateMachine.update();
        assertEquals(stateMachine.getState(), State.EXIT);
    }

    @Test
    void stateMachineBehavior() {
        StateMachine<State> stateMachine = new StateMachine<>(State.ENTRY);
        hasTransitioned = false;

        stateMachine.register(State.EXIT, () -> hasTransitioned = true);

        assertEquals(hasTransitioned, false);

        stateMachine.setTransitionCondition(State.ENTRY, State.EXIT, () -> true);
        stateMachine.update();

        assertEquals(hasTransitioned, true);
    }
}
