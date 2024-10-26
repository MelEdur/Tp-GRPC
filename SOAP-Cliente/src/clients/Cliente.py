
from zeep import Client

class Cliente(object):
    def __init__(self):
        self.wsdl_url = "http://localhost:8082/ws/stockeate.wsdl"
        self.client = Client(wsdl=self.wsdl_url)

    def agregarUsuarios(self,data):
        #Realizo envio al servidor soap
        response = self.client.service.agregarUsuarios(usuarios=data)
        return response