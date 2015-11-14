package com.dm.clustering.algos.agglomerative;

import com.dm.clustering.data.pojo.AgglomerativeCluster;
import com.dm.clustering.data.pojo.Cluster;
import com.dm.clustering.data.pojo.ClusterPair;
import com.dm.clustering.data.pojo.Gene;
import com.dm.clustering.data.reader.InputParser;
import com.dm.clustering.utility.Utility;
import com.dm.clustering.validation.ExternalIndex;
import com.dm.clustering.validation.InternalIndex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Samved on 11/1/2015.
 */
public class AgglomerativeClustering {

    static List<AgglomerativeCluster> currentClusters = new ArrayList<>();
    static Map<Integer, ClusterPair> clusterPairs = new LinkedHashMap<>();
    static List<Gene> originalGenes = new ArrayList<>();
    private static int clusterId = 0;
    private static Integer mergedClusterId = 0;
    //Add each data point as a different cluster

    public static void initiateClusters(String filePath) {
        InputParser ip = new InputParser(filePath);
        List<Gene> genes = ip.parseData();
        for (Gene g : genes) {
            currentClusters.add(new AgglomerativeCluster(g, clusterId++));
        }
        originalGenes.addAll(genes);
        mergedClusterId = clusterId;
    }

    public static void mergeClusters(ClusterPair cp) {

        AgglomerativeCluster parentCluster = new AgglomerativeCluster(cp, clusterId++);
        currentClusters.remove(cp.getFirstCluster());
        currentClusters.remove(cp.getSecondCluster());
        currentClusters.add(parentCluster);

        for (Integer i : clusterPairs.keySet()) {
            ClusterPair currentPair = clusterPairs.get(i);
            if(cp.getFirstCluster().getClusterId() == currentPair.getFirstCluster().getClusterId()  ||
                    cp.getFirstCluster().getClusterId() == currentPair.getSecondCluster().getClusterId()) {
                cp.getFirstCluster().setClusterId(i);
                cp.getFirstCluster().getGenes().addAll(currentPair.getFirstCluster().getGenes());
                cp.getFirstCluster().getGenes().addAll(currentPair.getSecondCluster().getGenes());
            }
            if(cp.getSecondCluster().getClusterId() == currentPair.getFirstCluster().getClusterId()  ||
                    cp.getSecondCluster().getClusterId() == currentPair.getSecondCluster().getClusterId()) {
                cp.getSecondCluster().setClusterId(i);
                cp.getSecondCluster().getGenes().addAll(currentPair.getFirstCluster().getGenes());
                cp.getSecondCluster().getGenes().addAll(currentPair.getSecondCluster().getGenes());
            }

        }
        clusterPairs.put(mergedClusterId++ , cp);

        //System.out.println("Merging: " + (cp.getFirstCluster().getClusterId()) + " and " + (cp.getSecondCluster().getClusterId()) + " into " + (parentCluster.getClusterId() ));
    }


    public static double getDistBetweenClustersUsingSingleLink(AgglomerativeCluster cluster1, AgglomerativeCluster cluster2) {
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

    public static double getDistBetweenClustersUsingCompleteLink(AgglomerativeCluster cluster1, AgglomerativeCluster cluster2) {
        List<Gene> clusterOneGenes = cluster1.getGenes();
        List<Gene> clusterTwoGenes = cluster2.getGenes();
        double distBetweenClusters = Double.MIN_VALUE;
        for (Gene g1 : clusterOneGenes) {
            for (Gene g2 : clusterTwoGenes) {
                double distBetweenGenes = Utility.calculateEuclideanDistance(g1.getGeneExpValues(), g2.getGeneExpValues());
                if (distBetweenClusters < distBetweenGenes )
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
            for (int j = 0; j < currentClusters.size(); j++) {
                if (i == j)
                    continue;
                AgglomerativeCluster temp2 = currentClusters.get(j);
                double distBetweenClusters = getDistBetweenClustersUsingSingleLink(temp1, temp2);
                //double distBetweenClusters = getDistBetweenClustersUsingCompleteLink(temp1, temp2);
                if (distBetweenClusters < minDist) {
                    first = temp1;
                    second = temp2;
                    minDist = distBetweenClusters;
                }
            }
        }
        return new ClusterPair(first, second, minDist);
    }

  /*  public static void printClusters() {
        System.out.print(currentClusters.size() + ":-  ");
        for (AgglomerativeCluster cluster : currentClusters)
        {
            System.out.print(cluster.getClusterId() + "::");
            for (Gene g : cluster.getGenes())
                System.out.print(g.getGeneID() + "  ");
            System.out.println();
        }
    }
*/
    public static void generateInputForDendrogram()
    {
        File f = new File("HACResults.txt");
        String singleRecord;
        try {
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Integer i : clusterPairs.keySet()) {
                ClusterPair cp = clusterPairs.get(i);
                singleRecord = (cp.getFirstCluster().getClusterId() + "," + cp.getSecondCluster().getClusterId() + ","
                        + cp.getMinDistance() + "," + (cp.getFirstCluster().getGenes().size() + cp.getSecondCluster().getGenes().size()));
                bw.write(singleRecord);
                bw.newLine();
            }
            bw.close();
            fw.close();
        }catch (IOException e){
            System.out.println("IO Exception while writing results to the file");
        }

    }
    public static void calculateValidationCoef(){
        HashMap<Integer, Cluster> clusters = new HashMap<>();
        ArrayList<Gene> geneList = new ArrayList<>();
        HashMap<Integer, Gene> geneMap= new HashMap<>();
        for(AgglomerativeCluster ac : currentClusters){
            Cluster c = new Cluster();
            HashSet<Integer> geneIds = new HashSet<>();
            for (Gene g : ac.getGenes()) {
                g.setClusterID(ac.getClusterId());
                geneIds.add(g.getGeneID());
                geneMap.put(g.getGeneID(), g);

            }
            geneList.addAll(ac.getGenes());
            c.setGeneIDs(geneIds);
            clusters.put(ac.getClusterId(), c);
        }
        ExternalIndex ei = new ExternalIndex(geneList);
        System.out.println("Jaccard Index : " + ei.calculateJaccardIndex());
        System.out.println("Rand Index : " + ei.calculateRandIndex());
        InternalIndex ii = new InternalIndex();
        System.out.println("Silhouette Index: " + ii.calculateSilhouetteCoefficient(clusters, geneList, geneMap));
        generateInputFileforPCA(geneMap);

    }

    private static void generateInputFileforPCA(HashMap<Integer, Gene> geneMap) {

        try {
            FileWriter fw = new FileWriter(new File("HACInputForPCA.txt"));
            BufferedWriter bw = new BufferedWriter(fw);

            for(int i = 0; i<originalGenes.size(); i++){
                Gene g = originalGenes.get(i);
                int clusterId = (geneMap.get(g.getGeneID())).getClusterID();
                bw.write("" + clusterId);
                bw.newLine();
            }
            bw.close();
            fw.close();

        }catch (IOException e){
            System.out.println("IOException while creating file HACInputForPCA.txt");
        }


    }

    public static void executeAgglomerativeClustering(int k, String filePath) {
        initiateClusters(filePath);
        while (currentClusters.size() > 1) {
            //System.out.println();
            if(currentClusters.size() == k)
                calculateValidationCoef();
            mergeClusters(findClosestClusters());
        }
        // printClusters();
        generateInputForDendrogram();
    }
}
