#include "Adu.h"

//****************************************************************************
// Getters
//****************************************************************************

uint8_t Adu::getSlaveId() { return slaveId; }
uint8_t Adu::getFunction() { return function; }
uint8_t Adu::getData(int index) { return data[index]; }
uint16_t Adu::getCrc() { return crc; }
bool Adu::getRequest() { return request; }
bool Adu::getResponse() { return !request; }
bool Adu::getError() { return error; }

//****************************************************************************
// Setters
//****************************************************************************

void Adu::setSlaveId(uint8_t _slaveId) { slaveId = _slaveId; }
void Adu::setData(int index, uint8_t value) { data[index] = value; }
void Adu::setCrc(uint16_t _crc) { crc = _crc; }
void Adu::setResponse(bool _response) { request = !_response; }

//****************************************************************************
// Computed Getters
//****************************************************************************

// Add 4 to the data bits needed to accomadate for the ADU's slave ID,
// function or error code, and CRC.
int Adu::getAduSize() {
  const int bufferBytes = 4;
  
  int dataSize = getDataSize();
  if(dataSize) { return bufferBytes + dataSize; }
  return NULL;
}

// Retrieves the size of the data part of the ADU based on the function.
int Adu::getDataSize() {
  
  // All errors only have the exception code byte as data.
  if(error) { return 1; }
  
  // Implement the data bits needed for each function here.
  if(function == WRITE_SINGLE_COIL) { return 4; }
  
  // Unknown function means we don't know how many bytes the data must be.
  return NULL;
}

// Assembles all the various attributes into the final ADU
uint8_t* Adu::getAdu() {
  int size = getAduSize();
  
  if(size) {
    uint8_t adu[size];
    
    adu[0] = slaveId;
    
    // Re-adds the error value if it's an error.
    adu[1] = function;
    if(error) { adu[1] += 0x80; }
    
    // Places the data into the ADU with an offset of 2
    int dataSize = getDataSize();
    for(int i = 0; i < dataSize; i++) {
      adu[i + 2] = data[i];
    }
    
    // CRC placement with the data offset
    adu[dataSize + 2] = highWord(crc);
    adu[dataSize + 3] = lowWord(crc);
    
    return adu;
  }
  
  // The ADU size wasn't able to be computed for some reason.
  return NULL;
}

//****************************************************************************
// Computed Setters
//****************************************************************************

// Checks if bit 7 (from the LSB) is a 1. This means that the function code
// is an error code because it is over the value of 127.
void Adu::setFunction(uint8_t _function) {
  const int errorBit = 7;
  
  if(bitRead(_function, errorBit)) {
    function = _function - 0x80;
    setError(true);
  }else{
    function = _function;
    setError(false);
  }
}

// Requests cannot be responses.
void Adu::setRequest(bool _request) {
  setError(false);
  request = _request;
}

// Errors can only responses.
void Adu::setError(bool _error) {
  if(error == true) { request = false; }
  error = _error;
}

// Anything with an exception code must be a response.
void Adu::setExceptionCode(uint8_t exceptionCode) {
  setError(true);
  data[0] = exceptionCode;
}

//****************************************************************************
// CRC
//****************************************************************************

// Computes the CRC to check for any errors or to assemble a response.
uint8_t Adu::computeCrc() {
  uint8_t* adu = getAdu();
  
  for(int i = 0; i < (sizeof(adu) - 2); i++) {
    crc = _crc16_update(crc, adu[i]);
  }
  
  return crc;
}

bool Adu::validCrc() { crc == computeCrc(); }