package com.dm.clustering.data.pojo;

/**
 * Created by Samved on 11/1/2015.
 */
public class ClusterPair {

    AgglomerativeCluster firstCluster = null;
    AgglomerativeCluster secondCluster = null;
    double minDistance = Double.MAX_VALUE;



    private int sampleCount = 0;
    public ClusterPair(AgglomerativeCluster cluster1, AgglomerativeCluster cluster2, double minDist) {
        firstCluster = cluster1;
        secondCluster = cluster2;
        minDistance = minDist;
       // sampleCount = sampleCt;
    }

    public AgglomerativeCluster getFirstCluster() {
        return firstCluster;
    }

    public void setFirstCluster(AgglomerativeCluster firstCluster) {
        this.firstCluster = firstCluster;
    }

    public AgglomerativeCluster getSecondCluster() {
        return secondCluster;
    }

    public void setSecondCluster(AgglomerativeCluster secondCluster) {
        this.secondCluster = secondCluster;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }
    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }
}
