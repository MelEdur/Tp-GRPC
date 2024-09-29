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
            nombreProducto = data['nombreProducto'],
            talle = data['talle'],
            color = data['color'],
            foto = data['foto'],
            id = [proto.service_pb2.Id(id = idItem['id']) for idItem in data['ids']])
        return self.stub.AgregarProducto(request,metadata = g.metadata)


    def modificarProducto(self,data):
        request = proto.service_pb2.ProductoStockid(
            id = data['id'],
            codigoProducto = data['codigoProducto'],
            nombreProducto = data['nombreProducto'],
            talle = data['talle'],
            color = data['color'],
            foto = data['foto'],
            habilitado = data['habilitado'],
            idStockCompleto = data['idStockCompleto']
        )
        return self.stub.ModificarProducto(request,metadata = g.metadata)


    def traerProductos(self):
        request = proto.service_pb2.Empty()
        return self.stub.TraerProductos(request,metadata = g.metadata)


    def traerStocksPorFiltro(self,data):
        request = proto.service_pb2.FiltroProducto(
            nombreProducto = data['nombreProducto'],
            codigoProducto = data['codigoProducto'],
            talle = data['talle'],
            color = data['color'],
            codigoTienda = data['codigoTienda']
        )
        return self.stub.TraerStocksPorFiltro(request,metadata = g.metadata)

    def traerStocks(self):
        request = proto.service_pb2.Empty()
        return self.stub.TraerStocks(request,metadata = g.metadata)

    def eliminarStock(self,id):
        request = proto.service_pb2.Id(id=id)
        return self.stub.EliminarStock(request,metadata = g.metadata)

    def modificarStockCantidad(self,data):
        request = proto.service_pb2.ModificarStockCantidadRequest(
            idStockCompleto = data['idStockCompleto'],
            cantidad = data['cantidad']
        )
        return self.stub.ModificarProducto(request,metadata = g.metadata)