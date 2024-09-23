import os, sys

import grpc
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..'))

from flask import Flask, g,request
from flask_cors import CORS
from controllers.usuario import usuario_blueprint
from controllers.tienda import tienda_blueprint
from controllers.producto import producto_blueprint
from controllers.stock import stock_blueprint
from controllers.auth import auth_blueprint
import controllers.Errors as Errors

def create_app():
    app = Flask(__name__)

    #Registrar controladores
    app.register_blueprint(usuario_blueprint)
    app.register_blueprint(tienda_blueprint)
    app.register_blueprint(producto_blueprint)
    app.register_blueprint(stock_blueprint)
    app.register_blueprint(auth_blueprint)

    @app.before_request
    def extraerToken():
        token = request.headers.get('Authorization')
        g.metadata = None
        if(token):
            g.metadata = [('authorization', f'{token}')]


    @app.errorhandler(grpc.RpcError)
    def manejarErrorGrpc(e):
        return Errors.manejarError(e)

    return app