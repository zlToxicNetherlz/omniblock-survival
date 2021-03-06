/*
 * Omniblock Developers Team - Copyright (C) 2018 - All Rights Reserved
 *
 * 1. This software is not a free license software, you are not authorized to read, copy, modify, redistribute or
 * alter this file in any form without the respective authorization and consent of the Omniblock Developers Team.
 *
 * 2. If you have acquired this file violating the previous clause described in this Copyright Notice then you must
 * destroy this file from your hard disk or any other storage device.
 *
 * 3. As described in the clause number one, no third party are allowed to read, copy, modify, redistribute or
 * alter this file in any form without the respective authorization and consent of the Omniblock Developers Team.
 *
 * 4. Any concern about this Copyright Notice must be discussed at our support email: soporte.omniblock@gmail.com
 * -------------------------------------------------------------------------------------------------------------
 *
 * Equipo de Desarrollo de Omniblock - Copyright (C) 2018 - Todos los Derechos Reservados
 *
 * 1. Este software no es un software de libre uso, no está autorizado a leer, copiar, modificar, redistribuir
 * o alterar este archivo de ninguna manera sin la respectiva autorización y consentimiento del
 * Equipo de Desarrollo de Omniblock.
 *
 * 2. Si usted ha adquirido este archivo violando la clausula anterior descrita en esta Noticia de Copyright entonces
 * usted debe destruir este archivo de su unidad de disco duro o de cualquier otro dispositivo de almacenamiento.
 *
 * 3. Como se ha descrito en la cláusula número uno, ningun tercero está autorizado a leer, copiar, modificar,
 * redistribuir o alterar este archivo de ninguna manera sin la respectiva autorización y consentimiento del
 * Equipo de Desarrollo de Omniblock.
 *
 * 4. Cualquier duda acerca de esta Noticia de Copyright deberá ser discutido mediante nuestro correo de soporte:
 * soporte.omniblock@gmail.com
 */

package net.omniblock.survival.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.omniblock.dep.essentialsutils.TextUtil;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 *
 * En esta clase hay metodos estaticos
 * que podrían servir para ayudar a los
 * usuarios comprender los comandos.
 *
 * @author SoZyk
 *
 */
public class HelpUtil {

	/**
	 * Mensaje default para la ayuda del comando
	 */
	private static String[] utilidadesTop = new String[]{
			"",
			"&8&l&m=============================================",
			TextUtil.centerMessage(" &b&lUtilidades » &7¡Te ha fallado algún argumento!"),
			TextUtil.centerMessage(" &7Rercuerda que todos los datos deben estár puestos."),
			TextUtil.centerMessage(" &7El formato actual del comando es el siguiente:"),
	};

	/**
	 * Envia un mensaje con formato de ayuda
	 * sobre un comando a un jugador.
	 *
	 * @param player Jugador que se le envía el mensaje.
	 * @param format Formato del comando correcto.
	 */
	public static void cmdFormat(Player player, String format){

		player.sendMessage(utilidadesTop);
		player.spigot().sendMessage(cmdFormatComponent(format));
		player.sendMessage(new String[]{
			"&8&l&m=============================================",
					""
		});
	}

	/**
	 * Envia un mensaje con formato de ayuda
	 * sobre un comando a un jugador.
	 *
	 * @param player Jugador que se le envía el mensaje.
	 * @param format Formato para usar el comando.
	 * @param example Ejemplo de uso del comando.
	 *                %player% para reemplazar por un nombre al azar.
	 */
	public static void cmdFormat(Player player, String format, String example){

		player.sendMessage(utilidadesTop);
		player.spigot().sendMessage(cmdFormatComponent(format));
		player.sendMessage(new String[]{
				TextUtil.centerMessage(" &bEjemplo:  &7"+example.replace("%player%", Names.randomName())),
				"&8&l&m=============================================",
				""
		});
	}



	private static TextComponent cmdFormatComponent(String format){
		StringBuilder blankSpaces = new StringBuilder();
		String cache = "Formato: " + format;
		int spaces = TextUtil.centerMessage(cache).length() - cache.length();
		for(int i = 0; i < spaces; i++)
			blankSpaces.append(" ");

		TextComponent message = new TextComponent(blankSpaces.toString());

		TextComponent cmd = new TextComponent(TextUtil.format("&e&l"+format));
		cmd.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.format("&bHaz click para copiar el comando. :)")).create()));
		cmd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, format));

		message.addExtra(TextUtil.format("&bFormato: "));
		message.addExtra(cmd);

		return message;
	}



	/**
	 * Enum con nombres de usuario al azar
	 * para cualquier situación
	 */
	public enum Names{
		Steve,
		Alex,
		Notch,
		Herobrine,
		Edgron,
		ToxicNether,
		DCiancestraL,
		Boogst,
		Wirlie,
		SoZyk,
		Pedro,
		BobConstructor,
		JuanCarpintero,
		KamiKaze,
		Goku,
		Rodri;

		public static String randomName(){
			return values()[new Random().nextInt(values().length)].toString();
		}
	}
}

