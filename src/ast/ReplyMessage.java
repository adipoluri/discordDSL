package ast;

import visitor.DiscordBotVisitor;
import java.util.Objects;

public class ReplyMessage extends Statement {
    private final Exp replyContent;
    private final Exp originalMessage;

    public ReplyMessage(Exp replyContent, Exp originalMessage) {
        this.replyContent = replyContent;
        this.originalMessage = originalMessage;
    }

    public Exp getReplyContent() {
        return this.replyContent;
    }

    public Exp getOriginalMessage() {
        return this.originalMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReplyMessage)) return false;
        ReplyMessage that = (ReplyMessage) o;
        return Objects.equals(replyContent, that.replyContent) &&
                Objects.equals(originalMessage, that.originalMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replyContent, originalMessage);
    }

    @Override
    public <T, U> U accept(DiscordBotVisitor<T, U> v, T t) {
        return v.visit(this, t);
    }
}

