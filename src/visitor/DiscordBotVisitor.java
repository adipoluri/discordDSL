package visitor;

import ast.*;

public interface DiscordBotVisitor<T, U> {
    U visit(Name name, T t);
    U visit(NumberConstant num, T t);
    U visit(BooleanConstant bool, T t);
    U visit(StringConstant string, T t);
    U visit(BinaryExp binExp, T t);
    U visit(ArrayExp arrayExp, T t);
    U visit(Set set, T t);
    U visit(Command command, T t);
    U visit(Routine routine, T t);
    U visit(SendMessage sendMessage, T t);
    U visit(ReplyMessage replyMessage, T t);
    U visit(WaitMessage waitMessage, T t);
    U visit(GetMessage getMessage, T t);
    U visit(DeleteMessage deleteMessage, T t);
    U visit(GiveUserRole giveUserRole, T t);
    U visit(KickUser kickUser, T t);
    U visit(BanUser banUser, T t);
    U visit(Conditional conditional, T t);
    U visit(Loop loop, T t);
    U visit(Program program, T t);
    U visit(Setup setup, T t);
    U visit(Choose choose, T t);
    U visit(WaitInMessage waitInMessage, T t);
    U visit(GetUser getUser, T t);
}