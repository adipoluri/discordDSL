package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class SendMessage extends Statement {
    private final Exp message;
    private final Exp target;

    public SendMessage(Exp message, Exp target) {
        this.message = message;
        this.target = target;
    }

    public Exp getMessage() {
        return this.message;
    }

    public Exp getTarget() {
        return this.target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SendMessage)) return false;
        SendMessage that = (SendMessage) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, target);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
