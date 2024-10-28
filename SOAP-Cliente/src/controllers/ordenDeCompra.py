import base64, io
from flask import Blueprint, request, jsonify, send_file

from clients.Cliente import Cliente

ordenDeCompra_blueprint = Blueprint('ordenDeCompra',__name__)

#Instancio cliente de soap
cliente= Cliente()

@ordenDeCompra_blueprint.route('/ordenDeCompras', methods=['POST'])
def traerInformeOrdenDeCompra():
    data = request.get_json()
    result = cliente.traerInformeOrdenDeCompra(data)

    return result,200
