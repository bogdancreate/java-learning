package aufgabe6;
/**
 * Fächer für AIN 1.
 * @author Bohdab Cherednychenko
 * @version 18.01.2026
 */
public final class Faecher {

    private Faecher() { }
    /**
     * Alle zulässige Fächer.
     */
    private static final String[] FAECHER = {
        "Programmiertechnik 1",
        "Digitaltechnik",
        "Softwaremodellierung",
        "Algebra"
    };
    /**
     * Prüft, ob Fach zulässig ist.
     * @param x das Fach
     * @return true(falls zulässig) oder false
     */
    public static boolean istZulaessig(String x) {
        if (x == null) {
            return false;
        }
        for (int i = 0; i < FAECHER.length; i++) {
            if (FAECHER[i].equals(x)) {
                return true;
            }
        }
        return false;
    }
}