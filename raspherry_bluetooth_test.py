import bluetooth
from socket import *
import threading
import time

def send(server_socket):
 while True:
   sendData =input('')
   server_socket.send(sendData.encode('utf-8'))

def receive(server_socket):
 while True:
   recvData = server_socket.recv(1024)
   print(recvData.decode('utf-8'))


server_socket=bluetooth.BluetoothSocket( bluetooth.RFCOMM )

port = 1
server_socket.bind(("",port))
server_socket.listen(1)

client_socket,address = server_socket.accept()
print("Accepted connection from ",address)

sender=threading.Thread(target=send,args=(client_socket,))
receiver=threading.Thread(target=receive,args=(client_socket,))

sender.start()
receiver.start()
while True:
 time.sleep(1)
 pass
