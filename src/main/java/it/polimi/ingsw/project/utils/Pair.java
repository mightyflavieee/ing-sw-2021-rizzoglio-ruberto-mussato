package it.polimi.ingsw.project.utils;

import java.util.Objects;

public class Pair<A, B> {
    public final A _1;
    public final B _2;

    public Pair(A _1, B _2) {
        this._1 = _1;
        this._2 = _2;
    }

    @Override
    public String toString() {
        return "(" +
                (_1 != null ? _1.toString() : "null") +
                ", " +
                (_2 != null ? _2.toString() : "null") +
                ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(_1, pair._1) &&
                Objects.equals(_2, pair._2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2);
    }
}
