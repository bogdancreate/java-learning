package aufgabe3;

/*
 * class Evaluator
 * repl-Schleife: lese von der Konsole eine Zeile und
 * werte sie als arithmetischen Ausdruck aus.
 * Da tokenizer String-Konstante zurÃ¼ckliefert, reicht
 * GleichheitsprÃ¼fung mit == aus (siehe z.B. shift-Methode).
 *
 */


import java.util.Scanner;
import static aufgabe3.Tokenizer.*;

/**
 * Klasse zum Auswerten von arithmetischen AusdrÃ¼cken.
 *
 * Aufgabe3; 18.7.2024
 */
public class Evaluator {

    private static final String ANSI_BLUE = "\u001B[34m";
    private static Object[] stack = new Object[10];		// Stack
    private static int top = -1;					// Index des obersten Kellerelements
    private static Object token;					// Aktuelles Token
    private static Tokenizer tokenizer;				// Zerlegt String-Eingabe in Tokens

    /**
     * Wertet expr als arithmetischen Ausdruck aus.
     *
     * @param expr Arthmetischer Ausdruck als String
     * @return Wert des Ausdrucks oder null, falls der Ausdruck fehlerhaft ist.
     */
    public static Double eval(String expr) {
        // Dollar in leeren Stack ablegen:
        top = -1;
        stack[++top] = DOLLAR;

        // expr in Tokens zerlegen und erstes Token abholen:
        tokenizer = new Tokenizer(expr);
        token = tokenizer.nextToken();

        while (token != null) {
            //System.out.println("token = " + token);
            if (accept())   // prÃ¼fen ob Ausdruck erfolgreich evaluiert wurde
                return (Double) stack[top];
            if (shift())    // Shift durchfÃ¼hren, falls mÃ¶glich
                continue;
            if (reduce())   // Reduce durchfÃ¼hren, falls mÃ¶glich
                continue;
            return null;    // Fehler beim Auswerten
        }
        return null;    // Fehler beim Auswerten
    }

    private static boolean shift() {
        if (top == -1) return false;

        if ((stack[top] == DOLLAR || stack[top] == KL_AUF || isOp(stack[top]))
                && (isVal(token) || token == KL_AUF)) {
            doShift();
            return true;
        }

        if (isVal(stack[top]) && isOp(token)) {
            if (stack[top-1] == DOLLAR || stack[top-1] == KL_AUF) {
                doShift();
                return true;
            }
            Object opInStack = stack[top-1];
            if (opInStack == PLUS && (token == MULT || token == POWER)) {
                doShift(); return true;
            }
            if (opInStack == MULT && token == POWER) {
                doShift(); return true;
            }
            if (opInStack == POWER && token == POWER) {
                doShift(); return true;
            }
            return false;
        }

        if (stack[top-1] == KL_AUF && isVal(stack[top]) && token == KL_ZU) {
            doShift();
            return true;
        }

        return false;
    }

    private static void doShift() {
        // Ihr Code:
        if (top == stack.length - 1) {
            Object[] newStack = new Object[stack.length * 2];

            for (int i = 0; i < stack.length; i++) {
                newStack[i] = stack[i];
            }
            stack = newStack;
        }

        stack[++top] = token;
        token = tokenizer.nextToken();
    }

    private static boolean isOp(Object o) {
        return (o == PLUS || o == MULT || o == POWER);
    }

    private static boolean isVal(Object o) {
        return o instanceof Double;
    }

    private static boolean reduce() {
        // Ihr Code:
        if (top >= 2 && stack[top] == KL_ZU && isVal(stack[top - 1]) && stack[top - 2] == KL_AUF) {
            doReduceKlValKl();
            return true;
        }
        if (top >= 2 && isVal(stack[top]) && isOp(stack[top-1]) && isVal(stack[top-2])) {
            Object op = stack[top - 1];

            if (token == POWER) return false;
            if (op == PLUS && token == MULT) return false;

            doReduceValOpVal();
            return true;
        }
        return false;
    }

    private static void doReduceKlValKl() {
        // Ihr Code:
        Double val = (Double) stack[top - 1];
        top -= 2;
        stack[top] = val;
    }

    private static void doReduceValOpVal() {
        // Ihr Code:
        Double right = (Double) stack[top];
        Object op = stack[top - 1];
        Double left = (Double) stack[top - 2];

        Double result;

        if (op == PLUS) {
           result = left + right;
        } else if (op == MULT) {
            result = left * right;
        } else {//POWER
        result = Math.pow(left, right);
        }

        top -= 2;
        stack[top] = result;
    }

    private static boolean accept() {
        // Ihr Code:
        if (top == 1 && stack[0] == DOLLAR && isVal(stack[1]) && token == DOLLAR) {
            return true;
        }
        return false;
    }

    /**
     * Liest von der Konsole eine Folge von Zeilen, wertet jede Zeile als
     * Ausdruck aus und gibt seinen Wert aus. (repl = read-evaluate-print-loop).
     */
    public static void repl() {
        Scanner in = new Scanner(System.in);
        System.out.print(ANSI_BLUE + ">> ");

        while (in.hasNextLine()) {
            String line = in.nextLine();
            // Ihr Code:
            if (line.equals("end")) {
                System.out.println("bye!");
                break;
            }

            Double result = eval(line);

            if (result == null) {
                System.out.println("!!! error");
            } else {
                System.out.println(result);
            }
            System.out.print(ANSI_BLUE + ">> ");
        }
    }

    /**
     * Testprogramm.
     *
     * @param args wird nicht benutzt.
     */
    public static void main(String[] args) {
        // Zum Testen, spÃ¤ter auskommentieren:
        String s1 = "1+2";
        String s2 = "2^10+5";
        String s3 = "5+2^10";
        String s4 = "(2+3*4+4)^2";
        String s5 = "(2+3*4+4))*2";
        String s6 = "2+3**4";
        String s7 = "2^3^2";
        String s8 = "2^2*5";
        String s9 = "1+(2+(3+(4+(5+6))))";
        String s10 = "1+2+3+4+5+6";

        System.out.println(eval(s1));	// 3.0
        System.out.println(eval(s2));	// 1029.0
        System.out.println(eval(s3));	// 1029.0
        System.out.println(eval(s4));	// 324.0
        System.out.println(eval(s5));	// null; Syntaxfehler
        System.out.println(eval(s6));	// null; Syntaxfehler
        System.out.println(eval(s7));	// 512.0
        System.out.println(eval(s8));	// 20.0
        System.out.println(eval(s9));	// 21.0
        System.out.println(eval(s10));	// 21.0

        repl();
    }
}