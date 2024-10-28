
from zeep import Client

class Cliente(object):
    def __init__(self):
        self.wsdl_url = "http://localhost:8082/ws/stockeate.wsdl"
        self.client = Client(wsdl=self.wsdl_url)

    def agregarUsuarios(self,data):
        #Realizo envio al servidor soap
        response = self.client.service.agregarUsuarios(usuarios=data)
        return response

    def agregarCatalogo(self,data):
        response = self.client.service.agregarCatalogo(nombre=data['nombre'],
                                                       codigoTienda=data['codigoTienda'],
                                                       ids=data['ids'])
        return response

    def modificarCatalogo(self,data):
        response = self.client.service.modificarCatalogo(idCatalogo=data['idCatalogo'],
                                                       nombre=data['nombre'],
                                                       codigoTienda=data['codigoTienda'],
                                                       ids=data['ids'])
        return response

    def eliminarCatalogo(self,id):
        response = self.client.service.eliminarCatalogo(id=id)
        return response
    
    def traerCatalogos(self,data):
        response = self.client.service.traerCatalogos(codigoTienda=data['codigoTienda'])
        return response
    
    def pdfCatalogo(self,id):
        response = self.client.service.pdfCatalogo(id=id)
        return response
    
    def traerInformeOrdenDeCompra(self,data):
        response = self.client.service.traerInformeOrdenDeCompra(filtros=data)
        return response