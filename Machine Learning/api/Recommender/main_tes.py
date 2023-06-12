from flask import Flask, jsonify, request
from inference import get_sorted_place_ids
import mysql.connector
from dotenv import dotenv_values
import pandas as pd


 
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


app = Flask(__name__)


@app.route('/', methods=['POST', 'GET'])
def index():


    mycursor = mydb.cursor()

    mycursor.execute("SELECT * FROM mytable")

    myresult = mycursor.fetchall()

    # make a variable to store the data in json format

    data = {}

    for x in myresult:
    
        if x[0] in data.keys():
    
            data[x[0]]["User_Id"].append(x[0])
    
            data[x[0]]["Place_Id"].append(x[1])
    
            data[x[0]]["Place_Ratings"].append(x[2])
    
        else:
    
            data[x[0]] = {
    
                "User_Id": [x[0]],
    
                "Place_Id": [x[1]],
    
                "Place_Ratings": [x[2]],
    
            }


    User_Id_login = None

    if User_Id_login is None:

        User_Id_login = 1

    

    result = data.get(User_Id_login, {})

    User_Id = result.get("User_Id", [])

    Place_Id = result.get("Place_Id", [])

    Place_Ratings = result.get("Place_Ratings", [])

    new_data = {
    
        "User_Id" : User_Id,
        "Place_Id": Place_Id,
        "Place_Ratings" : Place_Ratings

    }

       
    # get the sorted place ids

    place_ids = get_sorted_place_ids(new_data)

    data = {
        "Place_Id": place_ids
    }

    return jsonify(data)





if __name__ == "__main__":
    app.run(debug=True)




