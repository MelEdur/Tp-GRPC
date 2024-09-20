import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.dirname(CURRENT_DIR))

from flask import g
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


    def agregarUsuario(self, data):
        request = proto.service_pb2.CrearUsuarioRequest(
            nombreUsuario = data['nombreUsuario'],
            contrasenia = data['contrasenia'],
            nombre = data['nombre'],
            apellido = data['apellido'],
            habilitado = data['habilitado'],
            codigoTienda = data['codigoTienda']
        )
        return self.stub.AgregarUsuario(request,metadata = g.metadata)


    def modificarUsuario(self,data):
        request = proto.service_pb2.Usuario(
            id = data['id'],
            nombreUsuario = data['nombreUsuario'],
            contrasenia = data['contrasenia'],
            nombre = data['nombre'],
            apellido = data['apellido'],
            habilitado = data['habilitado'],
            codigoTienda = data['codigoTienda']
        )
        return self.stub.ModificarUsuario(request,metadata = g.metadata)


    def traerUsuarios(self):
        request = proto.service_pb2.Empty()
        return self.stub.TraerUsuarios(request,metadata = g.metadata)


    def traerUsuario(self,id):
        request = proto.service_pb2.Id(id=id)
        return self.stub.TraerUsuario(request,metadata = g.metadata)

