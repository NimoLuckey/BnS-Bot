package net.luckey.bnsbot;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Events extends ListenerAdapter {

	@Override
	public void onReady(ReadyEvent e) {
		System.out.println("Bot iniciado com sucesso.");
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		if (e.getAuthor().isBot())
			return;
		String[] args = e.getMessage().getContentDisplay().split(" ");
		String cmd = args[0];
		args = e.getMessage().getContentDisplay().replace(cmd, "").split(" ");

		if (cmd.equalsIgnoreCase("!adicionar")) {
			if (!e.getMember().hasPermission(Permission.ADMINISTRATOR)) {
				e.getMessage().delete().queue();
				e.getAuthor().openPrivateChannel().complete()
						.sendMessage("Você não tem acesso para executar este comando.").queue();
				return;
			}
			if (args.length >= 2) {
				e.getMessage().delete().queue();
				Role r = null;
				String sb = "";
				Member m = null;
				for (int i = 1; i < args.length; i++) {
					sb += args[i];
					for (Role rs : e.getJDA().getRoles()) {
						sb = sb.replace("@", "");
						sb = sb.replace("!msg ", "");
						if (rs.getName().equalsIgnoreCase(sb)) {
							r = rs;
							sb.replace(rs.getName(), "");
						}
					}
					if (r != null) {
						sb = sb.toString().replace(r.getName(), "");
						for (Member ms : e.getGuild().getMembers()) {
							if (ms.getNickname() != null) {
								if (ms.getUser().getName().replace(" ", "").equalsIgnoreCase(sb)
										|| ms.getNickname().replace(" ", "").equalsIgnoreCase(sb)) {
									e.getGuild().getController().addRolesToMember(ms, r).queue();
									m = ms;
									break;
								}
							} else {
								if (ms.getUser().getName().replace(" ", "").equalsIgnoreCase(sb)) {
									e.getGuild().getController().addRolesToMember(ms, r).queue();
									m = ms;
									break;
								}
							}
						}
					}
				}
				e.getJDA().getTextChannelById("520717282406563850").sendMessage("@everyone").queue();
				e.getJDA().getTextChannelById("520717282406563850")
						.sendMessage(new EmbedBuilder()
								.setDescription(" \n " + m.getAsMention() + " Foi adicionado ao cargo "
										+ r.getAsMention() + " \n ")
								.setColor(Color.white)
								.setAuthor("Cargos", e.getGuild().getSelfMember().getUser().getAvatarUrl(),
										e.getGuild().getSelfMember().getUser().getAvatarUrl())
								.setFooter("Por: " + e.getAuthor().getName(), e.getAuthor().getAvatarUrl()).build())
						.queue();
				return;
			}
			e.getMessage().delete().queue();
			e.getAuthor().openPrivateChannel().complete()
					.sendMessage("Você deve utilizar: !msg (style1,style2) Mensagem").queue();
		}
		if (cmd.equalsIgnoreCase("!msg")) {
			if (!e.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
				e.getMessage().delete().queue();
				e.getAuthor().openPrivateChannel().complete()
						.sendMessage("Você não tem acesso para executar este comando.").queue();
				return;
			}
			if (args.length >= 2) {
				e.getMessage().delete().queue();
				String type = args[1];
				StringBuilder sb = new StringBuilder();
				for (int i = 2; i < args.length; i++) {
					sb.append(args[i]).append(" ");
				}
				String msg = sb.toString();
				if (type.equalsIgnoreCase("style1")) {
					e.getChannel().sendMessage(msg).queue();
				} else if (type.equalsIgnoreCase("style2")) {
					e.getChannel().sendMessage("@everyone").queue();
					e.getJDA().getTextChannelById(e.getChannel().getId())
							.sendMessage(new EmbedBuilder().setDescription(msg).setColor(Color.white)
									.setAuthor("🔔Anuncio🔔", e.getGuild().getSelfMember().getUser().getAvatarUrl(),
											e.getGuild().getSelfMember().getUser().getAvatarUrl())
									.setFooter("Por: " + e.getMember().getEffectiveName(),
											e.getMember().getUser().getAvatarUrl())
									.build())
							.queue();
				}

				// !msg syle1 a
				return;
			}
			e.getMessage().delete().queue();
			e.getAuthor().openPrivateChannel().complete()
					.sendMessage("Você deve utilizar: !msg (style1,style2) Mensagem").queue();

		}
	}

}
