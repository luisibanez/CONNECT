/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services. 
 * All rights reserved. 
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 *     * Redistributions of source code must retain the above 
 *       copyright notice, this list of conditions and the following disclaimer. 
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the documentation 
 *       and/or other materials provided with the distribution. 
 *     * Neither the name of the United States Government nor the 
 *       names of its contributors may be used to endorse or promote products 
 *       derived from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package gov.hhs.fha.nhinc.patientdb.dao;

import gov.hhs.fha.nhinc.patientdb.persistence.HibernateUtil;

import gov.hhs.fha.nhinc.patientdb.model.Personname;

import java.util.List;

import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;

import org.hibernate.Session;

import org.hibernate.SessionFactory;

import org.hibernate.Transaction;

import org.hibernate.Criteria;

import org.hibernate.criterion.Expression;

/**
 * 
 * PersonnameDAO Class provides methods to query and update Personname Data to/from MySQL Database using Hibernate
 * 
 * @author richard.ettema
 */

public class PersonnameDAO {

    private static Log log = LogFactory.getLog(PersonnameDAO.class);

    private static PersonnameDAO personnameDAO = new PersonnameDAO();

    /**
     * 
     * Constructor
     */

    private PersonnameDAO() {

        log.info("PersonnameDAO - Initialized");

    }

    /**
     * 
     * Singleton instance returned...
     * 
     * @return PersonnameDAO
     */

    public static PersonnameDAO getPersonnameDAOInstance() {

        log.debug("getPersonnameDAOInstance()..");

        return personnameDAO;

    }

    // =========================

    // Standard CRUD DML Methods

    // =========================

    /**
     * 
     * Create a single <code>Personname</code> record. The generated id
     * 
     * will be available in the personnameRecord.
     * 
     * @param personnameRecord
     * 
     * @return boolean
     */

    public boolean create(Personname personnameRecord) {

        log.debug("PersonnameDAO.create() - Begin");

        Session session = null;

        Transaction tx = null;

        boolean result = true;

        if (personnameRecord != null) {

            try {

                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

                session = sessionFactory.openSession();

                tx = session.beginTransaction();

                log.info("Inserting Record...");

                session.persist(personnameRecord);

                log.info("Personname Inserted seccussfully...");

                tx.commit();

            } catch (Exception e) {

                result = false;

                if (tx != null) {

                    tx.rollback();

                }

                log.error("Exception during insertion caused by :" + e.getMessage(), e);

            } finally {

                // Actual Personname insertion will happen at this step

                if (session != null) {

                    session.close();

                }

            }

        }

        log.debug("PersonnameDAO.create() - End");

        return result;

    }

    /**
     * 
     * Read (Query) the database to get a <code>Personname</code> record based
     * 
     * on a known id.
     * 
     * @param id
     * 
     * @return Personname
     */

    public Personname read(Long id) {

        log.debug("PersonnameDAO.read() - Begin");

        if (id == null) {

            log.info("-- id Parameter is required for Personname Query --");

            log.debug("PersonnameDAO.read() - End");

            return null;

        }

        Session session = null;

        List<Personname> queryList = null;

        Personname foundRecord = null;

        try {

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            session = sessionFactory.openSession();

            log.info("Reading Record...");

            // Build the criteria

            Criteria aCriteria = session.createCriteria(Personname.class);

            aCriteria.add(Expression.eq("id", id));

            queryList = aCriteria.list();

            if (queryList != null && queryList.size() > 0) {

                foundRecord = queryList.get(0);

            }

        } catch (Exception e) {

            log.error("Exception during read occured due to :" + e.getMessage(), e);

        } finally {

            // Flush and close session

            if (session != null) {

                session.flush();

                session.close();

            }

        }

        log.debug("PersonnameDAO.read() - End");

        return foundRecord;

    }

    /**
     * 
     * Update a single <code>Personname</code> record.
     * 
     * @param personnameRecord
     * 
     * @return boolean
     */

    public boolean update(Personname personnameRecord) {

        log.debug("PersonnameDAO.update() - Begin");

        Session session = null;

        Transaction tx = null;

        boolean result = true;

        if (personnameRecord != null) {

            try {

                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

                session = sessionFactory.openSession();

                tx = session.beginTransaction();

                log.info("Updating Record...");

                session.saveOrUpdate(personnameRecord);

                log.info("Personname Updated seccussfully...");

                tx.commit();

            } catch (Exception e) {

                result = false;

                if (tx != null) {

                    tx.rollback();

                }

                log.error("Exception during update caused by :" + e.getMessage(), e);

            } finally {

                // Actual Personname update will happen at this step

                if (session != null) {

                    session.close();

                }

            }

        }

        log.debug("PersonnameDAO.update() - End");

        return result;

    }

    /**
     * 
     * Delete a <code>Personname</code> record from the database
     * 
     * @param personnameRecord
     */

    public void delete(Personname personnameRecord) {

        log.debug("PersonnameDAO.delete() - Begin");

        Session session = null;

        try {

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            session = sessionFactory.openSession();

            log.info("Deleting Record...");

            // Delete the Personname record

            session.delete(personnameRecord);

        } catch (Exception e) {

            log.error("Exception during delete occured due to :" + e.getMessage(), e);

        } finally {

            // Flush and close session

            if (session != null) {

                session.flush();

                session.close();

            }

        }

        log.debug("PersonnameDAO.delete() - End");

    }

    // =========================

    // Find DML Methods

    // =========================

    /**
     * 
     * Read (Query) the database to get all <code>Personname</code> records based
     * 
     * on a known patientId.
     * 
     * @param patientId
     * 
     * @return List<Personname>
     */

    public List<Personname> findPatientPersonnames(Long patientId) {

        log.debug("PersonnameDAO.findPatientPersonnames() - Begin");

        if (patientId == null) {

            log.info("-- patientId Parameter is required for Personname Query --");

            log.debug("PersonnameDAO.findPatientPersonnames() - End");

            return null;

        }

        Session session = null;

        List<Personname> queryList = null;

        try {

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            session = sessionFactory.openSession();

            log.info("Reading Record...");

            // Build the criteria

            Criteria aCriteria = session.createCriteria(Personname.class);

            aCriteria.add(Expression.eq("patient.patientId", patientId));

            queryList = aCriteria.list();

        } catch (Exception e) {

            log.error("Exception during read occured due to :" + e.getMessage(), e);

        } finally {

            // Flush and close session

            if (session != null) {

                session.flush();

                session.close();

            }

        }

        log.debug("PersonnameDAO.findPatientPersonnames() - End");

        return queryList;

    }

}
