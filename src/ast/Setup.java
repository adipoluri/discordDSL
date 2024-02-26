package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;
import java.util.List;

public class Setup extends Statement {
    private final List<Statement> body;

    public Setup(List<Statement> body) {
        this.body = body;
    }

    public List<Statement> getBody() {
        return this.body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setup setup = (Setup) o;
        return Objects.equals(body, setup.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }

}
