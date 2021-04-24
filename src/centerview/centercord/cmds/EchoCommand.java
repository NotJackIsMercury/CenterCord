package centerview.centercord.cmds;

import centerview.centercord.Utility;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EchoCommand extends Command {
	public EchoCommand() {
		super("echo", false, Utility.arrayOfOne(Permission.MANAGE_CHANNEL));
	}

	@Override
	public Object handle(MessageReceivedEvent mre, String args) {
		mre.getMessage().reply(new MessageBuilder(args).build()).queue();
		return args;
	}
}
