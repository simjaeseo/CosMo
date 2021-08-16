#define TRIG1 2
#define ECHO1 3
#define TRIG2 12
#define ECHO2 13

#define LOCK1 10
#define TRIG_AMP  //안쓸수있음 (tone 7)//
#define LOCK_AMP //안쓸수있음 (tone 8)//

#define MAG1 5

int state = 400;
int logout= 500;
int login = 600;
int char_value = 0;
int int_value = 0;
const char endMark = 'E';
int mag_check = 0;
 
void setup() {
 Serial.begin(9600);
 
 pinMode(TRIG1,OUTPUT);
 pinMode(ECHO1,INPUT);
 pinMode(TRIG2,OUTPUT);
 pinMode(ECHO2,INPUT);
 //pinMode(TRIG_AMP,OUTPUT);
 
 pinMode(LOCK1,OUTPUT);
 //pinMode(LOCK_AMP,OUTPUT);

 pinMode(MAG1,INPUT); 
}

void loop() {
  
 if(Serial.available())
  {
    
     char_value = Serial.read();
     Serial.println("Serial.available():");
     Serial.println(Serial.available());
     Serial.println("char_value:");
     Serial.println(char_value);
     if(isDigit(char_value))
     {
      int_value =(int_value*10) +(char_value -'0');
      //1000 이라는 숫자를 1,0,0,0 이아닌 1000으로 읽기 위함
      Serial.println("int_value:");
      Serial.println(int_value);
     }
     else if(char_value == endMark)
     {
       state = int_value;
       int_value = 0;
       Serial.println("state:");
       Serial.println(state);
     }
     
  }
/*  
  drive_detect(state);

  mag_check = digitalRead(MAG1);   //자석감지 센서 (passive)
  if(mag_check == 1){
    mag_check =1;
  }
  else mag_check=2;
  
  Serial.print(mag_check);
  */
}




//후방감지기능 함수
void drive_detect(int val) {

   if(val ==2000 || val ==600){  //후방감지 기능 on
    digitalWrite(TRIG1,LOW); //초음파센서1 초기화
    delayMicroseconds(2);
    digitalWrite(TRIG1,HIGH);
    delayMicroseconds(10);
    digitalWrite(TRIG1,LOW);

    long distance1 = pulseIn(ECHO1,HIGH)/58.2;
    delay(10);

    digitalWrite(TRIG2,LOW); //초음파센서2 초기화
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
   else if(val ==2400 || val ==500){ //후방감지 기능 off
    
   }
   else {
   //예외 처리 만들면좋을듯 오류 알림
   }
   Serial.println(val);
}
