package com.itheima.test.utils;

import com.google.gson.Gson;
import com.itheima.test.entity.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Component
public class RunThreadTools {
//判断环境是否为空
    public boolean isInvalidEnvironment(Environment environment) {
        return environment.py_environment == null || environment.py_address == null ||
                environment.input_paths == null || environment.out_paths == null;
    }
//建立控制台命令
    public List<String> buildCommand(Environment environment) {
        List<String> command = new ArrayList<>();
        command.add(environment.py_environment);
        command.add(environment.py_address);
        command.add(createJsonFile(environment).getAbsolutePath());
        return command;
    }
//生成json文件
    public File createJsonFile(Environment environment) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(environment);

        File jsonFile = null;
        try {
            String configPath = getConfigPath(environment.py_address);
            File configDir = new File(configPath);
            createDirectoryIfNotExists(configDir);
//uuid确保文件名不会重复
            jsonFile = new File(configDir, UUID.randomUUID() + "config.json");
            try (FileWriter writer = new FileWriter(jsonFile)) {
                gson.toJson(environment, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonFile;
    }
//回溯到config目录下写json文件
    public String getConfigPath(String pyAddress) {
        File filePath = new File(pyAddress);
        return new File(filePath.getParent()).getParent() + "\\test_config";
    }
//判断文件是否存在，不存在则创建
    public void createDirectoryIfNotExists(File configDir) {
        if (!configDir.exists()) {
            boolean created = configDir.mkdirs();
            if (created) {
                System.out.println("Directory created successfully at: " + configDir.getAbsolutePath());
            } else {
                System.out.println("Failed to create directory at: " + configDir.getAbsolutePath());
            }
        } else {
            System.out.println("Directory already exists at: " + configDir.getAbsolutePath());
        }
    }
//设置当前工作目录
    public void setWorkingDirectory(ProcessBuilder processBuilder, String pyAddress) {
        File filePath = new File(pyAddress);
        processBuilder.directory(new File(new File(filePath.getParent()).getParent()));
    }
//执行线程
    public void executeProcess2(ProcessBuilder processBuilder, File jsonFile, StringBuilder output) {
        try {
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"gbk"));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "gbk"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.equals("  0%")) {
                        output.append(line).append("\n");
                        System.out.println(line);
                    }
                }
                while ((line = errorReader.readLine()) != null) {
                    output.append(line).append("\n");
                    System.err.println(line);
                }
            }
            process.waitFor();
            jsonFile.delete(); // 清理临时文件
        } catch (Exception e) {
            e.printStackTrace();
            output.append("Exception: ").append(e.getMessage()).append("\n");
        }
    }


    public void executeProcess(ProcessBuilder processBuilder, File jsonFile, StringBuilder output) {
        try {
            Process process = processBuilder.start();
            handleProcessOutput(process, output);
            int exitCode = process.waitFor();
            Files.delete(jsonFile.toPath());
            System.out.println("Exited with code " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    //获取控制台输出
    public void handleProcessOutput(Process process, StringBuilder output) throws InterruptedException {
        Thread outputThread = new Thread(() -> readStream(process.getInputStream(), output));
        Thread errorThread = new Thread(() -> readStream(process.getErrorStream(), System.err));

        outputThread.start();
        errorThread.start();

        outputThread.join();
        errorThread.join();

        System.out.println("Initialization complete, ID is xxx");
    }
//读数据流
    public void readStream(InputStream stream, Appendable appendable) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "gbk"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("  0%")) {
                    appendable.append(line).append("\n");
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
