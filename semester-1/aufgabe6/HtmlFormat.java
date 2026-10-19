package aufgabe6;

import java.io.PrintWriter;

/**
 * Utility-Klasse zur Ausgabe von Notenspiegeln im HTML-Format.
 * @author Bohdan Cherednychenko
 * @version 20.01.2026
 */
public final class HtmlFormat {

    /**
     * Privater Konstruktor verhindert die Instanziierung.
     */
    private HtmlFormat() { }

    /**
     * Schreibt den HTML-Code fuer den Notenspiegel in den PrintWriter.
     * @param ausgabe der PrintWriter, in den geschrieben wird
     * @param name Array mit den Namensbestandteilen (Vorname, Nachname)
     * @param leistungen die Liste der zu druckenden Leistungen
     */
    public static void ausgeben(PrintWriter ausgabe, String[] name,
                                 LeistungsListe leistungen) {
        ausgabe.println("<!DOCTYPE html>");
        ausgabe.println("<html lang=\"de\">");
        ausgabe.println("<head>");
        ausgabe.println("<meta charset=\"utf-8\">");
        ausgabe.println("<meta name=\"viewport\""
            + "content=\"width=device-width, initial-scale=1.0\">");
        ausgabe.println("<style>");
        ausgabe.println("table { width:100%; }");
        ausgabe.println("th { text-align:left; }");
        ausgabe.println("</style>");
        ausgabe.println("<title>Notenspiegel</title>");
        ausgabe.println("</head>");

        ausgabe.println("<body>");


        ausgabe.println("<h1>Notenspiegel f&uuml;r");
        for (String n : name) {
            ausgabe.println(n);
        }
        ausgabe.println("</h1>");

        ausgabe.println("<hr>");

        ausgabe.println("<table>");
        ausgabe.println("<tr><th>Fach:</th><th>Art:</th><th>Note:</th></tr>");

        for (Leistung l : leistungen) {

            String art = l.istBenotet() ? "L" : "S";

            String style = "";
            if (!l.istBestanden()) {
                style = " style=\"color:red\"";
            }

            String noteText = l.getNoteInWorten();
            if (l.istBenotet()) {
                noteText += " (" + l.getNote() + ")";
            }

            ausgabe.printf("<tr><td%s>%s</td><td>%s</td><td>%s</td></tr>%n",
                           style,
                           l.getFach(),
                           art,
                           noteText);
        }

        ausgabe.println("</table>");
        ausgabe.println("<hr>");
        ausgabe.println("L = Leistungsnachweis, S = Schein");
        ausgabe.println("</body>");
        ausgabe.println("</html>");
    }
}