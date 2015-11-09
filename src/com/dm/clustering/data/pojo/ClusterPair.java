package com.dm.clustering.data.pojo;

/**
 * Created by Samved on 11/1/2015.
 */
public class ClusterPair {

    AgglomerativeCluster firstCluster = null;
    AgglomerativeCluster secondCluster = null;

    public ClusterPair(AgglomerativeCluster cluster1, AgglomerativeCluster cluster2) {
        firstCluster = cluster1;
        secondCluster = cluster2;
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


}
