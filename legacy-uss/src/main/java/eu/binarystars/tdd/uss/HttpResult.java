package eu.binarystars.tdd.uss;

/**
 * What a controller returns, independent of the web server.
 */
public record HttpResult(int status, Object body) {
}
