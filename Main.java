package net.luckey.bnsbot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class Main {

	public static JDA jda;
	public static JDABuilder b;
	private static String token = "NTIwNzM3OTcxMjY4NDg1MTM5.DuyPEg.erQIC6WyehfTLKUzKUdcBYrm164";

	public static void main(String[] args) {
		start("342065846085");
	}

	private static void start(String password) {
		b = new JDABuilder(AccountType.BOT);
		b.setToken(token);
		b.setAutoReconnect(true);
		b.setGame(Game.of(Game.GameType.DEFAULT, "Criado por: Luckey LINDO"));
		b.addEventListener(new Events());
		try {
			jda = b.buildBlocking();
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
