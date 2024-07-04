package com.app.trading.service;

import com.app.trading.domain.PaymentMethod;
import com.app.trading.domain.PaymentOrder;
import com.app.trading.domain.PaymentOrderStatus;
import com.app.trading.model.User;
import com.app.trading.repository.PaymentOrderRepo;
import com.app.trading.response.PaymentResponse;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentOrderRepo paymentOrderRepo;

//    @Value("${stripe.api_key}")
//    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecretKey;

    @Override
    public PaymentOrder createOrder(User user, Long amount,
                                    PaymentMethod paymentMethod) {

        PaymentOrder paymentOrder=new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        paymentOrder.setStatus(PaymentOrderStatus.PENDING);


        return paymentOrderRepo.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {

        return paymentOrderRepo.findById(id).orElseThrow(()-> new Exception("payment order not found"));
    }

    @Override
    public Boolean proccedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {

        if (paymentOrder.getStatus()==null){

            paymentOrder.setStatus(PaymentOrderStatus.PENDING);

        }

        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){

            if (paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecretKey);
                Payment payment = razorpay.payments.fetch(paymentId);

                Integer amount = payment.get("amount");
                String status = payment.get("status");

                if(status.equals("captured")){
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    return true;
                }
                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepo.save(paymentOrder);
                return false;
            }
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepo.save(paymentOrder);

            return true;

        }
        return false;
    }

    @Override
    public PaymentResponse createRazorpayPaymentLink(User user, Long amount , Long orderId) throws RazorpayException {

        Long Amount = amount *100;

        try{
            //Instantiate a Razorpay client with your key Id and secret
            RazorpayClient razorpay = new RazorpayClient(apiKey,apiSecretKey);

            //Create a JSON object with the payment link request parameters
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            // Create a JSON object with the customer details
            JSONObject customer = new JSONObject();
            customer.put("name",user.getFullName());
            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);

            //Create a JSON object with the notification settings
            JSONObject notify = new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);

            //set the remainder settings
            paymentLinkRequest.put("reminder_enable", true);

            //Set the callback URL and method
            paymentLinkRequest.put("callback_url", "http://localhost:5464/wallet?order_id="+orderId);
            paymentLinkRequest.put("callback_method", "get");

            //Create the payment link using the paymentLink.create() method
            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentResponse res = new PaymentResponse();
            res.setPayment_url(paymentLinkUrl);

            return res;

        } catch (RazorpayException e) {
            System.out.println("Error creating payment link : "+e.getMessage());
            throw new RazorpayException(e.getMessage());
        }

    }


}
