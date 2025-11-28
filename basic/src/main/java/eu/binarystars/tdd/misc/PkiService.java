package eu.binarystars.tdd.misc;

import java.util.Date;

public class PkiService {

    String sign(String csr, Date validFrom, Date validUntil, String hostname) {
        return sign(validUntil, validFrom, csr, hostname);
    }

    String sign(Date validUntil, Date validFrom, String csr, String hostname) {
        return "base64";
    }
}
