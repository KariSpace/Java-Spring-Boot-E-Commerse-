/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mit.airport.dao;

/**
 *
 * @author Asus
 */

 
import java.io.IOException;
import java.util.Date;
 
import javax.persistence.NoResultException;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.mit.airport.entity.Ticket;
import com.mit.airport.form.TicketForm;
import com.mit.airport.model.TicketInfo;
import com.mit.airport.pangination.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
 
@Transactional
@Repository
public class TicketDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public Ticket findTicket(String code) {
        try {
            String sql = "Select e from " + Ticket.class.getName() + " e Where e.code =:code ";
 
            Session session = this.sessionFactory.getCurrentSession();
            Query<Ticket> query = session.createQuery(sql, Ticket.class);
            query.setParameter("code", code);
            return (Ticket) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
 
    public TicketInfo findTicketInfo(String code) {
        Ticket ticket = this.findTicket(code);
        if (ticket == null) {
            return null;
        }
        return new TicketInfo(ticket.getCode(), ticket.getName(), ticket.getPrice());
    }
 
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void save(TicketForm ticketForm) {
 
        Session session = this.sessionFactory.getCurrentSession();
        String code = ticketForm.getCode();
 
        Ticket ticket = null;
 
        boolean isNew = false;
        if (code != null) {
            ticket = this.findTicket(code);
        }
        if (ticket == null) {
            isNew = true;
            ticket = new Ticket();
            ticket.setCreateDate(new Date());
        }
        ticket.setCode(code);
        ticket.setName(ticketForm.getName());
        ticket.setPrice(ticketForm.getPrice());
 
        if (ticketForm.getFileData() != null) {
            
        }
        if (isNew) {
            session.persist(ticket);
        }
        // If error in DB, Exceptions will be thrown out immediately
        session.flush();
    }
 
    public PaginationResult<TicketInfo> queryTicket(int page, int maxResult, int maxNavigationPage,
            String likeName) {
        String sql = "Select new " + TicketInfo.class.getName() //
                + "(p.code, p.name, p.price) " + " from "//
                + Ticket.class.getName() + " p ";
        if (likeName != null && likeName.length() > 0) {
            sql += " Where lower(p.name) like :likeName ";
        }
        sql += " order by p.createDate desc ";
        // 
        Session session = this.sessionFactory.getCurrentSession();
        Query<TicketInfo> query = session.createQuery(sql, TicketInfo.class);
 
        if (likeName != null && likeName.length() > 0) {
            query.setParameter("likeName", "%" + likeName.toLowerCase() + "%");
        }
        return new PaginationResult<TicketInfo>(query, page, maxResult, maxNavigationPage);
    }
 
    public PaginationResult<TicketInfo> queryTicket(int page, int maxResult, int maxNavigationPage) {
        return TicketDAO.this.queryTicket(page, maxResult, maxNavigationPage, null);
    }
 
}