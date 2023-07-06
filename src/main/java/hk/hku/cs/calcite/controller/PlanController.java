package hk.hku.cs.calcite.controller;

import hk.hku.cs.calcite.entity.Plan;
import hk.hku.cs.calcite.service.PlanService;
import hk.hku.cs.calcite.util.CommandUtil;
import hk.hku.cs.calcite.util.StringUtil;
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
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PlanController {
    private static final Logger logger = LoggerFactory.getLogger(PlanController.class);
    private final AtomicInteger counter = new AtomicInteger();
    private PlanService planService;

    @Value("${calcite.path.sql}")
    private String sqlPath;

    @Value("${calcite.path.text}")
    private String textPath;

    @Value("${calcite.path.json}")
    private String jsonPath;

    @Value("${calcite.path.xml}")
    private String xmlPath;

    @Value("${calcite.path.dot}")
    private String dotPath;

    @Value("${velox.exec.path}")
    private String execPath;


    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/plan")
    public List<Map<Integer, String>> query(@RequestParam(value = "sql", defaultValue = "") String sql) {
        logger.info("Input:\n" + sql);

        Plan plan = new Plan(counter.incrementAndGet(), sql);
        try {
            planService.getPlan(plan);
        } catch (SqlParseException e) {
            logger.error(e.getMessage());
        }

        logger.info("Output:\n" + plan.getPhysicalPlan());
        logger.info("Output in JSON:\n" + plan.getJsonPlan());
        logger.info("Output in XML:\n" + plan.getXmlPlan());
        logger.info("Output in DOT:\n" + plan.getDotPlan());

        String textOutput = textPath + plan.getId() + "txt";
        String jsonOutput = jsonPath + plan.getId() + "json";
        String xmlOutput = xmlPath + plan.getId() + "xml";
        String dotOutput = dotPath + plan.getId() + "txt";

        try {
            TpchUtil.stringToFile(textOutput, plan.getPhysicalPlan());
            TpchUtil.stringToFile(jsonOutput, plan.getJsonPlan());
            TpchUtil.stringToFile(xmlOutput, plan.getXmlPlan());
            TpchUtil.stringToFile(dotOutput, plan.getDotPlan());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        String[] execArgs = {execPath, jsonOutput};
        String res = CommandUtil.callCMD(execArgs);
        logger.info("Velox return string: " + res);

        List<Map<Integer, String>> data = new ArrayList<>();
        Map<Integer, String> header = StringUtil.extractHeader(res);
        List<Map<Integer, String>> rowData = StringUtil.extractData(res);
        data.add(header);
        data.addAll(rowData);
        return data;
    }

    @GetMapping("/plan/file")
    public List<Map<Integer, String>> queryWithFile(@RequestParam(value = "filename", defaultValue = "") String filename) {

        logger.info("Input:\n" + filename);
        String filePath = sqlPath + filename;
//        String outputPath = (jsonPath + filename).replace("sql", "json");
        String textOutput = (textPath + filename).replace("sql", "txt");
        String jsonOutput = (jsonPath + filename).replace("sql", "json");
        String xmlOutput = (xmlPath + filename).replace("sql", "xml");
        String dotOutput = (dotPath + filename).replace("sql", "txt");
//        String outputPath = prefix + jsonOutput;

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

//        String json = plan.getJsonPlan();

        try {
//            TpchUtil.stringToFile(outputPath, json);
            TpchUtil.stringToFile(textOutput, plan.getPhysicalPlan());
            TpchUtil.stringToFile(jsonOutput, plan.getJsonPlan());
            TpchUtil.stringToFile(xmlOutput, plan.getXmlPlan());
            TpchUtil.stringToFile(dotOutput, plan.getDotPlan());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

//        String[] array1 = {"cat", outputPath};
//        String[] array2 = {"stat", outputPath};
//        String res1 = CommandUtil.callCMD(array1);
//        String res2 = CommandUtil.callCMD(array2);

//        String[] array3 = {"/Users/apple/velox/cmake-build-release/velox/exec/tests/velox_in_10_min_demo",
//                outputPath};
        String[] execArgs = {execPath, jsonOutput};
        String res = CommandUtil.callCMD(execArgs);
        logger.info("Velox return string: " + res);

        List<Map<Integer, String>> data = new ArrayList<>();
        Map<Integer, String> header = StringUtil.extractHeader(res);
        List<Map<Integer, String>> rowData = StringUtil.extractData(res);
        data.add(header);
        data.addAll(rowData);
        return data;
    }
}
