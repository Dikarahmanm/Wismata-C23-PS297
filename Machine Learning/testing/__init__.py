from flask import Flask, request, jsonify, render_template
from inference import run
import requests
import json
import os


app = Flask(__name__)


@app.route ("/", methods=["POST"])
def index():

    data = request.get_json(force=True)
    category = data["category"]
    price = data["price"]

    prediction =  run(category, price)

    print(prediction)
 



