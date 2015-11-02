package com.dm.clustering;

import com.dm.clustering.algos.kmeans.KMeans;
import com.dm.clustering.data.pojo.Gene;
import com.dm.clustering.data.reader.InputParser;

import java.util.ArrayList;

public class Main {
    static final String FILE_ONE_PATH = "D:\\Courses\\DM\\Project 2\\Data\\iyer.txt";

    public static void main(String args[]) {
        InputParser inputParser = new InputParser(FILE_ONE_PATH);
        ArrayList<Gene> genesList = inputParser.parseData();
        if(genesList.size() > 0) {
            KMeans kMeans = new KMeans();
            kMeans.executeKMeans(genesList);
        }
    }
}
