'''
Created on 2022. 7. 11.

@author: ykm09
'''

from flask import Flask, render_template
from jinja2.nodes import Const

app = Flask(__name__)

@app.route('/')
def hello():
    return render_template('testMap.html', total_chart_data=70, co2List=getKM(), nums=getLo())

def getKM():
    SEOUL=[14, 22, 0, 0]
    HAEUNDAE=[131,83,0,0]
    SEOLBONG=[2, 1, 0, 0]
    LOTTETOWER=[17,12,0,0]
    return [SEOUL, HAEUNDAE, SEOLBONG, LOTTETOWER]

def getLo():
    return [22,83,1,12]


if __name__ == '__main__':
    app.run()