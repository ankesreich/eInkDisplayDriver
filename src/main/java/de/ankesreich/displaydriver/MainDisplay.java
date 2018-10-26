package de.ankesreich.displaydriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiBcmPin;
import com.pi4j.io.spi.SpiChannel;

import de.ankesreich.displaydriver.fourtwo.Epd4in2;
import de.ankesreich.displaydriver.sevenfive.Epd7in5;

public class MainDisplay {
	
	private static Logger log = Logger.getLogger(ImagePreparator.class.getName());
	
	private static final String NAME_PROPERTYFILE = "eInkDisplayDriver.properties";
	private static final String DISPLAY42 = "-display42";

	private static final String DISPLAY75 = "-display75";

	//display 4.2
	private static final String FILE_NAME_42 = "file.42";
	private static final String FILE_INVERTED_42 = "file.inverted.42";
	private static final String DCPIN_42 = "dcPin.42";
	private static final String RESETPIN_42 = "resetPin.42";
	private static final String BUSYPIN_42 = "busyPin.42";
	private static final String CSPIN_42 = "csPin.42";
	private static final String SPIDEVICE_42= "spiDevice.42";
	
	//display 7.5
	private static final String FILE_BLACK_75 = "file.black.75";
	private static final String FILE_COLOR_75 = "file.color.75";
	private static final String FILE_INVERTED_75 = "file.inverted.75";
	private static final String DCPIN_75 = "dcPin.75";
	private static final String RESETPIN_75 = "resetPin.75";
	private static final String BUSYPIN_75 = "busyPin.75";
	private static final String CSPIN_75 = "csPin.75";
	private static final String SPIDEVICE_75= "spiDevice.75";
	
	private static final Map<Integer, Pin> mapIntBcmPin = new HashMap<>();
	
	static
	{
		mapIntBcmPin.put(2, RaspiBcmPin.GPIO_02);
		mapIntBcmPin.put(3, RaspiBcmPin.GPIO_03);
		mapIntBcmPin.put(4, RaspiBcmPin.GPIO_04);
		mapIntBcmPin.put(5, RaspiBcmPin.GPIO_05);
		mapIntBcmPin.put(6, RaspiBcmPin.GPIO_06);
		mapIntBcmPin.put(7, RaspiBcmPin.GPIO_07);
		mapIntBcmPin.put(8, RaspiBcmPin.GPIO_08);
		mapIntBcmPin.put(9, RaspiBcmPin.GPIO_09);
		mapIntBcmPin.put(10, RaspiBcmPin.GPIO_10);
		mapIntBcmPin.put(11, RaspiBcmPin.GPIO_11);
		mapIntBcmPin.put(12, RaspiBcmPin.GPIO_12);
		mapIntBcmPin.put(13, RaspiBcmPin.GPIO_13);
		mapIntBcmPin.put(14, RaspiBcmPin.GPIO_14);
		mapIntBcmPin.put(15, RaspiBcmPin.GPIO_15);
		mapIntBcmPin.put(16, RaspiBcmPin.GPIO_16);
		mapIntBcmPin.put(17, RaspiBcmPin.GPIO_17);
		mapIntBcmPin.put(18, RaspiBcmPin.GPIO_18);
		mapIntBcmPin.put(19, RaspiBcmPin.GPIO_19);
		mapIntBcmPin.put(20, RaspiBcmPin.GPIO_20);
		mapIntBcmPin.put(21, RaspiBcmPin.GPIO_21);
		mapIntBcmPin.put(22, RaspiBcmPin.GPIO_22);
		mapIntBcmPin.put(23, RaspiBcmPin.GPIO_23);
		mapIntBcmPin.put(24, RaspiBcmPin.GPIO_24);
		mapIntBcmPin.put(25, RaspiBcmPin.GPIO_25);
		mapIntBcmPin.put(26, RaspiBcmPin.GPIO_26);
		mapIntBcmPin.put(27, RaspiBcmPin.GPIO_27);
		mapIntBcmPin.put(28, RaspiBcmPin.GPIO_28);
		mapIntBcmPin.put(29, RaspiBcmPin.GPIO_29);
		mapIntBcmPin.put(30, RaspiBcmPin.GPIO_30);
		mapIntBcmPin.put(31, RaspiBcmPin.GPIO_31);
	}
	
	public static void main(String args[]) {
		if (args.length == 0 || args.length > 2) {
			usage();
			System.exit(0);
		}
		Properties props = null;
		try {
			props = loadProps();
		} catch (IOException e) {
			log.log(Level.SEVERE, "Exception loading properties " + NAME_PROPERTYFILE + ": " + e.getMessage(), e);
		}
		for(String arg : args)
		{
			if(!DISPLAY42.equals(arg) && !DISPLAY75.equals(arg))
			{
				throw new RuntimeException("Parameter " + arg + " not valid.");
			}
		}
		
		if (DISPLAY42.equals(args[0]) ||  (args.length == 2 && DISPLAY42.equals(args[1]))) {
			display42(props);
		}
		if (DISPLAY75.equals(args[0]) || (args.length == 2 && DISPLAY75.equals(args[1]))) {
			display75(props);
		}
		
		
		
	}
	
	private static Properties loadProps() throws IOException
	{
		Properties props = new Properties();
		FileInputStream input = new FileInputStream(NAME_PROPERTYFILE);
		// load a properties file
		props.load(input);
		return props;
	}

	
	
	private static Pin getPin(String property, Properties props)
	{
		String value = getPropValue(property, props);
		try
		{
			int intPin = Integer.parseInt(value);
			Pin pin = mapIntBcmPin.get(intPin);
			if(pin != null)
			return pin;
		}
		catch(Exception e)
		{
			throw new RuntimeException("Property " + property + " not valid, no pin found.");
		}
		throw new RuntimeException("Property " + property + " not valid, no pin found.");
	}

	private static String getPropValue(String property, Properties props) {
		String value = (String)props.get(property);
		if(value == null)
		{
			throw new RuntimeException("Property " + property + " not found.");
		}
		return value;
	}
	
	private static SpiChannel getSpiChannel(String property, Properties props)
	{
		String value = getPropValue(property, props);
		try
		{
			int channel = Integer.parseInt(value);
			if(channel == 0)
			{
				return SpiChannel.CS0;
			}
			if(channel == 1)
			{
				return SpiChannel.CS1;
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("Property " + property + " not valid, no SpiChannel found.");
		}
		throw new RuntimeException("Property " + property + " with value " + value + " not valid, no SpiChannel found.");
	}
	
	private static String getFilePath(String property, Properties props) {
		String value = getPropValue(property, props);
		if(!new File (value).exists())
		{
			throw new RuntimeException("Property " + property + " no File " + value + " found.");
		}
		return value;
	}
	
	private static boolean isInverted(String property, Properties props) {
		String value = getPropValue(property, props);
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			throw new RuntimeException("Property " + property + " not valid boolean.");
		}
	}

	private static void display42(Properties props) {
		
		RaspiConfiguration config = new RaspiConfiguration(
				getPin(DCPIN_42, props),
				getPin(RESETPIN_42, props),
				getPin(BUSYPIN_42, props),
				getPin(CSPIN_42, props),
				getSpiChannel(SPIDEVICE_42, props));
		Epd4in2 epd4in2 = new Epd4in2();
		try {
			epd4in2.init(config);
			Thread.sleep(5000);
			epd4in2.displayImageFromFile(getFilePath(FILE_NAME_42, props), isInverted(FILE_INVERTED_42, props));
			epd4in2.sleep();

		} catch (Exception e) {
			log.log(Level.SEVERE, "display42 Exception " + e.getMessage(), e);
		}
	}

	private static void display75(Properties props) {
		RaspiConfiguration config = new RaspiConfiguration(
				getPin(DCPIN_75, props),
				getPin(RESETPIN_75, props),
				getPin(BUSYPIN_75, props),
				getPin(CSPIN_75, props),
				getSpiChannel(SPIDEVICE_75, props));
		Epd7in5 epd = new Epd7in5();
		try {
			epd.init(config);
			Thread.sleep(8000);
			epd.displayImage(getFilePath(FILE_BLACK_75, props),
					getFilePath(FILE_COLOR_75, props), 
					isInverted(FILE_INVERTED_75, props));
			epd.sleep();
		} catch (Exception e) {
			log.log(Level.SEVERE, "display75 Exception " + e.getMessage(), e);
		}
	}

	private static void usage() {
		log.info("usage: " + DISPLAY42 + " " + DISPLAY75);
	}
	
}
