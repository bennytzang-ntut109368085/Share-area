const int val = A5;
const int led = 6;

void setup() {

}

void loop() {
  int sensorVal = analogRead(val);
  analogWrite(led, sensorVal/4);
  delay(200);
}
