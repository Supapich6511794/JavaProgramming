package CHAPTER_2;

import java.util.Scanner;
public class ComputeAreaWithInputJava {
    public static void main(String[] args) {
         Scanner input = new Scanner(System.in);

         System.out.print("Enter num for radius :");

         double radius = input.nextDouble();

         double area = 3.14*radius*radius;

         System.out.println("the area of the cicle of radius"+ radius +"is " + area);



    }
    
}

