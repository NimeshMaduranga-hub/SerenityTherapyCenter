package lk.ijse.serenity.serenitytherapycenter.dao.custom.impl;

import lk.ijse.serenity.serenitytherapycenter.config.FactoryConfiguration;
import lk.ijse.serenity.serenitytherapycenter.dao.custom.ProgramDao;
import lk.ijse.serenity.serenitytherapycenter.entity.Program;
import lk.ijse.serenity.serenitytherapycenter.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProgramDaoImpl implements ProgramDao {
    @Override
    public boolean save(Program program) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.save(program);
            transaction.commit();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
        finally {
            session.close();
        }
    }

    @Override
    public boolean update(Program program) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            Program oldProgram = session.get(Program.class, program.getId());
            oldProgram.setName(program.getName());
            oldProgram.setDuration(program.getDuration());
            oldProgram.setFee(program.getFee());

            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean delete(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try{
            Program program = session.get(Program.class, id);
            session.delete(program);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Program> search(String text) {
        List<Program> programs= new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        String hql;
        if(text.startsWith("PR_")){
            String p_id = text.substring(3);
            hql = "FROM Program WHERE id= " + p_id;
            programs = session.createQuery(hql, Program.class).list();
        }
        else {
            hql = "FROM Program WHERE name LIKE :name";
            programs = session.createQuery(hql, Program.class).setParameter("name", text+"%").list();
        }
        session.close();
        return programs;
    }

    @Override
    public List<Program> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Program> programs = session.createQuery("from Program ", Program.class).list();
        session.close();
        return programs;
    }

    @Override
    public String getNextID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Program> programsQuery = session.createQuery("FROM Program ORDER BY id DESC", Program.class).list();
        int lastId ;
        if(programsQuery.isEmpty()){
            lastId = 1;
        }
        else{
            lastId = programsQuery.getFirst().getId()+1;
        }
        session.close();
        return ("PR_" + lastId);
    }

    @Override
    public List<Program> getAllProgramsWithTherapists() {
        Session session = FactoryConfiguration.getInstance().getSession();

        List<Program> programs = session.createQuery("SELECT DISTINCT p FROM Program p LEFT JOIN FETCH p.therapists", Program.class).list();
        session.close();
        return programs;
    }

    @Override
    public List<Therapist> getProgramWithTherapists(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Program program = session.get(Program.class, id);
        List<Therapist> therapists = new ArrayList<>(program.getTherapists());
        session.close();
        return therapists;
    }

    @Override
    public Program getDataByID(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Program p = session.get(Program.class, id);
        session.close();
        return p;
    }
}
