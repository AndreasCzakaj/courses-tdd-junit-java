package eu.binarystars.tdd.fibonacci

class Fibonacci {

    int calc(Integer index) {
        if (index == null || index < 0 || index > 46) {
            throw new IllegalArgumentException("Index must be between 0 and 46, got: $index")
        }
        if (index <= 1) return index
        int a = 0, b = 1
        (2..index).each {
            int tmp = a + b
            a = b
            b = tmp
        }
        return b
    }
}
