import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..','clients'))

from flask import Blueprint, request
from flask_cors import cross_origin
from google.protobuf.json_format import MessageToJson

from clients.ProductoCliente import ProductoCliente

producto_blueprint = Blueprint('producto',__name__)

producto = ProductoCliente()

@producto_blueprint.route('/productos',methods=['POST'])
@cross_origin()
def agregarProducto():

    data = request.get_json()
    result = producto.agregarProducto(data)
    return MessageToJson(result)


@producto_blueprint.route('/productos',methods=['PATCH'])
@cross_origin()
def modificarProducto():

    data = request.get_json()
    result = producto.modificarProducto(data)
    return MessageToJson(result)


@producto_blueprint.route('/productos',methods=['GET'])
@cross_origin()
def traerProductos():
    result = producto.traerProductos()
    return MessageToJson(result)


@producto_blueprint.route('/productos/<int:id>', methods=['PATCH'])
@cross_origin()
def eliminarProducto(id):

   result = producto.eliminarProducto(id)
   return MessageToJson(result)


@producto_blueprint.route('/productos/stocks',methods=['GET'])
@cross_origin()
def traerStocks():
    result = producto.traerStocks()
    return MessageToJson(result)

@producto_blueprint.route('/productos/filtrado',methods=['GET'])
@cross_origin()
def traerProductosPorFiltro():

    data = request.get_json()
    result = producto.traerProductosPorFiltro(data)
    return MessageToJson(result)