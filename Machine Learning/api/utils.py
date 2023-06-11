import tensorflow as tf
import numpy as np
import pandas as pd
from sklearn.preprocessing import StandardScaler, MinMaxScaler

destination = pd.read_csv('data/tourism_with_id.csv')
rating = pd.read_csv('data/tourism_rating.csv')
user = pd.read_csv('data/user.csv')


destination_jogja = destination[destination['City'] == 'Yogyakarta']
destination_jogja = destination_jogja.reset_index(drop=True)
destination_jogja['IdWisata'] = destination_jogja.index + 1

destination_fix = destination_jogja[["IdWisata", "Place_Name", "Category", "Price", "Rating"]]

# Perform one-hot encoding on the "Category" column
category_encoded = pd.get_dummies(destination_fix["Category"], prefix="Category")

# Concatenate the one-hot encoded categories with the selected columns
destination_fix = pd.concat([destination_fix[["IdWisata", "Place_Name", "Price", "Rating"]], category_encoded], axis=1)

id_jogja = destination_jogja['IdWisata']
ratings_jogja = rating[rating["IdWisata"].isin(id_jogja)]
ratings_jogja = ratings_jogja.reset_index(drop=True)



def calculate_user_ratings(ratings_jogja, destination_jogja=destination_jogja):
    # Merge with destination_jogja to get category information
    ratings_jogja['IdWisata'] = ratings_jogja['IdWisata'].astype(int)
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
pivoted_data

merged_df = pd.merge(pivoted_data, ratings_jogja, on='IdUser', how='left')

# Merge the destination_fix dataset with the merged pivoted_data and df2 datasets based on 'IdWisata'
final_df = pd.merge(merged_df, destination_fix, on='IdWisata', how='left')

full_df = final_df.sample(frac=1).reset_index(drop=True)

user_full = full_df[['IdUser', 'average_category_Bahari',
       'average_category_Budaya', 'average_category_Cagar Alam',
       'average_category_Pusat Perbelanjaan', 'average_category_Taman Hiburan',
       'number_of_ratings_Bahari', 'number_of_ratings_Budaya',
       'number_of_ratings_Cagar Alam', 'number_of_ratings_Pusat Perbelanjaan',
       'number_of_ratings_Taman Hiburan', 'Average_All_Ratings']]

item_full = full_df[['IdWisata', 'Price', 'Rating', 'Category_Bahari', 'Category_Budaya', 'Category_Cagar Alam', 
                      'Category_Pusat Perbelanjaan', 'Category_Taman Hiburan']]

y_full = full_df[['rating']]

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


model = tf.keras.models.load_model("model/Recommender_Model.h5")

# new_data = {
#     'IdUser': [1, 1, 1, 1, 1,1,1],
#     'IdWisata': [1, 5, 3, 5, 4,4,4],
#     'rating': [5, 5, 5, 3, 3,2,2]
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

# # IdWisata value from sorted_items put on json file

# IdWisata = sorted_items['IdWisata'].values.tolist()

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
