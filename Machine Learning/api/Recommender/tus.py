from flask import Flask, jsonify, request
import mysql.connector
from dotenv import dotenv_values



app = Flask(__name__)


# Load environment variables from .env file

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


# load the data from the database

@app.route('/', methods=['GET'])

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

        User_ID_login = 2

        result = data.get(User_ID_login, {})

        # retrive only place_id

        result = result.get("Place_Id", [])


    return jsonify(result)



if __name__ == "__main__":
    app.run(debug=True)