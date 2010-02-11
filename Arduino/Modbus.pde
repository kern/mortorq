/*

 Basic.pde - example using ModbusMaster library
 
 This file is part of ModbusMaster.
 
 ModbusMaster is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 ModbusMaster is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with ModbusMaster.  If not, see <http://www.gnu.org/licenses/>.
 
 Written by Doc Walker (Rx)
 Copyright ï¿½ 2009, 2010 Doc Walker <dfwmountaineers at gmail dot com>
 $Id: Basic.pde 29 2010-02-05 03:02:19Z dfwmountaineers $
 
 */

#include <ModbusMaster.h>

// instantiate ModbusMaster object as slave ID 2
// defaults to serial port 0 since no port was specified
ModbusMaster node(1);

void setup() {
  // initialize Modbus communication baud rate
  node.begin(9600);
}

void loop() {
  uint8_t j, result;
  uint16_t data[6];

  // slave 1: read (6) 1-bit coils starting at coils 1 to RX buffer
  result = node.ReadCoils(1, 6);
  
  // do something with data if read is successful
  if(result == node.ku8MBSuccess) {
    for(j = 0; j < 6; j++) {
      data[j] = node.GetResponseBuffer(j);
      Serial.println("test2");
    }
  }
}

