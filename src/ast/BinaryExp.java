package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class BinaryExp extends Exp {
    private final Exp left;
    private final Exp right;
    private final String operator;

    public BinaryExp(Exp left, Exp right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public Exp getLeft() {
        return this.left;
    }

    public Exp getRight() {
        return this.right;
    }

    public String getOperator() {
        return this.operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryExp binaryExp = (BinaryExp) o;
        return Objects.equals(left, binaryExp.left) &&
                Objects.equals(right, binaryExp.right) &&
                Objects.equals(operator, binaryExp.operator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, operator);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}
