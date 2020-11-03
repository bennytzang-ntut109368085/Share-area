void setup() { 
  /* 將第 13 號腳位設為 OUTPUT 模式 */
  pinMode(13, OUTPUT);
} 

void loop() {
   /* 設第 13 號腳位輸出高電位 */
  digitalWrite(13, HIGH);
  /* 暫停 0.5 秒, 維持在上一行所執行的狀態 */
  delay(500);
  /* 將低電位輸出到第 13 號腳位 */
  digitalWrite(13, LOW);
  /* 使程式暫停 0.5 秒, 維持在上一行所執行的狀態 */
  delay(500);
}
