#!/usr/bin/env python3
import tensorflow as tf
import pandas as pd
import numpy as np


model_path = "../model/filtering_model.tflite"


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


def run():

    interpreter = Interpreter(model_path)

    # test data 

    category_test = 3
    price_test = 1

    input_data = np.array([[category_test, price_test]], dtype=np.float32)

    prediction = interpreter.predict(input_data)

    print(prediction)

    


if __name__ == "__main__":

    run()

    




    

