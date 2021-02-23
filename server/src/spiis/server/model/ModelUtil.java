package spiis.server.model;

import spiis.server.error.ModelError;

public class ModelUtil {

    /**
     * Makes sure the text is trimmed and of legal length
     * @param text the text string in question
     * @param min the minimum allowed length
     * @param max the maximum allowed length
     * @param name the name of the field
     * @throws ModelError if the text is too long, too short, or not trimmed
     */
    public static void ensureTextTrimAndLength(String text, int min, int max, String name) {
        int length = text.length();
        if (length != text.trim().length())
            throw new ModelError(String.format("%s is not trimmed", name));
        if (length < min)
            throw new ModelError(String.format("%s is too short (min %d)", name, min));
        if (length > max)
            throw new ModelError(String.format("%s is too long (max %d)", name, max));
    }

    /**
     * Makes sure the text is not too long
     * @param text the text string in question
     * @param max the maximum allowed length
     * @param name the name of the field
     * @throws ModelError if the text is too long, too short, or not trimmed
     */
    public static void ensureTextMaxLength(String text, int max, String name) {
        if (text.length() > max)
            throw new ModelError(String.format("%s is too long (max %d)", name, max));
    }
}
