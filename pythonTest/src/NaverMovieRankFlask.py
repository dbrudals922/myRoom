import re
import threading

from bs4 import BeautifulSoup
from flask import Flask, render_template
import requests


app = Flask(__name__)

@app.route('/')
def hello():
    return '<h1>영화순위가 보고싶은 날짜를 입력하세요 /YYYYMMDD</h1>'

@app.route('/<int:date>', methods = ['GET'])
def ranking(date):
    soup=getSoup(date)
    movieRanking = makeMovieList(soup)
    gResult = movieChart(movieRanking)
    max1 = max([x['count'] for x in gResult])+5
    return render_template('movie_rank.html', values=movieRanking, date=date, title=str(date), max=max1, genres=gResult) # genres=gResult

@app.route('/<int:date>/chart', methods = ['GET'])
def chart(date):
    soup=getSoup(date)
    movieRanking = makeMovieList(soup)
    gResult = movieChart(movieRanking)
     
    return render_template('movie_chart.html', genres=gResult, title=str(date), max = max([x['count'] for x in gResult])+5)

def getSoup(date):
    response = requests.get('https://movie.naver.com/movie/sdb/rank/rmovie.nhn?sel=cnt&tg=0&date=%s' % date)
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')
    return soup

def makeMovieList(soup):
    movieRanking=list()
    ranking = arrangeRank(soup)
    c=0
    p=re.compile('[0-9]')
    for tag in soup.select('div[class=tit3] a'):
        rank = dict()
        m = p.findall(tag.get('href'))
        rank['rank'] = ranking[c]
        rank['name'] = tag.text
        rank['link'] = "https://movie.naver.com/movie/bi/mi/basic.naver?code="+''.join(m)
        movieRanking.append(rank)
        c+=1
    return movieRanking
        
def arrangeRank(soup):
    ranking = list()
    for tag in soup.select('tr td[class=ac] img'):
        rankI = tag.get('alt')
        a = rankI.isnumeric()
        if(a):
            ranking.append(int(rankI))
    for i in range(1, 51):
        if i not in ranking:
            ranking.append(i-1)
    ranking.sort()
    return ranking


def movieChart(movies):
    genreList=list()
    gResult=list()
    for movie in movies:
        t = threading.Thread(target=movieInfo, args=(movie, genreList))
        t.start()
#         movieInfo(movie, genreList)
    for genre in list(set(genreList)):
        gResult.append({'genre': genre, 'count' : genreList.count(genre)})
    return gResult

def movieInfo(movie, genreList):
    response = requests.get(movie['link'])
#     print(movie['link'])
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')
    for tag in soup.select("dl[class=info_spec] span")[0].select("a"):
        genreList.append(tag.text)
    
if __name__ == '__main__':
    app.run(host="localhost", port="40031")