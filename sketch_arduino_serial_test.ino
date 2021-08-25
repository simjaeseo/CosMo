#define TRIG1 2
#define ECHO1 3
#define TRIG2 12
#define ECHO2 13

#define LOCK1 10
#define MAG1 5

int mag_check = 1;
int pw = 0;
char val =0;
int int_val = 0;
const char endMArk = 'E';

void setup()
{
 Serial.begin(9600); 
}

void loop()
{
  if(Serial.available()){
    val = Serial.read();
    if(isDigit(val))
    {
      int_val = (int_val*10) + (val-'0');
    }
    else if(val==endMArk)
    {
       pw = int_val;
       int_val = 0;
    }
    
    
    delay(200);
  }
  
  if(pw == 500){ //start(lock open, back_detect on)
    lock(500);
    back_detect(500);
  }
  else if(pw == 600){ //finish(lock close, back_detect off)
    back_detect(600);
    MAG(600);
  }
  else if(pw == 1100){ // lock open
    lock(500);
  }
  else if(pw == 2100){ // back_detect on
    back_detect(500);
  }
  else if(pw == 2200){ // back_detect off
    back_detect(600);
  }
  
  
}

void MAG(int val){
  mag_check = digitalRead(MAG1);
  if(val == 600){
    if(mag_check != 1){
      Serial.println(100); //helmet exist
    }
    else{
      Serial.println(200); //helmet empty
    }
  }
  else{
    Serial.println(101);
  }
}

void lock(int val){
  if(val==500){
    digitalWrite(LOCK1, HIGH);
    delayMicroseconds(2);
    digitalWrite(LOCK1, LOW);
    delayMicroseconds(2);
  }
  else{
    Serial.println(501, DEC);
  }
  
}

void back_detect(int val){
  if(val==500){   //detect on
     digitalWrite(TRIG1,LOW);
     delayMicroseconds(2);
     digitalWrite(TRIG1,HIGH);
     delayMicroseconds(10);
     digitalWrite(TRIG1,LOW);
     
     long distance1 = pulseIn(ECHO1,HIGH)/58.2;
     delay(10);
     
     digitalWrite(TRIG2,LOW);
     delayMicroseconds(2);
     digitalWrite(TRIG2,HIGH);
     delayMicroseconds(10);
     digitalWrite(TRIG2,LOW);
     
     long distance2 = pulseIn(ECHO2,HIGH)/58.2;
     delay(10);
     
     
     if(distance1<20 || distance2<20){
       tone(8, 1000, 60);
     }
     else if(distance1<40 || distance2<40){
       tone(8, 1000, 60);
       delay(200);
     }
     else if(distance1<60 || distance2<60){
       tone(8, 1000, 60);
       delay(400);
     }
     
     delay(100);
     
  }
  
  else if(val==600){
    digitalWrite(TRIG1,LOW);
    delayMicroseconds(10);
    digitalWrite(TRIG2,LOW);
    delayMicroseconds(10);
  }
  else{
    Serial.println(601, DEC);
  }
    
}
