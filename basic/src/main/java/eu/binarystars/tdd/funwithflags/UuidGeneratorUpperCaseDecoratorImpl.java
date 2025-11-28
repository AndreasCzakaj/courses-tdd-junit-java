package eu.binarystars.tdd.funwithflags;

public class UuidGeneratorUpperCaseDecoratorImpl implements UuidGenerator {
    private final UuidGenerator delegate;

    public UuidGeneratorUpperCaseDecoratorImpl(final UuidGenerator delegate) {
        this.delegate = delegate;
    }

    @Override
    public String create() {
        return delegate.create().toUpperCase();
    }
}
