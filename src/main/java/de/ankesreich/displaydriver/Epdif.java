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

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPinNumberingScheme;
import com.pi4j.io.spi.SpiDevice;
import com.pi4j.io.spi.SpiFactory;


public abstract class Epdif {

	Logger log = Logger.getLogger(Epdif.class.getName());
	private SpiDevice spiDevice;
	private GpioPinDigitalOutput resetPin;
	private GpioPinDigitalOutput dcPin;
	private GpioPinDigitalOutput csPin;
	private GpioPinDigitalInput busyPin;
	private GpioController gpio;

	protected abstract void initDisplay()  throws IOException;
	
	public abstract void sleep() throws IOException;
	
	public void wakeUp() throws IOException
	{
		log.info("wakeUp");
		reset();
		initDisplay();
	}
	
	public void sendData(int data) throws IOException
	{
		gpio.setState(PinState.HIGH, dcPin);
		log.fine("sendData " + data);
		checkShort(data);
		spiDevice.write((short)data);
	}

	private void checkShort(int data) {
		try
		{
			Short.parseShort(Integer.toString(data), 16);
		}
		catch(Exception e)
		{
			throw new RuntimeException("the value is bigger than a short, won't work: " + data);
		}
	}
	
	
	
	/**
	 *  @brief: module reset. 
	 *          often used to awaken the module in deep sleep, 
	 *          see Epd::Sleep();
	 */
	protected void reset()
	{
		log.info("reset");
		gpio.setState(PinState.LOW, resetPin);
	    delayMs(200);
	    gpio.setState(PinState.HIGH, resetPin);
	    delayMs(200);   
	}
	
	public void sendCommand(Command command) throws IOException {
		log.info("sendCommand " + command.getCommand());
		gpio.setState(PinState.LOW, dcPin);
		checkShort(command.getCommand());
		spiDevice.write(command.getCommand());
	}
	
	public void sendCommand(short command) throws IOException {
		log.info("sendCommand " + command);
		gpio.setState(PinState.LOW, dcPin);
		spiDevice.write(command);
	}

	
	public void waitUntilIdle() {
		int count = 0;
	    while(busyPin.isHigh())
	    {
	    	delayMs(1);
	    	count++;
	    	if (count == 10000)
	    	{
	    		throw new RuntimeException("Busy Pin is too busy");
	    	}      
	    }
	}
	
	public void delayMs (long ms)
	{
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			throw new RuntimeException("InterruptedException should never happen", e);
		}
	}

	protected void initPins(RaspiConfiguration pinconfig) throws IOException {
		GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
	
		spiDevice = SpiFactory.getInstance(pinconfig.getSpiDevice(), SpiDevice.DEFAULT_SPI_SPEED,
				SpiDevice.DEFAULT_SPI_MODE);

		gpio = GpioFactory.getInstance();

		resetPin = gpio.provisionDigitalOutputPin(pinconfig.getResetPin());
		dcPin = gpio.provisionDigitalOutputPin(pinconfig.getDcPin());
		csPin = gpio.provisionDigitalOutputPin(pinconfig.getCsPin());
		busyPin = gpio.provisionDigitalInputPin(pinconfig.getBusyPin());

		logBusy();
		log.info(".. init finished");
	}

	private void logBusy() {
		
		for (GpioPinDigitalOutput pin : Arrays.asList(resetPin, dcPin, csPin))
		{
			if( gpio.isHigh(pin))
			{
				log.info(pin.getName()+  " is busy (high).");
			}
		}
		if( gpio.isHigh(busyPin))
		{
			log.info(busyPin.getName()+  " is busy (high).");
		}
	}

	
}
