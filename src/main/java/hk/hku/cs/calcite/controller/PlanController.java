package hk.hku.cs.calcite.controller;

import com.google.gson.Gson;
import hk.hku.cs.calcite.entity.Plan;
import hk.hku.cs.calcite.service.PlanService;
import hk.hku.cs.calcite.util.CommandUtil;
import hk.hku.cs.calcite.util.TpchUtil;
import org.apache.calcite.sql.parser.SqlParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PlanController {
    private static final Logger logger = LoggerFactory.getLogger(PlanController.class);
    private final AtomicInteger counter = new AtomicInteger();
    private PlanService planService;

    @Value("${calcite.path.json}")
    private String jsonPath;

    @Value("${calcite.path.sql}")
    private String sqlPath;


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
        logger.info(plan.getJsonPlan());
        return plan;
    }

    @GetMapping("/plan/file")
    public Plan queryWithFile(@RequestParam(value = "filename", defaultValue = "") String filename) {

        logger.info("Input:\n" + filename);
        String filePath = sqlPath + filename;
        String outputPath = (jsonPath + filename).replace("sql", "json");

        String sql = null;
        try {
            sql = Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        Plan plan = new Plan(counter.incrementAndGet(), sql);
        try {
            planService.getPlan(plan);
        } catch (SqlParseException e) {
            logger.error(e.getMessage());
        }

        logger.info("Output:\n" + plan.getPhysicalPlan());
        SimpleDateFormat sdf = new SimpleDateFormat(); // 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a"); // a为am/pm的标记
        Date date = new Date(); // 获取当前时间
        logger.info(sdf.format(date));

        Gson gson = new Gson();
        String json1 = gson.toJson(plan);
        String json2 = gson.toJson(plan.getPhysicalPlan());

        try {
            TpchUtil.stringToFile(outputPath, json1 + json2);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        String[] array1 = {"cat", "plan.json"};
        String[] array2 = {"stat", "plan.json"};
        String res1 = CommandUtil.callCMD(array1);
        String res2 = CommandUtil.callCMD(array2);

        return plan;
    }
}
