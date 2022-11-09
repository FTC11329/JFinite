package jfinite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EventEmitterTest {

    Boolean hasTransitioned;

    @Test
    void eventEmitterTransistions() {
        EventEmitter<State> eventEmitter = new EventEmitter<>(State.ENTRY);

        eventEmitter.setTransition(State.ENTRY, State.EXIT, () -> false);

        eventEmitter.checkTransition();
        assertEquals(eventEmitter.getState(), State.ENTRY);

        eventEmitter.setTransition(State.ENTRY, State.EXIT, () -> true);

        eventEmitter.checkTransition();
        assertEquals(eventEmitter.getState(), State.EXIT);
    }

    @Test
    void eventEmittterModifiesValue() {
        EventEmitter<State> eventEmitter = new EventEmitter<>(State.ENTRY);
        hasTransitioned = false;

        eventEmitter.register(
                State.EXIT,
                () -> {
                    hasTransitioned = true;
                });

        assertEquals(hasTransitioned, false);

        eventEmitter.setTransition(State.ENTRY, State.EXIT, () -> true);
        eventEmitter.checkTransition();

        assertEquals(hasTransitioned, true);
    }
}
