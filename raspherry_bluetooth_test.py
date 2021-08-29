import bluetooth
from socket import *
import threading
import time

import RPi.GPIO as GPIO
import serial
from time import sleep

port1 = "/dev/ttyUSB1"
serialToArduino = serial.Serial(port1,9600)

def send(client_socket):
 while True:
   sendData =input('')
   server_socket.send(sendData.encode('utf-8'))

def receive(client_socket):
 while True:
   recvData = client_socket.recv(1024)
   print(recvData.decode('utf-8'))
   
   
   message = "500"
   endMark = 'E'
   
   if recvData==None:
      message = "500"
      print("if1")
   else:
      message = "600"
      print(message)
   
   serialToArduino.writelines(message)
   print(message)
   serialToArduino.write(endMark.encode())
   print("sended message:",   message, endMark)
   time.sleep(.5)
   

server_socket=bluetooth.BluetoothSocket( bluetooth.RFCOMM )

port = 1
server_socket.bind(("",port))
server_socket.listen(1)

client_socket,address = server_socket.accept()
print("Accepted connection from ",address)

print("Ready to communication")

sender=threading.Thread(target=send,args=(client_socket,))
receiver=threading.Thread(target=receive,args=(client_socket,))

sender.start()
receiver.start()

