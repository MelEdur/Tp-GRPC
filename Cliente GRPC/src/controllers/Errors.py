from flask import Flask, jsonify

grpc_to_rest_mapping = {
        0: 200,
        1: 499,
        2: 500,
        3: 400,
        4: 504,
        5: 404,
        6: 409,
        7: 403,
        8: 429,
        9: 400,
        10: 409,
        11: 400,
        12: 501,
        13: 500,
        14: 503,
        15: 500,
        16: 401
    }

def manejarError(e):

    # Extract details from the gRPC error
    status_code = e.code().value[0]  # Get the integer value of the status code
    description = e.details()  # Get the error message

        # Create a response with just the code and message
    response = {
        'error': {
            'message': description
        }
    }
    return jsonify(response), grpc_to_rest_mapping.get(status_code)  # Adjust the status code as needed
