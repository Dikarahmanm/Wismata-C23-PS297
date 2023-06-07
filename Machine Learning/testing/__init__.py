from flask import Flask, request, jsonify, render_template, json
from inference import recommend
import requests
import json
import os


app = Flask(__name__)


@app.route ("/", methods=[ "GET" ])
def result():

    prediction = recommend(1,4)

    prediction = int(prediction)

    return jsonify(prediction)


if __name__ == "__main__":

    app.run(debug=True)




