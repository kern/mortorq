#include <ModbusSlave.h>
#include <Adu.h>

ModbusSlave slave = ModbusSlave();

void setup() {
  pinMode(13, OUTPUT);
  slave.begin(9600);
}

void loop() {
  if(slave.refresh()) {
    digitalWrite(13, HIGH);
  }else{
    digitalWrite(13, LOW);
  }
}