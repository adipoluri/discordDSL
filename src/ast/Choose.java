package ast;

import visitor.DiscordBotVisitor;
import java.util.List;
import java.util.Random;
import java.util.Objects;
public class Choose extends Exp{
    private final List<Exp> choices;
    private static final Random random = new Random();

    public Choose(List<Exp> choices) {
        this.choices = choices;
    }

    public List<Exp> getChoices() {
        return this.choices;
    }

    public Exp chooseRandomly() {
        return choices.get(random.nextInt(choices.size()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choose choose = (Choose) o;
        return Objects.equals(choices, choose.choices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(choices);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }

}
