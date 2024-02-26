package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class GetMessage extends Statement {
    private final Exp from;

    public GetMessage(Exp from) {
        this.from = from;
    }

    public Exp getFrom() {
        return this.from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetMessage)) return false;
        GetMessage that = (GetMessage) o;
        return Objects.equals(from, that.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
