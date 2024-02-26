package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class GiveUserRole extends Statement {
    private final Exp user;
    private final Exp role;

    public GiveUserRole(Exp user, Exp role) {
        this.user = user;
        this.role = role;
    }

    public Exp getUser() {
        return this.user;
    }

    public Exp getRole() { return this.role; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiveUserRole)) return false;
        GiveUserRole that = (GiveUserRole) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
