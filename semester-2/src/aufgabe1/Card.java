package aufgabe1;
import java.util.Objects;
public abstract class Card {
    public enum Suit {KARO, HERZ, PIQUE, KREUZ}
    public enum Rank {SIEBEN, ACHT, NEUN, ZEHN, BUBE, DAME, KOENIG, ASS}

    protected final Suit suit;
    protected final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {return suit;}
    public Rank getRank() {return rank;}

    @Override
    public String toString() {
        return suit + " " + rank;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
}