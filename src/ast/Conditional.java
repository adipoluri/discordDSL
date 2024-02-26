package ast;

import visitor.DiscordBotVisitor;
import java.util.List;
import java.util.Objects;

public class Conditional extends Statement {
    private final Exp condition;
    private final List<Statement> ifBody;
    private final List<Statement> elseBody;

    public Conditional(Exp condition, List<Statement> ifBody, List<Statement> elseBody) {
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    public Exp getCondition() {
        return this.condition;
    }

    public List<Statement> getIfBody() {
        return this.ifBody;
    }

    public List<Statement> getElseBody() {
        return this.elseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conditional that = (Conditional) o;
        return Objects.equals(condition, that.condition) &&
                Objects.equals(ifBody, that.ifBody) &&
                Objects.equals(elseBody, that.elseBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, ifBody, elseBody);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
