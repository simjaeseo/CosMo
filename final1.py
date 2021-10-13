import bluetooth
from socket import *
import threading
import time

import RPi.GPIO as GPIO
import serial
from time import sleep

port1 = "/dev/ttyUSB0"
serialToArduino = serial.Serial(port1,9600)


class Blue_tele(threading.Thread): 
 def __init__(self, client_socket, serialToArduino):
     threading.Thread.__init__(self)
     super(Blue_tele, self).__init__()
     self.socket = client_socket
     self.data = None
     self.serialToArduino = serialToArduino
 def run(self):
     
     while True:
       self.data = self.socket.recv(1024)
       print(str(self.data))
       self.socket.send(self.data)
       self.serialToArduino.writelines(str(self.data)+'E')
       time.sleep(0.5)
 def send(self, ardu_data):
    self.socket.send(ardu_data)

class Ardu_tele(threading.Thread):

 def __init__(self, serialToArduino):
     threading.Thread.__init__(self)
     super(Ardu_tele, self).__init__()
     self.Ardu_data=None
     self.serialToArduino = serialToArduino

 def run(self):
    while True:
      self.Ardu_data = serialToArduino.readline()
      time.sleep(0.5)
def lock():
   pass


server_socket=bluetooth.BluetoothSocket( bluetooth.RFCOMM )
port = 1
server_socket.bind(("",port))
server_socket.listen(1)

client_socket,address = server_socket.accept()

print("Accepted connection from ",address)
print("Ready to communication")

receive = Blue_tele(client_socket, serialToArduino)
receive.start()

ardu_receive = Ardu_tele(serialToArduino)
ardu_receive.start()

while True:
  if ardu_receive.Ardu_data == None:
   pass
  else:
   print(ardu_receive.Ardu_data)
   receive.send(ardu_receive.Ardu_data)
   time.sleep(0.5)
   ardu_receive.Ardu_data = None

  if receive.data == '1000':
   lock()
   recieve.data = None
  time.sleep(0.1)
