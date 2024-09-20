package giorgiaipsarop.GestioneEventi.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException (String message) {
        super(message);

    }

    public NotFoundException(UUID id) {
        super("User identified as " + id + " was not found.");
    }
}