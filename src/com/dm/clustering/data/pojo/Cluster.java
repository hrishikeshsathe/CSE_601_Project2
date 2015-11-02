package com.dm.clustering.data.pojo;

import java.util.ArrayList;

public class Cluster {
    private ArrayList<Double> centroid;
    private ArrayList<Integer> geneIDs;

    public ArrayList<Double> getCentroid() {
        return centroid;
    }

    public void setCentroid(ArrayList<Double> centroid) {
        this.centroid = centroid;
    }

    public ArrayList<Integer> getGeneIDs() {
        return geneIDs;
    }

    public void setGeneIDs(ArrayList<Integer> geneIDs) {
        this.geneIDs = geneIDs;
    }
}
