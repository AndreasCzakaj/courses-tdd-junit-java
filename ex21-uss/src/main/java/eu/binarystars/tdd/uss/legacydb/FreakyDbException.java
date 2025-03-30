package eu.binarystars.tdd.uss.legacydb;

public class FreakyDbException extends Exception {
    public FreakyDbException(Exception e) {
        super(e);
    }
    public FreakyDbException(String message) {
        super(message);
    }
    public FreakyDbException() {
        super();
    }
}
