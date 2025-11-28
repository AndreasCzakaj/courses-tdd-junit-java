package eu.binarystars.tdd.fibonacci;

public class FibonacciRecursionImpl extends FibonacciImplBase implements Fibonacci {

    @Override
    int calculateInternal(final int index) {
        return calculate(index - 2) + calculate(index - 1);
    }
}
