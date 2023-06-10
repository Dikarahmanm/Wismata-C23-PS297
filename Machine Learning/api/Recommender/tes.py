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

# show database 

mycursor = mydb.cursor()

mycursor.execute("select * from user")


for x in mycursor:

    print(x)












    







