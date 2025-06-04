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

}
