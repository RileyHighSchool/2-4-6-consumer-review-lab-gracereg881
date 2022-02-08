// Write a program with a main method.
//  Create at least one new method called from main that takes at least one parameter.
//  Call at least two distinct methods in the String class.
//  Use conditional statements or compound Boolean expressions.
//  Use iteration.

public class Main {

    public static void main(String[] args){
        //System.out.println(Review.sentimentVal("terrible."));

        //double num = Review.sentimentVal("warm");

        System.out.println(Review.totalSentiment("SimpleReview.txt"));

        System.out.println(Review.starRating("SimpleReview.txt"));
    }
}
