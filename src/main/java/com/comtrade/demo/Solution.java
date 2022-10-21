package com.comtrade.demo;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Solution {
    public static void solve(final File folder) throws ExecutionException, InterruptedException {
        String outputString="";
        ExecutorService executor= Executors.newFixedThreadPool(Objects.requireNonNull(folder.listFiles()).length);
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                solve(fileEntry);
            } else {
                Future<String> future=executor.submit(()->readFile(fileEntry));
                outputString=outputString.concat(future.get());
            }
        }
        writeStringToFile(outputString);
    }
    public static int searchLine(String firstLine, String nonFirstLine){
        if (nonFirstLine.equals("") || nonFirstLine.length() < firstLine.length()) {
            return 0;
        }
        HashMap<Character, Integer> firstLineMap=convertStringToHashMap(firstLine);
        HashMap<Character, Integer> nonFirstLineMap=convertStringToHashMap(nonFirstLine);
        return findMinNumberOfRepetitions(firstLine, firstLineMap, nonFirstLineMap);
    }

    public static HashMap<Character,Integer> convertStringToHashMap(String line){
        HashMap<Character,Integer> tmpHashMap=new HashMap<>();
        for (Character c:  line.toCharArray()) {
            if(c >= 'A' && c <= 'Z') {
                c = (char) ((int)c + 32);
            }
            if(tmpHashMap.containsKey(c)){
                tmpHashMap.put(c,tmpHashMap.get(c)+1);
            }
            tmpHashMap.putIfAbsent(c,1);
        }
        return tmpHashMap;
    }

    public static int findMinNumberOfRepetitions(String firstLine, HashMap<Character, Integer> firstLineMap, HashMap<Character, Integer> nonFirstLineMap){
        int minNumberOfRepetitions=Integer.MAX_VALUE;
        for (Character c: firstLine.toCharArray()) {
            int tmp=0;
            if(c >= 'A' && c <= 'Z') {
                c = (char) ((int)c + 32);
            }
            if(nonFirstLineMap.containsKey(c)){
                tmp=nonFirstLineMap.get(c)/firstLineMap.get(c);
            }
            minNumberOfRepetitions=Math.min(minNumberOfRepetitions, tmp);
        }
        if(minNumberOfRepetitions==Integer.MAX_VALUE){
            return 0;
        }
        return minNumberOfRepetitions;
    }
    public static void writeStringToFile(String s){
        try {
            File file = new File("src\\main\\resources\\outputFile\\output.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(s);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(File file){
        Scanner input;
        String firstString="";
        String entireString="";
        try {
            input = new Scanner(new FileReader(file));
            try {
                if(input.hasNextLine()){
                    firstString=input.nextLine();
                }
                while (input.hasNextLine()) {
                    String tmp=input.nextLine();
                    entireString=entireString.concat(firstString+" in "+tmp+" "+searchLine(firstString,tmp)+" time \n");
                }
            }
            catch (Exception e) {
                System.out.println(Thread.currentThread().getName());
                System.out.println("An error occurred while reading the file..");
                System.out.println("Error: " + e);
            } finally {
                input.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File "+file.getName()+" does not exist or cannot be opened.");
        }
        return entireString;
    }
}
