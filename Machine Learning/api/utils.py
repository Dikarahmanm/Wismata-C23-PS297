import tensorflow as tf
import numpy as np
import pandas as pd
import pickle
import mysql.connector
from dotenv import dotenv_values


# load destination_jogja from pickle

with open('destination_jogja.pkl', 'rb') as f:
    destination_jogja = pickle.load(f)


# load destination_fix from pickle
with open('destination_fix.pkl', 'rb') as f:
    destination_fix = pickle.load(f)


# load scalerUser from pickle
with open('scalerUser.pkl', 'rb') as f:
    scalerUser = pickle.load(f)

# load scalerItem from pickle

with open('scalerItem.pkl', 'rb') as f:
    scalerItem = pickle.load(f)

# load scalerTarget from pickle

with open('scalerTarget.pkl', 'rb') as f:
    scalerTarget = pickle.load(f)

# Load model from file

model = tf.keras.models.load_model('model/Recommender_Model.h5')


def calculate_user_ratings(ratings_jogja, destination_jogja=destination_jogja):

    ratings_jogja = ratings_jogja.astype({'IdUser': 'int64', 'IdWisata': 'int64', 'rating': 'int64'})
    # Merge with destination_jogja to get category information
    df = ratings_jogja.merge(pd.DataFrame(destination_jogja), on='IdWisata')

    # Initialize dictionary to store aggregated data
    aggregated_data = {}

    # Iterate through each row and aggregate ratings
    for row in df.itertuples(index=False):
        IdUser = row.IdUser
        category = row.Category
        rating = row.rating

        if IdUser not in aggregated_data:
            aggregated_data[IdUser] = {
                'average_category_Bahari': 0.0,
                'average_category_Budaya': 0.0,
                'average_category_Cagar Alam': 0.0,
                'average_category_Pusat Perbelanjaan': 0.0,
                'average_category_Taman Hiburan': 0.0,
                'number_of_ratings_Bahari': 0,
                'number_of_ratings_Budaya': 0,
                'number_of_ratings_Cagar Alam': 0,
                'number_of_ratings_Pusat Perbelanjaan': 0,
                'number_of_ratings_Taman Hiburan': 0,
                'Average_All_Ratings': 0.0
            }

        aggregated_data[IdUser]['IdUser'] = IdUser
        aggregated_data[IdUser]['average_category_' + category] += rating
        aggregated_data[IdUser]['number_of_ratings_' + category] += 1
        aggregated_data[IdUser]['Average_All_Ratings'] += rating

    # Calculate average ratings and overall average
    for IdUser, data in aggregated_data.items():
        for category in ['Bahari', 'Budaya', 'Cagar Alam', 'Pusat Perbelanjaan', 'Taman Hiburan']:
            count = data['number_of_ratings_' + category]
            if count > 0:
                data['average_category_' + category] /= count

        total_ratings = sum(data['number_of_ratings_' + category] for category in ['Bahari', 'Budaya', 'Cagar Alam', 'Pusat Perbelanjaan', 'Taman Hiburan'])
        data['Average_All_Ratings'] /= total_ratings

    # Convert aggregated data to DataFrame
    pivoted_data = pd.DataFrame.from_dict(aggregated_data, orient='index')

    # Reorder columns
    columns_order = ['IdUser', 'average_category_Bahari', 'average_category_Budaya', 'average_category_Cagar Alam',
                     'average_category_Pusat Perbelanjaan', 'average_category_Taman Hiburan',
                     'number_of_ratings_Bahari', 'number_of_ratings_Budaya', 'number_of_ratings_Cagar Alam',
                     'number_of_ratings_Pusat Perbelanjaan', 'number_of_ratings_Taman Hiburan', 'Average_All_Ratings']

    pivoted_data = pivoted_data[columns_order]

    # Display the resulting DataFrame
    return pivoted_data



def get_sorted_IdWisatas(new_data):

    df_pred = pd.DataFrame(new_data)

    

    user_vec = calculate_user_ratings(df_pred, destination_jogja=destination_jogja)

    user_vecs = np.tile(user_vec, (len(destination_fix), 1))

    user_vec = calculate_user_ratings(df_pred, destination_jogja)

    item_vecs = destination_fix.drop('Place_Name', axis=1)

    # Scale user and item vectors
    suser_vecs = scalerUser.transform(user_vecs)
    sitem_vecs = scalerItem.transform(item_vecs)

    # Make a prediction
    y_p = model.predict([suser_vecs[:, 1:], sitem_vecs[:, 1:]])

    # Unscale y prediction
    y_pu = scalerTarget.inverse_transform(y_p)

    # Sort the results, highest prediction first
    sorted_index = np.argsort(-y_pu, axis=0).reshape(-1).tolist()  # Negate to get largest rating first
    sorted_ypu = y_pu[sorted_index]

    sorted_index = [x + 0 for x in sorted_index]

    sorted_items = item_vecs.loc[sorted_index]  # Using unscaled vectors for display

    # Get IdWisata values from sorted_items
    IdWisatas = sorted_items['IdWisata'].values.tolist()

    return IdWisatas


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

       
    # get the sorted place ids

    IdWisatas = get_sorted_IdWisatas(new_data)


    print(IdWisatas)