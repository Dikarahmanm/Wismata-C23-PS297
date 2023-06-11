from dotenv import dotenv_values
import mysql.connector
import json

# Load environment variables from .env file
env_vars = dotenv_values(".env")

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

mycursor = mydb.cursor()

mycursor.execute("SELECT * FROM mytable")

myresult = mycursor.fetchall()

# make a variable to store the data in json format

data = []

# loop through the data and append the data in the variable

for x in myresult:

    data.append({

        "User_Id": x[0],

        "Place_Id": x[1],

        "Place_Ratings": x[2],


    })

# convert the data into json format

json_data = json.dumps(data)


"""

{"User_Id": 1, "Place_Id": 179, "Place_Ratings": 3}, {"User_Id": 1, "Place_Id": 344, "Place_Ratings": 2},

{"User_Id": 2, "Place_Id": 344, "Place_Ratings": 2}, {"User_Id": 2, "Place_Id": 179, "Place_Ratings": 3},

turn this json into like this:

{
    "id" : 1
    "User_Id" : [1,1]
    "Place_Id" : [179,344]
    "Place_Ratings" : [3,2]
},

{
    "id" : 2
    "User_Id" : [2,2]
    "Place_Id" : [344,179]
    "Place_Ratings" : [2,3]
}





"""

# make like example above

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







        



# save into a json file

with open('data.json', 'w') as f:
    
        json.dump(data, f)


# show the User_Id  = 1 on data.json file

User_Id_Login = 1

with open('data.json') as f:

    data = json.load(f)

    print(data[str(User_Id_Login)])










    







