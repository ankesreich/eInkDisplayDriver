

package de.ankesreich.displaydriver;

import java.util.logging.Logger;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.spi.SpiChannel;

public class RaspiConfiguration {
	
	private static Logger log = Logger.getLogger(RaspiConfiguration.class.getName());
	
	private Pin resetPin;
	private Pin dcPin;
	private Pin csPin ;
	private Pin busyPin ;
	private SpiChannel spiDevice;
    
	public RaspiConfiguration(Pin dcPin, Pin resetPin, Pin busyPin, Pin csPin, SpiChannel spiDevice) {
		this.dcPin = dcPin;
		this.resetPin = resetPin;
		this.busyPin = busyPin;
		this.csPin = csPin;
		this.spiDevice = spiDevice;
		log.info("Configuration:");
		log.info(logPinInfo (dcPin, "dcPin"));
		log.info(logPinInfo (resetPin, "resetPin"));
		log.info(logPinInfo (busyPin, "busyPin"));
		log.info(logPinInfo (csPin, "csPin"));
		log.info("SPI Channel" + spiDevice.getChannel());
	}
	private String logPinInfo(Pin pin, String type)
	{
		return "type " + type + pin.getAddress() + " "+ pin.getName() ;
	}

	public SpiChannel getSpiDevice() {
		return spiDevice;
	}

	public Pin getResetPin() {
		return resetPin;
	}

	public void setResetPin(Pin resetPin) {
		this.resetPin = resetPin;
	}

	public Pin getDcPin() {
		return dcPin;
	}

	public void setDcPin(Pin dcPin) {
		this.dcPin = dcPin;
	}

	public Pin getCsPin() {
		return csPin;
	}

	public void setCsPin(Pin csPin) {
		this.csPin = csPin;
	}

	public Pin getBusyPin() {
		return busyPin;
	}

	public void setBusyPin(Pin busyPin) {
		this.busyPin = busyPin;
	}
}
