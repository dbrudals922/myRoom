import sys
from PyQt5.QtWidgets import *
from PyQt5.QtCore import *
from PyQt5.QtGui import *
from PyQt5 import uic
import threading, json, pika
from dotenv.cli import list

form = uic.loadUiType("C:/Users/ykm09/project/myRoom/pythonTest/src/rabbitmq/chat.ui")[0]

class Chat(QWidget, form):
    def __init__(self, name):
        super().__init__()
        self.setupUi(self)
        self.setWindowTitle(name)
        self.listView.setEditTriggers(QAbstractItemView.NoEditTriggers)
        self.plainTextEdit.setReadOnly(True)
        self.send.clicked.connect(self.sendMsg)
        self.id=name
        self.userList = []
        self.dict = {'name': self.id, 'message': []}
        self.conn = pika.BlockingConnection(
            pika.ConnectionParameters('183.99.87.90', 5672, '/', pika.PlainCredentials('guest', 'guest')))
        self.channel = self.conn.channel()
        self.channel.exchange_declare(exchange='jamjam', exchange_type='topic', auto_delete = False)

        result = self.channel.queue_declare(queue='', exclusive=True)
        self.queue_name = result.method.queue

        self.channel.queue_bind(exchange='jamjam', queue=self.queue_name, routing_key="Join") #auto_delete = false
        self.channel.queue_bind(exchange='jamjam', queue=self.queue_name, routing_key="Leave")
        self.channel.queue_bind(exchange='jamjam', queue=self.queue_name, routing_key="SendMsg")
        self.channel.queue_bind(exchange='jamjam', queue=self.queue_name, routing_key="List")
        data = json.dumps(self.dict)
        self.channel.basic_publish(exchange='jamjam', routing_key="Join", body=data.encode('utf-8'))
        # self.channel.basic_publish(exchange='topic_logs', routing_key="Join", body=data.encode('utf-8'))

        print("클라이언트 소켓정보 : ", self.conn)
        th = threading.Thread(target=self.worker, name="[스레드 이름 {}]".format(self.conn), args=())
        th.start()

    """
        [ listView 항목 추가 하는 방법 ] 

        model = QStandardItemModel()
        for x in json.loads(datas['message']):
            model.appendRow(QStandardItem(x))
        self.listView.setModel(model)
    """

    def callback(self, ch, method, properties, body):
        # nameList.append(self.id)
        datas = json.loads(body.decode())
        
        if method.routing_key == "SendMsg":
            self.plainTextEdit.insertPlainText("[ %s ] : %s\n" % (datas['name'], datas['message']))
        elif method.routing_key == "Leave":
            self.plainTextEdit.insertPlainText("[ %s 님이 퇴장 하였습니다. ]\n" % datas['name'])
            
            if datas['name'] in self.userList:
                self.userList.remove(self.userList[self.userList.index(datas['name'])])
                dict = {'name': self.userList}
                self.channel.basic_publish(exchange='jamjam', routing_key="List", body=json.dumps(dict).encode("utf-8"))
                print(self.userList)
            
        elif method.routing_key == "Join":
            self.plainTextEdit.insertPlainText("[ %s 님이 참여 하였습니다. ]\n" % datas['name'])
            
            if datas['name'] not in self.userList:
                self.userList.append(datas['name'])
                dict = {'name': self.userList}
                self.channel.basic_publish(exchange='jamjam', routing_key="List", body=json.dumps(dict).encode("utf-8"))
                print(self.userList)
                
        elif method.routing_key == "List":
            if set(datas['name'])- set(self.userList): # 가지고 있는 유저 리스트가 부족하다면
                self.userList=datas['name']
            
            # 리스트에 유저 추가
            model = QStandardItemModel()
            for x in self.userList:
                model.appendRow(QStandardItem(x))
            self.listView.setModel(model)


    def keyPressEvent(self, e):
        if e.key() == Qt.Key_Return:
            self.sendMsg()

    def sendMsg(self):
        if len(self.lineEdit.text()) == 0:
            return
        self.dict['message'] = self.lineEdit.text()
        data = json.dumps(self.dict)
        self.channel.basic_publish(exchange='jamjam', routing_key="SendMsg", body=data.encode('utf-8'))
        self.lineEdit.clear()

    def worker(self):
        self.channel.basic_consume(queue=self.queue_name, on_message_callback=self.callback, auto_ack=True)
        self.channel.start_consuming()

    def closeEvent(self, event):
        data = json.dumps(self.dict)
        self.channel.basic_publish(exchange='jamjam', routing_key="Leave", body=data.encode('utf-8'))


class MyWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        self.setWindowTitle('SWH Academy Window.')

        centerGeometry = QDesktopWidget().availableGeometry().center()
        self.resize(300, 100)
        frameGeometry = self.frameGeometry()
        frameGeometry.moveCenter(centerGeometry)

        self.idLabel = QLabel("아이디 : ")
        self.idLineEdit = QLineEdit()
        self.loginButton = QPushButton("로그인")
        self.loginButton.clicked.connect(self.buttonClicked)
        layout = QGridLayout()

        layout.addWidget(self.idLabel, 0, 0)
        layout.addWidget(self.idLineEdit, 0, 1)

        layout.addWidget(self.loginButton, 1, 1)

        self.setLayout(layout)

    def keyPressEvent(self, e):
        if e.key() == Qt.Key_Return:
            self.buttonClicked()

    def buttonClicked(self):
        if len(self.idLineEdit.text()) == 0:
            return
        self.chatting = Chat(self.idLineEdit.text())
        self.chatting.show()
        window.hide()


if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())