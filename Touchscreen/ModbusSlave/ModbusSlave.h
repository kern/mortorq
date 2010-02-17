#ifndef ModbusSlave_h
#define ModbusSlave_h

// Wiring core API and CRC calculation
#include "WProgram.h"
#include "Adu.h"

class ModbusSlave {
  public:
    ModbusSlave();
    ModbusSlave(uint8_t);
    
    void begin();
    void begin(uint16_t);
    
    bool refresh();
    
    Adu* getRequest();
    uint8_t* receiveRequest();
    
  private:
    uint8_t slaveId;
    bool poop;
};

#endif