package com.dm.clustering.algos.kmeans;

import com.dm.clustering.data.pojo.Cluster;
import com.dm.clustering.data.pojo.Gene;
import com.dm.clustering.utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class KMeans {
    public static final int K = 10;
    ArrayList<Gene> centroids;
    HashMap<Integer, Cluster> clustersList;

    public void executeKMeans(ArrayList<Gene> genesList) {
        centroids = getInitialKCentroids(genesList);
        clustersList = new HashMap<>();

        // create initial clusters
        for(int i = 0; i < centroids.size(); i++) {
            Cluster cluster = new Cluster();
            cluster.setCentroid(genesList.get(centroids.get(i).getGeneID() - 1).getGeneExpValues());
            ArrayList<Integer> geneIDs = new ArrayList<>();
            geneIDs.add(centroids.get(i).getGeneID());
            cluster.setGeneIDs(geneIDs);
            clustersList.put(i + 1, cluster);
        }
    }

    /**
     * Get initial K centroids using K-means++
     * @param genesList
     * @return
     */
    private ArrayList<Gene> getInitialKCentroids(ArrayList<Gene> genesList) {
        int numberOfGenes = genesList.size();
        HashSet<Integer> selectedCentroidIndexes = new HashSet<>(K);
        ArrayList<Gene> selectedCentroids = new ArrayList<>();
        Random random = new Random();
        double distanceSquaredArray[] = new double[numberOfGenes];

        //KMeans++
        //Get first centroid selected uniformly at random
        int firstPointIndex = random.nextInt(numberOfGenes);
        selectedCentroidIndexes.add(firstPointIndex);
        selectedCentroids.add(genesList.get(firstPointIndex));

        //calculate distance from firstPoint
        for(int i = 0; i < numberOfGenes; i++) {
            if(i != firstPointIndex) {
                double distance = Utility.calculateEuclideanDistance(genesList.get(i).getGeneExpValues(),
                        genesList.get(firstPointIndex).getGeneExpValues());
                distanceSquaredArray[i] = distance * distance;
            }
        }

        //get rest of the points
        while(selectedCentroids.size() < K) {
            double distanceSquaredSum = 0.0;

            //calculate summation of D(x)^2
            for(int i = 0; i < numberOfGenes; i++) {
                if(!selectedCentroidIndexes.contains(i)) {
                    distanceSquaredSum += distanceSquaredArray[i];
                }
            }

            //next random point with probability proportional to D(x)^2
            double r = random.nextDouble() * distanceSquaredSum;
            int nextPointIndex = -1;
            double sum = 0.0;

            for(int i = 0; i < numberOfGenes; i++) {
                if(!selectedCentroidIndexes.contains(i)) {
                    sum += distanceSquaredArray[i];
                    if(sum >= r) {
                        nextPointIndex = i;
                        break;
                    }
                }
            }

            // found a point
            if(nextPointIndex >= 0) {
                // add new point to both lists
                selectedCentroidIndexes.add(nextPointIndex);
                selectedCentroids.add(genesList.get(nextPointIndex));
                ArrayList<Double> selectedCentroidExpValues = genesList.get(nextPointIndex).getGeneExpValues();

                if(selectedCentroids.size() < K) {
                    for(int i = 0; i < numberOfGenes; i++) {
                        if(!selectedCentroidIndexes.contains(i)) {
                            double distance = Utility.calculateEuclideanDistance(selectedCentroidExpValues,
                                    genesList.get(i).getGeneExpValues());
                            distance = distance * distance;
                            if(distance < distanceSquaredArray[i]) {
                                distanceSquaredArray[i] = distance;
                            }
                        }
                    } // end of for
                } // end of selectedCentroids.size() < K
            } // end of nextPointIndex >=0
            else {
                // no point found. break to prevent infinite loop
                break;
            }
        }
        return selectedCentroids;
    }
} // end of class
