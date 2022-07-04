package cz.majzlik.assignment.reservations.model;

/**
 * Enum that represents tennis court surfaces with its specific price for one minute.
 *
 * @author Adam Majzlik
 */
public enum Surface {

    CLAY(3),
    HARD(4),
    GRASS(8);

    private final int price;

    private Surface(int price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }
}
