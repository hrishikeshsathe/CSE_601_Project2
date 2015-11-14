package com.dm.clustering.utility;

import com.dm.clustering.Main;
import com.dm.clustering.data.pojo.Cluster;
import com.dm.clustering.data.pojo.Gene;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Utility {

    public static HashMap<Integer, Gene> GENE_MAP = new HashMap<>();
    /**
     * Calculate euclidean distance between two lists of points
     * @param list1 points in list1
     * @param list2 points in list2
     * @return distance
     */
    public static double calculateEuclideanDistance(ArrayList<Double> list1, ArrayList<Double> list2) {
        double sum = 0.0;
        for(int i = 0; i < list1.size(); i++) {
            sum += Math.pow(list1.get(i) - list2.get(i), 2);
        }
        return Math.sqrt(sum);
    }

    public static void writeGenesToFile(ArrayList<Gene> genesList) {
        File file = new File(Main.FILE_PATH + "geneData.txt");
        BufferedWriter w = null;
        try {
            w = new BufferedWriter(new FileWriter(file));
            for(int j = 0; j < genesList.size(); j++) {
                int size = genesList.get(j).getGeneExpValues().size();
                String line = "";
                for(int i = 0; i < size - 1; i ++) {
                    line += genesList.get(j).getGeneExpValues().get(i) + "\t";
                }
                line += genesList.get(j).getGeneExpValues().get(size - 1);
                w.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeCentroidsToFile(HashMap<Integer, Cluster> clusterList) {
        File file = new File(Main.FILE_PATH + "centroids.txt");
        BufferedWriter w = null;
        try {
            w = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < clusterList.size(); i++) {
                String line = "";

                ArrayList<Double> centroid = clusterList.get(i + 1).getCentroid();
                for(int j = 0; j < centroid.size() - 1; j++) {
                    line += centroid.get(j) + "\t";
                }
                line += centroid.get(centroid.size() - 1);
                w.write(line + "\n");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeLabelsToFile(ArrayList<Gene> genesList) {
        File file = new File(Main.FILE_PATH + "geneLabels.txt");
        BufferedWriter w = null;
        try {
            w = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < genesList.size() - 1; i++) {
                w.write(genesList.get(i).getClusterID() + "\n");
            }
            w.write(genesList.get(genesList.size() - 1).getClusterID() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
