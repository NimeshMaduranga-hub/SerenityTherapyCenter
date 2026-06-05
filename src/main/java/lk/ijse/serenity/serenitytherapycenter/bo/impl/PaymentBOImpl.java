package lk.ijse.serenity.serenitytherapycenter.bo.impl;

import lk.ijse.serenity.serenitytherapycenter.bo.PaymentBO;
import lk.ijse.serenity.serenitytherapycenter.dao.DAOFactory;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.PaymentDao;
import lk.ijse.serenity.serenitytherapycenter.dto.PatientDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.PaymentDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.PaymentDetailDTO;
import lk.ijse.serenity.serenitytherapycenter.dto.RegistrationDTO;
import lk.ijse.serenity.serenitytherapycenter.entity.Payment;
import lk.ijse.serenity.serenitytherapycenter.entity.PaymentDetail;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDao paymentDao = (PaymentDao) DAOFactory.getInstance().getDao(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public String getNextID() {
        return "PAY_" + paymentDao.getNextID();
    }

    @Override
    public List<String> getAllPaymentID() {
        List<Payment> payments = paymentDao.getAll();
        List<String> p = new ArrayList<>();
        for(Payment payment : payments){
            p.add("PAY_" + payment.getId());
        }
        return p;
    }

    @Override
    public PaymentDTO getPaymentDataByID(int id) {
        Payment p = paymentDao.getPaymentByID(id);

        List<PaymentDetail> paymentDetails = p.getPaymentDetails();
        List<PaymentDetailDTO> pdList = new ArrayList<>();

        for(PaymentDetail pd : paymentDetails){
            PaymentDetailDTO pdDTO = new PaymentDetailDTO();
            pdDTO.setPayType(pd.getPayType());
            pdDTO.setPayDate(pd.getPayDate());
            pdDTO.setAmount(pd.getAmount());

            pdList.add(pdDTO);
        }

        RegistrationDTO registration = new RegistrationDTO();
        registration.setId("R_" + p.getRegistration().getId());

        PatientDTO patient = new PatientDTO();
        patient.setName(p.getRegistration().getPatient().getName());
        patient.setContact(p.getRegistration().getPatient().getContact());
        patient.setAddress(p.getRegistration().getPatient().getAddress());

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId("PAY_" + p.getId());
        paymentDTO.setTotal(p.getTotal());
        paymentDTO.setPaymentDetails(pdList);
        paymentDTO.setStatus(p.getStatus());
        paymentDTO.setPatient(patient);
        paymentDTO.setRegistration(registration);

        return paymentDTO;
    }

    @Override
    public boolean savePaymentDetail(PaymentDetailDTO pd) {
        PaymentDetail paymentDetail = new PaymentDetail();
        Payment payment = new Payment();
        payment.setId(Integer.parseInt(pd.getPayment().getId().substring(4)));

        paymentDetail.setPayment(payment);
        paymentDetail.setPayDate(pd.getPayDate());
        paymentDetail.setPayType(pd.getPayType());
        paymentDetail.setAmount(pd.getAmount());

        return paymentDao.savePaymentDetail(paymentDetail);
    }

    @Override
    public boolean updatePayment(PaymentDTO p) {
        Payment payment = new Payment();
        PaymentDetail paymentDetail = new PaymentDetail();

        PaymentDetailDTO pd = p.getPaymentDetails().getFirst();

        paymentDetail.setAmount(pd.getAmount());
        paymentDetail.setPayType(pd.getPayType());
        paymentDetail.setPayDate(pd.getPayDate());

        payment.setId(Integer.parseInt(p.getId().substring(4)));
        payment.setDueAmount(p.getDueAmount());
        payment.setStatus(p.getStatus());

        paymentDetail.setPayment(payment);
        payment.getPaymentDetails().addFirst(paymentDetail);

        return paymentDao.update(payment);

    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        List<Payment> payments = paymentDao.getAll();
        List<PaymentDTO> payIDS = new ArrayList<>();
        for(Payment p : payments){
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId("PAY_" + p.getId());
            paymentDTO.setRegistrationID("R_" + p.getRegistration().getId());
            paymentDTO.setSessionID("S_" + p.getRegistration().getSession().getId());
            paymentDTO.setPatientName(p.getRegistration().getPatient().getName());
            paymentDTO.setTotal(p.getTotal());
            paymentDTO.setStatus(p.getStatus());
            payIDS.add(paymentDTO);
        }
        return payIDS;
    }

    @Override
    public List<String> getAllOngoingPaymentID() {
        List<Payment> payments = paymentDao.getAll();
        List<String> payIDS = new ArrayList<>();
        for(Payment p : payments){
            if(p.getStatus().equals("Ongoing")){
                payIDS.add("PAY_" + p.getId());
            }
        }
        return payIDS;
    }

    @Override
    public List<PaymentDTO> searchPayment(String text, int num) {
        List<Payment> payments = paymentDao.search(text, num);
        List<PaymentDTO> payIDS = new ArrayList<>();
        for(Payment p : payments){
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setId("PAY_" + p.getId());
            paymentDTO.setRegistrationID("R_" + p.getRegistration().getId());
            paymentDTO.setSessionID("S_" + p.getRegistration().getSession().getId());
            paymentDTO.setPatientName(p.getRegistration().getPatient().getName());
            paymentDTO.setTotal(p.getTotal());
            paymentDTO.setStatus(p.getStatus());
            payIDS.add(paymentDTO);
        }
        return payIDS;
    }

    @Override
    public void print(String id) {
        int payID = Integer.parseInt(id.substring(4));
        Payment p = paymentDao.getPaymentByID(payID);
        List<PaymentDTO> paymentDTOList = new ArrayList<>();

        List<PaymentDetailDTO> paymentDetailDTOList = new ArrayList<>();
        for(PaymentDetail pd : p.getPaymentDetails()){
            PaymentDetailDTO pdDTO = new PaymentDetailDTO();
            pdDTO.setPayDate(pd.getPayDate());
            pdDTO.setAmount(pd.getAmount());
            pdDTO.setPayType(pd.getPayType());

            paymentDetailDTOList.add(pdDTO);
        }

        PaymentDTO payment = new PaymentDTO();
        payment.setId("PAY_" + p.getId());
        payment.setRegistrationID("R_" + p.getRegistration().getId());
        payment.setSessionID("S_" + p.getRegistration().getSession().getId());
        payment.setPatientID("P_" + p.getRegistration().getPatient().getId());
        payment.setPatientName(p.getRegistration().getPatient().getName());
        payment.setPatientContact(p.getRegistration().getPatient().getContact());
        payment.setPatientAddress(p.getRegistration().getPatient().getAddress());
        payment.setProgramName(p.getRegistration().getSession().getProgram().getName());
        payment.setTotal(p.getTotal());
        payment.setDueAmount(p.getDueAmount());
        payment.setStatus(p.getStatus());
        payment.setPaymentDetails(paymentDetailDTOList);

        paymentDTOList.add(payment);

        try{
            InputStream reportStream = getClass().getResourceAsStream("/reports/sernity_invoice.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(paymentDTOList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            JasperViewer.viewReport(jasperPrint, false);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
