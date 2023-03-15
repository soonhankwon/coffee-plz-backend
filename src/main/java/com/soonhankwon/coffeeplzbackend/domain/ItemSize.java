package com.soonhankwon.coffeeplzbackend.domain;

public enum ItemSize {
    S(0), M(500), L(1000);
    public final int additionalFee;

    ItemSize(int additionalFee) {
        this.additionalFee = additionalFee;
    }
}
