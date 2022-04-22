import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class WordCount {
    private static int wordCount = 0;       //Holds the number of words in the sample.txt 

    static void checkFile(String filename){
        /*
         * Reads the sample.txt or filename that is passed through in the parameter,
         * Splits every line and counts how many elements "wordCount" after the split, excluding any spaces,
         * Adds the length of each word to the array list "lenList",
         * Prints the word count and the average word length.
         * Calls method "lenWord" which prints the length and most frequent.
         * */
        List<Integer> lenList = new ArrayList<>();
        try{
            File file = new File(filename);
            Scanner scan= new Scanner(file);
            while (scan.hasNextLine()){
                String[] value = scan.nextLine().split("([\"]|\\s)");
                for (String s : value) {
                    if (!s.equals("")) {
                        wordCount += 1;
                    }
                    int wordLen = s.replaceAll("\\p{Punct}", "").length();
                    lenList.add(wordLen);
                }
            }
            System.out.println("Word count = " + wordCount);    //Word Count
            System.out.println("Average word length = " + averageCalc(lenList,wordCount));  //Average Length
            lenWord(lenList);   //Most frequent and word lengths

        } catch (FileNotFoundException e) {     //No File exception
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public static String averageCalc(List<Integer> list, int wordCount) {
        int totalInt = 0;
        for (int i : list) {
            totalInt = totalInt + i;
        }
        double averageCount = (double) totalInt / wordCount;
        DecimalFormat df = new DecimalFormat("###.###");
        return df.format(averageCount);
    }

    static void lenWord(List<Integer> list){
        int[] wordLen = new int[45];                //45 because it is the longest English word in the Oxford English Dictionary.
        Arrays.fill(wordLen, 0);
        int highestFreq = 0;
        int indexFreq1 = 0;
        int indexFreq2 = 0;

        for (int j : list) {
            wordLen[j] += 1;
        }
        for (int k = 0; k <= wordLen.length-1; k++){
            if (wordLen[k] != 0 && k != 0){
                System.out.println("Number of words of length " + (k) + " is " + wordLen[k]);
            }
            if (wordLen[k] >= highestFreq){
                if (wordLen[k] == highestFreq){
                    indexFreq2 = k;
                }else {
                    highestFreq = wordLen[k];
                    indexFreq1 = k;
                }
            }
        }
        System.out.println("The most frequently occurring word length is " + highestFreq + ", for word lengths of " + 
                           ((indexFreq2 == 0) ? indexFreq1 : indexFreq1 + " & " + indexFreq2));
    }

    public static void main(String[] args) {
        checkFile("sample.txt");
    }
}
