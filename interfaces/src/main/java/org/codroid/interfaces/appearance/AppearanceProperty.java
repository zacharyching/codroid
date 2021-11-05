package org.codroid.interfaces.appearance;

import org.codroid.interfaces.env.AddonEnv;
import org.codroid.interfaces.env.Property;
import org.codroid.interfaces.exceptions.AttributeNotFoundException;

public class AppearanceProperty extends Property {

    public AppearanceProperty(AddonEnv addonEnv, String relativePathStr) {
        super(addonEnv, relativePathStr);
    }

    public enum PartEnum {
        EDITOR("editor");

        private String str;

        PartEnum(String str) {
            this.str = str;
        }

        public String value(){
            return this.str;
        }
    }

    public Part part(PartEnum partEnum) throws IllegalArgumentException, AttributeNotFoundException {
        if (toml == null) {
            open();
        }
        switch (partEnum) {
            case EDITOR:
                return new EditorPart(this.toml);
            default:
                throw new AttributeNotFoundException("Part: " + partEnum.value() + " not found!");
        }
    }
}
