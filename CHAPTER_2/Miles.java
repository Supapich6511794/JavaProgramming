package CHAPTER_2;

public class Miles {
    public static void main(String[] args) {
        double miles = 100;
        final double KILOMETERS_PER_MILES =1.609;

        double kilomerters = miles* KILOMETERS_PER_MILES ;

        System.out.println("the area "+kilomerters +" kilomerters "+miles+" miles.");
    }
    
}
