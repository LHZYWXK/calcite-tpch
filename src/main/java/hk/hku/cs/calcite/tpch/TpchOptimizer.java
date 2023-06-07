package hk.hku.cs.calcite.tpch;

import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.config.CalciteConnectionConfigImpl;
import org.apache.calcite.config.CalciteConnectionProperty;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.plan.*;
import org.apache.calcite.plan.volcano.VolcanoPlanner;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorUtil;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.sql2rel.StandardConvertletTable;
import org.apache.calcite.tools.Program;
import org.apache.calcite.tools.Programs;
import org.apache.calcite.tools.RuleSet;
import org.apache.calcite.tools.RuleSets;

import java.util.Collections;
import java.util.Properties;

public class TpchOptimizer {
    private final CalciteConnectionConfig config;
    private final SqlValidator validator;
    private final SqlToRelConverter converter;
    private final VolcanoPlanner planner;

    public TpchOptimizer(
            CalciteConnectionConfig config,
            SqlValidator validator,
            SqlToRelConverter converter,
            VolcanoPlanner planner
    ) {
        this.config = config;
        this.validator = validator;
        this.converter = converter;
        this.planner = planner;
    }

    public static TpchOptimizer create(CalciteSchema rootSchema, RelDataTypeFactory typeFactory) {
        Properties configProperties = new Properties();
        configProperties.put(CalciteConnectionProperty.CASE_SENSITIVE.camelName(), Boolean.FALSE.toString());
        configProperties.put(CalciteConnectionProperty.UNQUOTED_CASING.camelName(), Casing.UNCHANGED.toString());
        configProperties.put(CalciteConnectionProperty.QUOTED_CASING.camelName(), Casing.UNCHANGED.toString());
        CalciteConnectionConfig config = new CalciteConnectionConfigImpl(configProperties);

        CalciteCatalogReader catalogReader = new CalciteCatalogReader(
                rootSchema,
                Collections.emptyList(),
                typeFactory,
                config
        );

        SqlValidator validator = SqlValidatorUtil.newValidator(
                SqlStdOperatorTable.instance(), catalogReader, typeFactory, SqlValidator.Config.DEFAULT
        );

        VolcanoPlanner planner = new VolcanoPlanner(RelOptCostImpl.FACTORY, Contexts.of(config));
        planner.addRelTraitDef(ConventionTraitDef.INSTANCE);

        RelOptCluster cluster = RelOptCluster.create(planner, new RexBuilder(typeFactory));

        RelOptTable.ViewExpander NOOP_EXPANDER = (rowType, queryString, schemaPath, viewPath) -> null;

        SqlToRelConverter converter = new SqlToRelConverter(
                NOOP_EXPANDER,
                validator,
                catalogReader,
                cluster,
                StandardConvertletTable.INSTANCE,
                SqlToRelConverter.config()
        );

        return new TpchOptimizer(config, validator, converter, planner);
    }

    public SqlNode parse(String sql) throws SqlParseException {
        SqlParser sqlParser = SqlParser.create(sql);

//        System.out.println("[Parsed Query]");
//        System.out.println(sqlTree.toString());
//        System.out.println();

        return sqlParser.parseQuery();
    }

    public SqlNode validate(SqlNode sqlTree) {

//        System.out.println("[Validated Query]");
//        System.out.println(validatedSqlTree.toString());
//        System.out.println();
        return validator.validate(sqlTree);
    }

    public RelNode convert(SqlNode validatedSqlTree) {
        RelRoot root = converter.convertQuery(validatedSqlTree, false, true);
        //        System.out.println(
//                RelOptUtil.dumpPlan(
//                        "[Logical Plan]",
//                        relTree,
//                        TpchUtil.getFormat(format),
//                        SqlExplainLevel.EXPPLAN_ATTRIBUTES
//                )
//        );

        return root.rel;
    }

    public RelNode optimize(RelNode relTree, RelTraitSet requiredTraitSet, RuleSet rules) {
        Program program = Programs.of(RuleSets.ofList(rules));

        //        System.out.println(
//                RelOptUtil.dumpPlan(
//                        "[Physical plan]",
//                        optimizeRelTree,
//                        TpchUtil.getFormat(format),
//                        SqlExplainLevel.EXPPLAN_ATTRIBUTES
//                )
//        );
        return program.run(
                planner,
                relTree,
                requiredTraitSet,
                Collections.emptyList(),
                Collections.emptyList()
        );
    }
}