#include "ModbusSlave.h"

// Default Modbus slave ID 1
ModbusSlave::ModbusSlave(void) { slaveId = 1; }
ModbusSlave::ModbusSlave(uint8_t _slaveId) { slaveId = _slaveId; }

// Called after the class is instantiated to set up communication ports
void ModbusSlave::begin(void) { start(9600); }
void ModbusSlave::begin(uint16_t _baud) { Serial.begin(_baud); }

uint8_t ModbusSlave::getAdu(void) {
  
  // No data
  uint8_t length = Serial.available();
  if(length == 0) { return 0; }
  
  // Get the request
  uint8_t adu[length];
  if(getRequest(adu) == 0) { return 0; }
  
  // Check slave ID and function
  if(adu[0] != slaveId) { return 0; }
  uint8_t function = adu[1];
  
  switch(function) {
    case ku8MBReadCoils:
    case ku8MBReadDiscreteInputs:
    case ku8MBReadInputRegisters:
    case ku8MBReadHoldingRegisters:
    case ku8MBReadWriteMultipleRegisters:
      bytesLeft = adu[2];
      break;
      
    case ku8MBWriteSingleCoil:
    case ku8MBWriteMultipleCoils:
    case ku8MBWriteSingleRegister:
      bytesLeft = 3;
      break;
      
    case ku8MBMaskWriteRegister:
      bytesLeft = 5;
      break;
    }
  }
  
  // Invalid CRC
  if(validateCrc(adu, length) == false) {
    return 0;
  }
  
  return status;
}

bool ModbusSlave:validateCrc(uint8_t *adu[], uint8_t aduSize) {
  
  uint16_t crc = 0xFFFF;
  for(i = 0; i < (aduSize - 2); i++) {
    crc = _crc16_update(crc, adu[i]);
  }
  
  if(lowByte(crc) != adu[aduSize - 2] || highByte(crc) != adu[aduSize - 1]) {
    return false; // Invalid CRC
  }
  
  return true;
}



int start_mb_slave(void) {
  unsigned char query[MAX_MESSAGE_LENGTH];
  unsigned char errpacket[EXCEPTION_SIZE + CHECKSUM_SIZE];
  unsigned int start_addr;
  int exception;
  int length = Serial.available();
  unsigned long now = millis();
  
  // No new data
  if(length == 0) { return 0; }
  
  if(lastBytesReceived != length) {
    lastBytesReceived = length;
    Nowdt = now + 5; // Reset timeout
    return 0;
  }
  
  if(now < Nowdt) {
    return 0;
  }
  
  lastBytesReceived = 0;
  length = modbus_request(slave, query);
  if(length < 1) {
    return length;
  }else{
    exception = validate_request(query, length, regs_size);
    if(exception) {
      build_error_packet(slave, query[FUNC], exception, errpacket);
      send_reply(errpacket, EXCEPTION_SIZE);
      return exception;
    }else{
      start_addr = ((int) query[START_H] << 8) + (int) query[START_L];
      if(0x03 == query[FUNC]) {
        return read_holding_registers(slave, start_addr, query[REGS_L], regs);
      }
      
      if(0x10 == query[FUNC]) {
        return preset_multiple_registers(slave, start_addr, query[REGS_L], query, regs);
      }
    }
  }
}

uint8_t receiveRequest(uint8_t *request) {
  uint8_t bytesReceived = 0;
  
  while(Serial.available()) {
    receivedString[bytesReceived] = Serial.read();
    bytesReceived++;
    if(bytesReceived >= MAX_MESSAGE_LENGTH) { return 0; }
  }
  
  return bytesReceived;
}