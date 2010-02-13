#ifndef ModbusSlave_h
#define ModbusSlave_h

// Wiring core API and CRC calculation
#include "WProgram.h"
#include <util/crc16.h>

// Macros to return low and high word of a 32-bit integer
#define lowWord(ww) ((uint16_t) ((ww) & 0xFFFF))
#define highWord(ww) ((uint16_t) ((ww) >> 16))

// Macro to generate 32-bit integer from (2) 16-bit words
#define LONG(hi, lo) ((uint32_t) ((hi) << 16 | (lo)))

// Max sizes
#define MAX_BUFFER_SIZE 0x3F;

class ModbusSlave {
  public:
    ModbusSlave();
    ModbusSlave(uint8_t);
    
    void begin();
    void begin(uint16_t);
    
    uint16_t getResponseBuffer(uint8_t);
    void clearResponseBuffer();
    
    bool setTransmitBuffer(uint8_t, uint16_t);
    void clearTransmitBuffer();
    
    uint8_t readCoils(uint16_t, uint16_t);
    uint8_t readDiscreteInputs(uint16_t, uint16_t);
    uint8_t readHoldingRegisters(uint16_t, uint16_t);
    uint8_t readInputRegisters(uint16_t, uint8_t);
    uint8_t writeSingleCoil(uint16_t, uint8_t);
    uint8_t writeSingleRegister(uint16_t, uint16_t);
    uint8_t writeMultipleCoils(uint16_t, uint16_t);
    uint8_t writeMultipleRegisters(uint16_t, uint16_t);
    uint8_t maskWriteRegister(uint16_t, uint16_t, uint16_t);
    uint8_t readWriteMultipleRegisters(uint16_t, uint16_t, uint16_t, uint16_t);
  
  private:
    uint8_t slaveId;
};

#endif