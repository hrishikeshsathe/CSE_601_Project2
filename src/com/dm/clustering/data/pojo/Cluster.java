package com.dm.clustering.data.pojo;

import java.util.ArrayList;
import java.util.HashSet;

public class Cluster {
    private ArrayList<Double> centroid;
    private HashSet<Integer> geneIDs;

    public ArrayList<Double> getCentroid() {
        return centroid;
    }

    public void setCentroid(ArrayList<Double> centroid) {
        this.centroid = centroid;
    }

    public HashSet<Integer> getGeneIDs() {
        return geneIDs;
    }

    public void setGeneIDs(HashSet<Integer> geneIDs) {
        this.geneIDs = geneIDs;
    }
}
