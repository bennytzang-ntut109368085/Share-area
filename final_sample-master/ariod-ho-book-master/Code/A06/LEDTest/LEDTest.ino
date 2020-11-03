
const int firstNumPinLED = 4;
const int lastNumPinLED = 13;

void setup() {
  for(int led = firstNumPinLED; led <= lastNumPinLED; led = led + 1){
    pinMode( led, OUTPUT);
  }
}
void loop() {
  for(int s = 0; s <= 5; s++) {

    for(int led = firstNumPinLED; led <= lastNumPinLED; led++) {
      digitalWrite( led, HIGH);
      delay( s * 10);
      digitalWrite( led, LOW);
    }
    
    for(int led = lastNumPinLED; led >= firstNumPinLED; led--) {
      digitalWrite( led, HIGH);
      delay( s * 10);
      digitalWrite( led, LOW);
    }
  }
}
