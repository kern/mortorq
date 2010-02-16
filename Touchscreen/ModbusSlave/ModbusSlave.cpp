#include "ModbusSlave.h"

// Default Modbus slave ID 1
ModbusSlave::ModbusSlave() { slaveId = 1; }
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
  request->setRequest(true);
  
  const int timeout = 50;
  int counter = 0;
  int bytesReceived = 0;
  
  while(counter < timeout) {
    if(Serial.available()) {
      counter = 0;
      if(bytesReceived == 0) {
        // Check if the slave id is for this slave.
        if(slaveId != Serial.read()) { return NULL; }
        request->setSlaveId(slaveId);
      }else if(bytesReceived == 1) {
        // Set the function of the ADU
        request->setFunction(Serial.read());
      }else{
        int dataSize = request->getDataSize();
      
        if(dataSize) {
          for(int i = 0; i < dataSize; i++) {
            request->setData(i, Serial.read());
          }
        
          // Add in the CRC
          uint8_t high = Serial.read();
          uint8_t low = Serial.read();
          request->setCrc(((uint16_t) ((high) << 8 | (low))));
        
          uint8_t *adu = request->getAdu();
          for(int i = 0; i < request->getAduSize(); i++) {
            Serial.print(adu[i], BYTE);
          }
        
          return request;
        }else{
          // Unsupported or unknown function
          request->setExceptionCode(0x01);
          request->setCrc(request->computeCrc());
          return request;
        }
      }
      bytesReceived++;
    }else{
      counter++;
    }
  }
  
  return NULL;
}