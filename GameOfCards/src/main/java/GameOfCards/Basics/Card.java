package GameOfCards.Basics;

import java.io.Serializable;

/*
 * Representation of a single playing card. A card consists of a suit value (e.g. hearts, spades), a
 * rank value (e.g. ace, 7, king), A card object is immutable; once instantiated, the values cannot
 * change.
 */
public class Card implements Comparable, Serializable {
    private final static long serialVersionUID = 1;
    // instance variables for the card
    private Suit suitValue;
    private Rank rankValue;
    private static boolean sortRankMajorOrder = true;

    /**
     * Creates a new playing card.
     * 
     * @param suit     the suit value of this card.
     * @param rank     the rank value of this card.
     * @param cardFace the face image of this card.
     */
    public Card(Suit suit, Rank rank) {
        suitValue = suit;
        rankValue = rank;
    }

    /**
     * Returns the suit of the card.
     * 
     * @return a Suit constant representing the suit value of the card.
     */
    public Suit getSuit() {
        return suitValue;
    }

    /**
     * Returns the rank of the card.
     * 
     * @return a Rank constant representing the rank value of the card.
     */
    public Rank getRank() {
        return rankValue;
    }

    /**
     * Returns a description of this card.
     * 
     * @return the name of the card.
     */
    public String toString() {
        return rankValue.toString() + " of " + suitValue.toString();
    }

    /**
     * Returns a description of the rank of this card.
     * 
     * @return the rank value of the card as a string.
     */
    public String rankToString() {
        return rankValue.toString();
    }

    /**
     * Returns a description of the suit of this card.
     * 
     * @return the suit value of the card as a string.
     */
    public String suitToString() {
        return suitValue.toString();
    }


    /**
     * Specifies that cards are to be sorted in rank-major order. Cards are ordered first by their
     * rank value; cards of the same rank are then ordered by their suit value.
     */
    public static void setRankMajorSort() {
        sortRankMajorOrder = true;
    }


    /**
     * Specifies that cards are to be sorted in suit-major order. Cards are ordered first by their
     * suit value; cards of the same suit are then ordered by their rank value.
     */
    public static void setSuitMajorSort() {
        sortRankMajorOrder = false;
    }

    /**
     * Compares two cards for the purposes of sorting. Cards are ordered first by their suit value,
     * then by their rank value.
     * 
     * @param otherCardObject the other card
     * @return a negative integer, zero, or a positive integer is this card is less than, equal to,
     *         or greater than the referenced card.
     */
    public int compareTo(Object otherCardObject) {
        Card otherCard = (Card) otherCardObject;
        int suitDiff = suitValue.compareTo(otherCard.suitValue);
        int rankDiff = rankValue.compareTo(otherCard.rankValue);

        if (sortRankMajorOrder) {
            if (rankDiff != 0)
                return rankDiff;
            else
                return suitDiff;
        } else {
            if (suitDiff != 0)
                return suitDiff;
            else
                return rankDiff;
        }
    }


    /**
     * Compares two cards to determine if they have the same value. This is not the same as the use
     * of equals which compares two objects for equality.
     * 
     * @param card the other card
     * @return true if the two cards have the same rank and suit values, false if they do not.
     */
    public boolean isSameAs(Card card) {
        if ((rankValue != card.rankValue) || (suitValue != card.suitValue))
            return false;
        else
            return true;
    }
}

