package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class KickUser extends Statement {
    private final Exp user;

    public KickUser(Exp user) {
        this.user = user;
    }

    public Exp getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KickUser)) return false;
        KickUser that = (KickUser) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
