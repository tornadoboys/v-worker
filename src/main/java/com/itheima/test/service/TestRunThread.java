package com.itheima.test.service;
import com.itheima.test.entity.Environment;
import com.itheima.test.utils.RunThreadTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class TestRunThread {
    @Autowired
    RunThreadTools runThreadTools;
    public ResponseEntity<String> getExternalProcessOutput3(List<Environment> environments) {
        String combinedOutput="";
        int i=0;
        System.out.print("当前获取到的environments"+environments.size());
        for (Environment environment : environments) {
            StringBuilder output = new StringBuilder();
            List<String> command = runThreadTools.buildCommand(environment);
            File jsonFile = runThreadTools.createJsonFile(environment);
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            runThreadTools.setWorkingDirectory(processBuilder, environment.py_address);
            Thread thread = new Thread(() -> runThreadTools.executeProcess(processBuilder, jsonFile, output));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Error executing external process: " + e.getMessage());
            }
            combinedOutput = combinedOutput + "Output from script "+i+ ":\n" + output.toString() ;
            i++;
//            System.out.print(output.toString());
        }

//        String combinedOutput = "Output from script 1:\n" + output1.toString() + "\nOutput from script 2:\n" + output2.toString();
        return ResponseEntity.ok(combinedOutput);

    }

    public ResponseEntity<String> getExternalProcessOutput4(@RequestBody List<Environment> environments) {
        StringBuilder combinedOutput = new StringBuilder();
        //ExecutorService创建了一个固定大小为environments的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(environments.size());
        //Future接口代表一个异步计算的结果。它提供了一些方法来检查任务是否完成，等待任务完成，并获取任务的结果。
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < environments.size(); i++) {
            final int index = i;
            Environment environment = environments.get(i);
            //执行一个计算任务
            Callable<String> task = () -> {
                StringBuilder output = new StringBuilder();
                List<String> command = runThreadTools.buildCommand(environment);
                File jsonFile = runThreadTools.createJsonFile(environment);
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                runThreadTools.setWorkingDirectory(processBuilder, environment.py_address);
                runThreadTools.executeProcess2(processBuilder, jsonFile, output);
                return "Output from script " + index + ":\n" + output.toString();
            };
            futures.add(executorService.submit(task));
        }

        for (Future<String> future : futures) {
            try {
                // 使用future,等待任务完成并获取结果
                combinedOutput.append(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body("Error executing external process: " + e.getMessage());
            }
        }

        executorService.shutdown();
        return ResponseEntity.ok(combinedOutput.toString());
    }



    public ResponseEntity<String> getExternalProcessOutput(Environment environment) {
        StringBuilder output = new StringBuilder();

        if (runThreadTools.isInvalidEnvironment(environment)) {
            throw new IllegalArgumentException("One or more required parameters are null");
        }

        List<String> command = runThreadTools.buildCommand(environment);

        File jsonFile = runThreadTools.createJsonFile(environment);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        runThreadTools.setWorkingDirectory(processBuilder, environment.py_address);

        try {
            Process process = processBuilder.start();
            runThreadTools.handleProcessOutput(process, output);

            int exitCode = process.waitFor();
            Files.delete(jsonFile.toPath());

//            System.out.println("Exited with code " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error executing external process: " + e.getMessage());
        }
        return ResponseEntity.ok(output.toString());
    }


}
