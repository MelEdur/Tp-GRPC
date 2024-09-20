import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.dirname(CURRENT_DIR))

import grpc
import proto.service_pb2
import proto.service_pb2_grpc

class UsuarioCliente(object):
    def __init__(self):
        self.host = 'localhost'
        self.server_port = 9090

        self.channel = grpc.insecure_channel('{}:{}'.format(self.host, self.server_port))
        #Especificar ServiceStub Correspondiente
        self.stub = proto.service_pb2_grpc.UsuarioServiceStub(self.channel)

    #Definir los métodos a usar
    def traerUsuarios(self,token):
        request = proto.service_pb2.Empty()
        metadata = None
        if(token):
            metadata = [('authorization', f'Bearer {token}')]

        return self.stub.TraerUsuarios(request,metadata = metadata)

    def traerUsuario(self,id,token):
        request = proto.service_pb2.Id(id=id)
        metadata = None
        if(token):
            metadata = [('authorization', f'Bearer {token}')]

        return self.stub.TraerUsuario(request,metadata = metadata)