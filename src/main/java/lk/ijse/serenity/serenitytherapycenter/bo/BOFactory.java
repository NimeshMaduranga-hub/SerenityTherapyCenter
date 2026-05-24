package lk.ijse.serenity.serenitytherapycenter.bo;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getInstance(){
        if(boFactory == null){
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes{
        PATIENT,
        THERAPIST,
        PROGRAM,
        SESSION,
        REGISTRATION,
        PAYMENT,
        USER
    }

    public SuperBO getBO(BOTypes boType){
        switch (boType){
            case PATIENT      : return new PatientBOImpl();
            case THERAPIST    : return new TherapistBOImpl();
            case PROGRAM      : return new ProgramBOImpl();
            case SESSION      : return new SessionBOImpl();
            case REGISTRATION : return new RegistrationBOImpl();
            case PAYMENT      : return new PaymentBOImpl();
            case USER         : return new UserBOImpl();
            default           : return null;
        }
    }
}
