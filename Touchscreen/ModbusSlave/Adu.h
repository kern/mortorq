#ifndef Adu_h
#define Adu_h

// Wiring core API and CRC calculation
#include "WProgram.h"
#include <util/crc16.h>

// Macros to return low and high word of a 32-bit integer
#define lowWord(ww) ((uint16_t) ((ww) & 0xFFFF))
#define highWord(ww) ((uint16_t) ((ww) >> 16))

class Adu {
  public:
    uint8_t getSlaveId();
    uint8_t getFunction();
    uint8_t getData(int);
    uint16_t getCrc();
    bool getRequest();
    bool getResponse();
    bool getError();
    
    void setSlaveId(uint8_t);
    void setData(int, uint8_t);
    void setCrc(uint16_t);
    void setResponse(bool);
    
    int getAduSize();
    int getDataSize();
    uint8_t* getAdu();
    
    void setFunction(uint8_t);
    void setRequest(bool);
    void setError(bool);
    void setExceptionCode(uint8_t);
    
    uint8_t computeCrc();
    bool validCrc();
    
  private:
    static const int MAX_DATA_SIZE = 252;
    static const uint8_t WRITE_SINGLE_COIL = 0x05;
    
    uint8_t slaveId;
    uint8_t function;
    uint8_t data[MAX_DATA_SIZE];
    uint8_t crc;
    bool request;
    bool error;
};

#endif