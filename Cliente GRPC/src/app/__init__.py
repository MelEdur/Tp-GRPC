import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..'))

from flask import Flask
from controllers.usuario import usuario_blueprint
from controllers.tienda import tienda_blueprint
from controllers.producto import producto_blueprint
from controllers.stock import stock_blueprint

def create_app():
    app = Flask(__name__)

    #Registrar controladores
    app.register_blueprint(usuario_blueprint)
    app.register_blueprint(tienda_blueprint)
    app.register_blueprint(producto_blueprint)
    app.register_blueprint(stock_blueprint)

    return app