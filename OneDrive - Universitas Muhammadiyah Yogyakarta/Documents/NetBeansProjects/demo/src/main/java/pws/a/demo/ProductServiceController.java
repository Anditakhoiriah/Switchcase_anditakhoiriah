/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pws.a.demo;

import Model.Product;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author user
 */

//Andita Khoiriah 20200140137
@RestController //memanggil Restful controller
public class ProductServiceController { //membuat kelas public bernama ProductServiceController
    private static Map<String, Product> productRepo = new HashMap<>(); //menggunakan HashMap untuk menympan data dalam bentuk string kedalam Product.Java
static{
    Product honey = new Product(); //mendeklarasikan variabel baru dengan nama honey
    honey.setId("1"); //menentukan id ke-1
    honey.setName("Honey"); //menuliskan nama dari id ke-1 yaitu "Honey"
    productRepo.put(honey.getId(), honey); //
    
    Product almond = new Product(); //mendeklarasikan variabel baru dengan nama almond
    almond.setId("2"); //menentukan id ke-2
    almond.setName("Almond"); //menuliskan nama dari id ke-1 yaitu "Almond"
    productRepo.put(almond.getId(), almond); //
    }
    
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE) //path untuk melakukan delete data ketika id pada kode => "/products/{id}"
    public ResponseEntity<Object> delete(@PathVariable("id")String id){
        //jika id yang ingin dihapus tidak ada maka akan mucul pesan error "product could not be found"
        if(!productRepo.containsKey(id))
                {
                    return new ResponseEntity<>("product could not be found", HttpStatus.NOT_FOUND);
                }
        //jika id yang ingin di hapus ada maka muncul pesan "Product is deleted successfully"
        else    {
                    productRepo.remove(id); 
                    return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK); //Memunculkan pesan "Product is deleted successfully"
                }
    }
    
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT) //path untuk melakukan edit data ketika id pada kode => "/products/{id}"
    public ResponseEntity<Object> updateProduct(@PathVariable("id")String id, @RequestBody Product product){
        //jika tidak ada id untuk di edit maka akan muncul pesan error "product could not be found"
        if(!productRepo.containsKey(id))
                {
                    return new ResponseEntity<>("product could not be found", HttpStatus.NOT_FOUND);
                }
        //jika id yang ingin di edit ada maka mucul pesan "Product is update successsfully"
        else 
        {
            productRepo.remove(id);
            product.setId(id);
            productRepo.put(id, product);
            return new ResponseEntity<>("Product is update successsfully", HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/products",method = RequestMethod.POST) //Untuk menambahkan data pada path "/products"
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        //Jika id yang dimasukkan sudah ada maka akan mucul pesan error "The product is already on the list"
        if(productRepo.containsKey(product.getId()))
                {
                    return new ResponseEntity<>("The product is already on the list", HttpStatus.CONFLICT);
                }
        //Jika id yang dimasukkan belum ada dalam list maka muncul pesan "Product added successfully"
        else 
        {
            productRepo.put(product.getId(), product);
            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        }
    }
    
    //menampilkan produk pada path "/products"
    @RequestMapping(value="/products")
    public ResponseEntity<Object> getProduct(){
        return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);//
    }
}