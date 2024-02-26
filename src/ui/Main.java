package ui;

import ast.Program;
import evaluator.Evaluator;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import parser.ParseTreeToAST;
import parser.ParserLexer;
import parser.ParserGrammar;
import visitor.DiscordBotBaseVisitor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.Object.*;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner userInput = new Scanner(System.in);
        Boolean complete = false;
        String filename;

        do {
            System.out.println("|=========================================================|");
            System.out.println("Welcome to DiscordDSL. What file would you like to convert?");
            filename = userInput.nextLine();
            if(!filename.contains(".discord")) {
                System.out.println("Incorrect File type, please try again...");
                continue;
            }
            System.out.println("\nTokenizing " + filename);
            ParserLexer lexer = new ParserLexer(CharStreams.fromFileName("./User Files/input/" + filename));
            lexer.reset();
            TokenStream tokens = new CommonTokenStream(lexer);
            System.out.println("Done tokenizing");

            System.out.println("\nParsing " + filename);
            ParserGrammar parser = new ParserGrammar(tokens);
            System.out.println("Created Parser Tree");
            ParseTreeToAST visitor = new ParseTreeToAST();
            System.out.println("Created AST from Parse Tree");
            Program parsedProgram = visitor.visitProgram(parser.program());
            DiscordBotBaseVisitor<Void, Void> baseVisitor = new DiscordBotBaseVisitor<>();
            parsedProgram.accept(baseVisitor, null);
            System.out.println("Done parsing");

            System.out.println("\nEvaluating " + filename);

            //create output dir
            String destination = "./User Files/"+ filename.replace(".discord","Bot") + "Output";
            File destDir = new File(destination);
            if(destDir.exists()) {
                destDir.delete();
            }
            destDir.mkdirs();

            //Create pck.json
            PrintWriter pckgjson = new PrintWriter(new FileWriter(destination + "/package.json"));
            pckgjson.print("""
                            {
                              "name": "example",
                              "version": "1.0.0",
                              "description": "",
                              "main": "index.js",
                              "scripts": {
                                "test": "echo \\"Error: no test specified\\" && exit 1",
                                "start": "nodemon ./src/index.js"
                              },
                              "author": "",
                              "license": "ISC",
                              "dependencies": {
                                "@discordjs/builders": "^1.7.0",
                                "@discordjs/rest": "^2.2.0",
                                "discord.js": "^14.14.1"
                              }
                            }               
                            """);
            pckgjson.close();

            //evalutate
            PrintWriter out = new PrintWriter(new FileWriter(destination + "/index.js"));
            Evaluator e = new Evaluator();
            parsedProgram.accept(e, out);
            out.close();
            System.out.println("Done Evaluating :)");

            complete = true;

        } while(!complete);

        System.out.println("Finished Processing DiscordDSL File. Please follow the steps in the top of the index.js for further setup!");

    }

}
