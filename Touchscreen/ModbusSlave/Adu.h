#ifndef Adu_h
#define Adu_h

// Wiring core API and CRC calculation
#include "WProgram.h"
#include <util/crc16.h>

// Macros to return low and high word of a 32-bit integer
#define lowWord(ww) ((uint16_t) ((ww) & 0xFFFF))
#define highWord(ww) ((uint16_t) ((ww) >> 16))

// Max sizes
#define MAX_FRAME_SIZE 256;

// Supported function codes
#define WRITE_SINGLE_COIL 0x05;

class Adu {
  public:
    uint8_t getSlaveId();
    uint8_t getFunction();
    uint8_t getFunction();
    uint8_t getData();
    uint16_t getCrc();
    bool getRequest();
    bool getResponse();
    bool getError();
    
    void setFrame(uint8_t);
    void setSlaveId(uint8_t);
    void setFunction(uint8_t);
    void setData(uint8_t);
    void setCrc(uint16_t);
    void setRequest(bool);
    void setResponse(bool);
    void setError(bool);
    
    int aduSize();
    int dataSize();
    
    void setExceptionCode(uint8_t);
    
  private:
    uint8_t slaveId;
    uint8_t function;
    uint8_t data[MAX_FRAME_SIZE - 4]; // Accomadate for the slave ID, function, and CRC
    uint8_t crc;
    bool error;
};

#endif