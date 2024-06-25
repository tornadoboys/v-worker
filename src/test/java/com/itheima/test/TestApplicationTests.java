package com.itheima.test;

import com.itheima.test.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import com.itheima.test.entity.Sentinel2Band;
@SpringBootTest
class TestApplicationTests {
//    @Autowired
//    RedisService redisService;
    @Test
    void contextLoads() {
        String py_enrivonment = "F:\\program files\\Anaconda3\\envs\\environment_name\\python.exe";
        String py_address = "E:\\Workdata\\SpringBoot\\ToolCtr\\src\\main\\resources\\py\\log.py";
        String server_ip = "192.168.0.116";
        String file_name = "gf2-hz-xs-238.tif";

        File filePath = new File(py_address);
        String configPath = new File(filePath.getParent()).getParent()+"\\config";
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
        String jsonPath = configPath+"\\output.json";
        System.out.print(jsonPath);
//        if(!redisService.getList("01").isEmpty()) {
//            redisService.clearData("01");
//        }
//        ProcessBuilder processBuilder = new ProcessBuilder(py_enrivonment,py_address ,server_ip,file_name);
//        try {
//            Process process = processBuilder.start();
//            // 创建线程来读取标准输出
//            Thread outputThread = new Thread(() -> {
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "gbk"))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
////                        redisService.addToList("01", line);
//                        System.out.println("Output: " + line);
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//
//            // 创建线程来读取标准错误
//            Thread errorThread = new Thread(() -> {
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "gbk"))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        System.err.println(line);
////                        redisService.addToList("01", line);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//
//            // 启动线程
//            outputThread.start();
//            errorThread.start();
//
//            // 等待线程完成
//            outputThread.join();
//            errorThread.join();
//
//            int exitCode = process.waitFor();
//            System.out.println("Exited with code " + exitCode);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
    }
    @Test
    void getContext(){
        System.out.print(Sentinel2Band.getOrderByDescription("Red"));
//        System.out.print(redisService.getList("01"));
    }
}
