import base64, io
import json
from flask import Blueprint, request, jsonify, send_file

from clients.Cliente import Cliente

catalogo_blueprint = Blueprint('catalogo',__name__)

#Instancio cliente de soap
cliente= Cliente()

@catalogo_blueprint.route('/catalogos', methods=['POST'])
def agregarCatalogo():
    data = request.get_json()
    result = cliente.agregarCatalogo(data)

    return jsonify(result),200

@catalogo_blueprint.route('/catalogos', methods=['PATCH'])
def modificarCatalogo():
    data = request.get_json()
    result = cliente.modificarCatalogo(data)

    return jsonify(result),200

@catalogo_blueprint.route('/catalogos/<int:id>', methods=['DELETE'])
def eliminarCatalogo(id):
    result = cliente.eliminarCatalogo(id)

    return jsonify(result),200

@catalogo_blueprint.route('/catalogoslista', methods=['POST'])
def traerCatalogos():
    data = request.get_json()
    result = cliente.traerCatalogos(data)
    return soapAObjeto(result)

def soapAObjeto(response):
    # Lista donde guardará los catálogos
    catalogos = []

    # Recorremos el response del SOAP para extraer
    for catalogo in response:
        #Mapeamos un catálogo
        catalogoAux = {
            'idCatalogo': catalogo.idCatalogo,
            'nombre': catalogo.nombre,
            'productos': []
        }
        #Mapeamos los productos
        for producto in catalogo.productos:
            productoAux = {
                'idproducto': producto.idproducto,
                'nombre': producto.nombre,
                'codigo': producto.codigo,
                'talle': producto.talle,
                'color': producto.color,
                'foto': producto.foto,
                'habilitado': producto.habilitado,
            }
            #Agrego la lista de productos al catálogo mapeado
            catalogoAux['productos'].append(productoAux)

        #Añado a la lista de catalogo
        catalogos.append(catalogoAux)

    return catalogos

@catalogo_blueprint.route('/catalogos/pdf/<int:id>', methods=['GET'])
def descargarPdf(id):
    try:
        result = cliente.pdfCatalogo(id)
    except Exception as e:
        return f"No existe un catalogo con esa id",404

    pdf_data = base64.b64decode(result.pdf)
    pdf_stream = io.BytesIO(pdf_data)

    return send_file(pdf_stream,
                     as_attachment=True,
                     download_name=f"{result.nombre}.pdf",
                     mimetype='application/pdf')