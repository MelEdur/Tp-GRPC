from flask import Flask, send_from_directory
import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..'))

from flask_swagger_ui import get_swaggerui_blueprint
from controllers.usuario import usuario_blueprint
from controllers.catalogo import catalogo_blueprint
from controllers.ordenDeCompra import ordenDeCompra_blueprint
from controllers.filtro import filtro_blueprint

def create_app():
    app = Flask(__name__)

    app.route('/static/<path:path>')
    def send_static(path):
        return send_from_directory('static', path)

    SWAGGER_URL = '/swagger'
    API_URL = '/static/swagger.json'
    swaggerui_blueprint = get_swaggerui_blueprint(
        SWAGGER_URL,
        API_URL,
    )

    #Registrar controllers
    app.register_blueprint(usuario_blueprint)
    app.register_blueprint(catalogo_blueprint)
    app.register_blueprint(ordenDeCompra_blueprint)
    app.register_blueprint(filtro_blueprint)
    app.register_blueprint(swaggerui_blueprint, url_pregix=SWAGGER_URL)
    return app