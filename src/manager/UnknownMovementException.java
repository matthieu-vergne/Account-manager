package manager;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class UnknownMovementException extends RuntimeException {

    public UnknownMovementException(BigDecimal id) {
        super("the movement " + id + " does not exists in this manager");
    }
}
