package eu.binarystars.tdd.funwithflags;

import java.security.SecureRandom;
import java.util.Random;

public class UuidGeneratorNaiveRandomImpl implements UuidGenerator {
    private static final Random RANDOM = new SecureRandom();

    @Override
    public String create() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append(createOne());
        }
        return sb.toString();
    }

    String createOne() {
        return Integer.toHexString(RANDOM.nextInt(15));
    }
}
