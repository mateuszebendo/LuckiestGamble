package org.cefet.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoletaCombinacoes {

    public static List<String> getSplitCombinations() {
        List<String> splits = new ArrayList<>();
        for (int i = 0; i <= 36; i++) {
            if (i % 3 != 0 && i < 36) {
                splits.add(i + "-" + (i + 1));
            }
            if (i < 34) {
                splits.add(i + "-" + (i + 3));
            }
        }
        splits.add("0-1");
        splits.add("0-2");
        splits.add("0-3");
        return splits.stream().distinct().sorted().collect(Collectors.toList());
    }

    public static List<String> getStreetCombinations() {
        List<String> streets = new ArrayList<>();
        for (int i = 1; i <= 34; i += 3) {
            streets.add(i + "-" + (i + 1) + "-" + (i + 2));
        }
        return streets;
    }

    public static List<String> getCornerCombinations() {
        List<String> corners = new ArrayList<>();
        for (int row = 1; row <= 11; row++) {
            int startNum = (row - 1) * 3 + 1;
            if (startNum + 4 <= 36) {
                corners.add(startNum + "-" + (startNum + 1) + "-" + (startNum + 3) + "-" + (startNum + 4));
            }
        }
        return corners;
    }

    public static List<String> getSixLineCombinations() {
        List<String> sixLines = new ArrayList<>();
        for (int i = 1; i <= 31; i += 6) {
            sixLines.add(i + "-" + (i + 1) + "-" + (i + 2) + "-" + (i + 3) + "-" + (i + 4) + "-" + (i + 5));
        }
        return sixLines;
    }

    public static List<String> getDozens() {
        List<String> dozens = new ArrayList<>();
        dozens.add("1ª Dúzia (1-12)");
        dozens.add("2ª Dúzia (13-24)");
        dozens.add("3ª Dúzia (25-36)");
        return dozens;
    }

    public static List<String> getColumns() {
        List<String> columns = new ArrayList<>();
        columns.add("1ª Coluna (1,4,...,34)");
        columns.add("2ª Coluna (2,5,...,35)");
        columns.add("3ª Coluna (3,6,...,36)");
        return columns;
    }

    public static List<String> getHighLow() {
        List<String> highLow = new ArrayList<>();
        highLow.add("Baixo (1-18)");
        highLow.add("Alto (19-36)");
        return highLow;
    }
}