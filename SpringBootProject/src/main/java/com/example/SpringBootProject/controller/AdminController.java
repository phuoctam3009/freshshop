package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.DAO.BalanceRepository;
import com.example.SpringBootProject.DAO.CategoryRepository;
import com.example.SpringBootProject.DAO.ProductRepository;
import com.example.SpringBootProject.DAO.UserRepository;
import com.example.SpringBootProject.Entity.Category;
import com.example.SpringBootProject.Entity.Product;
import com.example.SpringBootProject.Entity.User;
import com.example.SpringBootProject.Service.FileUploadUtil;
import com.example.SpringBootProject.Service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private ShoppingCartService cartService;

    @RequestMapping(value = "/admin/dashboard", method = RequestMethod.GET)
    public String viewAdmin(Model model, HttpServletRequest request){
        Principal user = request.getUserPrincipal();
        if(user != null){
            User byUsername = userRepository.findByUsername(user.getName());
            if(byUsername.getRole().getId() == 2){
                String errorMessage = "Can't access this page";
                model.addAttribute("errorMessage", errorMessage);
                return "redirect:/";
            }
        }else{
            return "login";
        }
        //View default is list category
        List<Category> listCategory = categoryRepository.findAll();
        model.addAttribute("categories", listCategory);
        return "dashboard";
    }

    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public String viewUser(Model model, HttpServletRequest request){
        Principal user = request.getUserPrincipal();
        if(user != null){
            User byUsername = userRepository.findByUsername(user.getName());
            if(byUsername.getRole().getId() == 2){
                String errorMessage = "Can't access this page";
                model.addAttribute("errorMessage", errorMessage);
                return "redirect:/";
            }
        }else{
            return "login";
        }
        //View default is list user
        List<User> listUser = userRepository.findAll();
        model.addAttribute("users", listUser);
        return "admin-user";
    }

    @RequestMapping(value = "/admin/product", method = RequestMethod.GET)
    public String viewProduct(Model model, HttpServletRequest request){
        Principal user = request.getUserPrincipal();
        if(user != null){
            User byUsername = userRepository.findByUsername(user.getName());
            if(byUsername.getRole().getId() == 2){
                String errorMessage = "Can't access this page";
                model.addAttribute("errorMessage", errorMessage);
                return "redirect:/";
            }
        }else{
            return "login";
        }
        //View default is list user
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin-product";
    }

    @GetMapping(value = "/admin/user/resetpw/{userId}")
    public String resetPasswordUser(@PathVariable("userId") Long userId, Model model, HttpServletRequest request ,RedirectAttributes redirectAttributes){
        //Check current user
        Principal user = request.getUserPrincipal();
        if(user != null){
            User byUsername = userRepository.findByUsername(user.getName());
            if(byUsername.getRole().getId() == 2){
                String errorMessage = "Can't access this page";
                model.addAttribute("errorMessage", errorMessage);
                return "redirect:/";
            }
        }else{
            return "login";
        }
        //Sau khi xác thực role admin, thay đổi password của user

        return "redirect:/admin/user";
    }

    @GetMapping("/admin/category/update/{cateId}")
    public String editCategory(@PathVariable("cateId") Long categoryId, Model model, RedirectAttributes redirectAttributes){
        System.out.println("Id nhan duoc la : " + categoryId);
        Category category = categoryRepository.findById(categoryId).get();
        if(category == null){
            String message = "Can't find this category have ID : " + categoryId;
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/";
        }else{
            model.addAttribute("categoryObj", category);
        }
        return "update-category";
    }

    @GetMapping("/admin/category/formadd")
    public String formAddCategory(Model model, HttpServletRequest request){
        //Check current user
        Principal user = request.getUserPrincipal();
        if(user != null){
            User byUsername = userRepository.findByUsername(user.getName());
            if(byUsername.getRole().getId() == 2){
                String errorMessage = "Can't access this page";
                model.addAttribute("errorMessage", errorMessage);
                return "redirect:/";
            }
        }else{
            return "login";
        }
        Category category = new Category();
        model.addAttribute("categoryObj", category);
        return "add-category";
    }
    @GetMapping("admin/product/formadd")
    public String formAddProduct(Model model, HttpServletRequest request){
        //Check current user
        Principal user = request.getUserPrincipal();
        if(user != null){
            User byUsername = userRepository.findByUsername(user.getName());
            if(byUsername.getRole().getId() == 2){
                String errorMessage = "Can't access this page";
                model.addAttribute("errorMessage", errorMessage);
                return "redirect:/";
            }
        }else{
            return "login";
        }
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        Product product = new Product();
        model.addAttribute("product", product);
        return "add-product";
    }


    @PostMapping("/admin/category/save")
    public String saveCategory(@ModelAttribute(name = "categoryObj") Category category,
                               RedirectAttributes redirectAttributes,
                               @RequestParam("fileImage")MultipartFile multipartFile) throws IOException {
        String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImg(fileName);
        Category savedCategory = categoryRepository.save(category);

        String uploadDir = "./src/main/resources/static/images/category/" + savedCategory.getId();
        FileUploadUtil.saveFile(uploadDir, multipartFile, fileName);
        String message = "Added Category";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/product/save")
    public String saveProduct(@ModelAttribute(name="product") Product product,
                              RedirectAttributes redirectAttributes,
                              @RequestParam("mainImage") MultipartFile mainMultipartFile) throws IOException {
        String mainImageName = StringUtils.cleanPath(mainMultipartFile.getOriginalFilename());
        product.setImg(mainImageName);

        Product savedProduct = productRepository.save(product);

        String uploadDir = "./src/main/resources/static/images/product/" + savedProduct.getId();

        FileUploadUtil.saveFile(uploadDir, mainMultipartFile, mainImageName);
        String message = "Added Product";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/category/delete/{categoryid}")
    public String deleteCategory(@PathVariable("categoryid")Long categoryId, HttpServletRequest request, RedirectAttributes redirectAttributes){
        Category category = categoryRepository.findById(categoryId).get();
        if(category != null){
            categoryRepository.delete(category);
            String message = "Removed Category";
            redirectAttributes.addFlashAttribute("message", message);
            System.out.println("Đã thêm attribute message");
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/product/delete/{productId}")
    public String deleteProduct(@PathVariable("productId")Long productId, HttpServletRequest request, RedirectAttributes redirectAttributes){
        System.out.println("Vào được");
        Product product = productRepository.findById(productId).get();
        if(product != null){
            productRepository.delete(product);
            String message = "Removed Product";
            redirectAttributes.addFlashAttribute("message", message);
            System.out.println("Đã thêm attribute message");
        }
        return "redirect:/admin/product";
    }


}
