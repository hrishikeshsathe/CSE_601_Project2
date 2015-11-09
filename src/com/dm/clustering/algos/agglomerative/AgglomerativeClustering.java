package com.dm.clustering.algos.agglomerative;

import com.dm.clustering.data.pojo.AgglomerativeCluster;
import com.dm.clustering.data.pojo.ClusterPair;
import com.dm.clustering.data.pojo.Gene;
import com.dm.clustering.data.reader.InputParser;
import com.dm.clustering.utility.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samved on 11/1/2015.
 */
public class AgglomerativeClustering {

    static List<AgglomerativeCluster> currentClusters = new ArrayList<>();

    //Add each data point as a different cluster
    public static void initiateClusters() {
        InputParser ip = new InputParser("E:\\DM\\Project 2\\iyer.txt");
        List<Gene> genes = ip.parseData();
        for (Gene g : genes) {
            currentClusters.add(new AgglomerativeCluster(g));
        }
    }

    //TODO
    public static void mergeClusters(ClusterPair cp) {

        AgglomerativeCluster superAggCluster = new AgglomerativeCluster(cp);
        currentClusters.add(superAggCluster);
        currentClusters.remove(cp.getFirstCluster());
        currentClusters.remove(cp.getSecondCluster());
    }

    //TODO
    public static double getDistBetweenClusters(AgglomerativeCluster cluster1, AgglomerativeCluster cluster2) {
        List<Gene> clusterOneGenes = cluster1.getGenes();
        List<Gene> clusterTwoGenes = cluster2.getGenes();
        double distBetweenClusters = Double.MAX_VALUE;
        for (Gene g1 : clusterOneGenes) {
            for (Gene g2 : clusterTwoGenes) {
                double distBetweenGenes = Utility.calculateEuclideanDistance(g1.getGeneExpValues(), g2.getGeneExpValues());
                if (distBetweenGenes < distBetweenClusters)
                    distBetweenClusters = distBetweenGenes;
            }
        }
        return distBetweenClusters;
    }

    public static ClusterPair findClosestClusters() {

        AgglomerativeCluster first = null;
        AgglomerativeCluster second = null;
        double minDist = Double.MAX_VALUE;

        for (int i = 0; i < currentClusters.size(); i++) {
            AgglomerativeCluster temp1 = currentClusters.get(i);
            for (int j = i + 1; j < currentClusters.size(); j++) {
                AgglomerativeCluster temp2 = currentClusters.get(j);
                double distBetweenClusters = getDistBetweenClusters(temp1, temp2);
                if (distBetweenClusters < minDist) {
                    first = temp1;
                    second = temp2;
                    minDist = distBetweenClusters;
                }
            }
        }
        return new ClusterPair(first, second);
    }

    public static void printClusters() {
        for (AgglomerativeCluster cluster : currentClusters)
        {
            for (Gene g : cluster.getGenes())
                System.out.print(g.getGeneID() + "  ");
        }
    }

    public static void main(String[] args) {
        initiateClusters();

        while (currentClusters.size() > 1) {
            printClusters();
            System.out.println();
            mergeClusters(findClosestClusters());
        }

    }
}
