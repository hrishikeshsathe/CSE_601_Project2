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
    HashMap<Integer, Cluster> clustersList;

    /**
     * Execute the KMeans algorithm using KMeans++ for initial centroid selection on the given dataset
     * @param genesList list of all the genes in the data set
     */
    public void executeKMeans(ArrayList<Gene> genesList) {
        ArrayList<Gene> centroids = getInitialKCentroidsUsingKMeansPlusPlus(genesList);
        clustersList = new HashMap<>();

        // create initial clusters
        System.out.println("Initial Clusters:");
        for(int i = 0; i < centroids.size(); i++) {
            System.out.print(centroids.get(i).getGeneID()+",");
            Cluster cluster = new Cluster();
            cluster.setCentroid(genesList.get(centroids.get(i).getGeneID() - 1).getGeneExpValues());
            HashSet<Integer> geneIDs = new HashSet<>();
            cluster.setGeneIDs(geneIDs);
            clustersList.put(i + 1, cluster);
        }
        System.out.println();

        System.out.println("New Clusters:");
        // Assign points to clusters
        boolean clustersAreChanging = true;
        while(clustersAreChanging) {
            clustersAreChanging = false;
            for (Gene g: genesList) {
                int newClusterID = -1;
                int currentClusterID = g.getClusterID();
                int geneID = g.getGeneID();
                double minDistance = Double.MAX_VALUE;

                // calculate distance of current point with each cluster centroid and save the cluster ID which is closest
                for (Integer key : clustersList.keySet()) {
                        double temp = Utility.calculateEuclideanDistance(clustersList.get(key).getCentroid(),
                                g.getGeneExpValues());
                        if (temp < minDistance) {
                            minDistance = temp;
                            newClusterID = key;
                        }
                }

                // update clusters
                if (newClusterID != -1 && currentClusterID != newClusterID) {
                    clustersAreChanging = true;
                    HashSet<Integer> tempList = clustersList.get(newClusterID).getGeneIDs();
                    tempList.add(geneID);

                    // remove gene from current cluster
                    if(currentClusterID != -1) {
                        clustersList.get(currentClusterID).getGeneIDs().remove(geneID);
                        clustersList.get(currentClusterID).setCentroid(recalculateCentroidForRemoveGene(currentClusterID,
                                tempList.size(), g.getGeneExpValues()));
                    }

                    // add gene to new cluster
                    clustersList.get(newClusterID).setCentroid(recalculateCentroidForAddGene(newClusterID,
                            tempList.size(), g.getGeneExpValues()));
                   g.setClusterID(newClusterID);
                    clustersList.get(newClusterID).setGeneIDs(tempList);
                }
            }
        }

        // print clusters
        for(Integer key: clustersList.keySet()) {
            System.out.println(clustersList.get(key).getGeneIDs());
        }
    }

    /**
     * Get initial K centroids using K-means++
     * @param genesList all the genes in the data set
     * @return K Centroids that are selected using Kmeans++
     */
    private ArrayList<Gene> getInitialKCentroidsUsingKMeansPlusPlus(ArrayList<Gene> genesList) {
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

    /**
     * Recalculate the centroid for a new point to be added
     * @param clusterID the id for the cluster to be changed
     * @param size number of genes in the cluster
     * @param newPoint the point to be added to the cluster
     * @return new centroid after adding the point
     */
    private ArrayList<Double> recalculateCentroidForAddGene(int clusterID, int size, ArrayList<Double> newPoint) {
        ArrayList<Double> tempCentroid = clustersList.get(clusterID).getCentroid();
        for (int j = 0; j < tempCentroid.size(); j++) {
            tempCentroid.set(j,
                    ((tempCentroid.get(j) * (size - 1)) + newPoint.get(j)) / size);
        }
        return tempCentroid;
    }

    /**
     * Recalculate the centroid for a point to be deleted
     * @param clusterID the id for the cluster to be changed
     * @param size number of genes in the cluster
     * @param oldPoint the point to be removed from the cluster
     * @return new centroid after removing the old point
     */
    private ArrayList<Double> recalculateCentroidForRemoveGene(int clusterID, int size, ArrayList<Double> oldPoint) {
        ArrayList<Double> tempCentroid = clustersList.get(clusterID).getCentroid();
        for (int j = 0; j < tempCentroid.size(); j++) {
            tempCentroid.set(j,
                    ((tempCentroid.get(j) * size) - oldPoint.get(j)) / (size - 1));
        }
        return tempCentroid;
    }
} // end of class
