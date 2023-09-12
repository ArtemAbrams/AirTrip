package com.example.airtrip.controller.mvccontroller;

import com.example.airtrip.domain.dto.dtoformvc.ListProductsOfOrderDTO;
import com.example.airtrip.domain.dto.dtoformvc.ProductDTO;
import com.example.airtrip.domain.entity.entityformvc.OrderM;
import com.example.airtrip.domain.mapper.mvcmapper.ConflictCountryMapper;
import com.example.airtrip.domain.mapper.mvcmapper.OrderMMapper;
import com.example.airtrip.domain.mapper.mvcmapper.ProductMapper;
import com.example.airtrip.repository.ConflictCountryRepository;
import com.example.airtrip.repository.OrderRepository;
import com.example.airtrip.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderMController {
    private final OrderRepository orderRepository;
    private final ConflictCountryRepository countryRepository;
    private final ProductRepository productRepository;
    @GetMapping("/getAll")
    public String getAll(ModelMap modelMap){
        var orders = orderRepository.findAll()
                .stream()
                .map(OrderMMapper::entityToDTO)
                .toList();
        modelMap.addAttribute("listOrders", orders);
        return "order_index";
    }
    @PostMapping("/deleteOrder")
    public String deleteProduct(@RequestParam("id") Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with id " + id + " was not found"));
        orderRepository.delete(order);
        return "redirect:/order/getAll";
    }
    @GetMapping("/create")
    public String create(ModelMap modelMap){
        var countries = countryRepository.findAll()
                        .stream()
                        .map(ConflictCountryMapper::entityToDTO)
                        .toList();
        modelMap.addAttribute("countries", countries);
        return "create_order";
    }
    @PostMapping("/create")
    public String submitCreate(@RequestParam("name") String name,
                               @RequestParam("countryId") Long countryId,
                               @RequestParam("flag") MultipartFile file) throws IOException {
        var countries = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country with id " + countryId
                + " was not found"));
        var order = OrderM.builder()
                .companyName(name)
                .imageOfCompany(file.getBytes())
                .conflictCountry(countries)
                .build();
        orderRepository.saveAndFlush(order);
        return "redirect:/order/getAll";
    }
    @GetMapping("/editOrder")
    public String edit(@RequestParam("id") Long id,
                       ModelMap modelMap){
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with id " + id + " was not found"));
        var orderDTO = OrderMMapper.entityToDTO(order);
        var countries = countryRepository.findAll()
                .stream()
                .map(ConflictCountryMapper::entityToDTO)
                .toList();
        modelMap.addAttribute("countries", countries);
        modelMap.addAttribute("dto", orderDTO);
        return "edit_order";
    }
    @Transactional
    @PostMapping("/updateOrder")
    public String submitEditCountry(@RequestParam("id") Long id,
                                    @RequestParam("name") String name,
                                    @RequestParam("countryId") Long countryId,
                                    @RequestParam("flag") MultipartFile file) throws IOException {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with id " + id + " was not found"));
        var countries = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country with id " + countryId
                        + " was not found"));
        order.setCompanyName(name);
        order.setConflictCountry(countries);
        if(file.getBytes().length!=0){
            order.setImageOfCompany(file.getBytes());
        }
        return "redirect:/order/getAll";
    }
    @GetMapping("/addProduct")
    public String addProduct(@RequestParam("id") Long id,
                             ModelMap modelMap){
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with id " + id + " was not found"));
        var products = order.getProducts()
                .stream()
                .map(ProductMapper::entityToDTO)
                .toList();
        modelMap.addAttribute("listProducts", findProducts(products));
        modelMap.addAttribute("orderId", id);
        return "addProduct";
    }
    @GetMapping("/addProductToOrder")
    public String listProducts(@RequestParam("orderId") Long orderId,
                               ModelMap modelMap){
        var products = productRepository.findAll()
                .stream()
                .map(ProductMapper::entityToDTO)
                .toList();
        modelMap.addAttribute("listProducts", products);
        modelMap.addAttribute("orderId", orderId);
        return "listProducts";
    }
    @PostMapping("/deleteProductFromOrder")
    @Transactional
    public String delete(@RequestParam("productId") Long productId,
                         @RequestParam("orderId") Long orderId){
        var product = productRepository.findById(productId)
                .orElseThrow(() ->  new RuntimeException("Country with id " + productId + " was not found"));
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Country with id " + orderId + " was not found"));
        product.deleteOrder(order);
        return "redirect:/order/addProduct?id=" +orderId;
    }
    @PostMapping("/submitAddProduct")
    @Transactional
    public String submitAddProduct(@RequestParam("productId") Long productId,
                         @RequestParam("orderId") Long orderId){
        var product = productRepository.findById(productId)
                .orElseThrow(() ->  new RuntimeException("Country with id " + productId + " was not found"));
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Country with id " + orderId + " was not found"));
        product.addOrder(order);
        return "redirect:/order/addProductToOrder?orderId="+orderId;
    }
    private List<ListProductsOfOrderDTO> findProducts(List<ProductDTO> productDTOS){
        var products = productRepository.findAll()
                .stream()
                .map(ProductMapper::entityToDTO)
                .toList();
        var available = new ArrayList<ListProductsOfOrderDTO>();
        for(var product : products){
            if(productDTOS.contains(product)){
                available.add(ListProductsOfOrderDTO
                        .builder()
                        .id(product.getId())
                        .name(product.getName())
                        .typeOfWeapon(product.getTypeOfWeapon())
                        .image(product.getImage())
                        .count(Math.toIntExact(productDTOS.stream()
                                .filter(e -> e.equals(product))
                                .count()))
                        .build());
            }
        }
        return available;
    }
}
