package com.itheima.test.controller;

import com.google.gson.Gson;
import com.itheima.test.entity.EnviromentWrapper;
import com.itheima.test.entity.Environment;
import com.itheima.test.service.TestRunThread;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import java.util.UUID;
@Api(tags = "v-worker测试接口")
@RestController
public class ProcessController {
    @Autowired
    TestRunThread testRunThread;

    @ApiOperation(value = "测试单线程", notes = "获取单个工具")
    @PostMapping("/runThread")
    public ResponseEntity<String> runThread(@RequestBody Environment environment) {
        StringBuilder output = new StringBuilder();

        List<String> command = new ArrayList<>();
        command.add(environment.py_environment);
        command.add(environment.py_address);

        Gson gson = new Gson();
        // 将对象转换为 JSON 字符串
        String jsonString = gson.toJson(environment);
        System.out.println(jsonString);  // 打印输出 JSON 字符串，用于检查

        File filePath = new File(environment.py_address);
        String configPath = new File(filePath.getParent()).getParent() + "\\test_config";
        File configDir = new File(configPath);
        if (!configDir.exists()) {
            // 如果文件夹不存在，则创建文件夹
            boolean created = configDir.mkdirs();
            if (created) {
                System.out.println("Directory created successfully at: " + configPath);
            } else {
                System.out.println("Failed to create directory at: " + configPath);
            }
        } else {
            System.out.println("Directory already exists at: " + configPath);
        }
        String jsonPath = configPath + "\\output.json";
        System.out.print(jsonPath);

        // 定义 JSON 文件路径
        File jsonFile = new File(configDir, UUID.randomUUID() + "config.json");
        //UUID.randomUUID()生成唯一id
        command.add(jsonFile.getAbsolutePath());
        // 将 JSON 保存到文件
        try (FileWriter writer = new FileWriter(jsonFile)) {
            gson.toJson(environment, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (environment.py_environment == null || environment.py_address == null ||
                environment.input_paths == null ||
                environment.out_paths == null) {
            throw new IllegalArgumentException("One or more required parameters are null");
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(new File(filePath.getParent()).getParent()));
        try {
            Process process = processBuilder.start();
            // 创建线程来读取标准输出
            Thread outputThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.equals("  0%")) {  // 如果读取到的不是'  0%'
                            output.append(line).append("\n"); // 追加每一行输出加上换行符
                            System.out.println(line);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // 创建线程来读取标准错误
            Thread errorThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "gbk"))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println("Error: " + line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // 启动线程
            outputThread.start();
            errorThread.start();

            // 等待线程完成
            outputThread.join();
            errorThread.join();

            //线程完成后将图片的数据发送给图片服务器（待完成）
            System.out.println("初始化完毕，id号为xxx");


            int exitCode = process.waitFor();
            Files.delete(jsonFile.toPath());
            System.out.println("Exited with code " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error executing external process: " + e.getMessage());
        }
        return ResponseEntity.ok(output.toString());
    }

    @ApiOperation(value = "测试多线程", notes = "测试多个工具")
    @PostMapping("/runThreadGroup")
    public ResponseEntity<String> runThreadGroup(@RequestBody List<Environment> environments) {
        return testRunThread.getExternalProcessOutput4(environments);
    }

}
