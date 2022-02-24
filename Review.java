import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  private static final String SPACE = " ";
  
  static{
    try {
      Scanner input = new Scanner(new File("cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        // System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing postitiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal(String word)
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }

  public static double totalSentiment (String fileName)
  {
    String review = textToString(fileName);

    double total = 0.0;
    while (review.indexOf(" ") > 0)
    {
      int space = review.indexOf(" ");
      String word = review.substring(0, space);
      double sentiment = sentimentVal(word);
      total += sentiment;

      review = review.substring(space + 1);
    }
    total += sentimentVal(review);
    return total;
  }

  public static int starRating(String fileName){
    // get total sentiment
    double sentiment = totalSentiment(fileName);
    // check total sentiment against thresholds
    // return appropriate number of stars
    if (sentiment >= 21)
    {
      return 5;
    }
    else if (sentiment > 10)
    {
      return 4;
    }
    else if (sentiment > 0)
    {
      return 3;
    }
    else if (sentiment > -10)
    {
      return 2;
    }
    else
    {
      return 1;
    }


}
  
public static String fakeReview(String fileName, String posNeg)
{
  // get the review in a string
  String review = textToString(fileName);

  // empty string for new string
  String newReview = "";
  // loop through the string
  while (review.indexOf("*") > 0 && review.length() > 0)
  {
    // look for *s
    int starLoc = review.indexOf("*");
    // add everything before the * to new review
    if (posNeg.toLowerCase().equals("positive"))
    {
      newReview += randomPositiveAdj();
    }
    else if (posNeg.toLowerCase().equals("negative"))
    {
      newReview += randomNegativeAdj();
    }
    else 
    {
      newReview += randomAdjective();
    }
    newReview += review.substring(0, starLoc);
    // add a random adjective to new review
    newReview += randomAdjective();
    // cut off old review through starred adjective
    int spaceAfterStar = review.indexOf(" ", starLoc);
    review = review.substring(spaceAfterStar);
  }
  newReview += review;

  return newReview;

}

  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }

      /**
   * Returns the word after removing any beginning or ending punctuation
   */
  public static String removePunctuation( String word )
  {
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0)))
    {
      word = word.substring(1);
    }
    while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1)))
    {
      word = word.substring(0, word.length()-1);
    }
    
    return word;
  }
 
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

// maggie was my partner and helped me with this
  public static String textLingo(String fileName)
    {
    //get text into a string
    String text = textToString(fileName);

    // empty string for new string
    String newText = "";

    // loop through the string
    while (text.indexOf("k ") > 0 && text.length() > 0)
    {
      // look for k
      int kLoc = text.indexOf("k ");

      String isK = text.substring(kLoc, kLoc+2);

      String beforeK = text.substring(0, kLoc);

      String afterK = text.substring(kLoc + 1);

      newText += beforeK;
      System.out.println("beforeK:" + beforeK);

      // check if text is "k + something", change to okay + something
      if (isK.equals("k "))
      {
        newText += "okay ";
        System.out.println("newText:" + newText);
      }
      else {
        newText += afterK;
      }

      text = text.substring(kLoc);
      
    // if not, keep orginal text
    }

    newText += text;
    return newText;
  }    
}
