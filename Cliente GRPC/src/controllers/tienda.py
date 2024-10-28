import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..','clients'))

from flask import Blueprint, request
from flask_cors import cross_origin
from google.protobuf.json_format import MessageToJson
#Importar tu cliente de GRPC
from clients.TiendaCliente import TiendaCliente

tienda_blueprint = Blueprint('tienda',__name__)


cliente = TiendaCliente()

@tienda_blueprint.route('/tienda',methods=['POST'])
@cross_origin()
def agregarTienda():

    data = request.get_json() 
    result = cliente.agregarTienda(data)
    return MessageToJson(result)
 

@tienda_blueprint.route('/tienda',methods=['PATCH'])
@cross_origin()
def modificarTienda():

    data = request.get_json()
    result = cliente.modificarTienda(data)
    return MessageToJson(result)


@tienda_blueprint.route('/tienda/modificarStock',methods=['PATCH'])
@cross_origin()
def modificarStock():

    data = request.get_json()
    result = cliente.modificarStock(data)
    return MessageToJson(result)



@tienda_blueprint.route('/tienda/<int:id>', methods=['GET'])
@cross_origin()
def traerTienda(id):

   result = cliente.traerTienda(id)
   return MessageToJson(result,always_print_fields_with_no_presence=True)


@tienda_blueprint.route('/tiendas',methods=['GET'])
@cross_origin()
def traerTiendas():
    result = cliente.traerTiendas()
    return MessageToJson(result,always_print_fields_with_no_presence=True)

@tienda_blueprint.route('/tienda/traerStocks',methods=['GET'])
@cross_origin()
def traerStocksPorTienda():
    data = request.get_json()
    result = cliente.traerStocksPorTienda(data)
    return MessageToJson(result,always_print_fields_with_no_presence=True)

@tienda_blueprint.route('/tiendasPorFiltro', methods=['POST'])
@cross_origin()
def traerTiendasPorFiltro():
   data = request.get_json()
   data['filtro'] = str(data['filtro']).replace(" ", "_")
   result = cliente.traerTiendasPorFiltro(data)
   return MessageToJson(result,always_print_fields_with_no_presence=True)

@tienda_blueprint.route('/tienda/eliminarProducto', methods=['DELETE'])
@cross_origin()
def eliminarProductoDeTienda():
   
   data = request.get_json()
   result = cliente.eliminarProductoDeTienda(data)
   return MessageToJson(result)
