require 'rubygems'
require 'Arduino'

arduino = Arduino.new

while true
  arduino.on 13
  sleep 0.5
  arduino.off 13
  sleep 0.5
end

arduino.close