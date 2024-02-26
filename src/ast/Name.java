package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class Name extends Exp {
    private final String name;

    public Name(String n){
        name = n;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
