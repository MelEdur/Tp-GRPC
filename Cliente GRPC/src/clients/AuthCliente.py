import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.dirname(CURRENT_DIR))

from flask import g
import grpc
import proto.service_pb2
import proto.service_pb2_grpc

class AuthCliente(object):
    def __init__(self):
        self.host = 'localhost'
        self.server_port = 9090

        self.channel = grpc.insecure_channel('{}:{}'.format(self.host, self.server_port))
        #Especificar ServiceStub Correspondiente
        self.stub = proto.service_pb2_grpc.AuthServiceStub(self.channel)

    def login(self,data):
        request = proto.service_pb2.LoginRequest(
            usuario = data['usuario'],
            contrasenia = data['contrasenia']
        )
        return self.stub.Login(request,metadata = g.metadata)