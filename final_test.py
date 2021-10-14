import bluetooth
from socket import *
import threading
import time


class Blue_tele(threading.Thread):
  def __init__(self, client_socket):
      threading.Thread.__init__(self)
      super(Blue_tele, self).__init__()
      self.socket = client_socket
      self.data =None

  def run(self):
      while True:
          self.data = self.socket.recv(1024)
          print(str(self.data))
          self.socket.send(self.data)
          time.sleep(0.5)

  def send(self, send_data):
       self.socket.send(send_data)

class Blue_send(threading.Thread):
  def __init__(self, client_socket):
      threading.Thread.__init__(self)
      super(Blue_send, self).__init__()
      self.socket = client_socket
      self.data =None

  def run(self):
      while True:
          sendData =input( ' ' )
          self.socket.send(sendData.encode('utf-8'))

server_socket=bluetooth.BluetoothSocket(bluetooth.RFCOMM)
port = 1
server_socket.bind(("", port))
server_socket.listen(1)

client_socket ,address = server_socket.accept()

print("Accepted connection from" ,address)
print("Ready to communication")

recieve = Blue_tele(client_socket)
send = Blue_send(client_socket)
recieve.start()

while True:
  time.sleep(0.5)