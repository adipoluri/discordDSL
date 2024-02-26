package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class Set extends Statement {

    private final Name to_set;

    private final Exp set_value;

    public Set(Name name, Exp exp){
        to_set = name;
        set_value = exp;
    }

    public Name getTo_Set() {
        return this.to_set;
    }

    public Exp getSet_Value() {
        return this.set_value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Set set = (Set) o;
        return Objects.equals(to_set, set.to_set) && Objects.equals(set_value, set.set_value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to_set, set_value);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
