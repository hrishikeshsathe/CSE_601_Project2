package com.dm.clustering.data.reader;

import com.dm.clustering.data.pojo.Gene;
import com.dm.clustering.utility.StringUtility;
import com.dm.clustering.utility.Utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class InputParser {
    BufferedReader br;
    static final Logger LOGGER = Logger.getLogger(InputParser.class.getName());
    static final ConsoleHandler handler = new ConsoleHandler();

    /**
     * Initialize
     *
     * @param filePath path of the file to be parsed
     */
    public InputParser(String filePath) {
        LOGGER.addHandler(handler);
        try {
            FileReader fr = new FileReader(filePath);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    /**
     * Parse the input text file and return arraylist of genes
     *
     * @return ArrayList<Gene>
     */
    public ArrayList<Gene> parseData() {
        String line;
        ArrayList<Gene> genesList = new ArrayList<>();
        HashMap<Integer, Gene> geneHashMap = new HashMap<>();
        try {
            while ((line = br.readLine()) != null) {
                String[] strArray = line.split(StringUtility.TAB);
                Gene gene = new Gene();
                gene.setGeneID(Integer.parseInt(strArray[0]));
                gene.setClusterID(-1);
                gene.setGroundTruth(Integer.parseInt(strArray[1]));
                ArrayList<Double> geneExpList = new ArrayList<>();
                for (int i = 0; i < strArray.length - 2; i++) {
                    geneExpList.add(Double.valueOf(strArray[i + 2]));
                }
                gene.setGeneExpValues(geneExpList);
                genesList.add(gene);
                geneHashMap.put(gene.getGeneID(), gene);
            }
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
        Utility.GENE_MAP = geneHashMap;
        return genesList;
    }
}
