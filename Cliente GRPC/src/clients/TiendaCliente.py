import os, sys
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
sys.path.append(os.path.dirname(CURRENT_DIR))


from flask import g
import grpc
import proto.service_pb2
import proto.service_pb2_grpc


class TiendaCliente(object):
    def __init__(self):
        self.host = 'localhost'
        self.server_port = 9090

        self.channel = grpc.insecure_channel('{}:{}'.format(self.host, self.server_port))
        #Especificar ServiceStub Correspondiente
        self.stub = proto.service_pb2_grpc.TiendaServiceStub(self.channel)


    def agregarTienda(self, data):
        request = proto.service_pb2.Tienda(
        codigoTienda = data['codigoTienda'],
        direccion = data['direccion'],
        ciudad = data['ciudad'],
        provincia = data['provincia'],
        habilitada = data['habilitada'],
        )
        return self.stub.AgregarTienda(request,metadata = g.metadata)
   

    def modificarTienda(self, data):
        request = proto.service_pb2.Tienda(
            codigoTienda = data['codigoTienda'],
            direccion = data['direccion'],
            ciudad = data['ciudad'],
            provincia = data['provincia'],
            habilitada = data['habilitada'],
        )
        return self.stub.ModificarTienda(request,metadata = g.metadata)
    

    def modificarStock(self, data):
        request = proto.service_pb2.ModificarStockRequest(
            idTienda = data['idTienda'],
            idProducto = data['idProducto'],
            cantidad = data['cantidad'],
        )
        return self.stub.ModificarStock(request,metadata = g.metadata)
    


    def traerTienda(self,id):
        request = proto.service_pb2.Id(id=id)
        return self.stub.TraerTienda(request,metadata = g.metadata)


    def traerTiendas(self):
        request = proto.service_pb2.Empty()
        return self.stub.TraerTiendas(request,metadata = g.metadata)
    

    def traerTiendasPorFiltro(self,filtro):
       request = proto.service_pb2.Filtro(filtro = filtro)
       return self.stub.TraerTiendasPorFiltro(request,metadata = g.metadata)
    

    def eliminarProductoDeTienda(self, data):
       request = proto.service_pb2.EliminarProductoDeTiendaRequest(
            idTienda = data['idTienda'],
            idProducto = data['idProducto']
        )
       return self.stub.EliminarProductoDeTienda(request,metadata = g.metadata)
    

