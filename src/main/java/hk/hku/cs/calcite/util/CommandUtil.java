package hk.hku.cs.calcite.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommandUtil.class);

    public static String callCMD(String[] cmd) {
        File dir = null;
        StringBuilder res = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(cmd, null, dir);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            int status = process.waitFor();
            if (status != 0) {
                System.err.println("Failed");
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
        return res.toString();
    }
}
