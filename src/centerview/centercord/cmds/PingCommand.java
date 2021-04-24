package centerview.centercord.cmds;

import centerview.centercord.Utility;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends Command {
	public PingCommand() {
		super("ping", Utility.arrayOfOne("pong"), false, Utility.arrayOfOne(Permission.MANAGE_CHANNEL));
	}

	@Override
	public Object handle(MessageReceivedEvent mre, String args) {
		final long current = System.currentTimeMillis();
		mre.getMessage().reply(new MessageBuilder("Ping!").build()).queue(
			message -> {
				final long networkLatency = System.currentTimeMillis() - current;
				final long localLatency = current - getLastGlobalCall();
				message.editMessage(new StringBuilder("**Pong**\n> Local Latency: **`")
					.append(localLatency)
					.append("`**ms\n> Network Latency: **`")
					.append(networkLatency)
					.append("`**ms")).queue();
		});
		return current;
	}
}
