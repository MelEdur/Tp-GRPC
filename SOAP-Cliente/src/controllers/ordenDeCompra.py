import base64, io
from flask import Blueprint, request, jsonify, send_file

from clients.Cliente import Cliente

ordenDeCompra_blueprint = Blueprint('ordenDeCompra',__name__)

#Instancio cliente de soap
cliente= Cliente()

@ordenDeCompra_blueprint.route('/ordenDeCompras', methods=['POST'])
def getInformeDeCompra():
    data = request.get_json()
    result = cliente.getInformeDeCompra(data)

    return soapAObjeto(result)

def soapAObjeto(response):
    informeDeCompraList = []

    # Recorremos el response del SOAP para extraer
    for informeDeCompraResponse in response:
        #Mapeamos un catálogo
        informeDeCompraAux = {
            'idOrdenDeCompra': informeDeCompraResponse.idOrdenDeCompra,
            'codigoTienda': informeDeCompraResponse.codigoTienda,
            'codigoTienda': informeDeCompraResponse.codigoTienda,
            'estado': informeDeCompraResponse.estado,
            'fechaDeSolicitud': informeDeCompraResponse.fechaDeSolicitud,
            'productos': []
        }
        #Mapeamos los productos
        for producto in informeDeCompraResponse.items:
            productoAux = {
                'codigoProducto': producto.codigoProducto,
                'nombre': producto.nombre,
                'cantidadPedida': producto.cantidadPedida,
                'cantidadPedidaTotal': producto.cantidadPedidaTotal
            }
            #Agrego la lista de productos al catálogo mapeado
            informeDeCompraAux['productos'].append(productoAux)

        #Añado a la lista de catalogo
        informeDeCompraList.append(informeDeCompraAux)

    return informeDeCompraList