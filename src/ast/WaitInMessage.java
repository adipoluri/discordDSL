package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class WaitInMessage extends Exp {
    private final Exp in;

    public WaitInMessage(Exp in) {
        this.in = in;
    }

    public Exp getIn() {
        return this.in;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WaitInMessage)) return false;
        WaitInMessage that = (WaitInMessage) o;
        return Objects.equals(in, that.in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(in);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
