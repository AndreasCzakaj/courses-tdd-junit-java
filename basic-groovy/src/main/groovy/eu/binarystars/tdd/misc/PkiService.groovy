package eu.binarystars.tdd.misc

import java.util.Date

class PkiService {

    String sign(String csr, Date validFrom, Date validUntil, String hostname) {
        sign(validUntil, validFrom, csr, hostname)
    }

    String sign(Date validUntil, Date validFrom, String csr, String hostname) {
        "base64"
    }
}
