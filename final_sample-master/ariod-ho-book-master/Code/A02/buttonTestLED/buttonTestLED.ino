
/* 宣告 button 為整數常數, 並將其值設為 8 (pin 8) */
const int btn = 8;
/* 將按鈕電位 (btVoltage) 設為整數變數, 初始值為 0 */
int btnVol = 0;

void setup() {
   /* 將內建 LED 腳位設為輸出模式 */
  pinMode(LED_BUILTIN, OUTPUT);
   /* 將 button 腳位設為輸入模式 */
  pinMode(btn, INPUT);
}

void loop() {
  /* 讀取按鈕腳位目前的電位值 */
  btnVol = digitalRead(btn);
  /* 若按鈕狀態為高電位 */
  if (btnVol == HIGH){
     /* 內建 LED 腳位 HIGH, LED亮起 */
    digitalWrite(LED_BUILTIN, HIGH);
  } else {
     /* LED 腳位會接收到低電位熄滅 */
    digitalWrite(LED_BUILTIN, LOW);
  }
}
