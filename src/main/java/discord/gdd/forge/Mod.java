package discord.gdd.forge;

public class Mod {
	private String name;
	private String version;

	public Mod(String nome, String version) {
		this.name = nome;
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}
}
