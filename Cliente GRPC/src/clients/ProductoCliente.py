import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.dirname(CURRENT_DIR))

from flask import g
import grpc
import proto.service_pb2
import proto.service_pb2_grpc

class ProductoCliente(object):
    def __init__(self):
        self.host = 'localhost'
        self.server_port = 9090

        self.channel = grpc.insecure_channel('{}:{}'.format(self.host, self.server_port))
        #Especificar ServiceStub Correspondiente
        self.stub = proto.service_pb2_grpc.ProductoServiceStub(self.channel)

    def agregarProducto(self, data):
        request = proto.service_pb2.AgregarProductoRequest(
            #Recibe
            #Producto producto
            #repeated Id id
            #??????
            codigoProducto = data['codigoProducto'],
            nombreProducto = data['nombreProducto'],
            talle = data['talle'],
            color = data['color'],
            foto = data['foto'],
            habilitado = data['habilitado'],
            id = data['id']
        )
        return self.stub.AgregarProducto(request,metadata = g.metadata)


    def modificarProducto(self,data):
        request = proto.service_pb2.Producto(
            id = data['id'],
            codigoProducto = data['codigoProducto'],
            nombreProducto = data['nombreProducto'],
            talle = data['talle'],
            color = data['color'],
            foto = data['foto'],
            habilitado = data['habilitado']
        )
        return self.stub.ModificarProducto(request,metadata = g.metadata)


    def traerProductos(self):
        request = proto.service_pb2.Empty()
        return self.stub.TraerProductos(request,metadata = g.metadata)


    def traerProductosPorFiltro(self,data):
        request = proto.service_pb2.FiltroProducto(
            nombreProducto = data['nombreProducto'],
            codigoProducto = data['codigoProducto'],
            talle = data['talle'],
            color = data['color']
        )
        return self.stub.TraerProductosPorFiltro(request,metadata = g.metadata)

    def traerStocks(self):
        request = proto.service_pb2.Empty()
        return self.stub.TraerStocks(request,metadata = g.metadata)

    def eliminarProducto(self,id):
        request = proto.service_pb2.Id(id=id)
        return self.stub.EliminarProducto(request,metadata = g.metadata)
