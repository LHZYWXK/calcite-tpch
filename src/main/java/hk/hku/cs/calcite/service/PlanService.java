package hk.hku.cs.calcite.service;

import hk.hku.cs.calcite.entity.Plan;
import hk.hku.cs.calcite.tpch.TpchOptimizer;
import hk.hku.cs.calcite.util.TpchUtil;
import org.apache.calcite.adapter.enumerable.EnumerableConvention;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.sql.SqlExplainFormat;
import org.apache.calcite.sql.SqlExplainLevel;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.tools.RuleSet;
import org.springframework.stereotype.Service;

@Service
public class PlanService {
    public Plan getPlan(Plan plan) throws SqlParseException {
        CalciteSchema rootSchema = CalciteSchema.createRootSchema(false);
        RelDataTypeFactory typeFactory = new JavaTypeFactoryImpl();

        TpchUtil.addTables(rootSchema, typeFactory);

        TpchOptimizer tpchOptimizer = TpchOptimizer.create(rootSchema, typeFactory);

        SqlNode parsedSql = tpchOptimizer.parse(plan.getSql());
        SqlNode validatedSql = tpchOptimizer.validate(parsedSql);
        RelNode logicalPlan = tpchOptimizer.convert(validatedSql);
        RuleSet rules = TpchUtil.getRules();
        RelNode physicalPlan = tpchOptimizer.optimize(
                logicalPlan,
                logicalPlan.getTraitSet().plus(EnumerableConvention.INSTANCE),
                rules
        );

        plan.setParsedSql(parsedSql.toString());
        plan.setValidatedSql(validatedSql.toString());
        plan.setLogicalPlan(RelOptUtil.dumpPlan(
                "",
                logicalPlan,
                SqlExplainFormat.TEXT,
                SqlExplainLevel.EXPPLAN_ATTRIBUTES
        ));
        plan.setPhysicalPlan(RelOptUtil.dumpPlan(
                "",
                physicalPlan,
                SqlExplainFormat.TEXT,
                SqlExplainLevel.EXPPLAN_ATTRIBUTES
        ));
        plan.setJsonPlan(TpchUtil.sargHandler(RelOptUtil.dumpPlan(
                "",
                physicalPlan,
                SqlExplainFormat.JSON,
                SqlExplainLevel.EXPPLAN_ATTRIBUTES
        )));
        return plan;
    }
}
