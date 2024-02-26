package evaluator;

import ast.*;
import visitor.DiscordBotVisitor;

import java.io.PrintWriter;
import java.util.ArrayList;


public class Evaluator implements DiscordBotVisitor<PrintWriter,Void> {
    private ArrayList<String> commands = new ArrayList<String>();

    @Override
    public Void visit(Name name, PrintWriter t) {
        t.print(name.getName());
        return null;
    }

    @Override
    public Void visit(NumberConstant num, PrintWriter t) {
        t.print(num.getValue());
        return null;
    }

    @Override
    public Void visit(BooleanConstant bool, PrintWriter t) {
        if(bool.getBool()){
            t.print("true");
        } else {
            t.print("false");
        }
        return null;
    }

    @Override
    public Void visit(StringConstant string, PrintWriter t) {
        t.print(string.getString().replaceAll("[{]","\"+").replaceAll("[}]","+\""));
        return null;
    }

    @Override
    public Void visit(BinaryExp binExp, PrintWriter t) {
        //Handle different dsl equivalences
        switch(binExp.getOperator()){
            case "is":
                binExp.getLeft().accept(this, t);
                t.print(" == ");
                binExp.getRight().accept(this, t);
                break;
            case "is_in":
                binExp.getRight().accept(this, t);
                t.print(".includes(");
                binExp.getLeft().accept(this, t);
                t.print(")");
                break;
            default:
                binExp.getLeft().accept(this, t);
                t.print(" " + binExp.getOperator());
                binExp.getRight().accept(this, t);
        }
        return null;
    }

    @Override
    public Void visit(ArrayExp arrayExp, PrintWriter t) {
        //Print array formatting
        t.print("[");
        int len = arrayExp.getElements().size() - 1;
        for (Exp exp : arrayExp.getElements()) {
            exp.accept(this, t);

            //Handles preventing trailing 0 in array declaration
            if(len > 0) {
                t.print(",");
            }
            len--;
        }
        t.print("]");
        return null;
    }

    @Override
    public Void visit(Set set, PrintWriter t) {
        set.getTo_Set().accept(this, t);
        t.print( " = ");
        set.getSet_Value().accept(this, t);
        t.print("\n");
        return null;
    }

    @Override
    public Void visit(Command command, PrintWriter t) {
        //add command to repo
        commands.add(command.getName().toString());

        //Print Hook Function Declaration
        t.println("client.on('interactionCreate', async (interaction) => {\n\ttry{\n\t\tif (interaction.isChatInputCommand()) {\n");

        //Print helpers
        t.println("\t\t\t// Generated Helpers");
        //prevent bot from triggering command
        t.print("""
                \t\t\tif (interaction.user.bot) {
                    \t\t\t\treturn;
                \t\t\t}\n
                """);
        t.println("\t\t\tCaller = interaction.user.username;");
        t.println("\t\t\ttriggeredChannel = interaction.channel;");
        t.println("\t\t\tChannel = interaction.channel.name");
        t.println("\t\t\tMessage = interaction.message\n");
        t.println("\t\t\tInput = interaction.message.content\n");


        //Making sure this command only runs when it is called
        t.print("\t\t\tif (interaction.commandName === '");
        command.getName().accept(this,t);
        t.print("') {\n");
        t.print("\t\t\t\tconsole.log('Executing Command: ");
        command.getName().accept(this,t);
        t.print("');\n");
        t.print("\t\t\t\tinteraction.reply('Initiating Command: ");
        command.getName().accept(this,t);
        t.print("');\n");

        for (Statement statement : command.getBody()) {
            t.print("\t\t\t\t");
            statement.accept(this, t);
        }

        t.print("""
                \t\t\t\tconsole.log('Command Finished');
                \t\t\t}
                \t\t}
                \t} catch {
                \t\t console.log("Fatal Error Occured in Command")
                \t}
                });
                """);
        return null;
    }

    @Override
    public Void visit(Routine routine, PrintWriter t) {
        //Print Hook Function Declaration
        t.println("client.on('messageCreate', async (message) => {\n");
        t.println("\ttry {");
        //Print helpers
        t.println("\t\t// Generated Helpers");
        //prevent bot from triggering routine
        t.print("""
                \t\tif (message.author.bot) {
                    \t\t\treturn;
                \t\t}\n
                """);
        t.println("\t\tCaller = message.author.username;");
        t.println("\t\ttriggeredChannel = message.channel;");
        t.println("\t\tChannel = message.channel.name");
        t.println("\t\tMessage = message");
        t.println("\t\tInput = message.content\n");


        //Print comment and logging info
        t.print("\t\t/* ROUTINE: ");
        routine.getName().accept(this,t);
        t.print(" */\n");
        t.print("\t\tconsole.log('Executing Routine: ");
        routine.getName().accept(this,t);
        t.print("')\n\n");

        //print body
        for (Statement statement : routine.getBody()) {
            t.print("\t\t");
            statement.accept(this, t);
        }

        //close hook
        t.print("""
                \t} catch {
                \t\t console.log("Fatal Error Occured in running Routine")
                \t}
                """);
        t.println("});\n\n");
        return null;
    }

    @Override
    public Void visit(SendMessage sendMessage, PrintWriter t) {
        t.print("client.channels.cache.find(channel => channel.name === ");
        sendMessage.getTarget().accept(this, t);
        t.print(").send(");
        sendMessage.getMessage().accept(this, t);
        t.print(");\n\n");

        return null;
    }

    @Override
    public Void visit(ReplyMessage replyMessage, PrintWriter t) {
        t.print("Message.reply(");
        replyMessage.getReplyContent().accept(this, t);
        t.print(");\n");
        return null;
    }

    @Override
    public Void visit(WaitMessage waitMessage, PrintWriter t) {
        t.print("await triggeredChannel.awaitMessages({filter: response => {return (response.author.username === ");
        waitMessage.getFrom().accept(this, t);
        t.print("""
               )}, max: 1, time: 150_000, errors: ['time']})
               \t\t.then(collected => {return collected.first().content})
               \t\t.catch(err => console.log('No interactions were collected in the time allocated.'));
                """);
        return null;
    }
    @Override
    public Void visit(GetMessage getMessage, PrintWriter t) {
        return null;
    }

    @Override
    public Void visit(DeleteMessage deleteMessage, PrintWriter t) {
        t.println("message.delete();");
        t.println("return;");
        return null;
    }

    @Override
    public Void visit(GiveUserRole giveUserRole, PrintWriter t) {
        t.print("client.guilds.cache.get(GUILD_ID).members.addRole({user: client.users.cache.find(user => user.username === ");
        giveUserRole.getUser().accept(this, t);
        t.print("), role: client.guilds.cache.get(GUILD_ID).roles.cache.find(role => role.name ===");
        giveUserRole.getRole().accept(this, t);
        t.print(")}).catch(console.log(\"Error Adding Role\"));\n\n");
        return null;
    }

    @Override
    public Void visit(KickUser kickUser, PrintWriter t) {
        t.print("client.guilds.cache.get(GUILD_ID).members.kick(client.users.cache.find(user => user.username === ");
        kickUser.getUser().accept(this, t);
        t.print("), 'Kicked by Bot').catch(console.log(\"Error Kicking User\"));\n");
        return null;
    }

    @Override
    public Void visit(BanUser banUser, PrintWriter t) {
        t.print("client.guilds.cache.get(GUILD_ID).members.kick(client.users.cache.find(user => user.username === ");
        banUser.getUser().accept(this, t);
        t.print("), 'Banned by Bot').catch(console.log(\"Error Banning User\"));\n");
        return null;
    }

    @Override
    public Void visit(Conditional conditional, PrintWriter t) {
        t.print("if (");
        conditional.getCondition().accept(this, t);
        t.print(") {\n");
        for (Statement statement : conditional.getIfBody()) {
            t.print("\t");
            statement.accept(this, t);
        }
        t.print("\t}");
        if(!conditional.getElseBody().isEmpty()) {
            t.print(" else {\n");
            for (Statement statement : conditional.getElseBody()) {
                t.print("\t");
                statement.accept(this, t);
            }
            t.print("\t}");
        }
        t.print("\n");
        return null;
    }

    @Override
    public Void visit(Loop loop, PrintWriter t) {
        t.print("while (");
        loop.getCondition().accept(this, t);
        t.print(") {\n\n");
        for (Statement statement : loop.getBody()) {
            t.print("\t");
            statement.accept(this, t);
        }
        t.print("}\n");
        return null;
    }

    @Override
    public Void visit(Program program, PrintWriter t) {
        t.println(this.getFileHeaderText());
        for (Statement statement : program.getStatements()) {
            statement.accept(this, t);
        }
        this.printFileFooterText(t);
        return null;
    }

    @Override
    public Void visit(Setup setup, PrintWriter t) {
        t.println("\n\nclient.once(Events.ClientReady, async readyClient => {");
        t.println("\tconsole.log(`Ready! Logged in as ${readyClient.user.tag}`);\n");
        for (Statement statement : setup.getBody()) {
            t.print("\t");
            statement.accept(this, t);
        }
        t.println("\n});\n");
        return null;
    }
    @Override
    public Void visit(Choose choose, PrintWriter t) {
        for (Exp exp : choose.getChoices()) {
            exp.accept(this, t);
        }
        t.print("[Math.floor(Math.random()*");
        for (Exp exp : choose.getChoices()) {
            exp.accept(this, t);
        }
        t.print(".length)];\n");
        return null;
    }
    @Override
    public Void visit(WaitInMessage waitInMessage, PrintWriter t) {
        //Print async awaitMessages call for the next message in whatever channel is specified
        t.print("await client.channels.cache.find(channel => channel.name === ");
        waitInMessage.getIn().accept(this, t);
        t.print("""
                ).awaitMessages({ filter: null, max: 1, time: 150_000, errors: ['time']})
                \t\t.then(collected => {return collected.first().content})
                \t\t.catch(console.log('No interactions were collected in the time allocated.'));
                """);
        return null;
    }
    @Override
    public Void visit(GetUser getUser, PrintWriter t) {
        getUser.getUser().accept(this, t);
        return null;
    }



    private String getFileHeaderText() {
        return """
                /* Bot Created and Generated by DISCORD DSL */
                /* ======================================== */
                /*                                           \s
                Setup Instructions: Verify that you have Node.js installed on your computer. If not, download and install it from the official Node.js website.
                Also verify that you have entered the correct TOKEN, CLIENT_ID, and GUILD_ID keys below. They can be found in the Discord Developer Portal.
                Additionally, ensure that you have invited the bot onto your discord server and given it the necessary permissions needed
                such as the ability to send messages, read messages, and view channels. Also ensure that the bot has been given the necessary
                intents in the Discord Developer Portal. We hope you enjoy using this bot and that it serves you well.\s
                /* ======================================== */
                /*
                To run the bot, you need to copy the code from the ./botOutput directory into your desination folder. Once moved, open a terminal and navigate to the folder where the bot is located and run npm init.\s
                This will install the necessary dependencies for the bot to run. Once the dependencies are installed, run the bot by typing node . into the terminal.
                */
                /* ======================================== */
                                
                // Require the necessary discord.js classes
                const { REST, Client, Events, GatewayIntentBits, Routes, ComponentType} = require('discord.js');
                
                // Create a new client instance
                const client = new Client({
                    intents: [
                      GatewayIntentBits.Guilds,
                      GatewayIntentBits.GuildMessages,
                      GatewayIntentBits.MessageContent,
                    ],
                });
                """;
    }

    private void printFileFooterText(PrintWriter t) {
        t.print("""
                // Create a new REST instance
                const rest = new REST({ version: '10' }).setToken(TOKEN);
                                
                async function main() {
                  const commands = [
                """);
        for (String command : commands) {
            t.print("{name: '" + command + "', description: '" + command +" Command',},\n");
        }
        t.print("""
                  ];
                                
                  try {
                    console.log('Started refreshing application (/) commands.');
                    await rest.put(Routes.applicationGuildCommands(CLIENT_ID, GUILD_ID), {
                      body: commands,
                    });
                    client.login(TOKEN);
                  } catch (err) {
                    console.log(err);
                  }
                                
                }
                                
                main();
                """);
    }
}
