import turtle as t
import random

te = t.Turtle()
te.shape("turtle")
te.color("red")
te.speed(0)
te.up()
te.goto(0, 200)

ts = t.Turtle() # 먹이(초록색 동그라미)
ts.shape("circle")
ts.color("green")
ts.speed(0)
ts.up()
ts.goto(random.randint(-180, 180), -200)

scoreT = t.Turtle() # 점수
score=0

def turn_right():
    t.setheading(0)

def turn_up():
    t.setheading(90)

def turn_left():
    t.setheading(180)

def turn_down():
    t.setheading(270)
    
def click_spacebar():
    global score
    if(score>0):
        t.forward(40)
        score-=1
        scoreT.clear()

def play(): # 게임을 실제로 플레이하는 함수
    
    global score
    
    scoreT.write(str(score))
    t.forward(10) # 주인공 거북이가 10만큼 앞으로 이동합니다.
    ang = te.towards(t.pos())
    te.setheading(ang) # 악당 거북이가 주인공 거북이를 바라보게 합니다.
    te.forward(9) # 악당 거북이가 9만큼 앞으로 이동합니다.
    
    if t.distance(ts) < 12: # 주인공과 먹이의 거리가 12보다 작으면(가까우면)
        scoreT.clear()
        score+=1
        star_x = random.randint(-230, 230)
        star_y = random.randint(-230, 230)
        ts.goto(star_x, star_y) # 먹이를 다른 곳으로 옮깁니다.
        
    if t.distance(te) >= 12: # 주인공과 악당의 거리가 12 이상이면(멀면)
        t.ontimer(play, 100) # 0.1초 후 play 함수를 실행합니다(게임 계속).
    else:
        print("끄읕")
        t.bye()

t.setup(500, 500)
t.bgcolor("orange")
t.shape("turtle")
t.speed(0)
t.up()
t.color("white")

t.onkeypress(turn_right, "Right")
t.onkeypress(turn_up, "Up")
t.onkeypress(turn_left, "Left")
t.onkeypress(turn_down, "Down")
t.onkeypress(click_spacebar, "space")
t.listen()


scoreT.up()
scoreT.goto(-230, 230)
scoreT.hideturtle()
play()

t.mainloop()
