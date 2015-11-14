package com.dm.clustering;

import com.dm.clustering.algos.agglomerative.AgglomerativeClustering;
import com.dm.clustering.algos.kmeans.KMeans;
import com.dm.clustering.data.pojo.Gene;
import com.dm.clustering.data.reader.InputParser;
import com.dm.clustering.utility.Utility;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final String FILE_PATH = "E:\\DM\\Project 2\\";

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        String algorithm;
        String fileName;
        int numberOfClusters;
        System.out.println("-------------------------------- Clustering Algorithms --------------------------------");
        System.out.println("Select clustering algorithm type. Enter K or k for KMeans and H or h for Hierarchical Agglomerative clustering:");
        algorithm = in.nextLine();
        System.out.println("Specify file name:");
        fileName = in.nextLine();
        System.out.println("Enter number of clusters desired:");
        numberOfClusters = in.nextInt();
        switch (algorithm){
            case "K":
            case "k":
                InputParser inputParser = new InputParser(FILE_PATH + fileName);
                ArrayList<Gene> genesList = inputParser.parseData();
                if (genesList.size() > 0) {
                    Utility.writeGenesToFile(genesList);
                    long startTime = System.currentTimeMillis();
                    KMeans kMeans = new KMeans();
                    KMeans.K = numberOfClusters;
                    kMeans.executeKMeans(genesList);
                    System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + "ms");
                }
                break;
            case "H":
            case "h":
                AgglomerativeClustering.executeAgglomerativeClustering(numberOfClusters , FILE_PATH + fileName);
        }


    }

//    public static void main(String args[]) {
//        int avg_iterations = 0;
//        double avg_time = 0.0;
//
//        for (int i = 0; i < 20; i++) {
//            InputParser inputParser = new InputParser(FILE_PATH);
//            ArrayList<Gene> genesList = inputParser.parseData();
//            if (genesList.size() > 0) {
//                long startTime = System.currentTimeMillis();
//                KMeans kMeans = new KMeans();
//                KMeans.K = 6;
//                kMeans.executeKMeans(genesList);
//                avg_iterations += kMeans.iterations;
//                avg_time += (System.currentTimeMillis() - startTime);
////                System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + "ms");
//            }
//        }
//        System.out.println("Average iterations = " + (avg_iterations / 20.0));
//        System.out.println("Average time = " + (avg_time / 20.0));
//    }
}
