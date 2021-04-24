package centerview.centercord.cmds;

import centerview.centercord.Utility;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand extends Command {
	private final Command[] commands;

	public HelpCommand(Command... commands) {
		super("help", Utility.arrayOfOne("?"), false, Utility.arrayOfOne(Permission.MESSAGE_WRITE));
		this.commands = new Command[commands.length + 1];
		this.commands[0] = this;
		System.arraycopy(commands, 0, this.commands, 1, commands.length);
	}

	@Override
	public Object handle(MessageReceivedEvent mre, String args) {
		for (Command command : commands) {
			for (String key : command.keys()) {
				if (args.equals(key)) {
					final MessageEmbed embed = command.helpMessage();
					mre.getChannel().sendMessage(embed).queue();
					return embed;
				}
			}
		}
		return null;
	}
}
