import os, sys

CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..','clients'))

from flask import Blueprint, request, jsonify

from clients.Cliente import Cliente

usuario_blueprint = Blueprint('usuario',__name__)

#Instancio cliente de soap
cliente= Cliente()

@usuario_blueprint.route('/usuarios', methods=['POST'])
def agregarUsuarios():
    data = request.get_json()
    result = cliente.agregarUsuarios(data)
    return jsonify(result)