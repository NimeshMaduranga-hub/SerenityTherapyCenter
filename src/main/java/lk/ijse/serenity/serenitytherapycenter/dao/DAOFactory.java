package lk.ijse.serenity.serenitytherapycenter.dao;

import lk.ijse.serenity.serenitytherapycenter.dao.custom.impl.PatientDaoImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        if(daoFactory == null){
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes{
        PATIENT,
        THERAPIST,
        PROGRAM,
        SESSION,
        REGISTRATION,
        PAYMENT,
        PAYMENT_DETAIL,
        USER,
        QUERY
    }

    public SuperDao getDao(DAOTypes daoTypes){
        switch (daoTypes){
            case PATIENT        : return new PatientDaoImpl();
            case THERAPIST      : return new TherapistDaoImpl();
            case PROGRAM        : return new ProgramDaoImpl();
            case SESSION        : return new SessionDaoImpl();
            case REGISTRATION   : return new RegistrationDAOImpl();
            case PAYMENT        : return new PaymentDaoImpl();
            case PAYMENT_DETAIL : return new PaymentDetailDaoImpl();
            case USER           : return new UserDaoImpl();
            case QUERY          : return new QueryDaoImpl();
            default             : return null;
        }
    }

}
