package ast;

import visitor.DiscordBotVisitor;
import java.util.List;
import java.util.Objects;

public class Loop extends Statement {
    private final Exp condition;
    private final List<Statement> body;

    public Loop(Exp condition, List<Statement> body) {
        this.condition = condition;
        this.body = body;
    }

    public Exp getCondition() {
        return this.condition;
    }

    public List<Statement> getBody() {
        return this.body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Loop)) return false;
        Loop loop = (Loop) o;
        return Objects.equals(condition, loop.condition) &&
                Objects.equals(body, loop.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, body);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
