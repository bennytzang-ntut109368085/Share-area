

#include <SoftwareSerial.h> 

SoftwareSerial bluetooth(8, 9); 
/* 接收腳, 傳送腳 */
/* RX, TX */

unsigned long previousMillis = 0; /* will store last time */
const long interval = 500; /* interval at which to delay */
static uint32_t tmp; /* increment this */

void setup() {
  pinMode(13, OUTPUT); /* for LED status */
  bluetooth.begin(9600); /* start the bluetooth uart at 9600 which is its default */
  delay(200); /* wait for voltage stabilize */
}

void loop() {
  if (bluetooth.available()) { /* check if anything in UART buffer */
    if(bluetooth.read() == '1'){ /* did we receive this character? */
       digitalWrite(13,!digitalRead(13)); /* if so, toggle the onboard LED */
    }
  }
  
  unsigned long currentMillis = millis();
  if (currentMillis - previousMillis >= interval) {
    previousMillis = currentMillis;
    bluetooth.print(tmp++); /* print this to bluetooth module */
  }
}
