import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..','clients'))

from flask import Blueprint, request, g
from flask_cors import cross_origin
from google.protobuf.json_format import MessageToJson
import grpc
import controllers.Errors as Errors

from clients.UsuarioCliente import UsuarioCliente

usuario_blueprint = Blueprint('usuario',__name__)

cliente = UsuarioCliente()

@usuario_blueprint.route('/usuarios',methods=['GET'])
@cross_origin()
def traerUsuarios():
    result = cliente.traerUsuarios(g.token)
    return MessageToJson(result)



@usuario_blueprint.route('/usuarios/<int:id>', methods=['GET'])
@cross_origin()
def traerUsuario(id):

   result = cliente.traerUsuario(id,g.token)
   return MessageToJson(result)

