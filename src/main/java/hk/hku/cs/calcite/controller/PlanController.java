package hk.hku.cs.calcite.controller;

import hk.hku.cs.calcite.entity.Plan;
import hk.hku.cs.calcite.service.PlanService;
import org.apache.calcite.linq4j.function.Parameter;
import org.apache.calcite.sql.parser.SqlParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
//@Api(tags = "PlanController")
public class PlanController {
    private static final Logger logger = LoggerFactory.getLogger(PlanController.class);
    private final AtomicInteger counter = new AtomicInteger();
    private PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/test")
    public String test(){
        return "Hello World";
    }

    @RequestMapping(value = "/plan", method = RequestMethod.POST)
    @ResponseBody
    public Plan query(@RequestBody Map<String, String> data) {
        //@ApiParam("sql string") String sql
        String sql = data.get("sql");
        logger.info("Input:\n" + sql);

        Plan plan = new Plan(counter.incrementAndGet(), sql);
        try {
            planService.getPlan(plan);
        } catch (SqlParseException e) {
            logger.error(e.getMessage());
        }

        logger.info("Output:\n" + plan.getPhysicalPlan());
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        logger.info(sdf.format(date));
        return plan;
    }
}
