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
    return MessageToJson(result,always_print_fields_with_no_presence=True)


@producto_blueprint.route('/productos/<int:id>', methods=['PATCH'])
@cross_origin()
def eliminarStock(id):

   result = producto.eliminarStock(id)
   return MessageToJson(result)

@producto_blueprint.route('/productos/stock',methods=['PATCH'])
@cross_origin()
def modificarStockCantidad():

    data = request.get_json()
    result = producto.modificarStockCantidad(data)
    return MessageToJson(result)

@producto_blueprint.route('/productos/stocks',methods=['GET'])
@cross_origin()
def traerStocks():
    result = producto.traerStocks()
    return MessageToJson(result,always_print_fields_with_no_presence=True)

@producto_blueprint.route('/productos/filtrado',methods=['POST'])
@cross_origin()
def traerStocksPorFiltro():

    data = request.get_json()
    result = producto.traerStocksPorFiltro(data)
    return MessageToJson(result,always_print_fields_with_no_presence=True)

@producto_blueprint.route('/productos/proveedor',methods=['POST'])
@cross_origin()
def agregarProductoProveedor():

    data = request.get_json()
    result = producto.agregarProductoProveedor(data)
    return MessageToJson(result)

#@producto_blueprint.route('/productos/novedades',methods=['GET'])
#@cross_origin()
#def traerProductosNuevosProveedor():
#    result = producto.traerProductosNuevosProveedor()
#    return MessageToJson(result,always_print_fields_with_no_presence=True)