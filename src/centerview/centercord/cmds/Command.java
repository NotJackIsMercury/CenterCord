package centerview.centercord.cmds;

import centerview.centercord.CenterCord;
import centerview.centercord.Utility;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Command extends ListenerAdapter {
	private static long lastGlobalCall = System.currentTimeMillis();

	private static final String emptyString = "";

	private static final String[] emptyStringArray = {};

	public static long getLastGlobalCall() {
		return lastGlobalCall;
	}

	private final String[] keys;

	private final boolean allowBots;

	private long lastCall = System.currentTimeMillis();

	private Permission[] permissions;

	public Command(String key, boolean allowBots, Permission[] permissions) {
		this(key, emptyStringArray, allowBots, permissions);
	}

	public Command(String key, String[] alias, boolean allowBots, Permission[] permissions) {
		this.allowBots = allowBots;
		this.keys = new String[alias.length + 1];
		this.keys[0] = key != null ? key : "_";
		for (int i = 0; i < alias.length; i++)
			this.keys[i + 1] = alias[i] != null ? alias[i] : "_";
		this.permissions = permissions.clone();
	}

	public String description() {
		return emptyString;
	}

	public long getLastCall() {
		return lastCall;
	}

	public String[] keys() {
		return this.keys.clone();
	}

	public Object handle(MessageReceivedEvent mre, String args) {
		return null;
	}

	final MessageEmbed helpMessage() {
		final EmbedBuilder embedBuilder = new EmbedBuilder()
			.addField(Utility.upFirst(keys[0] + " command"), description(), false);
		final StringBuilder aliasBuilder = new StringBuilder();
		for (int i = 1; i < keys.length; i++)
			aliasBuilder.append('`').append(keys[i]).append("` ");
		return embedBuilder.addField("Alias", aliasBuilder.toString(), false).build();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent mre) {
		if (!this.allowBots && mre.getAuthor().isBot() || mre.isFromGuild() &&
			!mre.getMember().hasPermission(Permission.ADMINISTRATOR) && !mre.getMember().hasPermission(permissions))
			return;
		final String prefix = mre.isFromGuild() ? CenterCord.prefixOf(mre.getGuild()) : CenterCord.DEFAULT_PREFIX;
		final String args = mre.getMessage().getContentRaw().replaceAll(prefix + "\\s+", "prefix");
		for (String key : this.keys) {
			final String composite = prefix + key;
			if (composite.length() <= args.length() && args.startsWith(composite)) {
				lastGlobalCall = lastCall = System.currentTimeMillis();
				handle(mre, args.substring(composite.length()).trim());
				return;
			}
		}
	}
}
