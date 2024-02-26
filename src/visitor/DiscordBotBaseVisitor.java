package visitor;

import ast.*;

public class DiscordBotBaseVisitor<T, U> implements DiscordBotVisitor<T, U> {
    @Override
    public U visit(Name name, T t) {
//        System.out.println("Name = " + name.getName());
        return null;
    }

    @Override
    public U visit(NumberConstant num, T t) {
//        System.out.println("Value = " + num.getValue());
        return null;
    }

    @Override
    public U visit(BooleanConstant bool, T t) {
//        System.out.println("Bool = " + bool.getBool());
        return null;
    }

    @Override
    public U visit(StringConstant string, T t) {
//        System.out.println("String = " + string.getString());
        return null;
    }

    @Override
    public U visit(BinaryExp binExp, T t) {
//        System.out.println("Binary:");
        binExp.getLeft().accept(this, t);
        binExp.getRight().accept(this, t);
//        System.out.println(binExp.getOperator());
        return null;
    }

    @Override
    public U visit(ArrayExp arrayExp, T t) {
//        System.out.println("Array:");
        for (Exp exp : arrayExp.getElements()) {
            exp.accept(this, t);
        }
        return null;
    }

    @Override
    public U visit(Set set, T t) {
//        System.out.println("Set:");
        set.getTo_Set().accept(this, t);
        set.getSet_Value().accept(this, t);
        return null;
    }

    @Override
    public U visit(Command command, T t) {
//        System.out.println("Command:");
        command.getName().accept(this,t);
        for (Statement statement : command.getBody()) {
            statement.accept(this, t);
        }
        return null;
    }

    @Override
    public U visit(Routine routine, T t) {
//        System.out.println("Routine:");
//        System.out.println(routine.getName());
        for (Statement statement : routine.getBody()) {
            statement.accept(this, t);
        }
        return null;
    }

    @Override
    public U visit(SendMessage sendMessage, T t) {
//        System.out.println("Send Message:");
        sendMessage.getMessage().accept(this, t);
        sendMessage.getTarget().accept(this, t);
        return null;
    }

    @Override
    public U visit(ReplyMessage replyMessage, T t) {
//        System.out.println("Reply Message:");
        replyMessage.getReplyContent().accept(this, t);
        replyMessage.getOriginalMessage().accept(this, t);
        return null;
    }

    @Override
    public U visit(WaitMessage waitMessage, T t) {
//        System.out.println("Wait Message:");
        waitMessage.getFrom().accept(this, t);
        return null;
    }
    @Override
    public U visit(GetMessage getMessage, T t) {
//        System.out.println("Get Message:");
        getMessage.getFrom().accept(this, t);
        return null;
    }

    @Override
    public U visit(DeleteMessage deleteMessage, T t) {
//        System.out.println("Delete Message:");
        return null;
    }

    @Override
    public U visit(GiveUserRole giveUserRole, T t) {
//        System.out.println("Give User Role:");
        giveUserRole.getUser().accept(this, t);
        giveUserRole.getRole().accept(this, t);
        return null;
    }

    @Override
    public U visit(KickUser kickUser, T t) {
//        System.out.println("Kick User:");
        kickUser.getUser().accept(this, t);
        return null;
    }

    @Override
    public U visit(BanUser banUser, T t) {
//        System.out.println("Ban User:");
        banUser.getUser().accept(this, t);
        return null;
    }

    @Override
    public U visit(Conditional conditional, T t) {
//        System.out.println("Conditional:");
        conditional.getCondition().accept(this, t);
        for (Statement statement : conditional.getIfBody()) {
            statement.accept(this, t);
        }
        for (Statement statement : conditional.getElseBody()) {
            statement.accept(this, t);
        }
        return null;
    }

    @Override
    public U visit(Loop loop, T t) {
//        System.out.println("Loop:");
        loop.getCondition().accept(this, t);
        for (Statement statement : loop.getBody()) {
            statement.accept(this, t);
        }
        return null;
    }

    @Override
    public U visit(Program program, T t) {
//        System.out.println("Program:");
        for (Statement statement : program.getStatements()) {
            statement.accept(this, t);
        }
        return null;
    }
    @Override
    public U visit(Setup setup, T t) {
//        System.out.println("Setup:");
        for (Statement statement : setup.getBody()) {
            statement.accept(this, t);
        }
        return null;
    }
    @Override
    public U visit(Choose choose, T t) {
//        System.out.println("Choose:");
        for (Exp exp : choose.getChoices()) {
            exp.accept(this, t);
        }
        return null;
    }
    @Override
    public U visit(WaitInMessage waitInMessage, T t) {
//        System.out.println("Wait In Message:");
        waitInMessage.getIn().accept(this, t);
        return null;
    }
    @Override
    public U visit(GetUser getUser, T t) {
//        System.out.println("Get User:");
        getUser.getUser().accept(this, t);
        return null;
    }
}
