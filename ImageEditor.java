import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.*;
import java.awt.*;

/**
 * ImageEditor
 */
public class ImageEditor { 

    //function for bluring the image ----------
    
    public static BufferedImage blur(BufferedImage inputImage, int amountOfBlur){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i<height-amountOfBlur; i+=amountOfBlur){
            for(int j = 0; j<width-amountOfBlur; j+=amountOfBlur){
                
                BufferedImage tempImage = new BufferedImage(amountOfBlur, amountOfBlur, BufferedImage.TYPE_INT_RGB);
                int red_sum = 0; 
                int blue_sum = 0; 
                int green_sum = 0; 

                for(int m = 0; m<amountOfBlur; m++){
                    for(int n = 0; n<amountOfBlur; n++){
                        Color pixel = new Color(inputImage.getRGB(j+n, i+m));
                        red_sum += pixel.getRed(); 
                        blue_sum +=pixel.getBlue();
                        green_sum += pixel.getGreen();
                    }
                }
                
                int avg_red = (int)red_sum/(amountOfBlur*amountOfBlur); 
                int avg_green = (int)green_sum/(amountOfBlur*amountOfBlur);
                int avg_blue = (int)blue_sum/(amountOfBlur*amountOfBlur);

                for(int k= 0; k<amountOfBlur; k++){
                    for(int l = 0; l<amountOfBlur; l++){
                        Color newPixel = new Color(avg_red, avg_green, avg_blue);
                        tempImage.setRGB(l, k, newPixel.getRGB());
                    }
                }
                for(int m = 0; m<amountOfBlur; m++){
                    for(int n = 0; n<amountOfBlur; n++){
                        outputImage.setRGB(j+n, i+m, tempImage.getRGB(n,m));
                    }
                }
            }
        }
        return outputImage;
    }

    // function for changing the brightness of the image by by the percentage (input) -----

    public static BufferedImage changeBrightness(BufferedImage inputImage, int percentChange){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                Color pixel = new Color(inputImage.getRGB(j, i));
                int red = pixel.getRed();
                int blue = pixel.getBlue();
                int green = pixel.getGreen();
                red = red + (percentChange*red)/100;
                blue = blue + (percentChange*blue)/100;
                green = green + (percentChange*green)/100;
                if(red>255)
                red = 255;
                if(blue>255)
                blue = 255;
                if(green>255)
                green = 255;

                if(red<0)
                red = 0;
                if(blue<0)
                blue = 0;
                if(green<0)
                green = 0;

                Color newPixel = new Color(red, green, blue);
                outputImage.setRGB(j, i, newPixel.getRGB());
            }

        }
        return outputImage;

    }

    // function for changing to mirror image --------

    public static BufferedImage mirrorImage(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i<height; i++){
            for(int j = width-1; j>=0; j--){
                outputImage.setRGB(width-j-1, i, inputImage.getRGB(j,i));
            }
        }
        return outputImage;
    }

    // function for rotating antiClockWise  ---------

    public static BufferedImage transposeImage(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        for(int j = 0; j<width; j++){
            for(int i = 0; i<height; i++){
                outputImage.setRGB(i, j, inputImage.getRGB(j, i));
                
            }
        }
        for(int i = 0; i<width/2; i++){
            for(int j = 0; j<height; j++){
                int temp = outputImage.getRGB(j, i);
                outputImage.setRGB(j, i, outputImage.getRGB(j, width-i-1));
                outputImage.setRGB(j, width-i-1, temp);
            }
        }
        return outputImage;
        
        
    }

    // function for rotating clockWise --------

    public static BufferedImage clockWise(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        // outputImage = transposeImage(inputImage);
        // outputImage = mirrorImage(outputImage);
        // return outputImage;
        for(int i = height-1; i>=0; i--){
            for(int j = 0; j<width; j++){
                outputImage.setRGB(height-i-1, j, inputImage.getRGB(j,i));
            }
        }
        return outputImage;
    }

    // Converting to Black&White ------------

    public static BufferedImage convertToGrayScale(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage  = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for(int i = 0; i<height; i++){
            for(int j = 0; j<width; j++){
                outputImage.setRGB(j, i, inputImage.getRGB(j,i));
            }
        }
        return outputImage;
    }
    


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("path of your image : (/path/of/your/image.jpg) :-  ");
        String path = sc.next();
        System.out.println("path where you want to save the image : (/path/to/save/name_of_image.jpg)");
        String path2 = sc.next();
        
        // taking the image path ..... 

        File inputFile = new File(path);
        try{

            // reading the image from given path .... 

            BufferedImage inputImage = ImageIO.read(inputFile);

            // giving options to the users.........

            System.out.println("Choose any one option : -- \n");
            System.out.println("1.) Convert to Black and White : ");
            System.out.println("2.) Rotate ClockWise : ");
            System.out.println("3.) Rotate AnticlockWise : ");
            System.out.println("4.) MirrorImage : ");
            System.out.println("5.) Change Brightness : ");
            System.out.println("6.) Blur the image : ");
            System.out.print("Input any one from (1 - 6): --->  ");
            
            // taking input from user as option.......

            int option = sc.nextInt();
            
            // implementing switch case (conditions)....

            switch(option){
                case 1 :
                
                BufferedImage grayScale = convertToGrayScale(inputImage);
                File grayScaleImage = new File(path2);
                ImageIO.write(grayScale, "jpeg", grayScaleImage);

                break;



                case 2 :

                BufferedImage rotateRight = clockWise(inputImage);
                File rotateClockWise = new File(path2);
                ImageIO.write(rotateRight, "jpeg", rotateClockWise);

                break;



                case 3 :

                BufferedImage rotateLeft = transposeImage(inputImage);
                File rotateAntiClockWise = new File(path2);
                ImageIO.write(rotateLeft,"jpeg", rotateAntiClockWise);
                
                break;



                case 4 :

                BufferedImage lateralInvert = mirrorImage(inputImage);
                File mirror = new File(path2);
                ImageIO.write(lateralInvert, "jpeg", mirror);

                break;



                case 5 :
                System.out.println("amount of brightness change:(for increasing put +ve integer ,  for decreasing put a -ve integer ) ");
                int amount = sc.nextInt();
                BufferedImage changedBrightness = changeBrightness(inputImage, amount);
                File changedBrightnessImage = new File(path2);
                ImageIO.write(changedBrightness, "jpeg", changedBrightnessImage);

                break;

                
                case 6 :

                System.out.println("enter the amount of blur ( a +ve integer ) : ");
                int blur_amount = sc.nextInt();
                BufferedImage blured = blur(inputImage, blur_amount);
                File bluredImage = new File(path2);
                ImageIO.write(blured, "jpeg", bluredImage);
                
                break;
                

                default :
                System.out.println("something went wrong");
                break;

            }System.out.println("Done ! Your image is saved in the path : " + path2);

            
        
    }
        catch (IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
