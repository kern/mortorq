void setup() {
  Serial.begin(9600);
  
  // Loop through and set all the pins to output
  for(int i = 0; i < 13; i++) {
    pinMode(i, OUTPUT);
  }
}

void loop() {
  while(Serial.available()) {
    uint8_t byte = Serial.read();
    int command = byte - 0x41;
    
    if(command >= 0 and command <= 13) {
      // DVNO: 4 CAPTIAL LETTERS
      digitalWrite(command, HIGH);
    }else if(command >= 32 and command <= 45) {
      // do the d a n c e!
      digitalWrite(command - 32, LOW);
    }
  }
}
