package filter;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import log.BaseLogger;

public class Operation {
	
	Image image;
	static BufferedImage bufferedImage;
	static ImageMatrix originalMatrix;
	static int width;
	static int height;

	public Operation() {}
	
	/**
	 * Applies blur filter to a image.
	 * @param img -> the image that the filter is going to be applied.
	 * @param kernelSize -> the level of the filter.
	 * @return -> the modified image.
	 */
	public static BufferedImage blur(ImageIcon img, int kernelSize) {

		Operation.iconToBufferedConverter(img);
		ImageMatrix blurredMatrix = new ImageMatrix(width, height);

		int[][] kernel = new int[kernelSize][kernelSize];
		for (int i = 0; i < kernelSize; i++) {
		    for (int j = 0; j < kernelSize; j++) {
		        kernel[i][j] = 1;
		    }
		}
		
        int bounds = (kernelSize-1)/2;
        int area = (int) Math.pow(kernelSize, 2);
        
		for (int y = bounds; y < height - bounds; y++) {
		    for (int x = bounds; x < width - bounds; x++) {
		        int red = 0;
		        int green = 0;
		        int blue = 0;

		        for (int j = -bounds; j <= bounds; j++) {
		            for (int i = -bounds; i <= bounds; i++) {
		                red += originalMatrix.getRed(x + i, y + i);
		                green += originalMatrix.getGreen(x + i, y + i);
		                blue += originalMatrix.getBlue(x + i, y + i);
		            }
		        }

		        int blurredPixel = ImageMatrix.convertRGB(red/area, green/area, blue/area);
		        blurredMatrix.setRGB(x, y, blurredPixel);
		    }
		}
		Boolean result = writeImageToResources(img, blurredMatrix.getBufferedImage(), "_blur");
		if (result) {
			BaseLogger.info().log("Blur Filter applied to Image Path: <"+img.getDescription()+">.");
		}
		else {
			BaseLogger.error().log("Blur Filter couldn't be applied to Image Path: <"+img.getDescription()+">.");
		}
		return blurredMatrix.getBufferedImage();
	}
	
	/**
	 * Applies sharpen filter to a image.
	 * @param img -> the image that the filter is going to be applied.
	 * @param scale -> the level of the filter.
	 * @return -> the modified image.
	 */
	public static BufferedImage sharpen(ImageIcon img, int scale) {
		
		BufferedImage blurredImage = blur(img, (int) scale);
		ImageMatrix blurredMatrix = new ImageMatrix(blurredImage);
		ImageMatrix sharpenedMatrix = new ImageMatrix(width, height);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int diff = originalMatrix.getRGB(x, y) - blurredMatrix.getRGB(x, y);
				int add = originalMatrix.getRGB(x, y) + diff;
				sharpenedMatrix.setRGB(x, y, add);
			}
		}
		Boolean result = writeImageToResources(img, sharpenedMatrix.getBufferedImage(), "_sharpen");
		if (result) {
			BaseLogger.info().log("Sharpen Filter applied to Image Path: <"+img.getDescription()+">.");
		}
		else {
			BaseLogger.error().log("Sharpen Filter couldn't be applied to Image Path: <"+img.getDescription()+">.");
		}
		return sharpenedMatrix.getBufferedImage();		
	}
	
	/**
	 * Applies grayscale filter to a image.
	 * @param img -> the image that the filter is going to be applied.
	 * @param scale -> the level of the filter.
	 * @return -> the modified image.
	 */
	public static BufferedImage grayScale(ImageIcon img, int scale) {
		
		double scaleDouble = scale / 100;	
		Operation.iconToBufferedConverter(img);
		ImageMatrix grayScaleMatrix = new ImageMatrix(width, height);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int red = originalMatrix.getRed(x, y);
                int green = originalMatrix.getGreen(x, y);
                int blue = originalMatrix.getBlue(x, y);
                int grayscale = (int) (scaleDouble * 0.2989 * red + 0.587 * green + 0.114 * blue);
                grayScaleMatrix.setRGB(x, y, ImageMatrix.convertRGB(grayscale, grayscale, grayscale));
			}
		}
		Boolean result = writeImageToResources(img, grayScaleMatrix.getBufferedImage(), "_grayscale");
		if (result) {
			BaseLogger.info().log("GrayScale Filter applied to Image Path: <"+img.getDescription()+">.");
		}
		else {
			BaseLogger.error().log("GrayScale Filter couldn't be applied to Image Path: <"+img.getDescription()+">.");
		}
		return grayScaleMatrix.getBufferedImage();
	}
	
	/**
	 * Applies edge detection filter to a image.
	 * @param img -> the image that the filter is going to be applied.
	 * @param scale -> the level of the filter.
	 * @return -> the modified image.
	 */
	public static BufferedImage edgeDetection(ImageIcon img, int scale) {
		
		double scaleDouble = scale / 10;
		Operation.iconToBufferedConverter(img);
		ImageMatrix edgeDetectionMatrix = new ImageMatrix(width, height);
		
		int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sumX = 0;
                int sumY = 0;
                
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        int rgb = originalMatrix.getRGB(x + i, y + j);
                        int grayscale = (rgb >> 16) & 0xFF;
                        sumX += grayscale * sobelX[j + 1][i + 1];
                        sumY += grayscale * sobelY[j + 1][i + 1];
                    }
                }
                int magnitude = (int) (Math.sqrt(sumX * sumX + sumY * sumY)*scaleDouble);
                magnitude = Math.min(255, magnitude); 
                int edgeRGB = (magnitude << 16) | (magnitude << 8) | magnitude;
                edgeDetectionMatrix.setRGB(x, y, edgeRGB);
            }
        }
        Boolean result = writeImageToResources(img, edgeDetectionMatrix.getBufferedImage(), "_edgeDetection");
		if (result) {
			BaseLogger.info().log("Edge Detection Filter applied to Image Path: <"+img.getDescription()+">.");
		}
		else {
			BaseLogger.error().log("Edge Detection Filter couldn't be applied to Image Path: <"+img.getDescription()+">.");
		}
		return edgeDetectionMatrix.getBufferedImage();
	}
	
	/**
	 * Applies brightness filter to a image.
	 * @param img -> the image that the filter is going to be applied.
	 * @param scale -> the level of the filter.
	 * @return -> the modified image.
	 */
	public static BufferedImage brightness(ImageIcon img, int scale) {
		
		Operation.iconToBufferedConverter(img);
		ImageMatrix brightnessMatrix = new ImageMatrix(width, height);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int red = originalMatrix.getRed(x, y);
                int green = originalMatrix.getGreen(x, y);
                int blue = originalMatrix.getBlue(x, y);
                red = (int) Math.min((red + scale), 255);
                green = (int) Math.min((green + scale), 255);
                blue = (int) Math.min((blue + scale), 255);
                brightnessMatrix.setRGB(x, y, ImageMatrix.convertRGB(red, green, blue));
			}
		}
		Boolean result = writeImageToResources(img, brightnessMatrix.getBufferedImage(), "_brightness");
		if (result) {
			BaseLogger.info().log("Brightness Filter applied to Image Path: <"+img.getDescription()+">.");
		}
		else {
			BaseLogger.error().log("Brightness Filter couldn't be applied to Image Path: <"+img.getDescription()+">.");
		}
		return brightnessMatrix.getBufferedImage();
	}

	/**
	 * Applies contrast filter to a image.
	 * @param img -> the image that the filter is going to be applied.
	 * @param scale -> the level of the filter.
	 * @return -> the modified image.
	 */
	 public static BufferedImage contrast(ImageIcon img, int scale) {
		 
		 double scaleDouble = scale / 10;
		 Operation.iconToBufferedConverter(img);
		 ImageMatrix contrastMatrix = new ImageMatrix(width, height);
		 
		 for (int y = 0; y < height; y++) {
			 for (int x = 0; x < width; x++) {
				 int red = originalMatrix.getRed(x, y);
		         int green = originalMatrix.getGreen(x, y);
		         int blue = originalMatrix.getBlue(x, y);
		         red = Math.min(255, Math.max(0, (int) (scaleDouble * (red - 128) + 128)));
		         green = Math.min(255, Math.max(0, (int) (scaleDouble * (green - 128) + 128)));
		         blue = Math.min(255, Math.max(0, (int) (scaleDouble * (blue - 128) + 128)));
		         contrastMatrix.setRGB(x, y, ImageMatrix.convertRGB(red, green, blue));
			 }
		 }
		Boolean result = writeImageToResources(img, contrastMatrix.getBufferedImage(), "_contrast");
		if (result) {
			BaseLogger.info().log("Contrast Filter applied to Image Path: <"+img.getDescription()+">.");
		}
		else {
			BaseLogger.error().log("Contrast Filter couldn't be applied to Image Path: <"+img.getDescription()+">.");
		}
		 return contrastMatrix.getBufferedImage();
	}
	
	/**
	 * Turns the ImageIcon parameter to a BufferedImage object. Calls ImageMatrix Class to get a matrix of the original image, get its width and height.
	 * This method is always called first in every filter method since every filter method uses matrix, width, height values of the original image.
	 * @param icon -> the image that will be modified.
	 */
	public static void iconToBufferedConverter(ImageIcon icon) {
		Image image = icon.getImage();
		bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		bufferedImage.createGraphics().drawImage(image, 0, 0, null);
		originalMatrix = new ImageMatrix(bufferedImage);
		width = originalMatrix.getWidth();
		height = originalMatrix.getHeight();
	}
	
	/**
	 * Takes a image icon and saves a new ".jpeg" file with applied filter.
	 * @param imageIcon -> the original image.
	 * @param image -> the modified image.
	 * @param name -> the name of the filter that will be applied.
	 * @return -> true if file saved successfully, false otherwise.
	 */
	private static boolean writeImageToResources(ImageIcon imageIcon, RenderedImage image, String name) {

		String filePath = imageIcon.getDescription();
		String fileNameWithExtension = new File(filePath).getName();
		String fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf('.'));
		
		boolean result = true;
		try {
			ImageIO.write(image, "jpeg", new File(fileName + name));
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
}