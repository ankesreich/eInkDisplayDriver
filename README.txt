##################################################
### JAVA E-ink display driver for raspberry pi ###
##################################################

One raspberry pi controls two e-ink displays at the same time:
(It is also possible just to control one display)
With this program it is possible to show the bitmaps specified in the property file on the display(s).
The bitmaps have to have indexed colors 1-bit black and white. 

## Hardware
Raspberry Pi 3 - Model B 
Waveshare e ink Display 4.2 https://www.waveshare.com/wiki/4.2inch_e-Paper_Module 
Waveshare e ink Display 7.5 https://www.waveshare.com/wiki/7.5inch_e-Paper_HAT_(C)

## Software
on raspberry pi: java version "1.8.0_181"
for development: Software Development JavaSE-1.8

### pinout 
see display.fzz

### Configuration of pins and bitmaps to show
eInkDisplayDriver.properties

## Build of software
maven clean install

### Installation on raspberry pi
File eInkDisplayDriver.properties and the bitmap Files for the displays have to be in the same directory as the jar file

example:
/home/pi/displayDriver/eInkDisplayDriver-0.0.1-jar-with-dependencies.jar 
/home/pi/displayDriver/eInkDisplayDriver.properties
/home/pi/displayDriver/black.bmp
/home/pi/displayDriver/monocolor.bmp
/home/pi/displayDriver/red.bmp

### usage
java eInkDisplayDriver-0.0.1-jar-with-dependencies.jar 

### options
-display42 
-display75

for further information:
www.ankesreich.de