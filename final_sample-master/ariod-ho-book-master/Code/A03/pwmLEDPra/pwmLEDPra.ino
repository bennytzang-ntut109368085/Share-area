/* 宣告整數常數 led, 並將其值設為 3 */
const int led = 9;

void setup() {

}

void loop() {
  for (int fadeVal = 0 ; fadeVal <= 255; fadeVal = fadeVal + 15) {
    /* 將 PWM 調整後的電壓值輸出到指定的 PWM 腳位 */
    analogWrite(led, fadeVal);
    /* 使程式停止在上一行的階段 0.1 秒 */
    delay(100);
  }

  for (int fadeVal = 255 ; fadeVal >= 0; fadeVal = fadeVal - 15) {
     /* 將 PWM 調整後的電壓值輸出到指定的 PWM 腳位 */
    analogWrite(led, fadeVal);
     /* 使程式停止在上一行的階段 0.1 秒 */
    delay(100);
  }
}
