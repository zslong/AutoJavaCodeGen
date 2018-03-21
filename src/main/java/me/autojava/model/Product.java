package me.autojava.model;

/**
 * Created by shilong.zhang on 2018/2/2.
 */
public enum Product {
    IRS(1), FX(2), NCD(4), BOND(3), MM(5);

    private int ordinal;

    Product(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return this.ordinal;
    }
}
