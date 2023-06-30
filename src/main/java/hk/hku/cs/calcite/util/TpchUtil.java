package hk.hku.cs.calcite.util;

import hk.hku.cs.calcite.tpch.TPCH_TABLE;
import hk.hku.cs.calcite.tpch.TpchTable;
import org.apache.calcite.adapter.enumerable.EnumerableRules;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.rel.rules.CoreRules;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.sql.SqlExplainFormat;
import org.apache.calcite.tools.RuleSet;
import org.apache.calcite.tools.RuleSets;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TpchUtil {
    public static RuleSet getRules() {
        return RuleSets.ofList(
                CoreRules.FILTER_INTO_JOIN,
                CoreRules.JOIN_ASSOCIATE,
                CoreRules.JOIN_COMMUTE,
                CoreRules.JOIN_COMMUTE_OUTER,
                EnumerableRules.ENUMERABLE_SORT_RULE,
                EnumerableRules.ENUMERABLE_SORT_RULE,
                EnumerableRules.ENUMERABLE_PROJECT_RULE,
                EnumerableRules.ENUMERABLE_FILTER_RULE,
                EnumerableRules.ENUMERABLE_JOIN_RULE,
                EnumerableRules.ENUMERABLE_AGGREGATE_RULE,
                EnumerableRules.ENUMERABLE_TABLE_SCAN_RULE,
                EnumerableRules.ENUMERABLE_SORTED_AGGREGATE_RULE
        );
    }

    public static void addTables(CalciteSchema rootSchema, RelDataTypeFactory typeFactory) {
        for (TPCH_TABLE tpchTable : TPCH_TABLE.values()) {
            // Add the TPC-H table to the root schema
            RelDataTypeFactory.Builder builder = typeFactory.builder();
            List<String> fieldNames = new ArrayList<>();
            List<RelDataType> fieldTypes = new ArrayList<>();
            for (TPCH_TABLE.Column column : tpchTable.columns) {
                RelDataType type = typeFactory.createJavaType(column.type);
                fieldNames.add(column.name);
                fieldTypes.add(type);
                builder.add(column.name, type.getSqlTypeName()).nullable(true);
            }
            rootSchema.add(tpchTable.name(), new TpchTable(tpchTable.name(), fieldNames, fieldTypes, builder.build()));
        }
    }

    public static SqlExplainFormat getFormat(String format) {
        return switch (format) {
            case "text" -> SqlExplainFormat.TEXT;
            case "xml" -> SqlExplainFormat.XML;
            case "json" -> SqlExplainFormat.JSON;
            case "dot" -> SqlExplainFormat.DOT;
            default -> null;
        };
    }

    public static void stringToFile(final String filename, final String content) throws IOException {
        File fileText = new File(filename);
        FileWriter fileWriter = new FileWriter(fileText);
        fileWriter.write(content);
        fileWriter.close();
    }

    public static String sargHandler(String jsonPlan) {
        int searchFrom = 0;
        while (jsonPlan.indexOf("Sarg", searchFrom) != -1) {
            int beginIndex = jsonPlan.indexOf("Sarg", searchFrom);
            int endIndex = beginIndex;
            for (int i = beginIndex; i < jsonPlan.length(); i++) {
                if (jsonPlan.charAt(i) == '\n') {
                    endIndex = i;
                    break;
                }
            }
            String substring = jsonPlan.substring(beginIndex, endIndex - 1);
            jsonPlan = jsonPlan.replace(substring, "\"" + substring + "\"");
            searchFrom = endIndex;
        }
        return jsonPlan;
    }
}
