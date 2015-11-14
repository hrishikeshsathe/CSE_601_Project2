package com.dm.clustering;

import com.dm.clustering.algos.agglomerative.AgglomerativeClustering;
import com.dm.clustering.algos.kmeans.KMeans;
import com.dm.clustering.data.pojo.Gene;
import com.dm.clustering.data.reader.InputParser;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static final String FILE_PATH = "E:\\DM\\Project 2\\";

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
}
