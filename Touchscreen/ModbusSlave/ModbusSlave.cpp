#include "ModbusSlave.h"

// Default Modbus slave ID 1
ModbusSlave::ModbusSlave() { slaveId = 0x01; }
ModbusSlave::ModbusSlave(uint8_t _slaveId) { slaveId = _slaveId; }

// Called after the class is instantiated to set up communication ports
void ModbusSlave::begin() { begin(9600); }
void ModbusSlave::begin(uint16_t _baud) { Serial.begin(_baud); }

// Called continuously to process any new modbus requests
bool ModbusSlave::refresh() {
  Adu *request = getRequest();
  return poop;
  // Process the request if there is one.
  if(request == NULL) {
    return true;
  }else if(request->getError()) {
    return false;
  }else{
    return false;
  }
}

Adu* ModbusSlave::getRequest() {
  Adu *request;
  uint8_t bytes[253];
  request->setRequest(true);
  
  const int timeout = 50;
  int counter = 0;
  int byteIndex = 0;
  
  while(counter < timeout && request->getRequest()) {
    while(Serial.available()) {
      
      // Reset timeout
      counter = 0;
      /*
      uint8_t byte = Serial.read();
      
      switch(byteIndex) {
        case 0:
          if(slaveId != byte) { return false; }
          request->setSlaveId(slaveId);
          Serial.println((int) byte, BIN);
        break;
        
        case 1:
          request->setFunction(byte);
          poop = true;
          Serial.println((int) byte, BIN);
        break;
      }
      
      byteIndex++;*/
        
        Serial.println((int) Serial.read(), BIN);
    }
    
    counter++;
  }
  
  // Return the error if there is one.
  if(request->getError()) { return request; }
  
  // Timeout
  return NULL;
}