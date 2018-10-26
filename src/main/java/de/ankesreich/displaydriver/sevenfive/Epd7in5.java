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


package de.ankesreich.displaydriver.sevenfive;

import java.io.IOException;
import java.util.logging.Logger;

import de.ankesreich.displaydriver.Command;
import de.ankesreich.displaydriver.Epdif;
import de.ankesreich.displaydriver.ImagePreparator;
import de.ankesreich.displaydriver.RaspiConfiguration;


public class Epd7in5 extends Epdif {

	private static final int EPD_WIDTH = 640;
	private static final int EPD_HEIGHT = 384;

	Logger log = Logger.getLogger(Epd7in5.class.getName());	

	
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
		sendData(0x37);
		sendData(0x00);
		sendCommand(Command.PANEL_SETTING);
		sendData(0xCF);
		sendData(0x08);
		sendCommand(Command.BOOSTER_SOFT_START);
		sendData(0xc7);
		sendData(0xcc);
		sendData(0x28);
		sendCommand(Command.POWER_ON);
		waitUntilIdle();
		sendCommand(Command.PLL_CONTROL);
		sendData(0x3c);
		sendCommand(Command.TEMPERATURE_CALIBRATION);
		sendData(0x00);
		sendCommand(Command.VCOM_AND_DATA_INTERVAL_SETTING);
		sendData(0x77);
		sendCommand(Command.TCON_SETTING);
		sendData(0x22);
		sendCommand(Command.RESOLUTION_SETTING);
		sendData(0x02);// #source 640
		sendData(0x80);
		sendData(0x01);// #gate 384
		sendData(0x80);
		sendCommand(Command.VCM_DC_SETTING);
		sendData(0x1E); // #decide by LUT file
		sendCommand((short) 0xe5); // #FLASH MODE
		sendData(0x03);
	}
	
	@Override
	public void sleep() throws IOException {
		log.info("sleep");
	    sendCommand(Command.POWER_OFF);          //power off
	    waitUntilIdle();
	    sendCommand(Command.DEEP_SLEEP);         //deep sleep
	    sendData(0xA5);
	}
	
	public void displayFrame(short[] frame_buffer_black, short[] frame_buffer_red) throws IOException {
		log.info("displayFrame");
		sendCommand(Command.DATA_START_TRANSMISSION_1);
		for (int i = 0; i < EPD_WIDTH / 8 * EPD_HEIGHT; i++) {
			short black = frame_buffer_black[i];
			short yellow = frame_buffer_red[i];
			short result;
			int j = 0;
			while (j < 8) {
				if ((yellow & 0x80) == 0x00) {
					result = 0x04; // red
				} else if ((black & 0x80) == 0x00) {
					result = 0x00; // black
				} else {
					result = 0x03; // white
				}

				result = (short) ((result << 4) & 0xFF);
				black = (short) ((black << 1) & 0xFF);
				yellow = (short) ((yellow << 1) & 0xFF);
				j += 1;
				if ((yellow & 0x80) == 0x00) {
					result |= 0x04; // red

				} else if ((black & 0x80) == 0x00) {
					result |= 0x00;// #black

				} else {
					result |= 0x03; // #white

				}
				black = (byte) ((black << 1) & 0xFF);
				yellow = (byte) ((yellow << 1) & 0xFF);
				sendData(result);
				j += 1;
			}

		}
		sendCommand(Command.DISPLAY_REFRESH);
		delayMs(100);
		waitUntilIdle();
	}



	public void displayImage( String path, String pathYellow, boolean inverted) throws IOException {
		short[] bufferBlack = ImagePreparator.prepareImage(path, EPD_WIDTH, EPD_HEIGHT, inverted);
		short[] bufferYellow = ImagePreparator.prepareImage(pathYellow, EPD_WIDTH, EPD_HEIGHT, inverted);
		displayFrame(bufferBlack, bufferYellow);
		log.info("displayImage " +  path + " pathYellow " + pathYellow +  " finished ");
	}
	
	
}
