/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontreelearning;

/**
 *
 * @author Siamul Karim Khan
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 *
 * @author Siamul Karim Khan
 */
public class RandomSetSelector {
    public static String[][][] separateSet(String[][] table, int percent)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<table.length; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        int trainSetIndex = ((table.length - 1)*percent)/100;
        Vector<String[]> trainSet = new Vector<>();
        Vector<String[]> testSet = new Vector<>();
        trainSet.add(table[0]);
        testSet.add(table[0]);
        for(int i = 0; i<trainSetIndex; i++)
        {
            trainSet.add(table[list.get(i)]);
        }
        
        for(int i = trainSetIndex; i<list.size(); i++)
        {
            testSet.add(table[list.get(i)]);
        }
        String[][][] retVal = new String[2][][];
        retVal[0] = new String[trainSet.size()][];
        retVal[1] = new String[testSet.size()][];
        retVal[0] = trainSet.toArray(retVal[0]);
        retVal[1] = testSet.toArray(retVal[1]);
        return retVal;
    }
}

