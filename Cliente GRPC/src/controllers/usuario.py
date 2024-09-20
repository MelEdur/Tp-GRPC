import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..','clients'))

from flask import Blueprint, jsonify, request
from flask_cors import cross_origin
from google.protobuf.json_format import MessageToJson
import grpc
import controllers.Errors as Errors

from clients.UsuarioCliente import UsuarioCliente

usuario_blueprint = Blueprint('usuario',__name__)

cliente = UsuarioCliente()

@usuario_blueprint.route('/usuario',methods=['GET'])
@cross_origin()
def traerUsuarios():
    token = request.headers.get('Authorization')

    try:
        result = cliente.traerUsuarios(token)
        return MessageToJson(result)

    except grpc.RpcError as e:
        return Errors.manejarError(e)


@usuario_blueprint.route('/usuario/<int:id>', methods=['GET'])
@cross_origin()
def traerUsuario(id):
    token = request.headers.get('Authorization')

    try:
        result = cliente.traerUsuario(id,token)
        return MessageToJson(result)

    except grpc.RpcError as e:
        return Errors.manejarError(e)
