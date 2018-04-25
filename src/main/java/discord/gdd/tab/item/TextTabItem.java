package discord.gdd.tab.item;


import discord.gdd.tab.util.Skin;
import discord.gdd.tab.util.Skins;
import lombok.Getter;

import java.util.Objects;


public class TextTabItem implements TabItem {
    private String text;
    private int ping;
    public String getText() {
		return text;
	}

	public int getPing() {
		return ping;
	}


	@Getter private Skin skin;

    private String newText;
    private int newPing;
    private Skin newSkin;

    public TextTabItem(String text) {
        this(text, 1000);
    }



    public TextTabItem(String text, int ping) {
        this(text, ping, Skins.DEFAULT_SKIN);
    }

    public TextTabItem(String text, int ping, Skin skin) {
        this.newText = text;
        this.newPing = ping;
        this.newSkin = skin;
        updateText();
        updatePing();
        updateSkin();
    }

    public void setText(String text) {
        this.newText = text;
    }

    public void setPing(int ping) {
        this.newPing = ping;
    }

    public void setSkin(Skin skin) {
        this.newSkin = skin;
    }

    @Override
    public boolean updateText() {
        boolean update = !Objects.equals(this.text, this.newText);
        this.text = this.newText;
        return update;
    }

    @Override
    public boolean updatePing() {
        boolean update = this.ping != this.newPing;
        this.ping = this.newPing;
        return update;
    }

    @Override
    public boolean updateSkin() {
        boolean update = !Objects.equals(this.skin, this.newSkin);
        this.skin = this.newSkin;
        return update;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TextTabItem))
            return false;
        TextTabItem other = (TextTabItem) object;
        return this.text.equals(other.getText()) && this.skin.equals(other.getSkin()) && this.ping == other.getPing();
    }
}
