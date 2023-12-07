package online.ptsports.PTSports.Service.IMPL;



import online.ptsports.PTSports.DTO.CartDto;
import online.ptsports.PTSports.DTO.CartItemDto;
import online.ptsports.PTSports.DTO.OrderDto;
import online.ptsports.PTSports.DTO.ProductDto;
import online.ptsports.PTSports.Entity.*;
import online.ptsports.PTSports.Exeption.ResoureNotFoundException;
import online.ptsports.PTSports.Repository.*;
import online.ptsports.PTSports.Service.CartService;
import online.ptsports.PTSports.Service.NotificationService;
import online.ptsports.PTSports.Service.OrderService;
import online.ptsports.PTSports.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class OrderServiceImpl implements OrderService {

//    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderProductRepo orderProductRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    SizeRepo sizeRepo;

    @Autowired
    ColorRepo colorRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ProductService productService;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepo cartItemRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    OrderStatusRepo orderStatusRepo;

    @Autowired
    PaymentMethodRepo paymentMethodRepo;

    @Autowired
    NotificationService notificationService;


    @Override
    public OrderDto findOrderById(int orderID) {
        Order order = orderRepo.findById(orderID).orElseThrow(()
                -> new ResoureNotFoundException("Order", "ID", orderID));

        return convertToOrderDto(order);
    }

    @Override
    public OrderDto updateOrderByID(int orderID) {
        Order order = orderRepo.findById(orderID).orElseThrow(() -> new ResoureNotFoundException("Order", "ID", orderID));
        if (order.getStatus() == false) {
            order.setStatus(true);
            orderRepo.save(order);
        } else {
        }
        return convertToOrderDto(order);
    }

    @Override
    public OrderDto findOrderByCode(String code) {
        Order order = orderRepo.findByCode(code);
        return convertToOrderDto(order);
    }

    @Override
    public List<OrderDto> findOrderByUserID(int userID) {
        List<Order> orders = orderRepo.findOrderByUserID(userID);
        List<OrderDto> orderDtos = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            orderDtos.add(convertToOrderDto(orders.get(i)));
        }

        return orderDtos;
    }

    @Override
    public List<OrderDto> findOrder() {
        List<Order> orders = orderRepo.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            orderDtos.add(convertToOrderDto(orders.get(i)));
        }

        return orderDtos;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveOrder(Order order) {
        try {
            orderRepo.save(order);

        } catch (Exception e) {
            throw e;
        }
    }



    @Override
    @Transactional(rollbackOn = Exception.class)
    public void saveOrderService(OrderDto orderDto, HttpSession httpSession) {
        try {
            User user = userRepo.findById(orderDto.getUserID())
                    .orElseThrow(() -> new ResoureNotFoundException("User", "ID", orderDto.getUserID()));

            Order order = convertToOrder(orderDto);
            order.setOrderProducts(new ArrayList<>());

            int type = orderDto.getType();
            if (type == 0) {
                order.setStatus(false);
            }
            if (type == 1) {
                order.setStatus(true);
            }
            order.setUser(user);

            // Kiểm tra xem orderDto có giữ giá trị orderStatusID không
            if (orderDto.getOrderStatusID() != 0) {
                OrderStatus orderStatus = orderStatusRepo.findById(orderDto.getOrderStatusID())
                        .orElseThrow(() -> new ResoureNotFoundException("OrderStatus", "ID", orderDto.getOrderStatusID()));
                order.setOrderStatus(orderStatus);
            }

            // Kiểm tra xem orderDto có giữ giá trị paymentMethodID không
            if (orderDto.getPaymentMethodID() != 0) {
                PaymentMethod paymentMethod = paymentMethodRepo.findById(orderDto.getPaymentMethodID())
                        .orElseThrow(() -> new ResoureNotFoundException("PaymentMethod", "ID", orderDto.getPaymentMethodID()));
                order.setPaymentMethod(paymentMethod);
            }

            CartDto cart = cartService.getCart(orderDto.getUserID());
            List<CartItemDto> cartItems = cart.getItemList();

            double totalPrice = 0;

            for (int i = 0; i < cartItems.size(); i++) {
                            OrderProduct orderProduct = new OrderProduct();
            int finalI = i;
            orderProduct.setProductID(productRepo.findById(cartItems.get(i).getProductID())
                    .orElseThrow(() -> new ResoureNotFoundException("Product", "ID", cartItems.get(finalI).getProductID())));

            orderProduct.setSize(sizeRepo.findById(cartItems.get(i).getSizeID())
                    .orElseThrow(() -> new ResoureNotFoundException("Size", "ID", cartItems.get(finalI).getSizeID())));
            orderProduct.setColor(colorRepo.findById(cartItems.get(i).getColorID())
                    .orElseThrow(() -> new ResoureNotFoundException("Color", "ID", cartItems.get(finalI).getColorID())));
            orderProduct.setQuantity(cartItems.get(i).getQuantity());
            orderProduct.setTotalPrice(cartItems.get(i).getTotalPrice());

            orderProduct.setOrder(order);
            order.addOderProdcut(orderProduct);

            // Debug: Kiểm tra số lượng sản phẩm trước khi giảm
            System.out.println("Remaining quantity for product " + orderProduct.getProductID().getId() +
                    ": " + orderProduct.getProductID().getTotalQuantity());

            // Giảm số lượng sản phẩm
            orderProduct.getProductID().setTotalQuantity(
                    orderProduct.getProductID().getTotalQuantity() - orderProduct.getQuantity());

            // Debug: Kiểm tra số lượng sản phẩm sau khi giảm
            System.out.println("Remaining quantity for product " + orderProduct.getProductID().getId() +
                    " after reducing: " + orderProduct.getProductID().getTotalQuantity());

            totalPrice += cartItems.get(i).getTotalPrice();
        }
        order.setTotalPrice(totalPrice);


            // Gán trạng thái mặc định cho đơn hàng mới nếu chưa set
            if (order.getOrderStatus() == null) {
                OrderStatus defaultStatus = orderStatusRepo.findById(1)
                        .orElseThrow(() -> new ResoureNotFoundException("OrderStatus", "ID", 1));

                order.setOrderStatus(defaultStatus);
            }

            // Debug: Kiểm tra tổng giá trị đơn hàng
            System.out.println("Total price for the order: " + order.getTotalPrice());

            orderRepo.save(order);

            Cart cart1 = cartService.convertToCart(cart);
            List<CartItem> cartItems1 = cart1.getItemList();

            for (int i = 0; i < cartItems1.size(); i++) {
                cartItems1.get(i).setCart(null);
                cartItemRepo.delete(cartItems1.get(i));

            }
            cartRepo.delete(cart1);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }




    @Override
    public Order convertToOrder(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    @Override
    public OrderDto convertToOrderDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);


        return orderDto;
    }

    @Override
    public List<ProductDto> hotSaler() {
        String sql = "SELECT product_id\n" +
                "from order_product\n" +
                "GROUP BY product_id ORDER BY sum(quantity) DESC LIMIT 5 ";
        Query query = entityManager.createNativeQuery(sql);
        List list = query.getResultList();

        List<ProductDto> productDtos = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            Product product = productRepo.findById(((Integer) list.get(i))).orElseThrow(() -> new ResoureNotFoundException("Product", "ID", ((Integer) list.get(finalI))));
            productDtos.add(productService.convertToProductDto(product));
        }

        return productDtos;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateOrderStatus(Integer orderId, Integer newOrderStatusId) {
        try {
            Order order = orderRepo.findById(orderId)
                    .orElseThrow(() -> new ResoureNotFoundException("Order", "ID", orderId));

            OrderStatus newOrderStatus = orderStatusRepo.findById(newOrderStatusId)
                    .orElseThrow(() -> new ResoureNotFoundException("OrderStatus", "ID", newOrderStatusId));

            order.setOrderStatus(newOrderStatus);
            orderRepo.save(order);
            if (newOrderStatusId == 6) {
                notificationService.createNotificationForCompletedOrder(order, order.getUser());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
