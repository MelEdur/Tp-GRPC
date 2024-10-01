import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..','clients'))

from flask import Blueprint, request
from flask_cors import cross_origin
from google.protobuf.json_format import MessageToJson

from clients.AuthCliente import AuthCliente

auth_blueprint = Blueprint('auth',__name__)

cliente = AuthCliente()

@auth_blueprint.route('/login',methods=['POST'])
@cross_origin()
def login():

    data = request.get_json()
    result = cliente.login(data)

    return MessageToJson(result)