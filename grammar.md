```python
# Set Required Tokens and Consts
SET DiscordToken = "8a9658c6c5d94be93dd6b61db5"
SET CommandToken = "!"

# Set Constant for anything such as an admin username, Has to be lowercase
SET admin = "tails100"
SET general = "general"

#Special Function Types
setup:
	# Put code that runs on startup here

command _name_:
	# Code thatt runs when a user says !_name_ in any channel in the discord
	# Available objects from Command:
	# - Caller : The User who initiated the command
	# - Channel: The Channel The command was called in

routine _name_:
	# Code thats executed on every message sent
	# - Caller : The User who wrote the message
	# - Channel: The Channel The command was called in
	# - Message: The Message this routine was called on 
	Delete # will Delete the message AND end execution at this point for this routine call (CAN ONLY BE USED WITHIN ROUTINES)

# dynamic variables, they have to be lowercase, If set outside of routine or command, they are global
one = 1
two = 2
three = one + two

# arrays of numbers and strings and other variables
array = [1,2,3]
foo = ["one","two","three"]
bar = [one, two, three]
z = true
y = false

# data types and typing
# TODO: Numerical Values, Strings, Arrays, references

# User Functions

# Messaging Return Val -> Message as String
message = Wait Message "dancer0303" # Get Next Message From User Dancer0303 (Blocking)
message = Wait Message Caller # Get Next Message From User who initiated Command
message = Wait Message in "meeting room" # Get Next Message in Channel "general"
message = Wait Message in general # Get Next Message in Channel "general"
message = Wait Message in Channel # Get Next Message in the Channel the command was called in

# Bot Functions

# Messaging
Send Message "Bot Sending" in general 
Send Message "Bot Sending" in "Common Room"
Send Message "{user.name} just joined the server!" in general

Send Reply "This is a reply" to Caller

# Give Role
Give User "dancer0303" Role "member"
Give User Caller Role "member"

# Kick User
Kick User Caller # Kicks the User who called the command/wrote the message
Kick User "dancer0303"

# Ban User
Ban User Caller # Kicks the User who called the command/wrote the message
Ban User "dancer0303"

#Conditionals
if _condition_:
	# Body of If
else:
	# Else Case

while _condition_:
	#body

# example conditions
if 1 isin [1,2,3] # int in int array
if "a" isin letters # char in string array
if user_choice is bot_choice # string equivalence
if Input is '5' # Routine Message Equivalence
if 'cheat' isin Input # substring in larger string
if 'hi' isin 'hi how are you doing'

# Miscellaneous
random_choice = Choose [1,2,3,4,5,6,7,8,9,10] # Randomly picks item from list
random_choice = Choose possible_choices # Randomly picks item from reference to list



# ---------------------------------------------------------------------------------------- #

# EXAMPLE PROGRAM
# Set Tokens and Consts
SET TOKEN = "redacted"
SET CLIENT_ID = "redacted"
SET GUILD_ID = "1196895637405446176"

SET general = "general"


setup:
	Send Message "Bot Starting up" in general
;

command help:
    Send Message "Here are the commands I know" in Channel
    Send Message "1. /rockpaperscissors for a game of rock paper Scissors" in Channel
    Send Message "2. /help for help with how to interact with me!" in Channel
;

command rockpaperscissors:
	SET possible_actions = ["rock", "paper", "scissors"]
	SET tie = true

	while tie {
		Send Message "Ready to play? Send your choice below" in Channel
		SET user_choice = Wait Message Caller

		SET bot_choice = Choose possible_actions

		Send Message  "{Caller.name} chose {user_choice}, and I chose {bot_choice}!" in Channel

		if user_choice isin possible_actions {
			if user_choice is bot_choice {
			    Send Message "It's a tie!" in Channel
			}
	        else {
			    SET tie = false

			    if user_choice is "rock" {
				    if bot_choice is "scissors" {
					    Send Message "Rock smashes scissors! You win!" in Channel
			        } else {
		        		Send Message "Paper covers rock! You lose." in Channel
		            }
		        }

			    if user_choice is "paper"{
	    			if bot_choice is "rock"{
	        			Send Message "Paper covers rock! You win!" in Channel
	                } else{
		        		Send Message "Scissors cuts paper! You lose." in Channel
		            }
		        }

			    if user_choice is "scissors"{
	    			if bot_choice is "paper"{
	        			Send Message "Scissors cuts paper! You win!" in Channel
	                }
		    		else{
		        		Send Message "Rock smashes scissors! You lose." in Channel
		            }
		        }

		    }
	    }
	    else {
            Send Message "You didn't choose Rock, Paper, or Scissors! Lets try again." in Channel
        }
    }
;

```
