package com.dm.clustering.data.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Samved on 11/1/2015.
 */
public class AgglomerativeCluster {


    private int clusterId = 0;
    private List<AgglomerativeCluster> subClusters = null;
    private List<Gene> genes = null;

    public  AgglomerativeCluster (Gene g, int clusterId)
    {
        this.level = 0;
        this.clusterId = clusterId;
        this.genes = new ArrayList<>();
        genes.add(g);
    }
    public AgglomerativeCluster (ClusterPair clusterPair, int clusterId)
    {
        subClusters = new ArrayList<>();
        genes = new ArrayList<>();
        AgglomerativeCluster subFirst = clusterPair.getFirstCluster();
        AgglomerativeCluster subSecond = clusterPair.getSecondCluster();
        subClusters.add(subFirst);
        subClusters.add(subSecond);
        genes.addAll(subFirst.getGenes());
        genes.addAll(subSecond.getGenes());
        level = Math.max(subFirst.getLevel() , subSecond.getLevel());
        this.clusterId = clusterId;
    }

    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Gene> getGenes() {
        return genes;
    }

    public void setGenes(List<Gene> genes) {
        this.genes = genes;
    }
    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }
}
