package com.soap.server.service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.soap.server.entity.CatalogoEntity;
import com.soap.server.entity.ProductoEntity;
import com.soap.server.entity.TiendaEntity;
import org.springframework.stereotype.Service;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.soap.server.repository.ICatalogoRepository;
import com.soap.server.repository.IProductoRepository;
import com.soap.server.repository.ITiendaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import stockeate.*;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final ICatalogoRepository catalogoRepository;
    private final IProductoRepository productoRepository;
    private final ITiendaRepository tiendaRepository;

    @Transactional
    public AgregarCatalogoResponse agregarCatalogo( String nombre, String codigoTienda, List<Integer> ids){
        AgregarCatalogoResponse response = new AgregarCatalogoResponse();
        TiendaEntity tienda = tiendaRepository.findByCodigoTienda(codigoTienda).get();
        List<ProductoEntity> productos = new ArrayList<>();
        for(int id: ids){
            productos.add(productoRepository.findById(id).get());
        }
        CatalogoEntity catalogo = catalogoRepository.save(CatalogoEntity.builder()
                                        .nombre(nombre)
                                        .productos(productos)
                                        .tienda(tienda)
                                        .build());
        response.setErrores("No hubo errores");
        return response;
    }
    @Transactional
    public ModificarCatalogoResponse modificarCatalogo(int idCatalogo, String nombre, String codigoTienda, List<Integer> ids){
        ModificarCatalogoResponse response = new ModificarCatalogoResponse();
        TiendaEntity tienda = tiendaRepository.findByCodigoTienda(codigoTienda).get();
        List<ProductoEntity> productos = new ArrayList<>();
        CatalogoEntity catalogo = catalogoRepository.findById(idCatalogo).get();
        for(int id: ids){
            productos.add(productoRepository.findById(id).get());
        }
        catalogo.setNombre(nombre);
        catalogo.setProductos(productos);
        catalogoRepository.save(catalogo);

        return response;
    }

    @Transactional
    public EliminarCatalogoResponse eliminarCatalogo(int id){
        EliminarCatalogoResponse response = new EliminarCatalogoResponse();
        CatalogoEntity catalogo = catalogoRepository.findById(id).get();
        catalogoRepository.delete(catalogo);
        return response;
    }


    public TraerCatalogosResponse traerCatalogos(String codigoTienda){
        TraerCatalogosResponse response = new TraerCatalogosResponse();
        TiendaEntity tienda = tiendaRepository.findByCodigoTienda(codigoTienda).get();
        List<CatalogoEntity> catalogos = catalogoRepository.findByTienda(tienda);
        for(CatalogoEntity catalogo :catalogos){

            //Creamos catalogo del tipo que usa la response
            Catalogo auxCatalogo = new Catalogo();
            //Le cargamos el nombre e id
            auxCatalogo.setNombre(catalogo.getNombre());
            auxCatalogo.setIdCatalogo(catalogo.getId());
            //Le cargamos los productos
            for(ProductoEntity producto: catalogo.getProductos()){
                Producto auxProducto = new Producto();
                auxProducto.setIdproducto(producto.getId());
                auxProducto.setNombre(producto.getNombreProducto());
                auxProducto.setCodigo(producto.getCodigoProducto());
                auxProducto.setTalle(producto.getTalle());
                auxProducto.setFoto(producto.getFoto());
                auxProducto.setColor(producto.getColor());
                auxProducto.setHabilitado(producto.isHabilitado());

                auxCatalogo.getProductos().add(auxProducto);
            }


            response.getCatalogos().add(auxCatalogo);
        }
        return response;
    }

    public PdfCatalogoResponse pdfCatalogo(@RequestPayload PdfCatalogoRequest request){
        CatalogoEntity catalogo = catalogoRepository.findById(request.getId())
                .orElseThrow(()->new EntityNotFoundException("No existe un catalogo cone esa id"));

        PdfCatalogoResponse response = new PdfCatalogoResponse();

        Document documento = new Document();
        ByteArrayOutputStream salida = new ByteArrayOutputStream();

        try{
            PdfWriter.getInstance(documento, salida);
            documento.open();

            documento.add(new Paragraph("Productos:"));
            documento.add(Chunk.NEWLINE);


            for(ProductoEntity producto : catalogo.getProductos()){

                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{1,2});

                try {
                    Image image = Image.getInstance(new URL(producto.getFoto()));
                    image.scaleToFit(150,150);
                    PdfPCell imageCell = new PdfPCell(image);
                    imageCell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(imageCell);
                }catch (MalformedURLException e){
                    documento.add(new Paragraph("No se ha encontrado una imagen"));
                }catch (IOException e){
                    documento.add(new Paragraph("No se ha encontrado una imagen"));
                }
                PdfPCell detailsCell = new PdfPCell();
                detailsCell.addElement(new Paragraph("Nombre: "+ producto.getNombreProducto()));
                detailsCell.addElement(new Paragraph("Talle: "+ producto.getTalle()));
                detailsCell.addElement(new Paragraph("Color: "+ producto.getColor()));
                detailsCell.setBorder(Rectangle.NO_BORDER);

                table.addCell(detailsCell);
                documento.add(table);
                documento.add(Chunk.NEWLINE);
            }

            documento.close();

        } catch (DocumentException e){
            e.printStackTrace();
        }
        String base64Pdf = encodePdfToBase64(new ByteArrayInputStream(salida.toByteArray()));

        response.setNombre("Catalogo "+ catalogo.getNombre());
        response.setPdf(base64Pdf.getBytes());

        return response;
    }

    private String encodePdfToBase64(InputStream pdfInputStream) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = pdfInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to encode PDF to Base64", e);
        }
    }

}
