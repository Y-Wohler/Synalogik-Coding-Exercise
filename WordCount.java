import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class WordCount {
    private static int wordCount = 0;       //Holds the number of words in the sample.txt 

    static void checkFile(URL path){
        /*
         * Reads the sample.txt or the file path which is passed through in the parameter,
         * Splits every line and counts how many elements "wordCount" after the split, excluding any spaces,
         * Adds the length of each word to the array list "lenList",
         * Prints the word count and the average word length.
         * Calls method "lenWord" which prints the length and most frequent.
         * */
        List<Integer> lenList = new ArrayList<>();
        try{
            File file = new File(path.toURI());
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

        } catch (FileNotFoundException | URISyntaxException e) {     //No File exception
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
        DecimalFormat df = new DecimalFormat("###.###");        //Decimal Format
        return df.format(averageCount);
    }

    static void lenWord(List<Integer> list){        //Length function to calculate the legnth of the string and compares their frequency.
        int[] wordLen = new int[45];                //45 because it is the longest English word in the Oxford English Dictionary.
        Arrays.fill(wordLen, 0);
        int highestFreq = 0;
        int indexFreq1 = 0;
        int indexFreq2 = 0;
        List<Integer> moreFrequency = new ArrayList<>();

        for (int j : list) {
            wordLen[j] += 1;
        }
        for (int k = 0; k <= wordLen.length-1; k++) {
            if (wordLen[k] != 0 && k != 0) {
                System.out.println("Number of words of length " + (k) + " is " + wordLen[k]);
            }
            if (wordLen[k] >= highestFreq) {        //Comparison with the current value and the highest recorded value so far.
                if (wordLen[k] == highestFreq && wordLen[k] != 0) {
                    indexFreq2 = k;
                    moreFrequency.add(indexFreq2);

                }else {

                    highestFreq = wordLen[k];
                    indexFreq1 = k;
                    moreFrequency.add(indexFreq1);
                }
            }
        }
        for (int y = 0; y < moreFrequency.size();y++){      //Removes all false values with lower frequency as the highest frequency.
            if (wordLen[moreFrequency.get(y)] < wordLen[indexFreq2]){
                moreFrequency.remove(y);
                y--;
            }
        }
        if (moreFrequency.size() <=2){ //Print function for word lengths for alone values or 1 similarity, for the same frequency of 2 word lengths, if statement within
            System.out.println("The most frequently occurring word length is " + highestFreq + ", for word lengths of " +
                    ((indexFreq2 == 0) ? indexFreq1 : indexFreq1 + " & " + moreFrequency.get(1)));
        }else {             //Print Function for more than 2 similarities
            System.out.println("The most frequently occurring word length is " + highestFreq + ", for word lengths of:");
            moreFrequency.forEach(result -> System.out.print(result + ","));        //Lambda expression to print out all the values within the moreFrequency list
        }
    }
    public static void main(String[] args) {
        URL path = WordCount.class.getResource("sample.txt");   //Finds the path of the file within the same directory
        checkFile(path);
    }
}
