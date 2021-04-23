package centerview.centercord.cmds;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EchoCommand extends Command {
	public EchoCommand() {
		super("echo", "e");
	}

	@Override
	public Object handle(MessageReceivedEvent mre, String... args) {
		final String stitched = String.join(" ", args);
		mre.getMessage().reply(new MessageBuilder(stitched).build()).queue();
		return stitched;
	}
}
