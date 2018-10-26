//
 //  @filename   :  
 //  @brief      :   Implements for e-paper library
 //  @author     :   Yehui from Waveshare
 //
 //  Copyright (C) Waveshare     September 9 2017
 //
 // Permission is hereby granted, free of charge, to any person obtaining a copy
 // of this software and associated documnetation files (the "Software"), to deal
 // in the Software without restriction, including without limitation the rights
 // to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 // copies of the Software, and to permit persons to  whom the Software is
 // furished to do so, subject to the following conditions:
 //
 // The above copyright notice and this permission notice shall be included in
 // all copies or substantial portions of the Software.
 //
 // THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 // IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 // FITNESS OR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 // AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 // LIABILITY WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 // OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 // THE SOFTWARE.
 //

package de.ankesreich.displaydriver;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;


public class ImagePreparator {
	private static Logger log = Logger.getLogger(ImagePreparator.class.getName());
	
	public static short[] prepareImageForPartial(String path, boolean inverted) throws IOException {
		BufferedImage image = readBufferedImage(path);
		Raster data = image.getData();
		int width = data.getWidth();
		int height = data.getHeight();
		log.info("path " + path + " Height Image " + height + " Width  Image " + width);
		return prepareImage(path, width, height, inverted);
	}
	
	private static BufferedImage readBufferedImage(String path) throws IOException
	{
		FileInputStream fileInputStream  = null;
		try
		{
			fileInputStream = new FileInputStream(new File (path));
			return  ImageIO.read(fileInputStream);
		}
		finally
		{
			if (fileInputStream != null)
				fileInputStream.close();
		}
	}
	
	public static short[] prepareImage(String path, int width, int height, boolean inverted) throws IOException {
		log.info("prepareImage path " + path + " Height Image " + height + " Width  Image " + width
				 + " inverted " + inverted);
		BufferedImage image = readBufferedImage(path);
		return prepareImage(image, width, height,  inverted);
	}

	    
	private static short[] prepareImage(BufferedImage image, int width, int height, boolean invertedColors) throws IOException {
		Raster data = image.getData();

		short[] buf = new short[width * height / 8]; //
		for (int y = 0; y < height; y++) {
			// wenn das Bild kleiner ist, dann ist das auch ok
			if (y >= image.getHeight()) {
				continue;
			}
			for (int x = 0; x < width; x++) {
				if (x >= image.getWidth()) {
					continue;
				}
				int[] pixel = data.getPixel(x, y, (int[]) null);
				if (pixel.length != 1) {
					throw new RuntimeException(pixel.length + " pixel not found at x " + x + " y " + y + 
							" maybe no indexed color ");
				}
				if (invertedColors) {
					if (pixel[0] != 0) {
						buf[(x + y * width) / 8] |= 0x80 >> (x % 8);
					}
				} else {
					if (pixel[0] == 0) {
						buf[(x + y * width) / 8] |= 0x80 >> (x % 8);
					}
				}
			}
		}
		return buf;
	}

	   public static int getImageHeight(String path) throws IOException
	    {
		   BufferedImage image = readBufferedImage(path);
			Raster data = image.getData();
			return data.getHeight();
	    }
	    
	    public static int getImageWidth(String path) throws IOException
	    {
	    	BufferedImage image = readBufferedImage(path);
	    	Raster data = image.getData();
	    	return data.getWidth();
	    }

}
