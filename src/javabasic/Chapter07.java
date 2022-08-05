package javabasic;

public class Chapter07 {
    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck.pick());
        System.out.println(deck.pick());

        System.out.println(deck.pick(0));
        deck.shuffle();
        System.out.println(deck.pick(0));
    }

}

class Deck {
    final int CARD_NUM = Card.KIND_MAX * Card.NUM_MAX;
    Card[] cards = new Card[CARD_NUM];

    Deck () {
        for (int k=0; k<Card.KIND_MAX; k++) {
            for (int n=0; n<Card.NUM_MAX; n++) {
                cards[k*Card.NUM_MAX+n] = new Card(k, n);
            }
        }
    }

    public Card pick() {
        int pos = (int) (Math.random() * this.cards.length);
        return cards[pos];
    }

    public Card pick(int pos) {
        return cards[pos];
    }

    public void shuffle() {
        for (int i=0; i<cards.length; i++) {
            int j = (int) (Math.random() * cards.length);
            Card tmp = cards[i];
            cards[i] = cards[j];
            cards[j] = tmp;
        }
    }
}


class Card {
    static final int KIND_MAX = 4;
    static final int NUM_MAX = 13;

    static final String[] kinds = {"SPADE", "DIAMOND", "HEART", "CLOVER"};
    static final String numbers = "123456789XJQK";

    int kind;
    int number;

    Card(int kind, int number) {
        this.kind = kind;
        this.number = number;
    }

    @Override
    public String toString() {
        return kinds[this.kind] + " " + numbers.charAt(this.number);
    }
}
