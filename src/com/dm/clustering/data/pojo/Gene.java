package com.dm.clustering.data.pojo;

import java.util.ArrayList;

/**
 * Class to store genes
 */
public class Gene {

    private int geneID;
    private ArrayList<Double> geneExpValues;
    private int clusterID;
    private int groundTruth;


    /**
     * @return the groundTruth for the gene
     */
    public int getGroundTruth() {
        return groundTruth;
    }

    /**
     * Set the groundTruth for the gene
     * @param groundTruth
     */
    public void setGroundTruth(int groundTruth) {
        this.groundTruth = groundTruth;
    }

    /**
     * @return the clusterID for the gene
     */
    public int getClusterID() {
        return clusterID;
    }

    /**
     * Set the clusterID for the gene
     * @param clusterID
     */
    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }

    /**
     * @return the geneID for the gene
     */
    public int getGeneID() {
        return geneID;
    }

    /**
     * Set the geneID for the gene
     * @param geneID
     */
    public void setGeneID(int geneID) {
        this.geneID = geneID;
    }

    /**
     * Return a list of expression values for the gene
     * @return
     */
    public ArrayList<Double> getGeneExpValues() {
        return geneExpValues;
    }

    /**
     * Set the gene expression values for the gene
     * @param geneExpValues
     */
    public void setGeneExpValues(ArrayList<Double> geneExpValues) {
        this.geneExpValues = geneExpValues;
    }
}
