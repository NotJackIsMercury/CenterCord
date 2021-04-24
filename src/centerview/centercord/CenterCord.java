package centerview.centercord;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import centerview.centercord.cmds.Command;
import centerview.centercord.cmds.EchoCommand;
import centerview.centercord.cmds.HelpCommand;
import centerview.centercord.cmds.PingCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public final class CenterCord {
	public static final class CenterTask extends TimerTask {
		public final Runnable runnable;

		public CenterTask(Runnable r) {
			this.runnable = r;
		}

		@Override
		public void run() {
			if (runnable != null) runnable.run();
		}
	}

	public static final class CenterTimer extends Timer {
		public CenterTimer() {
			super(true);
		}

		public void schedule(Runnable task, long delay) {
			super.schedule(new CenterTask(task), delay);
		}

		public void schedule(Runnable task, Date time) {
			super.schedule(new CenterTask(task), time);
		}

		public void schedule(Runnable task, long delay, long period) {
			super.schedule(new CenterTask(task), delay, period);
		}

		public void schedule(Runnable task, Date firstTime, long period) {
			super.schedule(new CenterTask(task), firstTime, period);
		}

		public void scheduleAtFixedRate(Runnable task, long delay, long period) {
			super.scheduleAtFixedRate(new CenterTask(task), delay, period);
		}

		public void scheduleAtFixedRate(Runnable task, Date firstTime, long period) {
			super.scheduleAtFixedRate(new CenterTask(task), firstTime, period);
		}
	}

	public static String DEFAULT_PREFIX = "#";

	public static File GUILD_PREFIXES_FILE = new File("D:/guildPrefixes.properties");

	public static final JDA jda;

	private static final Properties guildPrefixes;

	public static final CenterTimer timer = new CenterTimer();

	public static void main(String[] args) {}

	static {
		try {
			guildPrefixes = new Properties();
			jda = JDABuilder.createDefault(System.getenv("discord.centercord.token"))
				.disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.EMOTE, CacheFlag.MEMBER_OVERRIDES,
					CacheFlag.ROLE_TAGS, CacheFlag.VOICE_STATE)
				.setActivity(Activity.watching("JavaDocs"))
				.build();
			loadGuildPrefixes();
			applyCommands();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void applyCommands() {
		final Command[] commands = { new EchoCommand(), new PingCommand() };
//		final Command[] allCommands = new Command[commands.length + 1];
		jda.addEventListener(new HelpCommand(commands));
		jda.addEventListener((Object[]) commands);
	}

	private static void loadGuildPrefixes() {
		if (GUILD_PREFIXES_FILE.exists()) {
			try {
				guildPrefixes.load(new FileReader(GUILD_PREFIXES_FILE));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static String prefixOf(Guild guild) {
		if (guild != null) return prefixOf(guild.getIdLong());
		else return DEFAULT_PREFIX;
	}

	public static String prefixOf(long id) {
		if (!guildPrefixes.containsKey(id)) {
			guildPrefixes.put(String.valueOf(id), "#");
			saveGuildPrefixes();
			return DEFAULT_PREFIX;
		} else return guildPrefixes.getProperty(String.valueOf(id));
	}

	private static void saveGuildPrefixes() {
		try {
			if (!GUILD_PREFIXES_FILE.exists()) GUILD_PREFIXES_FILE.createNewFile();
			guildPrefixes.store(new FileWriter(GUILD_PREFIXES_FILE), null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
