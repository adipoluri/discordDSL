package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class NumberConstant extends LiteralExp {
    private final int value;

    public NumberConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberConstant numberConstant = (NumberConstant) o;
        return value == numberConstant.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
