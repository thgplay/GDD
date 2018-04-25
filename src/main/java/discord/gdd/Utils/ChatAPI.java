package discord.gdd.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * API de manipulaÃ§Ã£o de Cores e CHAT do Minecraft
 * 
 * @author Eduard
 *
 */
public final class ChatAPI {

	public static ChatColor SUCCESS = ChatColor.GREEN;
	public static ChatColor SUCCESS_ARGUMENT = ChatColor.DARK_GREEN;
	public static ChatColor ERROR = ChatColor.RED;
	public static ChatColor ERROR_ARGUMENT = ChatColor.DARK_RED;
	public static ChatColor MESSAGE = ChatColor.GOLD;
	public static ChatColor MESSAGE_ARGUMENT = ChatColor.YELLOW;
	public static ChatColor CHAT_CLEAR = ChatColor.WHITE;
	public static ChatColor CHAT_NORMAL = ChatColor.GRAY;
	public static ChatColor GUI_TITLE = ChatColor.BLACK;
	public static ChatColor GUI_TEXT = ChatColor.DARK_GRAY;
	public static ChatColor CONFIG = ChatColor.AQUA;
	public static ChatColor CONFIG_ARGUMENT = ChatColor.DARK_AQUA;
	public static ChatColor ITEM_TITLE = ChatColor.LIGHT_PURPLE;
	public static ChatColor ITEM_TEXT = ChatColor.DARK_PURPLE;
	public static ChatColor TITLE = ChatColor.DARK_BLUE;
	public static ChatColor TEXT = ChatColor.BLUE;

	static {

	}

	/**
	 * Pega um CoraÃ§Ã£o vermelho
	 * 
	 * @return Simbolo CoraÃ§Ã£o
	 */
	public static String getRedHeart() {
		return ChatColor.RED + getHeart();
	}

	/**
	 * Pega um CoraÃ§Ã£o normal
	 * 
	 * @return Simbolo CoraÃ§Ã£o
	 */
	public static String getHeart() {
		return "â™¥";
	}

	public static String getArrow() {
		return "âžµ";
	}

	public static String getArrowRight() {
		return "â€ºâ€º";
	}

	public static String getArrowLeft() {
		return "â€¹â€¹";
	}

	public static String getSquared() {
		return "â�‘";
	}

	public static String getInterrogation() {
		return "âž�";
	}

	public static String getALlSimbols() {
		return "â�¤â�¥âœ�?âœ–âœ—âœ˜â�‚â‹†âœ¢âœ£âœ¤âœ¥âœ¦âœ©âœªâœ«âœ¬âœ­âœµâœ´âœ³âœ²âœ±â˜…âœ°âœ¯âœ®âœ¶âœ·âœ¸âœ¹âœºâœ»âœ¼â�„â�…âœ½âœ¡â˜†â�‹â�Šâ�‰â�ˆâ�‡â�†âœ¾âœ¿â�€â��â�ƒâœŒâ™¼â™½âœ‚âž£âž¢â¬‡âžŸâ¬†â¬…âž¡âœˆâœ„âž¤âž¥âž¦âž§âž¨âžšâž˜âž™âž›âž¶âžµâž´âž³âž²âž¸âžžâž�âžœâž·âž¹âž¹âžºâž»âž¼âž½â“‚â¬›â¬œâ„¹â˜•â–Œâ–„â–†â–œâ–€â–›â–ˆ";
	}

	public static String getAllSimbols2() {
		return "â„¢âš‘âš�â˜ƒâš âš�?âš–âš’âš™âšœâš€âš�âš‚âšƒâš„âš…âšŠâš‹âšŒâš�âš�âšŽâ˜°â˜±â˜²â˜³â˜´â˜µâ˜¶â˜·âš†âš‡âšˆâš‰â™¿â™©â™ªâ™«â™¬â™­â™®â™¯â™ â™¡â™¢â™—â™–â™•â™�?â™§â™›â™¦â™¥â™¤â™£â™˜â™™â™šâ™›â™œâ™�â™žâ™Ÿâšªâžƒâž‚âž�âž€âžŒâž‹âžŠâž‰âžˆâž‡âž†âž…âž„â˜£â˜®â˜¯âš«âžŒâž‹âžŠâž‰âžˆâž‡âž†âž…âž„âž�âžŽâž�âž�âž‘âž’âž“â“�â“‘â“š";
	}

	public static String getAllSimbols3() {
		return "ì›ƒìœ â™‹â™€â™‚â�£Â¿âŒšâ˜‘â–²â˜ â˜¢â˜¿â’¶âœ�â˜¤âœ‰â˜’â–¼âŒ˜âŒ›Â®Â©âœŽâ™’â˜�â˜¼ãƒ„áƒ¦Â¡Î£â˜­âœžâ„ƒâ„‰�?Ÿâ˜‚Â¢Â£âŒ¨âš›âŒ‡â˜¹â˜»â˜ºâ˜ªÂ½âˆžâœ†â˜ŽâŒ¥â‡§â†©â†�â†’â†‘â†“âš£âš¢âŒ²â™ºâ˜Ÿâ˜�â˜žâ˜œâž«â�‘â�’â—ˆâ—�â—‘Â«Â»â€¹â€ºÃ—Â±â€»â�‚â€½Â¶â€�?â�„â€�?â€“â‰ˆÃ·â‰ �?€â€ â€¡â€¡Â¥â‚¬â€°â—�â€¢Â·";
	}

}
