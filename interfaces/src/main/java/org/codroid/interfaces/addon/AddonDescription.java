/*
 *     Copyright (c) 2021 Zachary. All rights reserved.
 *
 *     This file is part of Codroid.
 *
 *     Codroid is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Codroid is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Codroid.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.codroid.interfaces.addon;

import org.codroid.interfaces.env.Property;
import org.codroid.interfaces.utils.TomlKt;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * This class manages the property file of addons.
 * Serializing and defining
 */
public final class AddonDescription extends Property<AddonDescription.Addon> {
    public static String ADDON_DESCRIPTION_FILE_NAME = "addon-des.toml";

    public AddonDescription(byte[] bytes) {
        super(new ByteArrayInputStream(bytes), Addon.class);
    }

    public AddonDescription(Path file) {
        super(file, Addon.class);
    }

    /**
     * Check integrity of the description
     *
     * @return empty set if it's not broken.
     */
    public Set<String> checkIntegrity() {
        Class<? extends Addon> entityClass = getEntity().getClass();
        Set<String> brokenField = new HashSet<>();
        for (Field field : entityClass.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(OptionalField.class)) {
                    if (Objects.isNull(field.get(getEntity()))) {
                        brokenField.add(field.getName());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return brokenField;
    }


    public Addon get() {
        return getEntity();
    }

    public static class Addon {
        private String name;

        private String _package;

        private String enterPoint;
        private String author;
        private Long versionCode;
        private String versionDes;
        private String supportVersion;
        private String description;

        private String link;

        @OptionalField
        private List<String> events;

        @OptionalField
        private String theme;

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackage() {
            return _package;
        }

        public void setPackage(String _package) {
            this._package = _package;
        }

        public String getEnterPoint() {
            return enterPoint;
        }

        public void setEnterPoint(String enterPoint) {
            this.enterPoint = enterPoint;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Long getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(Long versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionDes() {
            return versionDes;
        }

        public void setVersionDes(String versionDes) {
            this.versionDes = versionDes;
        }

        public String getSupportVersion() {
            return supportVersion;
        }

        public void setSupportVersion(String supportVersion) {
            this.supportVersion = supportVersion;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public List<String> getEvents() {
            return events;
        }

        public void setEvents(List<String> events) {
            this.events = events;
        }
    }
}
