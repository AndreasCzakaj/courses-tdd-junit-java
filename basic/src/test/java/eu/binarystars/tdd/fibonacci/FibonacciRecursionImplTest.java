package eu.binarystars.tdd.fibonacci;

public class FibonacciRecursionImplTest extends FibonacciTestBase {
    Fibonacci factory() {
        return new FibonacciRecursionImpl();
    }
}
