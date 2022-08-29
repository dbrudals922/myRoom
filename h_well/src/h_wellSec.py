'''
Created on 2022. 5. 29.

@author: ykm09
'''

import os
import sys

from PyQt5 import uic, QtCore
from PyQt5.Qt import QDate
from PyQt5.QtWidgets import *

from h_wellDb import h_wellDb


def resource_path(relative_path):
    base_path = getattr(sys, "_MEIPASS", os.path.dirname(os.path.abspath(__file__)))
    return os.path.join(base_path, relative_path)
 
form_second = resource_path('./pyqtUi/h_wellSec.ui')
form_secondwindow = uic.loadUiType(form_second)[0]


class secondwindow(QDialog,QWidget,form_secondwindow):
    
    
    def __init__(self, result):
        super(secondwindow, self).__init__()
        self.ui = uic.loadUi("./pyqtUi/h_wellSec.ui", self)
        
        diseaseList=["감기","눈병","천식", "피부염"]
        
        for disease in diseaseList:
            self.table = QTableWidget(self)
            self.table.setColumnCount(2)
            self.table.setHorizontalHeaderLabels(["Local" , "Count"])
            
            re=result[disease]
            self.table.setRowCount(len(re))
            
            for c in range(len(re)):
                self.table.setItem(c,0, QTableWidgetItem(re[c][0]))
                self.table.setItem(c,1, QTableWidgetItem(str(re[c][1])))
             
            self.tabWidget.addTab(self.table, disease)
#         self.__make_table()
#         self.initUI()
        self.show()
    def initUI(self):
        self.setupUi(self)
        
    def __make_table(self):
        self.coldTable.setRowCount(30)
        self.coldTable.setColumnCount(2)
          
#         self.coldTable.setHorizontalHeaderLabels(["지역", "count"])
#         self.coldTable.horizontalHeader().setSectionResizeMode(QHeaderView.Stretch)



    def btn_to_previous(self):
        self.close()                    #클릭시 종료됨.
        
