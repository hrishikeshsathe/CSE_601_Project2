package com.dm.clustering.validation;

import com.dm.clustering.data.pojo.Cluster;
import com.dm.clustering.data.pojo.Gene;
import com.dm.clustering.utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class InternalIndex {

    /**
     * Calculate the silhouette coefficient
     * @param clusterList clusters as key and genes in the cluster as values
     * @param geneList list of all genes
     * @return silhouette coefficient
     */
    public double calculateSilhouetteCoefficient(HashMap<Integer, Cluster> clusterList, ArrayList<Gene> geneList, HashMap<Integer, Gene> geneMap) {
        double silhouetteIndex = 0.0;
        for (int i = 0; i < geneList.size(); i++) {
            int clusterID = geneList.get(i).getClusterID();
            double a = calculateAverageDistanceToCluster(geneList.get(i).getGeneExpValues(),
                    clusterList.get(clusterID).getGeneIDs(), geneMap);
            double b = Double.MAX_VALUE;
            for (Integer clusterIndex : clusterList.keySet()) {
                if(clusterIndex != clusterID) {
                    b = Math.min(b, calculateAverageDistanceToCluster(geneList.get(i).getGeneExpValues(),
                            clusterList.get(clusterIndex).getGeneIDs(), geneMap));
                }
            }
            silhouetteIndex += (b - a) / Math.max(a, b);
        }
        return silhouetteIndex / geneList.size();
    }

    /**
     * Calculate the average distance of a point with a cluster
     * @param geneExpValues exp values of a point
     * @param geneIDs list of genes in that cluster
     * @return average distance
     */


    private double calculateAverageDistanceToCluster(ArrayList<Double> geneExpValues, HashSet<Integer> geneIDs,
                                                     HashMap<Integer, Gene> geneMap) {
        double sum = 0.0;
        for (Integer geneID : geneIDs) {
            sum += Utility.calculateEuclideanDistance(geneExpValues, geneMap.get(geneID).getGeneExpValues());
        }
        return sum / geneIDs.size();
    }
}


