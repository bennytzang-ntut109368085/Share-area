
int potPin = A0;
int val;
int dat;

void setup(){
    Serial.begin(9600);
    pinMode(potPin,INPUT);
}

void loop(){
    val = analogRead(potPin);
    dat = (125*val)>>8;
    Serial.print("Tep :");
    Serial.print(dat);
    Serial.println("c");
    delay(500);
}
