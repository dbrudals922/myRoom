'''
Created on 2022. 9. 4.

@author: ykm09
'''

import turtle as t
import random

class Game():
        count=0
        scoreT=t.Turtle()
        score=0
        def __init__(self):
                # 거북이가 동작하는 데 필요한 설정
                t.onkeypress(self.turn_up, "Up") # 위 화살표를 누르면 turn_up 함수 실행
                t.onkeypress(self.turn_down, "Down") # 아래 화살표를 누르면 turn_down 함수 실행
                t.onkeypress(self.fire, "space") # space 바를 눌렀을때 fire 함수 실행
                t.listen() #turtle 창에서 키보드 입력을 받도록 함.

                self.scoreT.ht()
                self.scoreT.up()
                self.scoreT.goto(-300, 300)
                self.scoreT.write(self.score, font=('arial',20), align='center')
                
                
        def turn_up(self): #아래방향키를 눌렀을때
                t.left(2)
                
        def turn_down(self): #위방향키를 눌렀을떄
                t.right(2)

        def fire(self): #space바 눌렀을때 (대포 발사)
                self.count+=1
                ang = t.heading() # 화살표가 바라보는 각도
                while t.ycor()>0: # 화살표의 y좌표가 0이상일때 (바닥을 벗어난 상태)
                        t.forward(15) 
                        t.right(5) # 오른쪽으로 5도씩 회전하며 포물선을 그린다.

                d=t.distance(self.target, 0) # 화살표와 목표지점 사이의 거리

                t.sety(random.randint(10, 100)) #화살표가 랜덤한 y의 값으로 이동.(글씨를 적기 위함)
                
                if d <25 : # 화살표가 목표지점 안에 들어왔다면
                        # t.color("blue")
                        # t.write("Good!", False, "center", ("", 15))
                        self.score+=2
                        self.scoreT.clear()
                        self.scoreT.write(self.score, font=('arial',20), align='center')

                        
                        t.clear()
                        self.start()
                        ang=20
                else: # 아니라면
                        t.color("red")
                        t.write("Bad!", False, "center", ("", 15))
                        if(self.score-1>=0):
                                self.score-=1
                                self.scoreT.clear()
                                self.scoreT.write(self.score, font=('arial',20), align='center')

                if(self.count>=10):
                        self.end()
                        return
                t.color("black") #다시 화살표를 검은색으로 바꿈
                t.goto(-200, 10) # 원래 위치로 이동
                t.setheading(ang) #이전 각도를 그대로 유지시켜줌

        def fountain(self): #땅 그리기 (-300~300)
                t.pensize(1)
                t.goto(-300, 0) 
                t.down()
                t.goto(300,0)

        def goal(self): # 목표 지점을 설정하고 그리기
                self.target = random.randint(50, 150) # 목표 지점은 50~150사이에서 랜덤
                t.pensize(3)
                t.color("green") #초록색으로 목표지점 표시
                t.up()
                t.goto(self.target - 25, 2)
                t.down()
                t.goto(self.target + 25, 2)
                
        def basic(self): # 화살표 색 검은색으로 지정, 처음으로 되돌림
                # 바닥을 다 그린 후 화살표를 시작지점으로 옮겨줌.
                t.color("black")
                t.up()
                t.goto(-200, 10)
                t.setheading(20)

        def start(self):
                self.fountain()
                self.goal()
                self.basic()

        def end(self):
                t.reset()
                t.ht()
                self.scoreT.clear()
                t.write(self.score, font=("arial", 50, "bold"), align="center")
                
a=Game()
a.start()
