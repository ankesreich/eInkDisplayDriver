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

package de.ankesreich.displaydriver.fourtwo;

import java.io.IOException;
import java.util.logging.Logger;

import de.ankesreich.displaydriver.Command;
import de.ankesreich.displaydriver.Epdif;
import de.ankesreich.displaydriver.ImagePreparator;
import de.ankesreich.displaydriver.RaspiConfiguration;

public class Epd4in2 extends Epdif {

	private static Logger log = Logger.getLogger(Epd4in2.class.getName());
	
    public static final int EPD_WIDTH = 400;
    public static final int  EPD_HEIGHT = 300;
	
	
	byte lut_vcom0[] =
	{
	0x40, 0x17, 0x00, 0x00, 0x00, 0x02,        
	0x00, 0x17, 0x17, 0x00, 0x00, 0x02,        
	0x00, 0x0A, 0x01, 0x00, 0x00, 0x01,        
	0x00, 0x0E, 0x0E, 0x00, 0x00, 0x02,        
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,        
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,        
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	};
	
	byte lut_vcom0_quick[] =
	{
	0x00, 0x0E, 0x00, 0x00, 0x00, 0x01,        
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,        
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,        
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,        
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,        
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,        
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	};
	
	
	
	byte lut_ww[] ={
	0x40, 0x17, 0x00, 0x00, 0x00, 0x02,
	(byte)0x90, 0x17, 0x17, 0x00, 0x00, 0x02,
	0x40, 0x0A, 0x01, 0x00, 0x00, 0x01,
	(byte)0xA0, 0x0E, 0x0E, 0x00, 0x00, 0x02,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	};
	
	byte lut_ww_quick[] ={
	(byte)0xA0, 0x0E, 0x00, 0x00, 0x00, 0x01,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	};
	
	
	byte lut_bw[] ={
	0x40, 0x17, 0x00, 0x00, 0x00, 0x02,
	(byte)0x90, 0x17, 0x17, 0x00, 0x00, 0x02,
	0x40, 0x0A, 0x01, 0x00, 0x00, 0x01,
	(byte)0xA0, 0x0E, 0x0E, 0x00, 0x00, 0x02,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     
	};
	
	
	byte lut_bw_quick[] ={
	(byte)0xA0, 0x0E, 0x00, 0x00, 0x00, 0x01,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     
	};
	
	byte lut_bb[] ={
	(byte)0x80, 0x17, 0x00, 0x00, 0x00, 0x02,
	(byte)0x90, 0x17, 0x17, 0x00, 0x00, 0x02,
	(byte)0x80, 0x0A, 0x01, 0x00, 0x00, 0x01,
	0x50, 0x0E, 0x0E, 0x00, 0x00, 0x02,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,        
	};
	
	byte lut_bb_quick[] ={
	0x50, 0x0E, 0x00, 0x00, 0x00, 0x01,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,     
	};
	
	
	byte lut_wb[] ={
	(byte)0x80, 0x17, 0x00, 0x00, 0x00, 0x02,
	(byte)0x90, 0x17, 0x17, 0x00, 0x00, 0x02,
	(byte)0x80, 0x0A, 0x01, 0x00, 0x00, 0x01,
	0x50, 0x0E, 0x0E, 0x00, 0x00, 0x02,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,         
	};
	
	byte lut_wb_quick[] ={
	0x50, 0x0E, 0x00, 0x00, 0x00, 0x01,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00,         
	};

	


	public void init(RaspiConfiguration config) throws IOException {
		super.initPins(config);
		reset();
		initDisplay();
		log.info("finished Configuration Display..");
	}




	@Override
	protected void initDisplay() throws IOException {
		log.info("init Configuration Display..");
		sendCommand(Command.POWER_SETTING);
		sendData(0x03); // VDS_EN, VDG_EN
		sendData(0x00); // VCOM_HV, VGHL_LV[1], VGHL_LV[0]
		sendData(0x2b); // VDH
		sendData(0x2b); // VDL
		sendData(0xff); // VDHR
		sendCommand(Command.BOOSTER_SOFT_START);
		sendData(0x17);
		sendData(0x17);
		sendData(0x17); // 07 0f 17 1f 27 2F 37 2f
		sendCommand(Command.POWER_ON);
		waitUntilIdle();
		sendCommand(Command.PANEL_SETTING);
		sendData(0xbf); // 300x400 B/W mode, LUT set by register
		sendCommand(Command.PLL_CONTROL);
		sendData(0x3C); // 3A 100Hz 29 150Hz 39 200Hz 31 171Hz 3C 50Hz (default) 0B 10Hz
	}
	
	
	/**
	 *  @throws IOException 
	 * @brief: transmit partial data to the SRAM.  The final parameter chooses between dtm=1 and dtm=2
	 * @width should be the multiple of 8
	 */
	public void setPartialWindow(short[] buffer_black, int x, int y, int width, int height, int dtm) throws IOException {
		log.info("setPartialWindow x " + x + " - y " + y + " - width " + width + " - height "+ height);
		sendCommand(Command.PARTIAL_IN);
		sendCommand(Command.PARTIAL_WINDOW);
		sendData(x >> 8);
		sendData(x & 0xf8);     // x should be the multiple of 8, the last 3 bit will always be ignored
		sendData(((x & 0xf8) + width  - 1) >> 8);
		sendData(((x & 0xf8) + width  - 1) | 0x07);
		sendData(y >> 8);        
		sendData(y & 0xff);
		sendData((y + height - 1) >> 8);        
		sendData((y + height - 1) & 0xff);
		sendData(0x01);         // Gates scan both inside and outside of the partial window. (default) 
		sendCommand((dtm == 1) ? Command.DATA_START_TRANSMISSION_1 : Command.DATA_START_TRANSMISSION_2);
	    if (buffer_black != null) {
	        for(int i = 0; i < width  / 8 * height; i++) {
	        	sendData(buffer_black[i]);  
	        }  
	        
	    } else {
	        for(int i = 0; i < width  / 8 * height; i++) {
	        	sendData(0xff);  //white 
	        	
	        }  
	    }
	    sendCommand(Command.PARTIAL_OUT);  
	}
	
	

	/**
	 *  @throws IOException 
	 * @brief: set the look-up table
	 */
	public void setLut() throws IOException {
	    int count;     
	    sendCommand(Command.LUT_FOR_VCOM);                            //vcom
	    for(count = 0; count < 44; count++) {
	    	sendData(lut_vcom0[count]);
	    }
	    
	    sendCommand(Command.LUT_WHITE_TO_WHITE);                      //ww --
	    for(count = 0; count < 42; count++) {
	    	sendData(lut_ww[count]);
	    }   
	    
	    sendCommand(Command.LUT_BLACK_TO_WHITE);                      //bw r
	    for(count = 0; count < 42; count++) {
	    	sendData(lut_bw[count]);
	    } 

	    sendCommand(Command.LUT_WHITE_TO_BLACK);                      //wb w
	    for(count = 0; count < 42; count++) {
	    	sendData(lut_wb[count]);
	    } 

	    sendCommand(Command.LUT_BLACK_TO_BLACK);                      //bb b
	    for(count = 0; count < 42; count++) {
	    	sendData(lut_bb[count]);
	    } 
	}
	
	/**
	 *  @throws IOException 
	 * @brief: set the look-up table for quick display (partial refresh)
	 */

	public void setLutQuick() throws IOException {
	    int count;     
	    sendCommand(Command.LUT_FOR_VCOM);                            //vcom
	    for(count = 0; count < 44; count++) {
	    	sendData(lut_vcom0_quick[count]);
	    }
	    
	    sendCommand(Command.LUT_WHITE_TO_WHITE);                      //ww --
	    for(count = 0; count < 42; count++) {
	    	sendData(lut_ww_quick[count]);
	    }   
	    
	    sendCommand(Command.LUT_BLACK_TO_WHITE);                      //bw r
	    for(count = 0; count < 42; count++) {
	    	sendData(lut_bw_quick[count]);
	    } 

	    sendCommand(Command.LUT_WHITE_TO_BLACK);                      //wb w
	    for(count = 0; count < 42; count++) {
	    	sendData(lut_wb_quick[count]);
	    } 

	    sendCommand(Command.LUT_BLACK_TO_BLACK);                      //bb b
	    for(count = 0; count < 42; count++) {
	    	sendData(lut_bb_quick[count]);
	    } 
	}
	

	

	/**
	 * @throws IOException
	 * @brief: refresh and displays the frame
	 */
	public void displayFrame(short[] frame_buffer) throws IOException {
		log.info("displayFrame" );
		sendCommand(Command.RESOLUTION_SETTING);
		sendData(EPD_WIDTH >> 8);
		sendData(EPD_WIDTH & 0xff);
		sendData(EPD_HEIGHT >> 8);
		sendData(EPD_HEIGHT & 0xff);

		sendCommand(Command.VCM_DC_SETTING);
		sendData(0x12);

		sendCommand(Command.VCOM_AND_DATA_INTERVAL_SETTING);
		sendCommand((short) 0x97); // VBDF 17|D7 VBDW 97 VBDB 57 VBDF F7 VBDW 77 VBDB 37 VBDR B7

		if (frame_buffer != null) {
			sendCommand(Command.DATA_START_TRANSMISSION_1);
			log.info("after DATA_START_TRANSMISSION_1 " );
			for (int i = 0; i < EPD_WIDTH / 8 * EPD_HEIGHT; i++) {
				sendData(0xFF); // bit set: white, bit reset: black
			}
			log.info("after sendData(0xFF) " );
			delayMs(2);
			sendCommand(Command.DATA_START_TRANSMISSION_2);
			log.info("after DATA_START_TRANSMISSION_2 " );
			for (int i = 0; i < EPD_WIDTH / 8 * EPD_HEIGHT; i++) {
				sendData(frame_buffer[i]);
			}
			delayMs(2);
		}

		setLut();

		sendCommand(Command.DISPLAY_REFRESH);
		delayMs(100);
		waitUntilIdle();
	}
	
	
	
	
	
	/**
	 * @throws IOException
	 * @brief: clear the frame data from the SRAM, this won't refresh the display
	 */
	public void clearFrames() throws IOException {
		log.info("clearFrames");
		sendCommand(Command.RESOLUTION_SETTING);
		sendData(EPD_WIDTH >> 8);
		sendData(EPD_WIDTH & 0xff);
		sendData(EPD_HEIGHT >> 8);
		sendData(EPD_HEIGHT & 0xff);

		sendCommand(Command.DATA_START_TRANSMISSION_1);
		delayMs(2);
		for (int i = 0; i < EPD_WIDTH / 8 * EPD_HEIGHT; i++) {
			sendData(0xFF);
		}
		delayMs(2);
		sendCommand(Command.DATA_START_TRANSMISSION_2);
		delayMs(2);
		for (int i = 0; i < EPD_WIDTH / 8 * EPD_HEIGHT; i++) {
			sendData(0xFF);
		}
		delayMs(2);
	}
	
	
	/**
	 * @throws IOException 
	 * @brief: This displays the frame data from SRAM
	 */
	public void displayFrame() throws IOException {
		log.info("displayFrame");
	    setLut();
	    sendCommand(Command.DISPLAY_REFRESH); 
	    delayMs(100);
	    waitUntilIdle();
	}
	
	public void displayFrameQuick() throws IOException {
		log.info("displayFrameQuick");
		setLutQuick();
		sendCommand(Command.DISPLAY_REFRESH); 
	  //  DelayMs(100);
	    waitUntilIdle();
	}
	
	
	/**
	 * @throws IOException 
	 * @brief: After this command is transmitted, the chip would enter the deep-sleep mode to save power. 
	 *         The deep sleep mode would return to standby by hardware reset. The only one parameter is a 
	 *         check code, the command would be executed if check code = 0xA5. 
	 *         You can use Epd::Reset() to awaken and use Epd::Init() to initialize.
	 */
	@Override
	public void sleep() throws IOException {
		log.info("sleep");
	    sendCommand(Command.VCOM_AND_DATA_INTERVAL_SETTING);
	    sendData(0x17);                       //border floating    
	    sendCommand(Command.VCM_DC_SETTING);          //VCOM to 0V
	    sendCommand(Command.PANEL_SETTING);
	    delayMs(100);          
	
	    sendCommand(Command.POWER_SETTING);           //VG&VS to 0V fast
	    sendData(0x00);        
	    sendData(0x00);        
	    sendData(0x00);              
	    sendData(0x00);        
	    sendData(0x00);
	    delayMs(100);          
	                
	    sendCommand(Command.POWER_OFF);          //power off
	    waitUntilIdle();
	    sendCommand(Command.DEEP_SLEEP);         //deep sleep
	    sendData(0xA5);
	}
	
	public void displayImageFromFile( String path, boolean inverted) throws IOException {
		log.info("displayImageFromFile " + path);
		short[] imageArray = ImagePreparator.prepareImage(path, EPD_WIDTH, EPD_HEIGHT, inverted);
		displayFrame(imageArray);
		log.info("displayImageFromFile " + path + " finished " );
	}

	
}
