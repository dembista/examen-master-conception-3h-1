package exam;

final class TestSupport {
    private TestSupport() {
    }

    static void assertEquals(long expected, long actual, String message) {
        if (expected != actual) throw new AssertionError(message + " attendu=" + expected + " obtenu=" + actual);
    }

    static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) throw new AssertionError(message + " attendu=" + expected + " obtenu=" + actual);
    }

    static void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    static void assertThrows(Class<? extends Throwable> type, Runnable action, String message) {
        try {
            action.run();
        } catch (Throwable error) {
            if (type.isInstance(error)) return;
            throw new AssertionError(message + " type obtenu=" + error.getClass().getName(), error);
        }
        throw new AssertionError(message + " aucune exception");
    }
}
