'''
Created on 2022. 5. 22.

@author: ykm09
'''
'''
from PyQt5.QtWidgets import QApplication, QLabel  

app = QApplication([])
label = QLabel('Hello World!')
label.show()
app.exec()()
'''

import csv
import threading

import pymysql.cursors


class h_wellDb():
    
    conn = None
    def __init__(self):
        self.conn = pymysql.connect(host='183.99.87.90',
                user='root',
                password='swhacademy!',
                db='k_min',
                charset='utf8')
    
    # 11 서울
    def insertHLo(self):
        with open("./h-well/시도 지역코드.csv", newline='') as csvfile:
            reader=csv.DictReader(csvfile)
            for line in reader:
                with self.conn.cursor() as cursor:
                    sql = "INSERT INTO h_well_lo (code, name) VALUES ('%s', '%s')" %(int(line['상위 시도지역코드']), line['지역명'])
                    cursor.execute(sql)
            self.conn.commit()
    
    # 11 11110 종로구
    def insertHLoSub(self):
        with open("./h-well/시군구 지역코드.csv", newline='') as csvfile:
            reader=csv.DictReader(csvfile)
            for line in reader:
                with self.conn.cursor() as cursor:
                    sql= "INSERT INTO h_well_lo_sub (code, sub_code, name) values ('%s', '%s', '%s')" %(int(line['상위 시도지역코드']), int(line['시군구지역코드']), line['시군구명'])
                    cursor.execute(sql)
            self.conn.commit()
        self.conn.close()
    
    def insertHLoSub2(self):
        result = self.manuLoSub()
        for i in result:
            with self.conn.cursor() as cursor:
                sql= "INSERT INTO h_well_lo_sub (code, sub_code, name) values ('%s', '%s', '%s')" % i
                cursor.execute(sql)
        self.conn.commit()
        self.conn.close()
        
        
    def manuLoSub(self):
        f = open("./h-well/법정동코드 전체자료.txt", 'r')
        result=[]
        while True:
            line = f.readline()
            if not line: break
            list = line.split()
            if list[0][5:]=='00000' and len(list)>2:
                list[0]=list[0][:5]
                if len(list)>4:
                    list[2]=list[2]+" "+list[3]
                    del list[3]
                if len(list)>3:
                    result.append((list[0][:2], list[0], list[2]))
                else:
                    result.append((list[0][:2], list[0], list[1]))
        return result
    
    # 20140101 11110 53 ?
    def insertHDisease(self, disease): # disease = 감기 1, 눈병 2, 천식 3, 피부염 4
        a='1'
        if disease=="눈병":a='2'
        elif disease=="천식":a='3'
        elif disease=="피부염":a='4'
        
        with open("./h-well/실제진료정보_%s_시군구.csv" %(disease), newline='') as csvfile:
            reader=csv.DictReader(csvfile)
            i=0
            for line in reader:
                if i>10000:
                    break
                try:
                    with self.conn.cursor() as cursor:
                        sql= "INSERT INTO h_well_disease (date, sub_code, count, n) values ('%s', '%s', %d, '%s')" %(line['날짜'], line['시군구지역코드'], int(line['발생건수(건)']), a)
#                         print((line['날짜'], int(line['시군구지역코드']), int(line['발생건수(건)'])))
                        cursor.execute(sql)
                        i+=1
                except:
                    result=self.manuLoSub()
                    b = [y[1] for y in result].index(line['시군구지역코드'])
                    with self.conn.cursor() as cursor:
                        sql="INSERT INTO h_well_lo_sub (code, sub_code, name) values ('%s', '%s', '%s')" %result[b]
                        cursor.execute(sql)
                        self.conn.commit()
                        sql= "INSERT INTO h_well_disease (date, sub_code, count, n) values ('%s', '%s', %d, '%s')" %(line['날짜'], line['시군구지역코드'], int(line['발생건수(건)']),a)
#                         print((line['날짜'], int(line['시군구지역코드']), int(line['발생건수(건)'])))
                        cursor.execute(sql)
                        i+=1
            self.conn.commit()
                

#=========================================================================
                
    def loadLocal(self):
        loList=[]
        with self.conn.cursor() as cursor:
            sql= "SELECT * FROM h_well_lo;"
            cursor.execute(sql)
            result = cursor.fetchall()
            for data in result:
                loList.append((data[0], data[1]))
        return loList
    
    def loadData(self, local, date):
        loList = self.loadLocal()
        r=list()
        result=dict()
        a=[y[1] for y in loList].index(local)
        with self.conn.cursor() as cursor:
            sql= """SELECT a.date, b.name, a.count, a.n 
                    FROM h_well_disease a, h_well_lo_sub b 
                    WHERE a.sub_code = b.sub_code AND b.code='%s' AND a.date='%s';""" %(loList[a][0], date)
            cursor.execute(sql)
            re = cursor.fetchall()
            for data in re:
                r.append((data[1], data[2], data[3]))
        result["감기"]=[(i[0],i[1]) for i in r if i[2]=='1']
        result["눈병"]=[(i[0],i[1]) for i in r if i[2]=='2']
        result["천식"]=[(i[0],i[1]) for i in r if i[2]=='3']
        result["피부염"]=[(i[0],i[1]) for i in r if i[2]=='4']
        return result
    
                