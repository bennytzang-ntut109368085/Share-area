const int analogPin = A5;
int readVal ;

void setup() {
  Serial.begin(9600);
}

void loop() {
  readVal = analogRead(analogPin);
  Serial.println(readVal);
  delay(300);
}
