package com.dm.clustering.validation;


import java.util.stream.IntStream;

public class ExternalIndex {

    int[] comparisonArray;
    static int SS = 0;
    static int DD = 1;
    static int SD = 2;
    static int DS = 3;


    public ExternalIndex(int[][] clusters, int[][] groundTruth) {
        comparisonArray = new int[4];

        for(int i = 0; i < clusters.length; i++) {
            for(int j = 0; j < clusters.length; j++) {
                switch(clusters[i][j]) {
                    case 0:{
                        if(clusters[i][j] == groundTruth[i][j])
                            comparisonArray[DD]++;
                        else
                            comparisonArray[DS]++;
                    }
                    break;
                    case 1: {
                        if(clusters[i][j] == groundTruth[i][j])
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

}
