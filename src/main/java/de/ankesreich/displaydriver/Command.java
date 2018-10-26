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

import java.util.Arrays;
import java.util.List;

public enum Command {
	PANEL_SETTING(0x00, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)),
	POWER_SETTING(0x01, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)),
	POWER_OFF(0x02, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)),
	POWER_OFF_SEQUENCE_SETTING(0x03, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)), 
	POWER_ON(0x04, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)), 
	POWER_ON_MEASURE(0x05, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)), 
	BOOSTER_SOFT_START(0x06, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)), 
	DEEP_SLEEP(0x07, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)), 
	DATA_START_TRANSMISSION_1(0x10, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)), 
	DATA_STOP(0x11, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)), 
	DISPLAY_REFRESH(0x12, Arrays.asList(Device.FOUR_TWO_INCH, Device.SEVEN_FIVE_INCH)), 
	LUT_FOR_VCOM(0x20, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	PLL_CONTROL(0x30, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	TEMPERATURE_SENSOR_COMMAND(0x40, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	TEMPERATURE_SENSOR_WRITE(0x42, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	TEMPERATURE_SENSOR_READ(0x43, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	VCOM_AND_DATA_INTERVAL_SETTING(0x50, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	LOW_POWER_DETECTION(0x51, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	TCON_SETTING(0x60, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	RESOLUTION_SETTING(0x61, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), //TCON_RESOLUTION
	GET_STATUS(0x71, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	AUTO_MEASUREMENT_VCOM(0x80, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	READ_VCOM_VALUE(0x81, Arrays.asList(Device.FOUR_TWO_INCH,  Device.SEVEN_FIVE_INCH)), 
	VCM_DC_SETTING(0x82, Arrays.asList(Device.FOUR_TWO_INCH,Device.SEVEN_FIVE_INCH)), 
	
	//4_2
	TEMPERATURE_SENSOR_SELECTION(0x41, Arrays.asList(Device.FOUR_TWO_INCH)), 
	DATA_START_TRANSMISSION_2(0x13, Arrays.asList(Device.FOUR_TWO_INCH)), 
	LUT_WHITE_TO_WHITE(0x21, Arrays.asList(Device.FOUR_TWO_INCH)), 
	LUT_BLACK_TO_WHITE(0x22, Arrays.asList(Device.FOUR_TWO_INCH)), 
	LUT_WHITE_TO_BLACK(0x23, Arrays.asList(Device.FOUR_TWO_INCH)), 
	LUT_BLACK_TO_BLACK(0x24, Arrays.asList(Device.FOUR_TWO_INCH)), 
	GSST_SETTING(0x65, Arrays.asList(Device.FOUR_TWO_INCH)), 
	PARTIAL_WINDOW(0x90, Arrays.asList(Device.FOUR_TWO_INCH)), 
	PARTIAL_IN(0x91, Arrays.asList(Device.FOUR_TWO_INCH)), 
	PARTIAL_OUT(0x92, Arrays.asList(Device.FOUR_TWO_INCH)), 
	PROGRAM_MODE(0xA0, Arrays.asList(Device.FOUR_TWO_INCH)), 
	ACTIVE_PROGRAMMING(0xA1, Arrays.asList(Device.FOUR_TWO_INCH)), 
	READ_OTP(0xA2, Arrays.asList(Device.FOUR_TWO_INCH)), 
	POWER_SAVING(0xE3, Arrays.asList(Device.FOUR_TWO_INCH)),
	
	//7_5
	IMAGE_PROCESS(0x13, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	LUT_BLUE(0x21, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	LUT_WHITE(0x22, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	LUT_GRAY_1(0x23, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	LUT_GRAY_2(0x24, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	LUT_RED_0(0x25, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	LUT_RED_1(0x26, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	LUT_RED_2(0x27, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	LUT_RED_3(0x28, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	LUT_XON(0x29, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	TEMPERATURE_CALIBRATION(0x41, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	SPI_FLASH_CONTROL(0x65, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	REVISION(0x70, Arrays.asList(Device.SEVEN_FIVE_INCH)),
	
	;

	private static enum Device
	{
		FOUR_TWO_INCH,SEVEN_FIVE_INCH;
	}
	
	private int command;
	private List<Device> devices;

	private Command(int command, List<Device> device) {
		this.command = command;
		this.devices = device;
		//make sure it is a short and not bigger..
		Short.parseShort(Integer.toString(command), 16);
	}

	public List<Device> getDevices() {
		return devices;
	}

	public short getCommand() {
		return (short)command;
	}
	
}
