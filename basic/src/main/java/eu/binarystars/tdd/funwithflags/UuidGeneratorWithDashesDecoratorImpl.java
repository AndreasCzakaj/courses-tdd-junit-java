package eu.binarystars.tdd.funwithflags;

public class UuidGeneratorWithDashesDecoratorImpl implements UuidGenerator {
    private final UuidGenerator delegate;

    public UuidGeneratorWithDashesDecoratorImpl(final UuidGenerator delegate) {
        this.delegate = delegate;
    }

    @Override
    public String create() {
        StringBuilder strb = new StringBuilder(delegate.create());
        strb.insert(20, "-");
        strb.insert(16, "-");
        strb.insert(12, "-");
        strb.insert(8, "-");
        return strb.toString();
    }
}
