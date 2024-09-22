import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..','clients'))

from flask import Blueprint, request, g
from flask_cors import cross_origin
from google.protobuf.json_format import MessageToJson

from clients.UsuarioCliente import UsuarioCliente

usuario_blueprint = Blueprint('usuario',__name__)

cliente = UsuarioCliente()

@usuario_blueprint.route('/usuarios',methods=['POST'])
@cross_origin()
def agregarUsuario():

    data = request.get_json()
    result = cliente.agregarUsuario(data)
    return MessageToJson(result)


@usuario_blueprint.route('/usuarios',methods=['PATCH'])
@cross_origin()
def modificarUsuario():

    data = request.get_json()
    result = cliente.modificarUsuario(data)
    return MessageToJson(result)


@usuario_blueprint.route('/usuarios',methods=['GET'])
@cross_origin()
def traerUsuarios():
    result = cliente.traerUsuarios()
    return MessageToJson(result,always_print_fields_with_no_presence=True)


@usuario_blueprint.route('/usuarios/<int:id>', methods=['GET'])
@cross_origin()
def traerUsuario(id):

   result = cliente.traerUsuario(id)
   return MessageToJson(result,always_print_fields_with_no_presence=True)

@usuario_blueprint.route('/usuarios/filtro', methods=['GET'])
@cross_origin()
def traerUsuariosPorFiltro():

    data = request.get_json()
    result = cliente.traerUsuariosPorFiltro(data)
    return MessageToJson(result,always_print_fields_with_no_presence=True)