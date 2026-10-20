package aufgabe1;

public class RedCard extends Card {
    public RedCard(Suit suit, Rank rank) {
        super(suit, rank);
        if (suit != Suit.KARO && suit != Suit.HERZ) {
            throw new IllegalArgumentException();
        }
    }

    public RedCard() {
        super(Math.random() > 0.5 ? Suit.HERZ : Suit.KARO,
                Rank.values()[(int)(Math.random() * Rank.values().length)]);
    }
}
