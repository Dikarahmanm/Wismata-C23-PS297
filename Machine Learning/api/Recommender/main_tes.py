import json
import tensorflow as tf
import numpy as np
import pandas as pd
from sklearn.preprocessing import StandardScaler, MinMaxScaler
from flask import Flask, request, jsonify, session
import mysql.connector
from dotenv import dotenv_values
import jwt

destination = pd.read_csv('data/tourism_with_id.csv')
rating = pd.read_csv('data/tourism_rating.csv')
user = pd.read_csv('data/user.csv')


destination_jogja = destination[destination['City'] == 'Yogyakarta']
destination_jogja = destination_jogja.reset_index(drop=True)
destination_jogja['Place_Id'] = destination_jogja.index + 1

destination_fix = destination_jogja[["Place_Id", "Place_Name", "Category", "Price", "Rating"]]

# Perform one-hot encoding on the "Category" column
category_encoded = pd.get_dummies(destination_fix["Category"], prefix="Category")

# Concatenate the one-hot encoded categories with the selected columns
destination_fix = pd.concat([destination_fix[["Place_Id", "Place_Name", "Price", "Rating"]], category_encoded], axis=1)

id_jogja = destination_jogja['Place_Id']
ratings_jogja = rating[rating["Place_Id"].isin(id_jogja)]
ratings_jogja = ratings_jogja.reset_index(drop=True)



def calculate_user_ratings(ratings_jogja, destination_jogja=destination_jogja):
    # Merge with destination_jogja to get category information
    ratings_jogja['Place_Id'] = ratings_jogja['Place_Id'].astype(int)
    df = ratings_jogja.merge(pd.DataFrame(destination_jogja), on='Place_Id')

    

    # Initialize dictionary to store aggregated data
    aggregated_data = {}

    # Iterate through each row and aggregate ratings
    for row in df.itertuples(index=False):
        user_id = row.User_Id
        category = row.Category
        rating = row.Place_Ratings

        if user_id not in aggregated_data:
            aggregated_data[user_id] = {
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

        aggregated_data[user_id]['User_Id'] = user_id
        aggregated_data[user_id]['average_category_' + category] += rating
        aggregated_data[user_id]['number_of_ratings_' + category] += 1
        aggregated_data[user_id]['Average_All_Ratings'] += rating

    # Calculate average ratings and overall average
    for user_id, data in aggregated_data.items():
        for category in ['Bahari', 'Budaya', 'Cagar Alam', 'Pusat Perbelanjaan', 'Taman Hiburan']:
            count = data['number_of_ratings_' + category]
            if count > 0:
                data['average_category_' + category] /= count

        total_ratings = sum(data['number_of_ratings_' + category] for category in ['Bahari', 'Budaya', 'Cagar Alam', 'Pusat Perbelanjaan', 'Taman Hiburan'])
        data['Average_All_Ratings'] /= total_ratings

    # Convert aggregated data to DataFrame
    pivoted_data = pd.DataFrame.from_dict(aggregated_data, orient='index')

    # Reorder columns
    columns_order = ['User_Id', 'average_category_Bahari', 'average_category_Budaya', 'average_category_Cagar Alam',
                     'average_category_Pusat Perbelanjaan', 'average_category_Taman Hiburan',
                     'number_of_ratings_Bahari', 'number_of_ratings_Budaya', 'number_of_ratings_Cagar Alam',
                     'number_of_ratings_Pusat Perbelanjaan', 'number_of_ratings_Taman Hiburan', 'Average_All_Ratings']

    pivoted_data = pivoted_data[columns_order]

    # Display the resulting DataFrame
    return pivoted_data

# Merge with destination_jogja to get category information
df = ratings_jogja.merge(pd.DataFrame(destination_jogja), on='Place_Id')

# Initialize dictionary to store aggregated data
aggregated_data = {}

# Iterate through each row and aggregate ratings
for row in df.itertuples(index=False):
    user_id = row.User_Id
    category = row.Category
    rating = row.Place_Ratings
    
    if user_id not in aggregated_data:
        aggregated_data[user_id] = {
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
    
    aggregated_data[user_id]['User_Id'] = user_id
    aggregated_data[user_id]['average_category_' + category] += rating
    aggregated_data[user_id]['number_of_ratings_' + category] += 1
    aggregated_data[user_id]['Average_All_Ratings'] += rating

# Calculate average ratings and overall average
for user_id, data in aggregated_data.items():
    for category in ['Bahari', 'Budaya', 'Cagar Alam', 'Pusat Perbelanjaan', 'Taman Hiburan']:
        count = data['number_of_ratings_' + category]
        if count > 0:
            data['average_category_' + category] /= count

    total_ratings = sum(data['number_of_ratings_' + category] for category in ['Bahari', 'Budaya', 'Cagar Alam', 'Pusat Perbelanjaan', 'Taman Hiburan'])
    data['Average_All_Ratings'] /= total_ratings

# Convert aggregated data to DataFrame
pivoted_data = pd.DataFrame.from_dict(aggregated_data, orient='index')

# Reorder columns
columns_order = ['User_Id', 'average_category_Bahari', 'average_category_Budaya', 'average_category_Cagar Alam',
                 'average_category_Pusat Perbelanjaan', 'average_category_Taman Hiburan',
                 'number_of_ratings_Bahari', 'number_of_ratings_Budaya', 'number_of_ratings_Cagar Alam',
                 'number_of_ratings_Pusat Perbelanjaan', 'number_of_ratings_Taman Hiburan', 'Average_All_Ratings']

pivoted_data = pivoted_data[columns_order]

# Display the resulting DataFrame
pivoted_data

merged_df = pd.merge(pivoted_data, ratings_jogja, on='User_Id', how='left')

# Merge the destination_fix dataset with the merged pivoted_data and df2 datasets based on 'Place_Id'
final_df = pd.merge(merged_df, destination_fix, on='Place_Id', how='left')

full_df = final_df.sample(frac=1).reset_index(drop=True)

user_full = full_df[['User_Id', 'average_category_Bahari',
       'average_category_Budaya', 'average_category_Cagar Alam',
       'average_category_Pusat Perbelanjaan', 'average_category_Taman Hiburan',
       'number_of_ratings_Bahari', 'number_of_ratings_Budaya',
       'number_of_ratings_Cagar Alam', 'number_of_ratings_Pusat Perbelanjaan',
       'number_of_ratings_Taman Hiburan', 'Average_All_Ratings']]

item_full = full_df[['Place_Id', 'Price', 'Rating', 'Category_Bahari', 'Category_Budaya', 'Category_Cagar Alam', 
                      'Category_Pusat Perbelanjaan', 'Category_Taman Hiburan']]

y_full = full_df[['Place_Ratings']]

item_full_unscaled = item_full
user_full_unscaled = user_full
y_full_unscaled    = y_full

scalerItem = StandardScaler()
scalerItem.fit(item_full)
item_full = scalerItem.transform(item_full)

scalerUser = StandardScaler()
scalerUser.fit(user_full)
user_full = scalerUser.transform(user_full)

scalerTarget = MinMaxScaler((-1, 1))
scalerTarget.fit(y_full.to_numpy().reshape(-1, 1))
y_full = scalerTarget.transform(y_full.to_numpy().reshape(-1, 1))


model = tf.keras.models.load_model("Recommender_Model.h5")

# new_data = {
#     'User_Id': [1, 1, 1, 1, 1,1,1],
#     'Place_Id': [1, 5, 3, 5, 4,4,4],
#     'Place_Ratings': [5, 5, 5, 3, 3,2,2]
# }



# df_pred = pd.DataFrame(new_data)

# user_vec = calculate_user_ratings(df_pred, destination_jogja=destination_jogja)

# user_vecs = np.tile(user_vec, (len(destination_fix), 1))

# user_vec = calculate_user_ratings(df_pred, destination_jogja)

# item_vecs = destination_fix.drop('Place_Name', axis=1)

# # scale our user and item vectors

# suser_vecs = scalerUser.transform(user_vecs)

# sitem_vecs = scalerItem.transform(item_vecs)

# # make a prediction

# y_p = model.predict([suser_vecs[:, 1:], sitem_vecs[:, 1:]])

# # unscale y prediction

# y_pu = scalerTarget.inverse_transform(y_p)

# # sort the results, highest prediction first

# sorted_index = np.argsort(-y_pu,axis=0).reshape(-1).tolist()  #negate to get largest rating first

# sorted_ypu   = y_pu[sorted_index]

# sorted_index = [x+0 for x in sorted_index]

# sorted_items = item_vecs.loc[sorted_index]  #using unscaled vectors for display

# # Place_Id value from sorted_items put on json file

# Place_Id = sorted_items['Place_Id'].values.tolist()

def get_sorted_place_ids(User_Id, Place_Id, Place_Ratings, destination_fix, destination_jogja, model, scalerUser, scalerItem, scalerTarget):

    new_data = {

        'User_Id': User_Id,
        'Place_Id': Place_Id,
        'Place_Ratings': Place_Ratings

    }


    df_pred = pd.DataFrame(new_data, index=[0])

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

    # Get Place_Id values from sorted_items
    place_ids = sorted_items['Place_Id'].values.tolist()

    return place_ids


# print(get_sorted_place_ids(new_data, destination_fix, destination_jogja, model, scalerUser, scalerItem, scalerTarget))


# # # convert to json

# json_str = json.dumps(Place_Ids)

# # convert to dict

# json_dict = json.loads(json_str)

# # write to json file

# with open('Place_Id.json', 'w') as json_file:

#     json.dump(json_dict, json_file)



# def print_prediction(sorted_items, sorted_ypu, num_recommend, destination_jogja=destination_jogja):
#     res = pd.merge(sorted_items[['Place_Id']], destination_jogja[["Place_Id", "Place_Name", "Category", "Rating"]], on="Place_Id")[:num_recommend]
#     res["Predict_Rating"] = sorted_ypu[:num_recommend]
#     return res

# print_prediction(sorted_items, sorted_ypu, 126, destination_jogja=destination_jogja)




app = Flask(__name__)


@app.route('/', methods=['POST', 'GET'])
def index():


    # User_Id = request.args.get('User_Id')
    # Place_Id = request.args.get('Place_Id')
    # Place_Ratings = request.args.get('Place_Ratings')

    # User_Id = [3]
    # Place_Id = [1]
    # Place_Ratings = [5]

    # input request

    User_Id = request.args.get('User_Id')
    Place_Id = request.args.get('Place_Id')
    Place_Ratings = request.args.get('Place_Ratings')

    # Check if any of the variables is None or empty
    if User_Id is None or Place_Id is None or Place_Ratings is None:
        return jsonify({'error': 'Missing required parameters'})

    # Convert variables to integers
    try:
        User_Id = int(User_Id)
        Place_Id = int(Place_Id)
        Place_Ratings = int(Place_Ratings)
    except ValueError:
        return jsonify({'error': 'Invalid parameter types'})
    
    place_ids = get_sorted_place_ids(User_Id, Place_Id, Place_Ratings, destination_fix, destination_jogja, model, scalerUser, scalerItem, scalerTarget)

    data = {
        "Place_Id": place_ids
    }

    return jsonify(data)





if __name__ == "__main__":
    app.run(debug=True)




