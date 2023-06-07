package hk.hku.cs.calcite.controller;

import hk.hku.cs.calcite.entity.Plan;
import hk.hku.cs.calcite.service.PlanService;
import org.apache.calcite.sql.parser.SqlParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PlanController {
    private static final Logger logger = LoggerFactory.getLogger(PlanController.class);
    private final AtomicInteger counter = new AtomicInteger();
    private PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/plan")
    public Plan query(@RequestParam(value = "sql", defaultValue = "") String sql) {
        logger.info("Input:\n" + sql);

        Plan plan = new Plan(counter.incrementAndGet(), sql);
        try {
            planService.getPlan(plan);
        } catch (SqlParseException e) {
            logger.error(e.getMessage());
        }

        logger.info("Output:\n" + plan.getPhysicalPlan());
        return plan;
    }
}
