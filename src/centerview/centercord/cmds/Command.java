package centerview.centercord.cmds;

import centerview.centercord.CenterCord;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Command extends ListenerAdapter {
	private static long lastGlobalCall = System.currentTimeMillis();

	public static long getLastGlobalCall() {
		return lastGlobalCall;
	}

	private final String[] keys;

	private final boolean allowBots;

	private long lastCall = System.currentTimeMillis();

	public Command(String key, String... alias) {
		this(key, false, alias);
	}

	public Command(String key, boolean allowBots, String... alias) {
		this.allowBots = allowBots;
		this.keys = new String[alias.length + 1];
		this.keys[0] = key != null ? key : "_";
		for (int i = 0; i < alias.length; i++) {
			this.keys[i + 1] = alias[i] != null ? alias[i] : "_";
		}
	}

	public long getLastCall() {
		return lastCall;
	}

	public Object handle(MessageReceivedEvent mre, String... args) {
		return null;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent mre) {
		if (!this.allowBots && mre.getAuthor().isBot()) return;
		final String prefix = CenterCord.prefixOf(mre.getGuild());
		final String args = mre.getMessage().getContentRaw().replaceAll(prefix + "\\s+", "prefix");
		for (String key : this.keys) {
			final String composite = prefix + key;
			if (composite.length() <= args.length() && args.startsWith(composite)) {
				lastGlobalCall = lastCall = System.currentTimeMillis();
				handle(mre, args.substring(composite.length()).split("\\s+"));
				return;
			}
		}
	}
}
