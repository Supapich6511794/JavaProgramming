package CHAPTER_2;

import java.util.Scanner;
public class NumberExpression {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("enter the seconds in integer : ");
        int seconds = input.nextInt();

        int minutes = seconds/60;
        int remainingSeconds = seconds % 60;
        System.out.println(seconds+" seconds is  "+minutes+ " minutes and "+ remainingSeconds+" seconds");

    }
    
}
