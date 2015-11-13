package com.dm.clustering.validation;


import com.dm.clustering.data.pojo.Gene;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class ExternalIndex {

    int[] comparisonArray;
    int[][] incidenceMatrixClusters;
    int[][] incidenceMatrixGroundTruth;
    static int SS = 0;
    static int DD = 1;
    static int SD = 2;
    static int DS = 3;


    public ExternalIndex(ArrayList<Gene> genesList) {
        comparisonArray = new int[4];
        populateIncidenceMatrix(genesList);
        for(int i = 0; i < incidenceMatrixClusters.length; i++) {
            for(int j = 0; j < incidenceMatrixClusters.length; j++) {
                switch(incidenceMatrixClusters[i][j]) {
                    case 0:{
                        if(incidenceMatrixClusters[i][j] == incidenceMatrixGroundTruth[i][j])
                            comparisonArray[DD]++;
                        else
                            comparisonArray[DS]++;
                    }
                    break;
                    case 1: {
                        if(incidenceMatrixClusters[i][j] == incidenceMatrixGroundTruth[i][j])
                            comparisonArray[SS]++;
                        else
                            comparisonArray[SD]++;
                    }
                    break;
                } // end of switch
            } // end of inner loop
        } // end of outer loop
    } // end of constructor

    public double calculateRandIndex() {
        double sum = IntStream.of(comparisonArray).sum();
        return (comparisonArray[SS] + comparisonArray[DD]) / sum;
    }

    public double calculateJaccardIndex() {
        double sum = comparisonArray[SS] + comparisonArray[SD] + comparisonArray[DS];
        return comparisonArray[SS] / sum;
    }

    /**
     * Populate the incidence matrix
     * @param genesList the genes in the dataset
     */
    private void populateIncidenceMatrix(ArrayList<Gene> genesList) {
        incidenceMatrixClusters = new int[genesList.size()][genesList.size()];
        incidenceMatrixGroundTruth = new int[genesList.size()][genesList.size()];
        for(int i = 0; i < genesList.size(); i++) {
            int clusterID = genesList.get(i).getClusterID();
            int groundTruth = genesList.get(i).getGroundTruth();
            for(int j = 0; j < genesList.size(); j++) {
                incidenceMatrixClusters[i][j] = (clusterID == genesList.get(j).getClusterID()) ? 1 : 0;
                incidenceMatrixGroundTruth[i][j] = (groundTruth == genesList.get(j).getGroundTruth()) ? 1 : 0;
            }
        }
    }

}
