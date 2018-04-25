package discord.gdd.tab.util;

import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.google.common.base.Preconditions;

import java.util.Objects;

/**
 * Representa a skin/avatar no tab.
 */
public class Skin {
    private final WrappedSignedProperty property;

    public Skin(String value, String signature) {
        this(new WrappedSignedProperty("textures", value, signature));
    }

    public WrappedSignedProperty getProperty() {
		return property;
	}

	public Skin(WrappedSignedProperty property) {
        Preconditions.checkArgument(property.getName().equals("textures"));
        this.property = property;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        else if (object instanceof Skin) {
            Skin other = (Skin) object;
            boolean sign = Objects.equals(this.property.getSignature(), other.getProperty().getSignature());
            boolean value = Objects.equals(this.property.getValue(), other.getProperty().getValue());
            return sign && value;
        }
        return false;
    }
}
