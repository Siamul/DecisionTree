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
public class DecisionTree {

    DecisionTreeNode tree = null;
    int hashTableSize = 10;
    void learn(String[][] table, Vector<Integer> attrList)
    {
        tree = makeDecTree(table,attrList);
    }
    DecisionTreeNode getTree()
    {
        return tree;
    }
    double[] test(String[][] table)
    {
        double truePositive = 0; 
        double trueNegative = 0;
        double falsePositive = 0;
        double falseNegative = 0;
               
        for(int i = 1; i<table.length; i++)
        {
            DecisionTreeNode temp = tree;
            while(temp.child != null)
            {
                //System.out.println(i + ", " + Integer.parseInt(temp.attribute));
                String attrVal = table[i][Integer.parseInt(temp.attribute)];
                //temp = temp.child[Integer.parseInt(attrVal)];
                int index;
                for(index = 0; index<temp.childList.length; index++)
                {
                    if(temp.childList[index].compareTo(attrVal) == 0)
                    {
                        break;
                    }
                }
                if(index == temp.childList.length)
                {
                    //System.out.println("No Child");
                    break;
                }
                temp = temp.child[index];
            }
            if(temp.result == 0)
            {
                if(table[i][table[0].length - 1].compareTo("0") == 0)
                {
                    trueNegative++;
                }
                else
                {
                    falseNegative++;
                }
            }
            else
            {
                if(table[i][table[0].length - 1].compareTo("0") == 0)
                {
                    falsePositive++;
                }
                else
                {
                    truePositive++;
                }
            }
        }
        double[] retValue = new double[3];
        retValue[0] = (truePositive+trueNegative)/(truePositive+trueNegative+falsePositive+falseNegative);
        retValue[1] = truePositive/(truePositive+falsePositive);
        retValue[2] = truePositive/(truePositive+falseNegative);
        return retValue;
    }
    private DecisionTreeNode makeDecTree(String[][] table, Vector<Integer> attrList)
    {
        if (table == null||table.length - 1 <= 0) return null;
        DecisionTreeNode node = new DecisionTreeNode();
        int classLoc = table[0].length - 1;
        double positiveCount = 0;
        double negativeCount = 0;
        for (int i = 1; i < table.length; i++) {
            if (table[i][classLoc].compareTo("0") == 0) {
                negativeCount++;
            } else {
                positiveCount++;
            }
        }
        if(positiveCount > negativeCount)
        {
            node.result = 1; 
        }
        else
        {
            node.result = 0;
        }
        if(positiveCount == table.length - 1)
        {
            node.result = 1;
            node.child = null;
            node.childList = null;
            node.attribute = "leaf-1";
            return node;
        }
        else if(negativeCount == table.length - 1)
        {
            node.result = 0;
            node.child = null;
            node.childList = null;
            node.attribute = "leaf-0";
            return node;
        }
        else if(attrList.isEmpty())
        {
            node.child = null;
            node.childList = null;
            System.out.println("attribute empty");
            return node;
        }
        else
        {
            String maxAttr = table[0][attrList.elementAt(0)];
            int maxAttrIndex = attrList.elementAt(0);
            int indexToDelete = 0;
            double maxGain = InformationGain.infoGain(table, attrList.elementAt(0));
            for(int i = 1; i<attrList.size(); i++)
            {
                double iGain = InformationGain.infoGain(table, attrList.elementAt(i));
                if(iGain > maxGain)
                {
                    maxGain = iGain;
                    maxAttr = table[0][attrList.elementAt(i)];
                    maxAttrIndex = attrList.elementAt(i);
                    indexToDelete = i;
                }
            }
            /*Vector<Integer> attrList_backup = new Vector<>();
            for(int i = 0; i<attrList.size(); i++)
            {
                attrList_backup.add(attrList.elementAt(i));
            }*/
            //Vector<Integer> attrList_backup = (Vector<Integer>) attrList.clone();
            attrList.remove(indexToDelete);
            node.attribute = maxAttr;
            //System.out.println(maxAttr);
            Vector[] hashTemp = new Vector[hashTableSize];
            for(int i = 0; i<10; i++)
            {
                hashTemp[i] = new Vector<String>();
            }
            hashTemp[hash(table[1][maxAttrIndex])].add(table[1][maxAttrIndex]);
            for (int i = 2; i < table.length; i++) {
                if (!hashTemp[hash(table[i][maxAttrIndex])].contains(table[i][maxAttrIndex])) {
                    hashTemp[hash(table[i][maxAttrIndex])].add(table[i][maxAttrIndex]);
                }
            }
            Vector<String> temp = new Vector<String>();
            for(int i = 0; i<hashTableSize; i++)
            {
                temp.addAll(hashTemp[i]);
            }
            String[] attrVal = new String[temp.size()];
            attrVal = temp.toArray(attrVal);
            node.childList = new String[attrVal.length];
            node.child = new DecisionTreeNode[attrVal.length];
            for(int i = 0; i<attrVal.length; i++)
            {
                node.childList[i] = attrVal[i];
                node.child[i] = makeDecTree(subTable(table,attrVal[i], maxAttrIndex), (Vector<Integer>) attrList.clone());
            }
            //attrList = attrList_backup;
            return node;
        }
    }
    
    int hash(String value)
    {
        return (value.hashCode() % hashTableSize);
    }
    
    Vector<Integer> makeClone(Vector<Integer> obj)
    {
        Vector<Integer> ret = new Vector<>();
        for(int i = 0; i<obj.size(); i++)
        {
            ret.add(obj.elementAt(i));
        }
        return ret;
    }
    
    private String[][] subTable(String[][] table, String attrVal, int attrIndex)
    {
        Vector<String[]> subT = new Vector<>();
        subT.add(table[0]);
        for(int i = 1; i<table.length; i++)
        {
            if(table[i][attrIndex].compareTo(attrVal) == 0)
            {
                subT.add(table[i]);
            }
        }
        String[][] retTable = new String[subT.size()][];
        retTable = subT.toArray(retTable).clone();
        return retTable;
    }
    
}
