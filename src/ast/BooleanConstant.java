package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class BooleanConstant extends LiteralExp {
    private final boolean bool;

    public BooleanConstant(boolean bool) {
        this.bool = bool;
    }

    public boolean getBool() {
        return this.bool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanConstant booleanConstant = (BooleanConstant) o;
        return bool == booleanConstant.bool;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bool);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
