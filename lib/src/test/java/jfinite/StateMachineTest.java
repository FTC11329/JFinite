package jfinite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StateMachineTest {

    Boolean hasTransitioned;
    int iteratingStuff;

    @Test
    void stateMachineTransitions() {
        StateMachine<State> stateMachine = new StateMachine<>(State.ENTRY);

        stateMachine.setTransitionCondition(State.ENTRY, State.EXIT, () -> false);

        stateMachine.update();
        assertEquals(State.ENTRY, stateMachine.getState());

        stateMachine.setTransitionCondition(State.ENTRY, State.EXIT, () -> true);

        stateMachine.update();
        assertEquals(State.EXIT, stateMachine.getState());
    }

    @Test
    void stateMachineBehavior() {
        StateMachine<State> stateMachine = new StateMachine<>(State.ENTRY);
        hasTransitioned = false;

        stateMachine.addBehaviour(State.EXIT, () -> hasTransitioned = true);

        assertEquals(hasTransitioned, false);

        stateMachine.setTransitionCondition(State.ENTRY, State.EXIT, () -> true);
        stateMachine.update();

        assertEquals(true, hasTransitioned);
    }

    @Test
    void stateMachineLoopingBehaviour() {
        StateMachine<State> stateMachine = new StateMachine<State>(State.ENTRY);

        iteratingStuff = 0;

        stateMachine.addLoopedBehaviour(State.ENTRY, () -> iteratingStuff++);

        for (int i = 0; i < 5; i++) {
            stateMachine.update();
        }

        assertEquals(5, iteratingStuff);
    }

    @Test
    void stateMachineCleanupBehaviour() {
        StateMachine<State> stateMachine = new StateMachine<State>(State.ENTRY);

        iteratingStuff = 0;

        stateMachine.addLoopedBehaviour(State.ENTRY, () -> iteratingStuff++, () -> iteratingStuff = 69420);
        stateMachine.setTransitionCondition(State.ENTRY, State.EXIT, () -> iteratingStuff == 3);

        for (int i = 0; i < 5; i++) {
            stateMachine.update();
        }

        assertEquals(69420, iteratingStuff);
    }
}
