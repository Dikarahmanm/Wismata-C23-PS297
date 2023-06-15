import tensorflow as tf
import numpy as np
import pandas as pd
import pickle


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

    

    user_vec = calculate_user_ratings(df_pred, destination_jogja)

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



model_path = "model/filtering_model.tflite"


class Interpreter():
    def __init__(self, model_path):
        self.interpreter = tf.lite.Interpreter(model_path=model_path)
        self.interpreter.allocate_tensors()
        self.input_details = self.interpreter.get_input_details()
        self.output_details = self.interpreter.get_output_details()

    def predict(self, input_data):
        self.interpreter.set_tensor(self.input_details[0]['index'], input_data)
        self.interpreter.invoke()
        output_data = self.interpreter.get_tensor(self.output_details[0]['index'])
        return np.argmax(output_data)


def recommend_rating(category_test, price_test):
    interpreter = Interpreter(model_path)
    input_data = np.array([[category_test, price_test]], dtype=np.float32)
    prediction = interpreter.predict(input_data)
    return prediction


