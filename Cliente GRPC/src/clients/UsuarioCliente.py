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
        metadata = None
        if(g.token):
            metadata = [('authorization', f'Bearer {g.token}')]
        return self.stub.AgregarUsuario(request,metadata = metadata)


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
        metadata = None
        if(g.token):
            metadata = [('authorization', f'Bearer {g.token}')]
        return self.stub.ModificarUsuario(request,metadata = metadata)


    def traerUsuarios(self):
        request = proto.service_pb2.Empty()
        metadata = None
        if(g.token):
            metadata = [('authorization', f'Bearer {g.token}')]

        return self.stub.TraerUsuarios(request,metadata = metadata)


    def traerUsuario(self,id):
        request = proto.service_pb2.Id(id=id)
        #Agregado de token a la request OBLIGATORIO
        metadata = None
        if(g.token):
            metadata = [('authorization', f'Bearer {g.token}')]
        #Fin de agregado

        return self.stub.TraerUsuario(request,metadata = metadata)

