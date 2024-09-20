import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.join(CURRENT_DIR,'..','clients'))

from flask import Blueprint, request
from flask_cors import cross_origin
from google.protobuf.json_format import MessageToJson
#Importar tu cliente de GRPC
#from clients.UsuarioCliente import UsuarioCliente

stock_blueprint = Blueprint('stock',__name__)