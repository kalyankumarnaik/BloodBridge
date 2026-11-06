package in.bloodbridge.statemachine;

import in.bloodbridge.enums.RequestStatus;
import in.bloodbridge.exception.ConflictException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RequestStateMachine {

    private static final Map<RequestStatus, Set<RequestStatus>> VALID_TRANSITIONS = new EnumMap<>(RequestStatus.class);

    static {
        VALID_TRANSITIONS.put(RequestStatus.PENDING, Set.of(
                RequestStatus.VERIFIED,
                RequestStatus.CANCELLED,
                RequestStatus.EXPIRED
        ));

        VALID_TRANSITIONS.put(RequestStatus.VERIFIED, Set.of(
                RequestStatus.ACTIVE,
                RequestStatus.CANCELLED,
                RequestStatus.EXPIRED
        ));

        VALID_TRANSITIONS.put(RequestStatus.ACTIVE, Set.of(
                RequestStatus.DONOR_RESPONDED,
                RequestStatus.FULFILLED,
                RequestStatus.CANCELLED,
                RequestStatus.EXPIRED
        ));

        VALID_TRANSITIONS.put(RequestStatus.DONOR_RESPONDED, Set.of(
                RequestStatus.FULFILLED,
                RequestStatus.CANCELLED,
                RequestStatus.EXPIRED
        ));

        // Terminal states cannot transition
        VALID_TRANSITIONS.put(RequestStatus.FULFILLED, Collections.emptySet());
        VALID_TRANSITIONS.put(RequestStatus.CANCELLED, Collections.emptySet());
        VALID_TRANSITIONS.put(RequestStatus.EXPIRED, Collections.emptySet());
    }

    public boolean canTransition(RequestStatus from, RequestStatus to) {
        if (from == null || to == null) return false;
        Set<RequestStatus> allowed = VALID_TRANSITIONS.getOrDefault(from, Collections.emptySet());
        return allowed.contains(to);
    }

    public void validateTransition(RequestStatus from, RequestStatus to) {
        if (!canTransition(from, to)) {
            throw new ConflictException(String.format("Invalid request state transition from %s to %s. Terminal states and backwards transitions are rejected.", from, to));
        }
    }

    public boolean isTerminal(RequestStatus status) {
        return status == RequestStatus.FULFILLED || status == RequestStatus.CANCELLED || status == RequestStatus.EXPIRED;
    }
}
