from flask import Flask, request, jsonify
from inference import recommend_rating


app = Flask(__name__)


@app.route("/")
def home():
    return ''' <p> Wismata Machine Learning API </p> '''


@app.route("/api/get_rating", methods=["GET"])
def get_recommendation_rating():

    # load data category and price from request
    category_test = request.args.get("category")
    price_test = request.args.get("price")
    prediction = recommend_rating(category_test, price_test)
    prediction = int(prediction)
    return jsonify(prediction)


if __name__ == "__main__":
    app.run(debug=True)