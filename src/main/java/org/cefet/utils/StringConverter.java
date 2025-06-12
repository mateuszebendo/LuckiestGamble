package org.cefet.utils;

public class StringConverter {

    public static String pascalToSnakeCase(String pascalCaseString) {
        if (pascalCaseString == null || pascalCaseString.isEmpty()) {
            return "";
        }

        StringBuilder snakeCaseBuilder = new StringBuilder();
        for (int i = 0; i < pascalCaseString.length(); i++) {
            char c = pascalCaseString.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    snakeCaseBuilder.append('_');
                }
                snakeCaseBuilder.append(Character.toLowerCase(c));
            } else {
                snakeCaseBuilder.append(c);
            }
        }
        return snakeCaseBuilder.toString();
    }

    public static String snakeToNormalCase(String snakeCaseString) {
        if (snakeCaseString == null || snakeCaseString.isEmpty()) {
            return snakeCaseString;
        }

        String spacedString = snakeCaseString.replace("_", " ");
        StringBuilder result = new StringBuilder();
        String[] words = spacedString.split(" ");
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return result.toString().trim();
    }

}
