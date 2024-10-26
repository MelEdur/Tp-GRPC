from flask import Flask
import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..'))

from controllers.usuario import usuario_blueprint

def create_app():
    app = Flask(__name__)

    #Registrar controllers
    app.register_blueprint(usuario_blueprint)
    return app