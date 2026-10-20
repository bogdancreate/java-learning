package aufgabe1;

public class BlackCard extends Card {
    public BlackCard(Suit suit, Rank rank) {
        super(suit, rank);
        if (suit != Suit.PIQUE && suit != Suit.KREUZ) {
            throw new IllegalArgumentException();
        }
    }

    public BlackCard() {
        super(Math.random() > 0.5 ? Suit.PIQUE : Suit.KREUZ,
                Rank.values()[(int)(Math.random() * Rank.values().length)]);
    }
}