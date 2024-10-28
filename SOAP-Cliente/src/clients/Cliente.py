
from zeep import Client

class Cliente(object):
    def __init__(self):
        self.wsdl_url = "http://localhost:8082/ws/stockeate.wsdl"
        self.client = Client(wsdl=self.wsdl_url)

    #USUARIOS
    def agregarUsuarios(self,data):
        #Realizo envio al servidor soap
        response = self.client.service.agregarUsuarios(usuarios=data)
        return response

    #CATALOGOS
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
    
    #INFORMES DE COMPRA
    def getInformeDeCompra(self,data):
        response = self.client.service.getInformeDeCompra(codigoProducto=data['codigoProducto'],
                                                       fechaDesde=data['fechaDesde'],
                                                       fechaHasta=data['fechaHasta'],
                                                       estado=data['estado'],
                                                       codigoTienda=data['codigoTienda'])
        return response
    
    #FILTROS
    def traerFiltros(self,data):
        response = self.client.service.traerFiltros(idUsuario=data['idUsuario'])
        return response
    
    def guardarFiltro(self,data):
        response = self.client.service.guardarFiltro(codigoProducto=data['codigoProducto'],
                                                       fechaDesde=data['fechaDesde'],
                                                       fechaHasta=data['fechaHasta'],
                                                       estado=data['estado'],
                                                       codigoTienda=data['codigoTienda'],
                                                       idUsuario=data['idUsuario'])
        return response
    
    def editarFiltro(self,data):
        response = self.client.service.editarFiltro(codigoProducto=data['codigoProducto'],
                                                       fechaDesde=data['fechaDesde'],
                                                       fechaHasta=data['fechaHasta'],
                                                       estado=data['estado'],
                                                       codigoTienda=data['codigoTienda'],
                                                       idFiltro=data['idFiltro'])
        return response
    
    def eliminarFiltro(self,id):
        response = self.client.service.eliminarFiltro(idFiltro=id)
        return response