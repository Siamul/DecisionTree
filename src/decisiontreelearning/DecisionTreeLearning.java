/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreelearning;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author Siamul Karim Khan
 */
public class DecisionTreeLearning {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //String csvFile = "mitchell_data_set.csv";
        String csvFile = "assignment1_data_set.csv";
        String line;
        String[] tableHeader;
        String[] newHeader;
        Vector<String[]> temp = new Vector<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String tHeader = br.readLine();
            tableHeader = tHeader.split(",");
            newHeader = new String[tableHeader.length];
            for(int i = 0; i<tableHeader.length; i++)
            {
                newHeader[i] = String.valueOf(i);
                //newHeader[i] = "a";
            }
            temp.add(newHeader);
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                temp.add(row);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        String[][] table = new String[temp.size()][];
        table = temp.toArray(table);
        double testAccuracy = 0, testPrecision = 0, testRecall = 0;
        double trainAccuracy = 0, trainPrecision = 0, trainRecall = 0;
        //printTree(dt.getTree());
        System.out.format("%s%24s%24s%24s%24s%24s", "Accuracy", "Precision", "Recall", "Train Set Accuracy", "Train Set Precision", "Train Set Recall");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        for(int j = 0; j<100; j++)
        {
            String[][][] setSel = RandomSetSelector.separateSet(table, 80);
            String[][] trainSet = setSel[0];
            String[][] testSet = setSel[1];
            //printTable(trainSet);
           //System.out.println("============================");
            //printTable(testSet);
            Vector<Integer> attrList = new Vector<>();
            for(int i = 0; i<table[0].length - 1; i++)
            {
                attrList.add(i);
            }
            DecisionTree dt = new DecisionTree();
            dt.learn(trainSet,attrList);
            double[] result = dt.test(testSet);
            testAccuracy += result[0];
            testPrecision += result[1];
            testRecall += result[2];
            System.out.format("%f%24f%24f", result[0], result[1], result[2]);
            //System.out.println(result[0] + "," + result[1] + "," + result[2]);
            result = dt.test(trainSet);
            trainAccuracy += result[0];
            trainPrecision += result[1];
            trainRecall += result[2];
            System.out.format("%24f%24f%24f", result[0], result[1], result[2]);
            System.out.println();
        }
        testAccuracy /= 100;
        testPrecision /= 100;
        testRecall /= 100;
        trainAccuracy /= 100;
        trainPrecision /= 100;
        trainRecall /= 100;
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Average accuracy over 100 runs = " + testAccuracy);
        System.out.println("Average precision over 100 runs = " + testPrecision);
        System.out.println("Average recall over 100 runs = " + testRecall);
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Average accuracy on training set over 100 runs= " + trainAccuracy);
        System.out.println("Average precision on training set over 100 run = " + trainPrecision);
        System.out.println("Average recall on training set over 100 run = " + trainRecall);
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        //DecisionTreeNode tree = dt.getTree();
        //printTree(tree);
        /*printTable(table);
        System.out.println("Train Set: ");
        printTable(trainSet);
        System.out.println("Test Set: ");
        printTable(testSet);
        //System.out.println("gain for outlook = " + InformationGain.infoGain(table, "Wind"));
        /*for(int i = 0; i<table[0].length; i++)
        {
            System.out.print(table[1][i]);
        }*/
    }
    
    
    
    static void printTable(String[][] table)
    {
        for(int i = 0; i<table.length; i++)
        {
            for(int j = 0; j<table[0].length; j++)
            {
                System.out.print(table[i][j]);
                if(j<table[0].length - 1) System.out.print(",");
            }
            System.out.println();
        }
    }
    
    static void printTree(DecisionTreeNode root)
    {
        System.out.println(root.attribute);
        if(root.child != null)
        {
            for(int i = 0; i<root.child.length; i++)
            {
                System.out.println(root.childList[i]);
                printTree(root.child[i]);
            }
        }
       
    }
    
}
