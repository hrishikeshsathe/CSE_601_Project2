package com.dm.clustering.utility;

import java.util.ArrayList;

public class Utility {

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
}
