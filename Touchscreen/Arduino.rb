require 'rubygems'
require 'serialport'

class Arduino
  
  def initialize
    port = '/dev/cu.usbserial-A8008pAI4'
    baud = 9600
    data_bits = 8
    stop_bits = 1
    parity = SerialPort::NONE
    
    @serial = SerialPort.new port, baud, data_bits, stop_bits, parity
  end
  
  def on(pin)
    @serial.write((pin+65).chr)
  end
  
  def off(pin)
    @serial.write((pin+97).chr)
  end
  
  def close
    @serial.close
  end
  
end