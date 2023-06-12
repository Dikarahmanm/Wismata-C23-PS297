from flask import Flask, request, jsonify
from inference import recommend_rating
from utils import get_sorted_IdWisatas
import mysql.connector
from dotenv import dotenv_values




app = Flask(__name__)


#Load environment variables from .env file

env_vars = dotenv_values(".env")


# Access the environment variables
# Access the environment variables
host = env_vars['DB_HOST']
user = env_vars['DB_USER']
password = env_vars['DB_PASSWORD']
database = env_vars['DB_DATABASE']

# Use the variables in your code
print(host, user, password, database)


# Connect to the database

mydb = mysql.connector.connect(

    host=host,

    user=user,

    password=password,


    database=database


)


def ml_function(IdUser_Login):


    mycursor = mydb.cursor()

    mycursor.execute("SELECT * FROM rating")

    myresult = mycursor.fetchall()

    # make a variable to store the data in json format

    data = {}

    for x in myresult:
    
        if x[0] in data.keys():
    
            data[x[0]]["IdUser"].append(x[0])
    
            data[x[0]]["IdWisata"].append(x[1])
    
            data[x[0]]["rating"].append(x[2])
    
        else:
    
            data[x[0]] = {
    
                "IdUser": [x[0]],
    
                "IdWisata": [x[1]],
    
                "rating": [x[2]],
    
            }


    IdUser_login = None

    IdUser_login = IdUser_Login

    # if user id is not provided, use the first user id in the database but if its arleady get request dont' do anything

    if IdUser_login is None:

        IdUser_login = 2

    # if user id is provided, use that user id

    else:
        IdUser_login = int(IdUser_login)

    result = data.get(IdUser_login, {})

    IdUser = result.get("IdUser", [])

    IdWisata = result.get("IdWisata", [])

    rating = result.get("rating", [])

    new_data = {
    
        "IdUser" : IdUser,
        "IdWisata": IdWisata,
        "rating" : rating

    }

       
    # get the sorted place ids

    IdWisatas = get_sorted_IdWisatas(new_data)

    data = {
        "IdWisata": IdWisatas
    }

    return jsonify(data)


@app.route("/", methods=["GET", "POST"])
def index():
    return """ <h1> Welcome to the MACHINE LEARNING MODEL API WISMATA </h1> """

@app.route("/api/predict_rating", methods=["GET", "POST"])
def predict_rating():

    # load data category and price from request
    category_test = request.args.get("category")
    price_test = request.args.get("price")
    prediction = recommend_rating(category_test, price_test)
    prediction = int(prediction)
    data = {
        "Rating" : prediction
    }
    return jsonify(data)


@app.route('/api/recommendation', methods=['GET', 'POST'])
def recommender():
    IdUser_login = request.args.get("IdUser_Login")
    return ml_function(IdUser_login)


if __name__ == "__main__":
    app.run(debug=True)