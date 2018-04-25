package discord.gdd.tag.tablist;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * So tem o header/footer.
 */
public class ComTitleTabList implements TabList {
    protected final Player player;
    private String header;
    private String footer;

    public Player getPlayer() {
		return player;
	}

	public String getHeader() {
		return header;
	}

	public String getFooter() {
		return footer;
	}

	public ComTitleTabList(Player player) {
        this.player = player;
    }

    @Override
    public ComTitleTabList enable() {
        return this;
    }

    @Override
    public ComTitleTabList disable() {
        resetHeaderFooter();
        return this;
    }

    public void setHeaderFooter(String header, String footer) {
        setHeader(header);
        setFooter(footer);
    }

    public void resetHeaderFooter() {
        resetHeader();
        resetFooter();
    }

    public void setHeader(String header) {
        this.header = header;
        updateHeaderFooter();
    }

    public void resetHeader() {
        setHeader(null);
    }

    public void setFooter(String footer) {
        this.footer = footer;
        updateHeaderFooter();
    }

    public void resetFooter() {
        setFooter(null);
    }

    private void updateHeaderFooter() {
        PacketContainer packet = new PacketContainer(Server.PLAYER_LIST_HEADER_FOOTER);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(this.header == null ? "" : this.header));
        packet.getChatComponents().write(1, WrappedChatComponent.fromText(this.footer == null ? "" : this.footer));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
