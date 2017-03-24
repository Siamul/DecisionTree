/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreelearning;

import java.util.Vector;

/**
 *
 * @author Siamul Karim Khan
 */
public class InformationGain {
    private static double entropy(String[][] set) {
        int classLoc = set[0].length - 1;
        //System.out.print(classLoc);
        double positiveCount = 0;
        double negativeCount = 0;
        for (int i = 1; i < set.length; i++) {
            if (set[i][classLoc].compareTo("0") == 0) {
                negativeCount++;
            } else {
                positiveCount++;
            }
        }
        //System.out.println("+" + positiveCount + " , " + "-" + negativeCount);
        double posProp = positiveCount / (positiveCount + negativeCount);
        return -posProp * (Math.log(posProp) / Math.log(2)) - (1-posProp) * (Math.log(1-posProp) / Math.log(2));
    }

    private static double weightedEntropy(String[][] set, int attrLoc) {
        int classLoc = set[0].length - 1;
        double totalSamples = (double) set.length - 1;
   
        Vector<String> temp = new Vector<>();
        temp.add(set[1][attrLoc]);
        for (int i = 2; i < set.length; i++) {
            if (!temp.contains(set[i][attrLoc])) {
                temp.add(set[i][attrLoc]);
            }
        }
        String[] attrVal = new String[temp.size()];
        attrVal = temp.toArray(attrVal);
        double[] positiveCount = new double[attrVal.length];
        double[] negativeCount = new double[attrVal.length];
        for(int i = 0; i<positiveCount.length; i++)
        {
            positiveCount[i]=0;
            negativeCount[i]=0;
        }
        for(int i = 0; i<set.length; i++)
        {
            for(int j = 0; j<attrVal.length; j++)
            {
                if(attrVal[j].compareTo(set[i][attrLoc]) == 0)
                {
                    if(set[i][classLoc].compareTo("0") == 0)
                    {
                        negativeCount[j]++;
                    }
                    else
                    {
                        positiveCount[j]++;
                    }
                    break;
                }
            }
        }
        //System.out.println(attrVal.length);
        /*for(int i = 0; i<attrVal.length; i++)
        {
            System.out.println(positiveCount[i] + " " + negativeCount[i]);
        }*/
        double[] entropy = new double[attrVal.length];
        for(int i = 0; i<attrVal.length; i++)
        {
            double posProp = positiveCount[i]/(positiveCount[i]+negativeCount[i]);
            if(posProp == 0 || posProp == 1)
            {
                entropy[i] = 0;
            }
            else
            {
                entropy[i] = -posProp * (Math.log(posProp) / Math.log(2)) - (1-posProp) * (Math.log(1-posProp) / Math.log(2));
            }
            //System.out.println(entropy[i]);
        }
        
        double weightedEntropy = 0;
        //System.out.println(totalSamples);
        for(int i = 0; i<entropy.length; i++)
        {
            weightedEntropy += ((positiveCount[i]+negativeCount[i])/totalSamples) * entropy[i];
        }
       // System.out.println(weightedEntropy);
        return weightedEntropy;
    }

    public static double infoGain(String[][] table, int attrLoc) {
        /*int attrLoc;
        for (attrLoc = 0; attrLoc < table[0].length; attrLoc++) {
            if (attribute.compareTo(table[0][attrLoc]) == 0) {
                break;
            }
        }*/
        if(attrLoc == table[0].length) return 0;
       // System.out.println(entropy(table));
        //System.out.println(weightedEntropy(table,attrLoc));
        double gain = entropy(table) - weightedEntropy(table,attrLoc);
        return gain;
    }
}
