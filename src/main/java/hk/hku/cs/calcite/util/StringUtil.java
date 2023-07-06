package hk.hku.cs.calcite.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static Map<Integer, String> extractHeader(String str) {
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(str);
        Map<Integer, String> header = new HashMap<>();
        if (matcher.find()) {
            String result = matcher.group(1);
            if (StringUtils.isNotEmpty(result)) {
                String[] temp = result.split(";");
                String attributeString = temp[1];
                String[] attributes = attributeString.split(",");
                for (int index = 0; index < attributes.length; index++) {
                    String names[] = attributes[index].split(":");
                    header.put(index, names[0].trim());
                }
            }
        }
        return header;
    }

    public static List<Map<Integer, String>> extractData(String str) {
        Pattern pattern = Pattern.compile("(\\d+: \\{.*?\\})");
        Matcher matcher = pattern.matcher(str);

        List<Map<Integer, String>> data = new ArrayList<>();

        while (matcher.find()) {
            String result = matcher.group(1);
            if (StringUtils.isNotEmpty(result)) {
                data.add(extractOneRow(result));
            }
        }
        return data;
    }

    public static Map<Integer, String> extractOneRow(String str) {
        Pattern pattern = Pattern.compile("\\{(.*)\\}");
        Matcher matcher = pattern.matcher(str);
        Map<Integer, String> oneRowMap = new HashMap<>();
        if (matcher.find()) {
            String result = matcher.group(1);
            if (StringUtils.isNotEmpty(str)) {
                String[] oneRow = result.split(",");
                for (int index = 0; index < oneRow.length; index++) {
                    oneRowMap.put(index, oneRow[index].trim());
                }
            }
        }
        return oneRowMap;
    }

}
