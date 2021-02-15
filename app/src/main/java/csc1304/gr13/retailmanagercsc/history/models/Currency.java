package csc1304.gr13.retailmanagercsc.history.models;
/**
 * Created by CS1304 on 8/02/2021.
 */
public class Currency {
    public String symbol;
    public boolean left;
    public boolean space;

    public Currency() {

    }

    public Currency(String symbol, boolean left, boolean space) {
        this.symbol = symbol;
        this.left=left;
        this.space=space;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isSpace() {
        return space;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }
}
