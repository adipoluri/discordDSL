package ast;

import visitor.DiscordBotVisitor;
import java.util.List;
import java.util.Objects;

public class Program extends Node {
    private final List<Statement> statements;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return Objects.equals(statements, program.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }

    public Program(List<Statement> ss) {
        statements = ss;
    }

    public List<Statement> getStatements() {
        return this.statements;
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
