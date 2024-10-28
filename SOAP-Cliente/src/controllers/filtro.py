import os, sys

CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..','clients'))

from flask import Blueprint, request, jsonify

from clients.Cliente import Cliente

filtro_blueprint = Blueprint('filtro',__name__)

#Instancio cliente de soap
cliente= Cliente()

@filtro_blueprint.route('/filtros', methods=['GET'])
def traerFiltros():
    data = request.get_json()
    result = cliente.traerFiltros(data)

    return soapAObjetoLista(result)

@filtro_blueprint.route('/filtros', methods=['POST'])
def guardarFiltro():
    data = request.get_json()
    result = cliente.guardarFiltro(data)

    return soapAObjeto(result)

@filtro_blueprint.route('/filtros', methods=['PATCH'])
def editarFiltro():
    data = request.get_json()
    result = cliente.editarFiltro(data)
    
    return jsonify(result), 200

@filtro_blueprint.route('/filtros/<int:id>', methods=['DELETE'])
def eliminarFiltro(id):
    result = cliente.eliminarFiltro(id)

    return jsonify(result),200



def soapAObjetoLista(response):
    # Lista donde guardará los filtros
    filtros = []

    # Recorremos el response del SOAP para extraer
    for filtro in response:

        #Mapeamos un filtro
        filtroAux = {
            'idFiltro': filtro.idFiltro,
            'codigoProducto': filtro.codigoProducto,
            'fechaDesde': filtro.fechaDesde,
            'fechaHasta': filtro.fechaHasta,
            'estado': filtro.estado,
            'codigoTienda': filtro.codigoTienda
        }

        #Añado a la lista de filtros
        filtros.append(filtroAux)

    return filtros

def soapAObjeto(response):
    filtroAux = {
            'idFiltro': response.idFiltro,
            'codigoProducto': response.codigoProducto,
            'fechaDesde': response.fechaDesde,
            'fechaHasta': response.fechaHasta,
            'estado': response.estado,
            'codigoTienda': response.codigoTienda
        }
    return filtroAux