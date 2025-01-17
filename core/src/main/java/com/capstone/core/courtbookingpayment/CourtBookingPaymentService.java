package com.capstone.core.courtbookingpayment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.capstone.core.courtbooking.CourtBookingRepository;
import com.capstone.core.courtbookingpayment.data.request.CreatePaymentRequestData;
import com.capstone.core.courtbookingpayment.data.request.SavePaymentRequestData;
import com.capstone.core.courtbookingpayment.data.response.CreatePaymentResponseData;
import com.capstone.core.courtbookingpayment.data.response.SavePaymentResponseData;
import com.capstone.core.table.CourtBookingPaymentTable;
import com.capstone.core.table.CourtBookingTable;
import com.capstone.core.util.consts.CourtBookingStatus;
import com.capstone.core.util.consts.PaymentType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourtBookingPaymentService {
    private CourtBookingPaymentRepository courtBookingPaymentRepository;
    private CourtBookingRepository courtBookingRepository;

    ResponseEntity<Object> createPayment(CreatePaymentRequestData requestData) throws UnsupportedEncodingException {
        String vnp_TxnRef = CourtBookingPaymentConfig.getRandomNumber(8);
        String vnp_TmnCode = CourtBookingPaymentConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", CourtBookingPaymentConfig.vnp_Version);
        vnp_Params.put("vnp_Command", CourtBookingPaymentConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(requestData.getAmount()));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_ReturnUrl", requestData.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", "192.168.1.1");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = CourtBookingPaymentConfig.hmacSHA512(CourtBookingPaymentConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = CourtBookingPaymentConfig.vnp_PayUrl + "?" + queryUrl;

        CreatePaymentResponseData responseData = new CreatePaymentResponseData();
        responseData.setStatus("Ok");
        responseData.setMessage("Success");
        responseData.setUrl(paymentUrl);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> savePayment(SavePaymentRequestData requestData) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        CourtBookingTable courtBooking = courtBookingRepository.getReferenceById(requestData.getCourtBookingId());
        courtBooking.setStatus(CourtBookingStatus.BOOKED.getValue());
        courtBookingRepository.save(courtBooking);

        CourtBookingPaymentTable courtBookingPayment = new CourtBookingPaymentTable();
        courtBookingPayment.setCourtBooking(courtBooking);
        courtBookingPayment.setTotalMoney(Long.parseLong(requestData.getVnpAmount()) / 100);
        courtBookingPayment.setCreateTimestamp(LocalDateTime.parse(requestData.getVnpPayDate(), formatter));
        courtBookingPayment.setUpdateTimestamp(LocalDateTime.parse(requestData.getVnpPayDate(), formatter));
        courtBookingPayment.setTransactionNo(requestData.getVnpTransactionNo());
        courtBookingPayment.setTxnRef(requestData.getVnpTxnRef());
        courtBookingPayment.setPaymentType(PaymentType.ONLINE.getValue());
        courtBookingPaymentRepository.save(courtBookingPayment);

        SavePaymentResponseData responseData = new SavePaymentResponseData();
        responseData.setCenterId(courtBooking.getCourt().getCenter().getId());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
