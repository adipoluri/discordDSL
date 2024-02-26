package ast;

import visitor.DiscordBotVisitor;
import java.util.List;
import java.util.Objects;

public class ArrayExp extends Exp {
    private final List<Exp> elements;

    public ArrayExp(List<Exp> elements) {
        this.elements = elements;
    }

    public List<Exp> getElements() {
        return this.elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayExp)) return false;
        ArrayExp arrayExp = (ArrayExp) o;
        return Objects.equals(elements, arrayExp.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
