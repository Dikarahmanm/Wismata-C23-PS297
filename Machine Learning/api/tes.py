import mysql.connector
from dotenv import dotenv_values


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

        IdUser_login = 1

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

    return new_data


print(ml_function(2))

    