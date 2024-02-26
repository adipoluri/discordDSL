package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;
import java.util.List;

public class Command extends Statement {
    private final Name name;
    private final List<Statement> body;

    public Command(Name name, List<Statement> body) {
        this.name = name;
        this.body = body;
    }

    public Name getName() {
        return this.name;
    }

    public List<Statement> getBody() {
        return this.body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name) &&
                Objects.equals(body, command.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, body);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }

}
