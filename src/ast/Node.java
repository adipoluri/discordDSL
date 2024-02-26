package ast;

import visitor.DiscordBotVisitor;

public abstract class Node {

    public abstract boolean equals(Object o);

    public abstract int hashCode();

    abstract public <T, U> U accept(DiscordBotVisitor<T, U> v, T t);

}
