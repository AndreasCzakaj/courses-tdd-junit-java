package eu.binarystars.tdd.fibonacci;

abstract class FibonacciImplBase {
    public final Integer calculate(final Integer index) {
        check(index);

        if (index < 2) {
            return index;
        }

        return calculateInternal(index);
    }

    abstract int calculateInternal(final int index);

    void check(final Integer index) {
        if (index == null) {
            throw new IllegalArgumentException("index must not be null");
        }
        if (index < 0) {
            throw new IllegalArgumentException("index must not be negative");
        }
        if (index > 46) {
            throw new IllegalArgumentException("index must not be > 46");
        }
    }
}
