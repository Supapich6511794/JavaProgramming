package CHAPTER_2;

import java.util.Scanner;

public class Exercise2_5 { 
    public static void main(String[] args) {
         Scanner input = new Scanner(System.in);

         System.out.print("Enter num for lenght :");

         double lenght = input.nextDouble();

         double area = ((3*Math.sqrt(3))/2)*lenght*lenght;

         System.out.println("the area of lenght"+ lenght +" is " + area);

    
 }
}