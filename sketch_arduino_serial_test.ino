#define TRIG1 2
#define ECHO1 3
#define TRIG2 12
#define ECHO2 13

#define LOCK1 10
#define MAG1 5

int mag_check = 0;
int pw = 0;
char val =0;
int int_val = 0;
int state=600;
const char endMArk = 'E';

void setup()
{
 Serial.begin(9600); 
 pinMode(TRIG1,OUTPUT);
 pinMode(ECHO1,INPUT); 
 pinMode(TRIG2,OUTPUT);
 pinMode(ECHO2,INPUT); 
 pinMode(8,OUTPUT);
 pinMode(MAG1,INPUT);
}

void loop()
{
  if(Serial.available()>0){
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
    state=pw;
    pw=0;
  }
    
 back_detect_on(state);
 lock(state);
 MAG(state);
 
  delay(100);
  
}

void MAG(int val){
 if(val == 600){
    mag_check = digitalRead(MAG1);
    delay(1000);
    if(mag_check == LOW) {
      mag_check =1;
      Serial.println(mag_check, DEC);
      mag_check = HIGH;
      delay(100);
     }
     delay(200);
  } 
}

void lock(int val){
  if(val == 500 || val==1100){
    digitalWrite(LOCK1, HIGH);
    delayMicroseconds(2);
    digitalWrite(LOCK1, LOW);
    delayMicroseconds(2);
   }
  else{
    //exception
  }
}


void back_detect_on(int val){
  //detect on
  if(val == 500){
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
  else{
    digitalWrite(TRIG1,LOW);
    delayMicroseconds(10);
    digitalWrite(TRIG2,LOW);
    delayMicroseconds(10);
   
 }
  

    
}
