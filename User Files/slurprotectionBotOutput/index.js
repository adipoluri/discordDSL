/* Bot Created and Generated by DISCORD DSL */
/* ======================================== */
/*                                            
Setup Instructions: Verify that you have Node.js installed on your computer. If not, download and install it from the official Node.js website.
Also verify that you have entered the correct TOKEN, CLIENT_ID, and GUILD_ID keys below. They can be found in the Discord Developer Portal.
Additionally, ensure that you have invited the bot onto your discord server and given it the necessary permissions needed
such as the ability to send messages, read messages, and view channels. Also ensure that the bot has been given the necessary
intents in the Discord Developer Portal. We hope you enjoy using this bot and that it serves you well. 
/* ======================================== */
/*
To run the bot, you need to copy the code from the ./botOutput directory into your desination folder. Once moved, open a terminal and navigate to the folder where the bot is located and run npm init. 
This will install the necessary dependencies for the bot to run. Once the dependencies are installed, run the bot by typing node . into the terminal.
*/
/* ======================================== */

// Require the necessary discord.js classes
const {
	REST,
	Client,
	Events,
	GatewayIntentBits,
	Routes,
	ComponentType,
} = require("discord.js");

// Create a new client instance
const client = new Client({
	intents: [
		GatewayIntentBits.Guilds,
		GatewayIntentBits.GuildMessages,
		GatewayIntentBits.MessageContent,
	],
});

TOKEN =
	"REDACTED";
CLIENT_ID = "REDACTED";
GUILD_ID = "1196895637405446176";
admin = "welcome-and-rules";
badwords = ["bad", "slur", "swear", "jesus"];

client.once(Events.ClientReady, async (readyClient) => {
	console.log(`Ready! Logged in as ${readyClient.user.tag}`);

	client.channels.cache
		.find((channel) => channel.name === "general")
		.send("Bot Starting up");
});

client.on("messageCreate", async (message) => {
	try {
		// Generated Helpers
		if (message.author.bot) {
			return;
		}

		Caller = message.author.username;
		triggeredChannel = message.channel;
		Channel = message.channel.name;
		Message = message;
		Input = message.content;

		/* ROUTINE: slurprotection */
		console.log("Executing Routine: slurprotection");

		if (badwords.includes(Input)) {
			client.channels.cache
				.find((channel) => channel.name === Channel)
				.send("" + Caller + " said a bad word in " + Channel + "!");

			message.delete();
			return;
		}
	} catch {
		console.log("Fatal Error Occured in running Routine");
	}
});

// Create a new REST instance
const rest = new REST({ version: "10" }).setToken(TOKEN);

async function main() {
	const commands = [];

	try {
		console.log("Started refreshing application (/) commands.");
		await rest.put(Routes.applicationGuildCommands(CLIENT_ID, GUILD_ID), {
			body: commands,
		});
		client.login(TOKEN);
	} catch (err) {
		console.log(err);
	}
}

main();
