import base64, io
from flask import Blueprint, request, jsonify, send_file

from clients.Cliente import Cliente

catalogo_blueprint = Blueprint('catalogo',__name__)

#Instancio cliente de soap
cliente= Cliente()

@catalogo_blueprint.route('/catalogos', methods=['POST'])
def agregarCatalogo():
    data = request.get_json()
    result = cliente.agregarCatalogo(data)

    return result,200

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