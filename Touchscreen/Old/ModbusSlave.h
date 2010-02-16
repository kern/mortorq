#ifndef ModbusSlave_h
#define ModbusSlave_h

// Wiring core API and CRC calculation
#include "WProgram.h"

class ModbusSlave {
  public:
    ModbusSlave();
    ModbusSlave(uint8_t);
    
    void begin();
    void begin(uint16_t);
    
  private:
    uint8_t slaveId;
    
};

#endif