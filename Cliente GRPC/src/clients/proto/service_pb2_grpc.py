# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc
import warnings

import clients.proto.service_pb2 as service__pb2

GRPC_GENERATED_VERSION = '1.66.1'
GRPC_VERSION = grpc.__version__
_version_not_supported = False

try:
    from grpc._utilities import first_version_is_lower
    _version_not_supported = first_version_is_lower(GRPC_VERSION, GRPC_GENERATED_VERSION)
except ImportError:
    _version_not_supported = True

if _version_not_supported:
    raise RuntimeError(
        f'The grpc package installed is at version {GRPC_VERSION},'
        + f' but the generated code in service_pb2_grpc.py depends on'
        + f' grpcio>={GRPC_GENERATED_VERSION}.'
        + f' Please upgrade your grpc module to grpcio>={GRPC_GENERATED_VERSION}'
        + f' or downgrade your generated code using grpcio-tools<={GRPC_VERSION}.'
    )


class AuthServiceStub(object):
    """Definimos los servicios y su funcionalidad (request-response)

    EDU
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.Login = channel.unary_unary(
                '/AuthService/Login',
                request_serializer=service__pb2.LoginRequest.SerializeToString,
                response_deserializer=service__pb2.Token.FromString,
                _registered_method=True)


class AuthServiceServicer(object):
    """Definimos los servicios y su funcionalidad (request-response)

    EDU
    """

    def Login(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_AuthServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'Login': grpc.unary_unary_rpc_method_handler(
                    servicer.Login,
                    request_deserializer=service__pb2.LoginRequest.FromString,
                    response_serializer=service__pb2.Token.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'AuthService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))
    server.add_registered_method_handlers('AuthService', rpc_method_handlers)


 # This class is part of an EXPERIMENTAL API.
class AuthService(object):
    """Definimos los servicios y su funcionalidad (request-response)

    EDU
    """

    @staticmethod
    def Login(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/AuthService/Login',
            service__pb2.LoginRequest.SerializeToString,
            service__pb2.Token.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)


class UsuarioServiceStub(object):
    """Missing associated documentation comment in .proto file."""

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.AgregarUsuario = channel.unary_unary(
                '/UsuarioService/AgregarUsuario',
                request_serializer=service__pb2.CrearUsuarioRequest.SerializeToString,
                response_deserializer=service__pb2.Id.FromString,
                _registered_method=True)
        self.ModificarUsuario = channel.unary_unary(
                '/UsuarioService/ModificarUsuario',
                request_serializer=service__pb2.Usuario.SerializeToString,
                response_deserializer=service__pb2.Id.FromString,
                _registered_method=True)
        self.TraerUsuarios = channel.unary_unary(
                '/UsuarioService/TraerUsuarios',
                request_serializer=service__pb2.Empty.SerializeToString,
                response_deserializer=service__pb2.UsuariosLista.FromString,
                _registered_method=True)
        self.TraerUsuario = channel.unary_unary(
                '/UsuarioService/TraerUsuario',
                request_serializer=service__pb2.Id.SerializeToString,
                response_deserializer=service__pb2.Usuario.FromString,
                _registered_method=True)
        self.TraerUsuariosPorFiltro = channel.unary_unary(
                '/UsuarioService/TraerUsuariosPorFiltro',
                request_serializer=service__pb2.FiltroUsuario.SerializeToString,
                response_deserializer=service__pb2.UsuariosLista.FromString,
                _registered_method=True)


class UsuarioServiceServicer(object):
    """Missing associated documentation comment in .proto file."""

    def AgregarUsuario(self, request, context):
        """Devolvemos ID a modo de un OK
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ModificarUsuario(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def TraerUsuarios(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def TraerUsuario(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def TraerUsuariosPorFiltro(self, request, context):
        """DUDA BUSCAR POR "NOMBRE" Y Tienda
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_UsuarioServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'AgregarUsuario': grpc.unary_unary_rpc_method_handler(
                    servicer.AgregarUsuario,
                    request_deserializer=service__pb2.CrearUsuarioRequest.FromString,
                    response_serializer=service__pb2.Id.SerializeToString,
            ),
            'ModificarUsuario': grpc.unary_unary_rpc_method_handler(
                    servicer.ModificarUsuario,
                    request_deserializer=service__pb2.Usuario.FromString,
                    response_serializer=service__pb2.Id.SerializeToString,
            ),
            'TraerUsuarios': grpc.unary_unary_rpc_method_handler(
                    servicer.TraerUsuarios,
                    request_deserializer=service__pb2.Empty.FromString,
                    response_serializer=service__pb2.UsuariosLista.SerializeToString,
            ),
            'TraerUsuario': grpc.unary_unary_rpc_method_handler(
                    servicer.TraerUsuario,
                    request_deserializer=service__pb2.Id.FromString,
                    response_serializer=service__pb2.Usuario.SerializeToString,
            ),
            'TraerUsuariosPorFiltro': grpc.unary_unary_rpc_method_handler(
                    servicer.TraerUsuariosPorFiltro,
                    request_deserializer=service__pb2.FiltroUsuario.FromString,
                    response_serializer=service__pb2.UsuariosLista.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'UsuarioService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))
    server.add_registered_method_handlers('UsuarioService', rpc_method_handlers)


 # This class is part of an EXPERIMENTAL API.
class UsuarioService(object):
    """Missing associated documentation comment in .proto file."""

    @staticmethod
    def AgregarUsuario(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/AgregarUsuario',
            service__pb2.CrearUsuarioRequest.SerializeToString,
            service__pb2.Id.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def ModificarUsuario(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/ModificarUsuario',
            service__pb2.Usuario.SerializeToString,
            service__pb2.Id.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def TraerUsuarios(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/TraerUsuarios',
            service__pb2.Empty.SerializeToString,
            service__pb2.UsuariosLista.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def TraerUsuario(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/TraerUsuario',
            service__pb2.Id.SerializeToString,
            service__pb2.Usuario.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def TraerUsuariosPorFiltro(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/TraerUsuariosPorFiltro',
            service__pb2.FiltroUsuario.SerializeToString,
            service__pb2.UsuariosLista.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)


class TiendaServiceStub(object):
    """SEBA
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.AgregarTienda = channel.unary_unary(
                '/TiendaService/AgregarTienda',
                request_serializer=service__pb2.Tienda.SerializeToString,
                response_deserializer=service__pb2.Id.FromString,
                _registered_method=True)
        self.ModificarTienda = channel.unary_unary(
                '/TiendaService/ModificarTienda',
                request_serializer=service__pb2.Tienda.SerializeToString,
                response_deserializer=service__pb2.Id.FromString,
                _registered_method=True)
        self.TraerTiendas = channel.unary_unary(
                '/TiendaService/TraerTiendas',
                request_serializer=service__pb2.Empty.SerializeToString,
                response_deserializer=service__pb2.TiendasLista.FromString,
                _registered_method=True)
        self.TraerTienda = channel.unary_unary(
                '/TiendaService/TraerTienda',
                request_serializer=service__pb2.Id.SerializeToString,
                response_deserializer=service__pb2.Tienda.FromString,
                _registered_method=True)
        self.ModificarStock = channel.unary_unary(
                '/TiendaService/ModificarStock',
                request_serializer=service__pb2.ModificarStockRequest.SerializeToString,
                response_deserializer=service__pb2.Id.FromString,
                _registered_method=True)
        self.TraerTiendasPorFiltro = channel.unary_unary(
                '/TiendaService/TraerTiendasPorFiltro',
                request_serializer=service__pb2.Filtro.SerializeToString,
                response_deserializer=service__pb2.TiendasLista.FromString,
                _registered_method=True)
        self.EliminarProductoDeTienda = channel.unary_unary(
                '/TiendaService/EliminarProductoDeTienda',
                request_serializer=service__pb2.EliminarProductoDeTiendaRequest.SerializeToString,
                response_deserializer=service__pb2.Id.FromString,
                _registered_method=True)
        self.TraerProductosPorTienda = channel.unary_unary(
                '/TiendaService/TraerProductosPorTienda',
                request_serializer=service__pb2.Id.SerializeToString,
                response_deserializer=service__pb2.ProductosLista.FromString,
                _registered_method=True)


class TiendaServiceServicer(object):
    """SEBA
    """

    def AgregarTienda(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ModificarTienda(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def TraerTiendas(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def TraerTienda(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ModificarStock(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def TraerTiendasPorFiltro(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def EliminarProductoDeTienda(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def TraerProductosPorTienda(self, request, context):
        """ELIAN
        """
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_TiendaServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'AgregarTienda': grpc.unary_unary_rpc_method_handler(
                    servicer.AgregarTienda,
                    request_deserializer=service__pb2.Tienda.FromString,
                    response_serializer=service__pb2.Id.SerializeToString,
            ),
            'ModificarTienda': grpc.unary_unary_rpc_method_handler(
                    servicer.ModificarTienda,
                    request_deserializer=service__pb2.Tienda.FromString,
                    response_serializer=service__pb2.Id.SerializeToString,
            ),
            'TraerTiendas': grpc.unary_unary_rpc_method_handler(
                    servicer.TraerTiendas,
                    request_deserializer=service__pb2.Empty.FromString,
                    response_serializer=service__pb2.TiendasLista.SerializeToString,
            ),
            'TraerTienda': grpc.unary_unary_rpc_method_handler(
                    servicer.TraerTienda,
                    request_deserializer=service__pb2.Id.FromString,
                    response_serializer=service__pb2.Tienda.SerializeToString,
            ),
            'ModificarStock': grpc.unary_unary_rpc_method_handler(
                    servicer.ModificarStock,
                    request_deserializer=service__pb2.ModificarStockRequest.FromString,
                    response_serializer=service__pb2.Id.SerializeToString,
            ),
            'TraerTiendasPorFiltro': grpc.unary_unary_rpc_method_handler(
                    servicer.TraerTiendasPorFiltro,
                    request_deserializer=service__pb2.Filtro.FromString,
                    response_serializer=service__pb2.TiendasLista.SerializeToString,
            ),
            'EliminarProductoDeTienda': grpc.unary_unary_rpc_method_handler(
                    servicer.EliminarProductoDeTienda,
                    request_deserializer=service__pb2.EliminarProductoDeTiendaRequest.FromString,
                    response_serializer=service__pb2.Id.SerializeToString,
            ),
            'TraerProductosPorTienda': grpc.unary_unary_rpc_method_handler(
                    servicer.TraerProductosPorTienda,
                    request_deserializer=service__pb2.Id.FromString,
                    response_serializer=service__pb2.ProductosLista.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'TiendaService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))
    server.add_registered_method_handlers('TiendaService', rpc_method_handlers)


 # This class is part of an EXPERIMENTAL API.
class TiendaService(object):
    """SEBA
    """

    @staticmethod
    def AgregarTienda(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/TiendaService/AgregarTienda',
            service__pb2.Tienda.SerializeToString,
            service__pb2.Id.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def ModificarTienda(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/TiendaService/ModificarTienda',
            service__pb2.Tienda.SerializeToString,
            service__pb2.Id.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def TraerTiendas(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/TiendaService/TraerTiendas',
            service__pb2.Empty.SerializeToString,
            service__pb2.TiendasLista.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def TraerTienda(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/TiendaService/TraerTienda',
            service__pb2.Id.SerializeToString,
            service__pb2.Tienda.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def ModificarStock(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/TiendaService/ModificarStock',
            service__pb2.ModificarStockRequest.SerializeToString,
            service__pb2.Id.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def TraerTiendasPorFiltro(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/TiendaService/TraerTiendasPorFiltro',
            service__pb2.Filtro.SerializeToString,
            service__pb2.TiendasLista.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def EliminarProductoDeTienda(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/TiendaService/EliminarProductoDeTienda',
            service__pb2.EliminarProductoDeTiendaRequest.SerializeToString,
            service__pb2.Id.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def TraerProductosPorTienda(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/TiendaService/TraerProductosPorTienda',
            service__pb2.Id.SerializeToString,
            service__pb2.ProductosLista.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)


class ProductoServiceStub(object):
    """KEVIN
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.AgregarProducto = channel.unary_unary(
                '/ProductoService/AgregarProducto',
                request_serializer=service__pb2.AgregarProductoRequest.SerializeToString,
                response_deserializer=service__pb2.Id.FromString,
                _registered_method=True)
        self.ModificarProducto = channel.unary_unary(
                '/ProductoService/ModificarProducto',
                request_serializer=service__pb2.Producto.SerializeToString,
                response_deserializer=service__pb2.Id.FromString,
                _registered_method=True)
        self.TraerProductos = channel.unary_unary(
                '/ProductoService/TraerProductos',
                request_serializer=service__pb2.Empty.SerializeToString,
                response_deserializer=service__pb2.ProductosLista.FromString,
                _registered_method=True)
        self.EliminarProducto = channel.unary_unary(
                '/ProductoService/EliminarProducto',
                request_serializer=service__pb2.Id.SerializeToString,
                response_deserializer=service__pb2.Id.FromString,
                _registered_method=True)
        self.TraerProductosPorFiltro = channel.unary_unary(
                '/ProductoService/TraerProductosPorFiltro',
                request_serializer=service__pb2.Filtro.SerializeToString,
                response_deserializer=service__pb2.ProductosLista.FromString,
                _registered_method=True)


class ProductoServiceServicer(object):
    """KEVIN
    """

    def AgregarProducto(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ModificarProducto(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def TraerProductos(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def EliminarProducto(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def TraerProductosPorFiltro(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_ProductoServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'AgregarProducto': grpc.unary_unary_rpc_method_handler(
                    servicer.AgregarProducto,
                    request_deserializer=service__pb2.AgregarProductoRequest.FromString,
                    response_serializer=service__pb2.Id.SerializeToString,
            ),
            'ModificarProducto': grpc.unary_unary_rpc_method_handler(
                    servicer.ModificarProducto,
                    request_deserializer=service__pb2.Producto.FromString,
                    response_serializer=service__pb2.Id.SerializeToString,
            ),
            'TraerProductos': grpc.unary_unary_rpc_method_handler(
                    servicer.TraerProductos,
                    request_deserializer=service__pb2.Empty.FromString,
                    response_serializer=service__pb2.ProductosLista.SerializeToString,
            ),
            'EliminarProducto': grpc.unary_unary_rpc_method_handler(
                    servicer.EliminarProducto,
                    request_deserializer=service__pb2.Id.FromString,
                    response_serializer=service__pb2.Id.SerializeToString,
            ),
            'TraerProductosPorFiltro': grpc.unary_unary_rpc_method_handler(
                    servicer.TraerProductosPorFiltro,
                    request_deserializer=service__pb2.Filtro.FromString,
                    response_serializer=service__pb2.ProductosLista.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'ProductoService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))
    server.add_registered_method_handlers('ProductoService', rpc_method_handlers)


 # This class is part of an EXPERIMENTAL API.
class ProductoService(object):
    """KEVIN
    """

    @staticmethod
    def AgregarProducto(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/ProductoService/AgregarProducto',
            service__pb2.AgregarProductoRequest.SerializeToString,
            service__pb2.Id.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def ModificarProducto(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/ProductoService/ModificarProducto',
            service__pb2.Producto.SerializeToString,
            service__pb2.Id.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def TraerProductos(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/ProductoService/TraerProductos',
            service__pb2.Empty.SerializeToString,
            service__pb2.ProductosLista.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def EliminarProducto(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/ProductoService/EliminarProducto',
            service__pb2.Id.SerializeToString,
            service__pb2.Id.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def TraerProductosPorFiltro(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/ProductoService/TraerProductosPorFiltro',
            service__pb2.Filtro.SerializeToString,
            service__pb2.ProductosLista.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)
