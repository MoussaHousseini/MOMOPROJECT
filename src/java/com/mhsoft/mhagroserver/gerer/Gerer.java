/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mhsoft.mhagroserver.gerer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mh
 */
public abstract class Gerer {
    @PersistenceContext
    protected EntityManager em;   
}