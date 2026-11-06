package in.bloodbridge.statemachine;

import in.bloodbridge.enums.RequestStatus;
import in.bloodbridge.exception.ConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestStateMachineTest {

    private RequestStateMachine stateMachine;

    @BeforeEach
    void setUp() {
        stateMachine = new RequestStateMachine();
    }

    @Test
    @DisplayName("Valid forward lifecycle transitions succeed")
    void testValidLifecycle() {
        // PENDING -> VERIFIED
        assertTrue(stateMachine.canTransition(RequestStatus.PENDING, RequestStatus.VERIFIED));
        assertDoesNotThrow(() -> stateMachine.validateTransition(RequestStatus.PENDING, RequestStatus.VERIFIED));

        // VERIFIED -> ACTIVE
        assertTrue(stateMachine.canTransition(RequestStatus.VERIFIED, RequestStatus.ACTIVE));
        assertDoesNotThrow(() -> stateMachine.validateTransition(RequestStatus.VERIFIED, RequestStatus.ACTIVE));

        // ACTIVE -> DONOR_RESPONDED
        assertTrue(stateMachine.canTransition(RequestStatus.ACTIVE, RequestStatus.DONOR_RESPONDED));
        assertDoesNotThrow(() -> stateMachine.validateTransition(RequestStatus.ACTIVE, RequestStatus.DONOR_RESPONDED));

        // DONOR_RESPONDED -> FULFILLED
        assertTrue(stateMachine.canTransition(RequestStatus.DONOR_RESPONDED, RequestStatus.FULFILLED));
        assertDoesNotThrow(() -> stateMachine.validateTransition(RequestStatus.DONOR_RESPONDED, RequestStatus.FULFILLED));
    }

    @Test
    @DisplayName("Cancellation and Expiration allowed from non-terminal states")
    void testCancellationFromNonTerminal() {
        assertTrue(stateMachine.canTransition(RequestStatus.PENDING, RequestStatus.CANCELLED));
        assertTrue(stateMachine.canTransition(RequestStatus.VERIFIED, RequestStatus.CANCELLED));
        assertTrue(stateMachine.canTransition(RequestStatus.ACTIVE, RequestStatus.CANCELLED));
        assertTrue(stateMachine.canTransition(RequestStatus.DONOR_RESPONDED, RequestStatus.CANCELLED));

        assertTrue(stateMachine.canTransition(RequestStatus.PENDING, RequestStatus.EXPIRED));
        assertTrue(stateMachine.canTransition(RequestStatus.ACTIVE, RequestStatus.EXPIRED));
    }

    @Test
    @DisplayName("Transitions from terminal states are rejected with ConflictException")
    void testTerminalStatesCannotTransition() {
        assertFalse(stateMachine.canTransition(RequestStatus.FULFILLED, RequestStatus.ACTIVE));
        assertThrows(ConflictException.class, () -> stateMachine.validateTransition(RequestStatus.FULFILLED, RequestStatus.ACTIVE));

        assertFalse(stateMachine.canTransition(RequestStatus.CANCELLED, RequestStatus.VERIFIED));
        assertThrows(ConflictException.class, () -> stateMachine.validateTransition(RequestStatus.CANCELLED, RequestStatus.VERIFIED));
    }

    @Test
    @DisplayName("Illegal backward transitions throw ConflictException")
    void testIllegalBackwardsTransitions() {
        assertFalse(stateMachine.canTransition(RequestStatus.ACTIVE, RequestStatus.PENDING));
        assertThrows(ConflictException.class, () -> stateMachine.validateTransition(RequestStatus.ACTIVE, RequestStatus.PENDING));

        assertFalse(stateMachine.canTransition(RequestStatus.DONOR_RESPONDED, RequestStatus.VERIFIED));
        assertThrows(ConflictException.class, () -> stateMachine.validateTransition(RequestStatus.DONOR_RESPONDED, RequestStatus.VERIFIED));
    }
}
