package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class StringConstant extends LiteralExp {
    private final String str;

    public StringConstant(String str) {
        this.str = str;
    }

    public String getString() {
        return this.str;
    }

    public String toString() {
        return getString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringConstant stringConstant = (StringConstant) o;
        return str == stringConstant.str;
    }

    @Override
    public int hashCode() {
        return Objects.hash(str);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
