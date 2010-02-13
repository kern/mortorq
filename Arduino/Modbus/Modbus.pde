#include <ModbusMaster.h>

// instantiate ModbusMaster object as slave ID 2
// defaults to serial port 0 since no port was specified
ModbusMaster node(1);
int ledPin =  13;

void setup()   {                
  pinMode(ledPin, OUTPUT);     
  node.begin(9600);
}

void loop() {
  uint8_t j, result;
  uint16_t data[6];

  result = node.ReadCoils(1, 6);

  // do something with data if read is successful
  if(result == node.ku8MBSuccess) {
    digitalWrite(ledPin, LOW);
  }else{
    digitalWrite(ledPin, HIGH);
  }
}

