package eu.binarystars.tdd.funwithflags

import java.security.SecureRandom

class UuidGeneratorNaiveRandomImpl implements UuidGenerator {
    private static final Random RANDOM = new SecureRandom()

    @Override
    String create() {
        def sb = new StringBuilder()
        32.times { sb.append(createOne()) }
        return sb.toString()
    }

    String createOne() {
        Integer.toHexString(RANDOM.nextInt(16))
    }
}
