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

    #Obtener valores del mensaje de error
    status_code = e.code().value[0]
    description = e.details()

    #Armar respuesta
    response = {
        'error': {
            'message': description
        }
    }
    #Enviar respuesta con el código de error traducido
    return jsonify(response), grpc_to_rest_mapping.get(status_code)
