package eu.binarystars.tdd.fibonacci;

public class FibonacciLoopImpl extends FibonacciImplBase implements Fibonacci {

    @Override
    int calculateInternal(final int index) {
        int previousPreviousIndex = 0;
        int previousIndex = 1;
        int result = 0;
        for (int i = 2; i <= index; i++) {
            result = previousIndex + previousPreviousIndex;
            previousPreviousIndex = previousIndex;
            previousIndex = result;
        }
        return result;
    }
}
