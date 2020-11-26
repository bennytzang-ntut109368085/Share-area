
int segmentPattern[10][7] =
  { {1,1,1,1,1,1,0}, /* 使七段顯示器顯示數字 0 */
    {0,1,1,0,0,0,0}, /* 顯示數字 1 */
    {1,1,0,1,1,0,1}, /* 顯示數字 2 */
    {1,1,1,1,0,0,1}, /* 顯示數字 3 */
    {0,1,1,0,0,1,1}, /* 顯示數字 4 */
    {1,0,1,1,0,1,1}, /* 顯示數字 5 */
    {1,0,1,1,1,1,1}, /* 顯示數字 6 */
    {1,1,1,0,0,0,0}, /* 顯示數字 7 */
    {1,1,1,1,1,1,1}, /* 顯示數字 8 */
    {1,1,1,1,0,1,1}, /* 顯示數字 9 */
  };

const int btn = 2; /*/把 btn 腳位設為 2 */
int count=0;

void setup() {
  for(int pin = 5; pin <= 11; pin = pin +1) {
    pinMode(pin, OUTPUT); /* 將 5-11 數位腳位模式設為 OUTPUT */
  }
  pinMode(btn, INPUT); /* 將 btn 的腳位模式設為 INPUT */
}

void sevenSegmentWrite(int digit) { /* digit 顯示 LED */
  for(int ledSeg = 0; ledSeg < 7; ledSeg = ledSeg +1) {
    digitalWrite(ledSeg+5, segmentPattern[digit][ledSeg]);
  }
}

void loop() {
  int btnStatus = 0; /* buttonStatus 為按鈕狀態變數, 並將其值設為 0 */
  btnStatus = digitalRead(btn);
  delay(100); /* 程式維持在上一行的狀態 0.1 秒 */
  sevenSegmentWrite(count); /* 將 count 值顯示到七段顯示器 */
  if (btnStatus == HIGH) /* 若 buttonStatus 為 HIGH */
    count = count + 1; /* count 值加 1 */
  if(count > 9) /* 如果 count 的值大於 9 */
    count = 0; /* count 值將會回到 -1 */
  delay(100); /* 程式維持在上一行的狀態 0.1 秒鐘 */
}
