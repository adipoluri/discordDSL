package parser;

import ast.Name;
import ast.Node;
import ast.*;
import ast.Set;
import java.util.*;

public class ParseTreeToAST extends ParserGrammarBaseVisitor<Node>{

    @Override
    public Program visitProgram(ParserGrammar.ProgramContext ctx){
        List<Statement> statements = new ArrayList<>();
        for (ParserGrammar.StatementContext s: ctx.statement()){
            statements.add((Statement) s.accept(this));
        }
        return new Program(statements);
    }

    @Override
    public Node visitSetStatement(ParserGrammar.SetStatementContext ctx) {
        Name varName = (Name) visitVarname(ctx.varname());
        Node tempNode = null;
        Exp expression = null;

        if (ctx.expression() != null) {
            tempNode = visit(ctx.expression());
        }
        else if (ctx.keywordExpression() != null) {
            tempNode = visit(ctx.keywordExpression());
        }

        if (tempNode instanceof Exp) {
            expression = (Exp) tempNode;
        }
        return new Set(varName, expression);
    }


    @Override
    public Node visitCommandStatement(ParserGrammar.CommandStatementContext ctx) {
        Name commandName = (Name) visitVarname(ctx.varname());
        List<Statement> statements = new ArrayList<>();
        for (ParserGrammar.StatementContext s : ctx.statements().statement()) {
            statements.add((Statement) s.accept(this));
        }
        return new Command(commandName, statements);
    }

    @Override
    public Node visitRoutineStatement(ParserGrammar.RoutineStatementContext ctx) {
        Name routineName = (Name) visitVarname(ctx.varname());
        List<Statement> statements = new ArrayList<>();
        for (ParserGrammar.StatementContext s : ctx.statements().statement()) {
            statements.add((Statement) s.accept(this));
        }
        return new Routine(routineName, statements);
    }

    @Override
    public Node visitSendStatement(ParserGrammar.SendStatementContext ctx) {
        Exp message = (Exp) visit(ctx.expression(0));
        Exp target = (Exp) visit(ctx.expression(1));
        return new SendMessage(message, target);
    }

    @Override
    public Node visitReplyStatement(ParserGrammar.ReplyStatementContext ctx) {
        Exp replyContent = (Exp) visit(ctx.expression(0));
        Exp originalMessage = (Exp) visit(ctx.expression(1));
        return new ReplyMessage(replyContent, originalMessage);
    }

    @Override
    public Node visitWaitMessage(ParserGrammar.WaitMessageContext ctx) {
        Exp from = (Exp) visit(ctx.expression());
        return new WaitMessage(from);
    }

    @Override
    public Node visitDelete(ParserGrammar.DeleteContext ctx) {
        return new DeleteMessage();
    }

    @Override
    public Node visitGiveRoleStatement(ParserGrammar.GiveRoleStatementContext ctx) {
        Exp user = (Exp) visit(ctx.expression(0));
        Exp role = (Exp) visit(ctx.expression(1));
        return new GiveUserRole(user, role);
    }

    @Override
    public Node visitBanStatement(ParserGrammar.BanStatementContext ctx) {
        Exp user = (Exp) visit(ctx.expression());
        return new BanUser(user);
    }

    @Override
    public Node visitKickStatement(ParserGrammar.KickStatementContext ctx) {
        Exp user = (Exp) visit(ctx.expression());
        return new KickUser(user);
    }

    @Override
    public Node visitCondition(ParserGrammar.ConditionContext ctx) {
        if (ctx.NAME() != null) {
            return new Name(ctx.NAME().getText());
        } else if (ctx.expression().size() == 2) {
            Exp left = (Exp) visit(ctx.expression(0));
            Exp right = (Exp) visit(ctx.expression(1));
            String operator = ctx.IS_IN() != null ? "is_in" : "is";
            return new BinaryExp(left, right, operator);
        }
        return null;
    }

    @Override
    public Node visitIfElseStatement(ParserGrammar.IfElseStatementContext ctx) {
        Exp condition = (Exp) visit(ctx.condition());

        List<Statement> ifBody = new ArrayList<>();
        for (ParserGrammar.StatementContext s : ctx.statements().statement()) {
            ifBody.add((Statement) s.accept(this));
        }

        List<Statement> elseBody = new ArrayList<>();

        if (ctx.else_() != null) {
            for (ParserGrammar.StatementContext s : ctx.else_().statements().statement()) {
                elseBody.add((Statement) s.accept(this));
            }
        }

        return new Conditional(condition, ifBody, elseBody);
    }


    @Override
    public Node visitWhileStatement(ParserGrammar.WhileStatementContext ctx) {
        Exp condition = (Exp) visit(ctx.condition());
        List<Statement> body = new ArrayList<>();
        for (ParserGrammar.StatementContext s : ctx.statements().statement()) {
            body.add((Statement) s.accept(this));
        }

        //System.out.println(ctx.condition().getText());
        if (condition == null) {
            // LOL who put this here??? - Adi
            System.out.println("WTF IS GOING ON");
        }
        return new Loop(condition, body);
    }

    public Node visitMath(ParserGrammar.MathContext ctx) {
        Exp left = null;
        Exp right = null;
        String operator = ctx.operator().getText();

        if (ctx.left().INT() != null) {
            left = new NumberConstant(Integer.parseInt(ctx.left().INT().getText()));
        } else if (ctx.left().NAME() != null) {
            left = new Name(ctx.left().NAME().getText());
        }
        if (ctx.right().INT() != null) {
            right = new NumberConstant(Integer.parseInt(ctx.right().INT().getText()));
        } else if (ctx.right().NAME() != null) {
            right = new Name(ctx.right().NAME().getText());
        }

        return new BinaryExp(left, right, operator);
    }

    @Override
    public Node visitGetMessage(ParserGrammar.GetMessageContext ctx) {
        Exp expression = (Exp) visit(ctx.expression());
        return new GetMessage(expression);
    }


        @Override
    public Node visitVarname(ParserGrammar.VarnameContext ctx) {
        return new Name(ctx.NAME().getText());
    }

    @Override
    public Node visitExpression(ParserGrammar.ExpressionContext ctx) {
        if (ctx.STRING() != null) {
            return new StringConstant(ctx.STRING().getText());
        } else if (ctx.INT() != null) {
            return new NumberConstant(Integer.parseInt(ctx.INT().getText()));
        } else if (ctx.BOOL() != null) {
            return new BooleanConstant(Boolean.parseBoolean(ctx.BOOL().getText()));
        } else if (ctx.NAME() != null) {
            return new Name(ctx.NAME().getText());
        } else if (ctx.array() != null) {
            return visitArray(ctx.array());
        }
        return visitChildren(ctx);
    }

    @Override
    public Node visitKeywordExpression(ParserGrammar.KeywordExpressionContext ctx) {
        if (ctx.math() != null) {
            return visitMath(ctx.math());
        } else if (ctx.getMessage() != null) {
            return visitGetMessage(ctx.getMessage());
        } else if (ctx.waitMessage() != null) {
            return visitWaitMessage(ctx.waitMessage());
        } else if (ctx.waitinMessage() != null) {
            return visitWaitinMessage(ctx.waitinMessage());
        } else if (ctx.getUser() != null) {
            return visitGetUser(ctx.getUser());
        } else if (ctx.choose() != null) {
            return visitChoose(ctx.choose());
        }
        return visitChildren(ctx);
    }

    @Override
    public Node visitArray(ParserGrammar.ArrayContext ctx) {
        List<Exp> elements = new ArrayList<>();
        for (ParserGrammar.ExpressionContext exprCtx : ctx.expression()) {
            elements.add((Exp) exprCtx.accept(this));
        }
        return new ArrayExp(elements);
    }

    @Override
    public Node visitChoose(ParserGrammar.ChooseContext ctx) {
        List<Exp> choices = new ArrayList<>();
        if (ctx.array() != null) {
            ArrayExp arrayExp = (ArrayExp) visitArray(ctx.array());
            choices = arrayExp.getElements();
        } else if (ctx.NAME() != null) {
            choices.add(new Name(ctx.NAME().getText()));
        }
        return new Choose(choices);
    }


    @Override
    public Node visitSetupStatement(ParserGrammar.SetupStatementContext ctx) {
        List<Statement> statements = new ArrayList<>();
        for (ParserGrammar.StatementContext statementContext : ctx.statements().statement()) {
            statements.add((Statement) statementContext.accept(this));
        }
        return new Setup(statements);
    }

    @Override
    public Node visitGetUser(ParserGrammar.GetUserContext ctx) {
        Exp userIdent = (Exp) visit(ctx.expression());
        return new GetUser(userIdent);
    }

    @Override
    public Node visitWaitinMessage(ParserGrammar.WaitinMessageContext ctx) {
        Exp channel = (Exp) visit(ctx.expression());

        return new WaitInMessage(channel);
    }
}

