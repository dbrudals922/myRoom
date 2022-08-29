'''
Created on 2022. 5. 29.

@author: ykm09
'''

import os
import sys

from PyQt5 import uic, QtCore, QtWidgets
from PyQt5.Qt import QDate
from PyQt5.QtWidgets import *

from h_wellDb import h_wellDb
from h_wellSec import secondwindow


def resource_path(relative_path):
    base_path = getattr(sys, "_MEIPASS", os.path.dirname(os.path.abspath(__file__)))
    return os.path.join(base_path, relative_path)
 
form = resource_path('./pyqtUi/h_wellMain.ui')
form_class = uic.loadUiType(form)[0]
 
form_second = resource_path('./pyqtUi/h_wellSec.ui')
form_secondwindow = uic.loadUiType(form_second)[0]


class WindowClass(QDialog):
    db = h_wellDb()
    result=''
    
    def __init__(self):
        super().__init__()
#         self.setupUi(self)
        self.ui = uic.loadUi("./pyqtUi/h_wellMain.ui",self)
        # setting maximum date
        self.calendarWidget.setMinimumDate(QDate(2014, 1, 1))
        self.calendarWidget.setSelectedDate(QDate(2014,1,1))
        self.calendarWidget.setMaximumDate(QDate(2018, 12, 31))
 
        localList=self.db.loadLocal()
        localList.pop()
        for local in localList:
            self.local.addItem(local[1])
        
        self.show()
# 
    def btn_to_next(self):
        lo=str(self.local.currentText())
        date=self.calendarWidget.selectedDate().toString("yyyy-MM-dd")
        result=self.db.loadData(lo, date)
        self.hide()  # 메인윈도우 숨김
        self.second = secondwindow(result)
        self.second.exec()              # 두번째 창을 닫을 때 까지 기다림
        self.show()                     # 두번째 창을 닫으면 다시 첫 번째 창이 보여짐짐
    
    def getResult(self):
        return self.result
        


if __name__ == '__main__':
    app = QApplication(sys.argv)
    myWindow = WindowClass()
    myWindow.setWindowTitle("Main")
    myWindow.show()
    app.exec_()