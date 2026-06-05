package lk.ijse.serenity.serenitytherapycenter.bo.impl;

import lk.ijse.serenity.serenitytherapycenter.bo.RegistrationBO;
import lk.ijse.serenity.serenitytherapycenter.dao.DAOFactory;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.RegistrationDao;
import lk.ijse.serenity.serenitytherapycenter.dto.*;
import lk.ijse.serenity.serenitytherapycenter.entity.*;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {

    RegistrationDao registrationDao = (RegistrationDao) DAOFactory.getInstance().getDao(DAOFactory.DAOTypes.REGISTRATION);

    @Override
    public boolean saveRegistration(RegistrationDTO r, PaymentDTO p, PaymentDetailDTO pd) {
        Registration registration = new Registration();
        Sessions sessions = new Sessions();
        Patient patient = new Patient();

        sessions.setId(Integer.parseInt(r.getSessionID().substring(2)));
        registration.setSession(sessions);
        patient.setId(Integer.parseInt(r.getPatientID().substring(2)));
        registration.setPatient(patient);
        registration.setRegisterDate(r.getRegisterDate());

        Payment payment = new Payment();
        payment.setRegistration(registration);
        payment.setTotal(p.getTotal());
        payment.setStatus(p.getStatus());
        payment.setDueAmount(p.getDueAmount());

        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setPayment(payment);
        paymentDetail.setAmount(pd.getAmount());
        paymentDetail.setPayDate(pd.getPayDate());
        paymentDetail.setPayType(pd.getPayType());

        List<PaymentDetail> paymentDetailList = payment.getPaymentDetails();
        paymentDetailList.add(paymentDetail);
        payment.setPaymentDetails(paymentDetailList);

        registration.setPayment(payment);

        return registrationDao.save(registration);

    }

    @Override
    public String getNextId() {
        return "R_" + registrationDao.getNextID();
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        List<Registration> registrations = registrationDao.getAll();
        List<RegistrationDTO> registrationDTOs = new ArrayList<>();

        for(Registration r : registrations){
            RegistrationDTO rDTO = new RegistrationDTO();
            rDTO.setId("R_" + r.getId());
            rDTO.setSessionID("S_" + r.getSession().getId());
            rDTO.setPatientName(r.getPatient().getName());
            rDTO.setProgramName(r.getSession().getProgram().getName());
            rDTO.setTherapistName(r.getSession().getTherapist().getName());
            rDTO.setRegisterDate(r.getRegisterDate());

            registrationDTOs.add(rDTO);
        }

        return registrationDTOs;
    }

    @Override
    public RegistrationDTO getRegistrationByID(int id) {
        Registration r = registrationDao.getRegistrationByID(id);

        TherapistDTO t = new TherapistDTO();
        t.setName(r.getSession().getTherapist().getName());
        t.setContact(r.getSession().getTherapist().getContact());
        t.setEmail(r.getSession().getTherapist().getEmail());

        ProgramDTO p = new ProgramDTO();
        p.setName(r.getSession().getProgram().getName());
        p.setFee(r.getSession().getProgram().getFee());
        p.setDuration(r.getSession().getProgram().getDuration());

        SessionDTO s = new SessionDTO();
        s.setId("S_" + r.getSession().getId());
        s.setDay(r.getSession().getDay());
        s.setTime(r.getSession().getTime());

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setId("R_" + r.getId());
        registrationDTO.setPatientName(r.getPatient().getName());
        registrationDTO.setTherapist(t);
        registrationDTO.setProgram(p);
        registrationDTO.setSession(s);

        return registrationDTO;
    }

    @Override
    public List<RegistrationDTO> searchRegistration(String text, int num) {
        List<Registration> registrations = registrationDao.search(text, num);
        List<RegistrationDTO> registrationDTOs = new ArrayList<>();

        for(Registration r : registrations){
            RegistrationDTO rDTO = new RegistrationDTO();
            rDTO.setId("R_" + r.getId());
            rDTO.setSessionID("S_" + r.getSession().getId());
            rDTO.setPatientName(r.getPatient().getName());
            rDTO.setProgramName(r.getSession().getProgram().getName());
            rDTO.setTherapistName(r.getSession().getTherapist().getName());
            rDTO.setRegisterDate(r.getRegisterDate());

            registrationDTOs.add(rDTO);
        }

        return registrationDTOs;
    }

}
